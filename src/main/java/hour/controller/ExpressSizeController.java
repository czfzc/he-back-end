package hour.controller;

import hour.model.ExpressSize;
import hour.repository.ExpressRepository;
import hour.repository.ExpressSizeRepository;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static hour.util.StringUtil.createStatus;

@RestController
@ComponentScan(basePackages = "hour")
@RequestMapping("/express_size")
public class ExpressSizeController {

    @Autowired
    UserService userService;

    @Autowired
    ExpressSizeRepository expressSizeRepository;

    @RequestMapping("/get_express_size")
    List<ExpressSize> getExpressSize(@RequestParam("mysession")String mysession){
        if(userService.getUserId(mysession)==null) return null;
        return expressSizeRepository.findAll();
    }
}
