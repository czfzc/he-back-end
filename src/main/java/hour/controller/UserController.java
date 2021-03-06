package hour.controller;

import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static hour.util.StringUtil.createStatus;

@RestController
@RequestMapping("/user")
@ComponentScan(basePackages = "hour")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户凭手机号自动登录
     * @return
     */
    @RequestMapping("/regist")
    String regist(@RequestParam("encrypted_data")String data, @RequestParam("iv")String iv, @RequestParam("code")String code){
        System.out.println(data+" "+iv+" "+code);
        return userService.registWithPhoneNum(data,iv,code);
    }

    /**
     * 普通登录
     */
    @RequestMapping("/login")
    String login(@RequestParam("code")String code){
        System.out.println(code);
        return userService.wxLogin(code);
    }

    /**
     * 用户凭手机号自动登录
     * @return
     */
    @RequestMapping("/set_userinfo")
    String setUserInfo(@RequestParam("encrypted_data")String data, @RequestParam("iv")String iv,
                       @RequestParam("mysession")String mysession){
        System.out.println(data+" "+iv);
        return userService.setUserInfo(data,iv,mysession);
    }

    /**
     判断用户有没有拿手机号注册
     */
    @RequestMapping("/get_regist_status")
    String getRegistStatus(@RequestParam("mysession")String mysession){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return createStatus(false);
        return createStatus(true);
    }

}
