package hour.controller;

import hour.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages = "hour")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    String login(@RequestParam("admin_id") String admin_id,@RequestParam("raw_password")String raw_password){
        return adminService.login(admin_id,raw_password);
    }

    @RequestMapping("/regist")
    String regist(@RequestParam("admin_id")String admin_id,@RequestParam("raw_pawword")String raw_password,
                  @RequestParam("name")String name,@RequestParam("sms_code")Integer sms_code){
        return adminService.regist(admin_id,raw_password,sms_code,name);
    }

    @RequestMapping("/send_sms")
    String sendSms(@RequestParam("admin_id")String admin_id){
        return adminService.send(admin_id);
    }

    @RequestMapping("/get_order")
    String getOrder(@RequestParam("session_key")String session_key){

    }
}
