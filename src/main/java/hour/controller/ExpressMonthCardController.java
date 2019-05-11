package hour.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hour.model.ExpressMonthCard;
import hour.repository.ExpressMonthCardRepository;
import hour.service.ExpressMonthCardService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static hour.util.StringUtil.createStatus;

@RestController
@ComponentScan(basePackages = "hour")
@RequestMapping("express_month_card")
public class ExpressMonthCardController {

    @Autowired
    ExpressMonthCardService expressMonthCardService;

    @Autowired
    ExpressMonthCardRepository expressMonthCardRepository;

    @Autowired
    UserService userService;

    @RequestMapping("has_card")
    public String hasCard(@RequestParam("mysession")String mysession){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return createStatus(false);
        int days=expressMonthCardService.getRemainsTime(user_id);
        if(days==0){
            return createStatus(false);
        }else{
            return new JSONObject(){
                {
                    this.put("true",false);
                    this.put("remain_days",days);
                }
            }.toJSONString();
        }
    }
}
