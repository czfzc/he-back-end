package hour.controller;

import hour.model.ExpressPoint;
import hour.repository.ExpressPointRepository;
import hour.service.ExpressPointService;
import hour.service.ExpressService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/express_point")
@ComponentScan(basePackages="hour")
public class ExpressPointController {

    @Autowired
    ExpressPointRepository expressPointRepository;

    @Autowired
    ExpressPointService expressPointService;

    @Autowired
    UserService userService;

    @RequestMapping("/get_point")
    List<ExpressPoint> getPoint(@RequestParam("mysession")String mysession){
        if(userService.getUserId(mysession)==null) return null;
        return expressPointRepository.findAll();
    }

    @RequestMapping("/get_express_point_id_and_code_by_sms_content")
    Map getPointNameAndCodeBySmsContent(@RequestParam("mysession")String mysession,
                                            @RequestParam("sms_content")String sms_content){
        if(userService.getUserId(mysession)==null) return null;
        Map map=new HashMap();
        map.put("express_point_id",expressPointService.getExpressPointIdBySms(sms_content));
        map.put("code",expressPointService.getCodeBySms(sms_content));
        return map;
    }
}
