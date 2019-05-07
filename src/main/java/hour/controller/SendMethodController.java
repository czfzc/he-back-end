package hour.controller;

import hour.model.SendMethod;
import hour.repository.SendMethodRepository;
import hour.service.SendMethodService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static hour.util.StringUtil.createStatus;

@RestController
@RequestMapping("/send_method")
@ComponentScan(basePackages = "hour")
public class SendMethodController {

    @Autowired
    SendMethodService sendMethodService;

    @Autowired
    UserService userService;

    @RequestMapping("/get_express_send_method")
    List<SendMethod> getExpressSendMethod(@RequestParam("mysession")String mysession){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return null;
        return sendMethodService.getSendMethodByServiceId("1");
    }
}
