package hour.controller;

import hour.model.Order;
import hour.model.Refund;
import hour.repository.OrderRepository;
import hour.repository.RefundRepository;
import hour.service.RefundService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

import static hour.util.StringUtil.createStatus;

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

    @Autowired
    RefundService refundService;

    @RequestMapping("/refund_order")
    String refundOrder(@RequestParam("order_id")String order_id,@RequestParam("mysession")String mysession,
                       @RequestParam("reason")String reason){

        String user_id=userService.getUserId(mysession);
        if(user_id==null) return createStatus(false);
        return refundService.userRefundOrder(user_id,order_id,reason);
    }

}
