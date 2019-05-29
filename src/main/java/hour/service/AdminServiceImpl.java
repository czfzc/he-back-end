package hour.service;

import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import hour.model.Admin;
import hour.repository.AdminRepository;
import hour.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import static hour.util.CodeUtil.md5;
import static hour.util.StringUtil.createStatus;
import static hour.util.StringUtil.getRandom;

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
        Admin admin = adminRepository.findFirstByAdminIdAndPasswordAndAbledTrue(admin_id, password);
        System.out.println(admin_id+"  "+password);
        if (admin != null&&admin.isAbled()) {
            String session_key = md5(System.currentTimeMillis() + password);
            admin.setSessionKey(session_key);
            admin.setLastLoginTime(new Date());
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
        Admin admin = adminRepository.findFirstByAdminIdAndSmsCodeAndAbledTrue(admin_id, sms_code);
        if (admin == null)
            return createStatus(false);
        else {
            String password = md5(admin_id + raw_password);
            String session_key = md5(System.currentTimeMillis() + password);
            admin.setPassword(password);
            admin.setName(name);
            admin.setSessionKey(session_key);
            admin.setLastLoginTime(new Date());
            admin.setAbled(true);
            return new JSONObject() {
                {
                    this.put("status", true);
                    this.put("session_key", session_key);
                }
            }.toJSONString();
        }

    }

    @Value("${admin.session-expire-min}")
    Integer expireMin;

    /**
     * 验证session_id
     */
    @Override
    public boolean validateSession(String session_key){
        Admin admin= adminRepository.findFirstBySessionKeyAndAbledTrue(session_key);
        if(admin==null) return false;
        Date lastLoginTime=admin.getLastLoginTime();
        if(lastLoginTime==null) return false;
        if(TimeUtil.getTimeDiffMin(new Date(),lastLoginTime)>expireMin) return false;
        admin.setLastLoginTime(new Date());
        adminRepository.save(admin);
        return true;
    }

    /**
     * 发送短信验证码（预注册）服务
     */
  /*  @Override
    public String send(String admin_id) {
        int code = sendCode(admin_id);
        Admin admin = new Admin();
        admin.setAdminId(admin_id);
        admin.setSmsCode(code);
        admin.setAbled(false);
        return createStatus(true);
    }*/

    @Override
    public String getAdminId(String session_key) {
        Admin admin=adminRepository.findFirstBySessionKeyAndAbledTrue(session_key);
        if(admin!=null)
            return admin.getAdminId();
        throw new RuntimeException("invalid session_key");
    }

}
