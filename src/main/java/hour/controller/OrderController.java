package hour.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.MoreProduct;
import hour.util.NetUtil;
import hour.util.StringUtil;
import hour.model.Order;
import hour.repository.OrderRepository;
import hour.service.OrderService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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
    @ResponseBody
    @RequestMapping("/unified_order")
    String unifiedOrder(@RequestBody JSONObject data,HttpServletRequest httpServletRequest){

        System.out.println(data.toJSONString());

        String mysession=data.getString("mysession");
        JSONArray preorders=data.getJSONArray("preorders");
        String ip= NetUtil.getIpAddr(httpServletRequest);

        return orderService.payOrder(ip, mysession, preorders);
    }

    @RequestMapping("/repay_order")
    String repayOrder(@RequestParam("mysession")String mysession,@RequestParam("order_id")String order_id){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return createStatus(false);
        return orderService.repayOrder(user_id,order_id);
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

    @RequestMapping("/cancel_order")
    String cancalOrder(@RequestParam("order_id")String order_id,@RequestParam("mysession")String mysession){
        return this.deleteOrder(order_id, mysession);
    }

    @RequestMapping("/get_order")
    Page<Order> testOrder(@RequestParam("mysession")String mysession,
                          @RequestParam("page")Integer page, @RequestParam("size")Integer size){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) throw new RuntimeException("invalid mysesion");
        return orderService.getOrderByUserId(user_id,page,size);
    }

    @RequestMapping("/get_order_by_id")
    Order testOrder(@RequestParam("mysession")String mysession,
                          @RequestParam("order_id")String order_id){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) throw new RuntimeException("invalid mysesion");
        Order order = orderService.getOrderById(order_id);
        if(!user_id.equals(order.getUserId())) throw new RuntimeException("invalid orderid");
        return order;
    }

    @Value("${shop.extra-fee}")
    Double extraFee = 1D;

    @ResponseBody
    @RequestMapping("/get_total")
    JSONObject getTotal(@RequestBody JSONObject data, HttpServletRequest httpServletRequest){
        String mysession=data.getString("mysession");

        String user_id=userService.getUserId(mysession);
        if(user_id==null) throw new RuntimeException("invalid mysesion");

        return orderService.getTotal(data);
    }
}
