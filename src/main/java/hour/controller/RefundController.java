package hour.controller;

import hour.model.Order;
import hour.model.Refund;
import hour.repository.OrderRepository;
import hour.repository.RefundRepository;
import hour.service.OrderService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

import static hour.Util.StringUtil.createStatus;

@RestController
@ComponentScan(basePackages = "hour")
@RequestMapping("/refund")
public class RefundController {

    @Autowired
    UserService userService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RefundRepository refundRepository;

    @RequestMapping("/refund_order")
    String refundOrder(@RequestParam("order_id")String order_id,@RequestParam("mysession")String mysession,
                       @RequestParam("reason")String reason){

        String user_id=userService.getUserId(mysession);
        if(user_id==null) return createStatus(false);
        Order order=orderRepository.findByOrderId(order_id);
        if(order==null) return  createStatus(false);
        if(!user_id.equals(order.getUserId())) return createStatus(false);
        if(order.getPayed()!=1) return createStatus(false);

        Refund refund=new Refund();
        String refund_id= UUID.randomUUID().toString().replace("-","");
        refund.setRefundId(refund_id);
        refund.setOrderId(order_id);
        refund.setUserId(user_id);
        refund.setTime(new Date());
        refund.setReason(reason);
        refund.setRefused(false);
        refund.setAbled(true);
        refund.setSucceed(false);

        order.setPayed(3);     //order的payed字段： 3是等待退款 2是退款成功 1是已付款 0是未付款

        refundRepository.save(refund);

        //此处应该通知管理员处理

        return createStatus(true);
    }

}
