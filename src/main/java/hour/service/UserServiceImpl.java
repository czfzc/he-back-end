package hour.service;

import com.alibaba.fastjson.JSONObject;
import hour.util.CodeUtil;
import hour.util.NetUtil;
import hour.model.User;
import hour.repository.UserRepository;
import hour.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import static hour.util.CodeUtil.md5;
import static hour.util.StringUtil.createStatus;

@Service("UserService")
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Value("${wexin.appid}")
    private String appid;
    @Value("${wexin.key}")
    private String secret;

    /**
     * 微信登录服务调用函数
     * @param code 微信传过来的登录码
     * @return
     */
    @Override
    public String wxLogin(String code) {

        User user=this.login(code);
        if(user.isAbled()) {
            if (user == null) {         //未注册
                return createStatus(false);
            } else {
                if (user.getUserId() == null) {
                    return new JSONObject() {
                        {
                            this.put("status", true);
                            this.put("registed", false);
                        }
                    }.toJSONString();
                } else {
                    return new JSONObject() {
                        {
                            this.put("status", true);
                            this.put("mysession", user.getMysession());
                            this.put("registed", true);
                            this.put("user_id", user.getUserId());
                        }
                    }.toJSONString();
                }
            }
        }else return createStatus(false);
    }

    /**
     * mysession为md5(openid+session_key);
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     * @param code 登录code 不能和注册之前登录的code一样
     * @return
     */
    @Override
    public String registWithPhoneNum(String encryptedData, String iv, String code) {

        User user=this.login(code);

        if(user==null||!user.isAbled()) {                          //注册之前未调用登录接口 所以失败
            return createStatus(false);
        }else{
            if(user.getUserId()==null){            //不完整User对象 表明未注册 这里开始注册
                String session_key=user.getSessionId();
                String openid=user.getOpenId();
                String mysession=md5(openid+session_key);

                if(session_key==null||openid==null) return createStatus(false);

                String str=CodeUtil.decrypt(encryptedData,session_key,iv);

                System.out.println(str);

                JSONObject json=JSONObject.parseObject(str);
                String phoneNum=json.getString("purePhoneNumber");
                String appid=json.getJSONObject("watermark").getString("appid");

                if(this.appid.equalsIgnoreCase(appid)){  //注册成功
                    user.setUserId(phoneNum);
                    user.setLastLoginTime(new Date());
                    user.setMysession(mysession);
                    user.setSessionId(session_key);
                    final User u=userRepository.save(user);
                    return new JSONObject(){
                        {
                            this.put("mysession",mysession);
                            this.put("status",mysession.equals(u.getMysession()));
                        }
                    }.toJSONString();
                }else return createStatus(false);
            }else{
                return createStatus(false);     //用户已注册
            }
        }


    }

    /**
     * 登录微信小程序 如果未注册 不完整属性的User对象 如果请求失败则返回null
     * @param code
     * @return
     */

    private User login(String code){

        String str= NetUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session" ,
                "appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code");
        System.out.println(str);
        JSONObject js=JSONObject.parseObject(str);
        String openid=js.getString("openid");
        String session_key=js.getString("session_key");
        Integer errcode=js.getInteger("errcode");

        if(errcode==null||errcode==0){
            User user=userRepository.findByOpenId(openid);
            if(user==null) {  //当此用户不存在 从未登陆过
                user=new User();
                user.setOpenId(openid);
                user.setSessionId(session_key);
                user.setAbled(true);
                return userRepository.save(user);
            }else{              //当此用户存在 以前登陆过
                String mysession = md5(openid + session_key);
                user.setMysession(mysession);
                return userRepository.save(user);
            }
        }else{
            return null;
        }
    }

    @Value("${user.session-expire-min}")
    Integer expire;

    @Override
    public String getUserId(String mysession) {
        User user=userRepository.findByMysessionAndAbledTrue(mysession);
        if(user==null){
            throw new RuntimeException("mysession is invalid");
        };
     //   Date lastLoginTime=user.getLastLoginTime();
    //    if(lastLoginTime==null) return null;
     //   System.out.println(TimeUtil.getTimeDiffMin(new Date(),lastLoginTime));
     //   if(TimeUtil.getTimeDiffMin(new Date(),lastLoginTime)>expire)
      //      return null;
        return user.getUserId();
    }


    @Override
    public User getUser(String mysession) {
        User user=userRepository.findByMysessionAndAbledTrue(mysession);
        if(user==null){
            throw new RuntimeException("mysession is invalid");
        };

        //   Date lastLoginTime=user.getLastLoginTime();
        //    if(lastLoginTime==null) return null;
        //   System.out.println(TimeUtil.getTimeDiffMin(new Date(),lastLoginTime));
        //   if(TimeUtil.getTimeDiffMin(new Date(),lastLoginTime)>expire)
        //      return null;
        return user;
    }


}
