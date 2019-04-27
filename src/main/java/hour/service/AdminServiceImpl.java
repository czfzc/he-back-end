package hour.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import hour.model.Admin;
import hour.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static hour.Util.CodeUtil.md5;
import static hour.Util.StringUtil.createStatus;
import static hour.Util.StringUtil.getRandom;

@Service("AdminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    /**
     * 登录方法
     * password=(admin_id+raw_password)
     *
     * @param admin_id
     * @param raw_password
     * @return
     */
    @Override
    public String login(String admin_id, String raw_password) {
        String password = md5(admin_id + raw_password);
        Admin admin = adminRepository.findFirstByAdminIdAndPassword(admin_id, password);
        if (admin != null&&admin.isAbled()) {
            String session_key = md5(System.currentTimeMillis() + password);
            admin.setSessionKey(session_key);
            adminRepository.save(admin);
            return new JSONObject() {
                {
                    this.put("status", true);
                    this.put("session_key", session_key);
                }
            }.toJSONString();
        } else return createStatus(false);
    }

    /**
     * 注册方法
     */
    @Override
    public String regist(String admin_id, String raw_password, Integer sms_code, String name) {
        Admin admin = adminRepository.findFirstByAdminIdAndSmsCode(admin_id, sms_code);
        if (admin == null)
            return createStatus(false);
        else {
            String password = md5(admin_id + raw_password);
            String session_key = md5(System.currentTimeMillis() + password);
            admin.setPassword(password);
            admin.setName(name);
            admin.setSessionKey(session_key);
            admin.setAbled(true);
            return new JSONObject() {
                {
                    this.put("status", true);
                    this.put("session_key", session_key);
                }
            }.toJSONString();
        }

    }

    /**
     * 发送短信验证码（预注册）服务
     */
    @Override
    public String send(String admin_id) {
        int code = sendCode(admin_id);
        Admin admin = new Admin();
        admin.setAdminId(admin_id);
        admin.setSmsCode(code);
        admin.setAbled(false);
        return createStatus(true);
    }

    @Override
    public String getAdminId(String session_key) {
        Admin admin=adminRepository.findFirstBySessionKey(session_key);
        if(admin!=null&&admin.isAbled())
            return admin.getAdminId();
        return null;
    }

    @Value("${tx.appid}")
    private int appid;
    @Value("${tx.appkey}")
    private String appkey;
    @Value("${tx.template.adminregist}")
    private int templateId;
    @Value("${tx.template.name}")
    private String smsSign;

    /**
     * 发送短信验证码方法
     * <p>
     * 需要设置短信模板
     */
    private int sendCode(String admin_id) {
        // 短信应用SDK AppID

        // 短信应用SDK AppKey

        // 需要发送短信的手机号码
        String[] phoneNumbers = {admin_id};

        // 短信模板ID，需要在短信应用中申请
             // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
        //templateId7839对应的内容是"您的验证码是: {1}"
        // 签名
       // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
        String res = null;
        String code = getRandom(6);
        try {
            String[] params = {code};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            res = ssender.sendWithParam("86", phoneNumbers[0],
                    templateId, params, smsSign, "", "").toString();  // 签名参数未提供或者为空时，会使用默认签名发送短信
        } catch (Exception e) {
            // HTTP响应码错误
            e.printStackTrace();
        }
        return Integer.valueOf(code);
    }


}
