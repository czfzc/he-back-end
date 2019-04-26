package hour.controller;

import hour.model.SendMethod;
import hour.repository.SendMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/send_method")
@ComponentScan(basePackages = "hour")
public class SendMethodController {

    @Autowired
    SendMethodRepository sendMethodRepository;

    @RequestMapping("/get_send_method")
    List<SendMethod> getSendMethod(){
        return sendMethodRepository.findAll();
    }
}
