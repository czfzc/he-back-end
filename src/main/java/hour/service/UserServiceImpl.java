package hour.service;

import com.alibaba.fastjson.JSONObject;
import hour.Util.CodeUtil;
import hour.Util.NetUtil;
import hour.model.User;
import hour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static hour.Util.CodeUtil.md5;
import static hour.Util.StringUtil.createStatus;

@Service("UserService")
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    private String session_key;
    private String openid;

    private String appid="appid";
    private String secret="secret";

    private User user;

    @Override
    public String wxLogin(String code) {

        String str= NetUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session" ,
                "appid="+appid+"&secret="+secret+"&js_code=JSCODE&grant_type=authorization_code");
        JSONObject js=JSONObject.parseObject(str);
        String openid=js.getString("openid");
        String session_key=js.getString("session_key");
        Integer errcode=js.getInteger("errcode");

        if(errcode==0){
            user=userRepository.findByOpenId(openid);
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
        return userRepository.findByMysession(mysession).getUserId();
    }


}
