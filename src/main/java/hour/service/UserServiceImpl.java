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

    @Autowired
    WexinTokenService wexinTokenService;

    @Value("${wexin.appid}")
    private String appid;
    @Value("${wexin.key}")
    private String secret;

    @Value("${gongzhonghao.appid}")
    String gongzhonghaoAppid;

    @Value("${gongzhonghao.key}")
    String gongzhonghaoKey;

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
                            this.put("has_info",user.getUnionId()!=null);
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

        System.out.println("hahahah");

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

                System.out.println("decrypted:\n"+str);

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
                            this.put("has_info",user.getUnionId()!=null);
                            this.put("status",mysession.equals(u.getMysession()));
                        }
                    }.toJSONString();
                }else return createStatus(false);
            }else{
                return createStatus(false);     //用户已注册
            }
        }


    }

    /**微信公众号关注时调用
     *<xml>
     *   <ToUserName><![CDATA[toUser]]></ToUserName>
     *   <FromUserName><![CDATA[FromUser]]></FromUserName>
     *   <CreateTime>123456789</CreateTime>
     *   <MsgType><![CDATA[event]]></MsgType>
     *   <Event><![CDATA[subscribe]]></Event>
     * </xml>
     *
     * {
     * "subscribe": 1,
     * "openid": "oq0CD1rxkyMUSR_lS5JklPSPYPQc",
     * "nickname": "Anonymous",
     * "sex": 1,
     * "language": "zh_CN",
     * "city": "西安",
     * "province": "陕西",
     * "country": "中国",
     * "headimgurl": "http://thirdwx.qlogo.cn/mmopen/k9riaLOS1S9nlXkeXVTtf7ZpQQ6KlyxY3tQIeWID0qpJZzX8FaHqMibBShQlmY7YVIWurEx0ZPEicKiaVlibcmwWskicCCJmSPmoMv/132",
     * "subscribe_time": 1559047305,
     * "unionid": "o3nMJ1jGLU8vlPawSp4ZQR_sd5kU",
     * "remark": "",
     * "groupid": 2,
     * "tagid_list": [
     * 2
     * ],
     * "subscribe_scene": "ADD_SCENE_QR_CODE",
     * "qr_scene": 0,
     * "qr_scene_str": ""
     * }
     */

    @Override
    public boolean gzhRegister(String gzh_open_id){
        JSONObject info=wexinTokenService.getInfoByOpenid(gzh_open_id,gongzhonghaoAppid,gongzhonghaoKey);

        String unionid=info.getString("unionid");

        System.out.println("unionid=="+unionid);

        if(unionid==null) return false;

        User user=userRepository.findByUnionId(unionid);
        if(user==null){
            user=new User();
            user.setUnionId(unionid);
            user.setGzhOpenId(gzh_open_id);

            user.setNickname(info.getString("nickname"));
            user.setSex(info.getInteger("sex"));
            user.setLanguage(info.getString("language"));
            user.setCity(info.getString("city"));
            user.setProvince(info.getString("province"));
            user.setCountry(info.getString("country"));
            user.setHeadimgurl(info.getString("headimgurl"));
            user.setAbled(true);

        }else{
            user.setGzhOpenId(gzh_open_id);

            user.setNickname(info.getString("nickname"));
            user.setSex(info.getInteger("sex"));
            user.setLanguage(info.getString("language"));
            user.setCity(info.getString("city"));
            user.setProvince(info.getString("province"));
            user.setCountry(info.getString("country"));
            user.setHeadimgurl(info.getString("headimgurl"));

        }
        return userRepository.save(user).getGzhOpenId()!=null;
    }

    @Override
    public boolean gzhCheckRegisted(String gzh_open_id){
        return userRepository.findByGzhOpenId(gzh_open_id)!=null;
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
        String unionid=js.getString("unionid");
        String session_key=js.getString("session_key");
        Integer errcode=js.getInteger("errcode");

        if(errcode==null||errcode==0){
            User user=userRepository.findByOpenId(openid);
            if(user==null&&unionid!=null) user=userRepository.findByUnionId(unionid);
            if(user==null) {  //当此用户不存在 从未登陆过
                user=new User();
                user.setOpenId(openid);
                user.setSessionId(session_key);
                user.setUnionId(unionid);
                user.setAbled(true);
                return userRepository.save(user);
            }else{              //当此用户存在 以前登陆过
                String mysession = md5(openid + session_key);
                user.setMysession(mysession);
                user.setOpenId(openid);
                user.setSessionId(session_key);
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

    /**
     * 获取用户信息
     * {
     *   "openId": "OPENID",
     *   "nickName": "NICKNAME",
     *   "gender": GENDER,
     *   "city": "CITY",
     *   "province": "PROVINCE",
     *   "country": "COUNTRY",
     *   "avatarUrl": "AVATARURL",
     *   "unionId": "UNIONID",
     *   "watermark": {
     *     "appid":"APPID",
     *     "timestamp":TIMESTAMP
     *   }
     * }
     */

    @Override
    public String setUserInfo(String encryptedData, String iv,String mysession) {

        User user=userRepository.findByMysessionAndAbledTrue(mysession);

        String session_key=user.getSessionId();

        String str=CodeUtil.decrypt(encryptedData,session_key,iv);

        System.out.println("decrypted:\n"+str);

        JSONObject json=JSONObject.parseObject(str);

        String openId=json.getString("openId");
        String unionId=json.getString("unionId");
        String appid=json.getJSONObject("watermark").getString("appid");

        if(openId==null||unionId==null) return createStatus(false);

        if(!openId.equalsIgnoreCase(user.getOpenId())) return createStatus(false);

        if(session_key==null) return createStatus(false);

        if(this.appid.equalsIgnoreCase(appid)){  //获取成功

            User user2=userRepository.findByUnionId(unionId);

            if(user2==null) {

                user.setUnionId(unionId);

                user.setNickname(json.getString("nickName"));
                user.setLanguage(json.getString("language"));
                user.setCity(json.getString("city"));
                user.setProvince(json.getString("province"));
                user.setCountry(json.getString("country"));
                user.setHeadimgurl(json.getString("avatarUrl"));

                if (userRepository.save(user).getUnionId() != null) {
                    return createStatus(true);
                }
            }else{
                user2.setUserId(user.getUserId());
                user2.setLastLoginTime(new Date());
                user2.setOpenId(user.getOpenId());
                user2.setMysession(user.getMysession());
                user2.setSessionId(user.getSessionId());
                user2.setNickname(json.getString("nickName"));
                user2.setLanguage(json.getString("language"));
                user2.setCity(json.getString("city"));
                user2.setProvince(json.getString("province"));
                user2.setCountry(json.getString("country"));
                user2.setHeadimgurl(json.getString("avatarUrl"));

                userRepository.delete(user);
                if (userRepository.save(user2).getUnionId() != null) {
                    return createStatus(true);
                }
            }

        }

        return createStatus(false);
    }


}
