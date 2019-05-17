package hour.service;

import com.alibaba.fastjson.JSONObject;
import hour.util.CodeUtil;
import hour.util.NetUtil;
import hour.model.User;
import hour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static hour.util.CodeUtil.md5;
import static hour.util.StringUtil.createStatus;

@Service("UserService")
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    private String session_key;
    private String openid;

    @Value("${wexin.appid}")
    private String appid;
    @Value("${wexin.key}")
    private String secret;

    private User user;

    @Override
    public String wxLogin(String code) {

        String str= NetUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session" ,
                "appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code");
        System.out.println(str);
        JSONObject js=JSONObject.parseObject(str);
        String openid=js.getString("openid");
        String session_key=js.getString("session_key");
        Integer errcode=js.getInteger("errcode");

        if(errcode==null||errcode==0){
            user=userRepository.findByOpenIdAndAbledTrue(openid);
            if(user==null)  //当此用户不存在
                user=new User();
            String mysession=md5(openid+session_key);
            user.setMysession(mysession);
            user.setOpenId(openid);
            user.setSessionId(session_key);
            this.session_key=session_key;
            this.openid=openid;
            userRepository.save(user);
            return new JSONObject(){
                {
                    this.put("mysession",user.getMysession());
                    this.put("status",true);
                    this.put("registed",user.getUserId()!=null);
                    if(user.getUserId()!=null)
                        this.put("user_id",user.getUserId());
                }
            }.toJSONString();
        }else return createStatus(false);
    }

    /**
     * mysession为md5(openid+session_key);
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     * @param code
     * @return
     */
    @Override
    public String registWithPhoneNum(String encryptedData, String iv, String code) {

        this.wxLogin(code);

        System.out.println(session_key);

        if(session_key==null) return createStatus(false);

        String str=CodeUtil.decrypt(encryptedData,session_key,iv);

        JSONObject json=JSONObject.parseObject(str);
        String phoneNum=json.getString("purePhoneNumber");
        String appid=json.getJSONObject("watermark").getString("appid");

        if(this.appid.equalsIgnoreCase(appid)){
            user.setUserId(phoneNum);
            userRepository.save(user);
            return new JSONObject(){
                {
                    this.put("mysession",user.getMysession());
                    this.put("status",true);
                }
            }.toJSONString();
        }else return createStatus(false);
    }

    @Override
    public String getUserId(String mysession) {
        User user=userRepository.findByMysessionAndAbledTrue(mysession);
        if(user==null) return null;
        return user.getUserId();
    }


}
