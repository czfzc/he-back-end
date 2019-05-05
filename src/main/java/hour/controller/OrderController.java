package hour.controller;

import com.alibaba.fastjson.JSONArray;
import hour.util.NetUtil;
import hour.util.StringUtil;
import hour.model.Order;
import hour.repository.OrderRepository;
import hour.service.OrderService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static hour.util.StringUtil.createStatus;

@RestController
@RequestMapping("/order")
@ComponentScan(basePackages = "hour")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

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

    @RequestMapping("/delete_order")
    String deleteOrder(@RequestParam("order_id")String order_id,@RequestParam("mysession")String mysession){
        //条件效验
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return createStatus(false);
        Order order=orderRepository.findByOrderId(order_id);
        if(order==null) return createStatus(false);
        if(!order.isAbled()) return createStatus(false);
        if(order.getPayed()==3||order.getPayed()==1) return  createStatus(false);
        if(!order.getUserId().equals(user_id)) return createStatus(false);

        order.setAbled(false);

        orderRepository.save(order);

        return createStatus(true);

    }

    @RequestMapping("/get_order")
    List<Order> testOrder(@RequestParam("mysession")String mysession,
                          @RequestParam("page")Integer page,@RequestParam("size")Integer size){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return null;
        return orderService.getOrder(page,size);
    }

}
