package hour.controller;

import com.alibaba.fastjson.JSONArray;
import hour.Util.NetUtil;
import hour.Util.StringUtil;
import hour.model.Order;
import hour.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/order")
@ComponentScan(basePackages = "hour")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 统一下单
     * @return
     */

    @RequestMapping("/unified_order")
    String unifiedOrder(@RequestParam("mysession")String mysession,
                        @RequestParam("preorders")String preorders, HttpServletRequest request){
        String ip= NetUtil.getIpAddr(request);
        return orderService.payOrder(ip, mysession, JSONArray.parseArray(preorders));
    }

    @RequestMapping("/on_finish_pay")
    String finishPay(HttpServletRequest request){
        String xml= StringUtil.getRawContent(request);
        if (orderService.onFinishPayed(xml)) {
            return ("<xml>\n" +
                    "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "<return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>\n");
        } else return ("<xml>\n" +
                "<return_code><![CDATA[FAILURE]]></return_code>\n" +
                "<return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>\n");
    }

    @RequestMapping("get_order")
    List<Order> getOrder(@RequestParam("mysession")String mysession){
        return orderService.getOrder(mysession);
    }

}
