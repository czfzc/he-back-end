package hour.controller;

import com.alibaba.fastjson.JSONObject;
import hour.model.SendMethod;
import hour.repository.SendMethodRepository;
import hour.service.SendMethodService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 根据业务类型选择送货方式 默认为快递代取（service_id == 1）
     * @param mysession
     * @param service_id
     * @return
     */

    @RequestMapping("/get_express_send_method")
    List<SendMethod> getExpressSendMethod(@RequestParam("mysession")String mysession,
                        @RequestParam("service_id")@Nullable Integer service_id){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return null;
        if(service_id == null)
            return sendMethodService.getSendMethodByServiceId(1);
        else return sendMethodService.getSendMethodByServiceId(service_id);
    }

    @RequestMapping("/get_product_send_method")
    List<SendMethod> getProductSendMethod(@RequestBody JSONObject json){
        String user_id=userService.getUserId(json.getString("mysession"));
        Integer service_id = json.getInteger("service_id");
        if(user_id==null) return null;
        if(service_id == null)
            return sendMethodService.getSendMethodByServiceId(1);
        else return sendMethodService.getSendMethodByServiceId(service_id);
    }
}
