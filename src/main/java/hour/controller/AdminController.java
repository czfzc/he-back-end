package hour.controller;

import hour.model.Express;
import hour.model.Order;
import hour.model.Preorder;
import hour.repository.ExpressRepository;
import hour.repository.OrderRepository;
import hour.repository.PreorderRepository;
import hour.service.AdminService;
import hour.service.OrderService;
import hour.service.PreorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import static hour.Util.StringUtil.createStatus;

@RestController
@ComponentScan(basePackages = "hour")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ExpressRepository expressRepository;

    @Autowired
    PreorderRepository preorderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    PreorderService preorderService;


    @RequestMapping("/login")
    String login(@RequestParam("admin_id") String admin_id,@RequestParam("raw_password")String raw_password){
        return adminService.login(admin_id,raw_password);
    }

    @RequestMapping("/regist")
    String regist(@RequestParam("admin_id")String admin_id,@RequestParam("raw_pawword")String raw_password,
                  @RequestParam("name")String name,@RequestParam("sms_code")Integer sms_code){
        return adminService.regist(admin_id,raw_password,sms_code,name);
    }

    @RequestMapping("/send_sms")
    String sendSms(@RequestParam("admin_id")String admin_id){
        return adminService.send(admin_id);
    }

    @RequestMapping("/get_order")
    List<Order> getOrder(@RequestParam("session_key")String session_key){
        if(adminService.getAdminId(session_key)!=null)
            return orderRepository.findAll();
        else return null;
    }

    @RequestMapping("/get_express")
    List<Express> getExpress(@RequestParam("session_key")String session_key){
        String admin_id=adminService.getAdminId(session_key);
        if(admin_id!=null){
            return expressRepository.findAll();
        }return null;
    }

    @RequestMapping("/get_preorder")
    List<Preorder> getPreorder(@RequestParam("session_key")String session_key){
        if(adminService.getAdminId(session_key)!=null)
            return preorderRepository.findAll();
        else return null;
    }

    /**
     * 设置已取到快递
     * @return
     */
    @RequestMapping("/set_status_to_withdraw")
    String setStatusToWithdraw(@RequestParam("session_key")String session_key,@RequestParam("express_id")String express_id){
        String admin_id=adminService.getAdminId(session_key);
        if(admin_id==null)
            return createStatus(false);
        Express express = expressRepository.findFirstByExpressId(express_id);
        if(express==null)
            return createStatus(false);
        express.setStatus(1);
        expressRepository.save(express);
        return createStatus(true);
    }

    /**
     * 设置已送达快递
     * @return
     */
    @RequestMapping("/set_status_to_sended")
    String setStatusToSended(@RequestParam("session_key")String session_key,@RequestParam("express_id")String express_id){
        String admin_id=adminService.getAdminId(session_key);
        if(admin_id==null)
            return createStatus(false);
        Express express = expressRepository.findFirstByExpressId(express_id);
        if(express==null)
            return createStatus(false);
        express.setStatus(2);
        expressRepository.save(express);
        return createStatus(true);
    }

    /**
     * 设置订单不可用
     * @param session_key
     * @param order_id
     * @return
     */

    @RequestMapping("/set_order_to_disabled")
    String setOrderToDisabled(@RequestParam("session_key")String session_key,@RequestParam("order_id")String order_id){
        String admin_id=adminService.getAdminId(session_key);
        if(admin_id==null)
            return createStatus(false);
        Order order=orderRepository.findByOrderId(order_id);
        if(order==null)
            return createStatus(false);
        order.setAbled(false);
        orderRepository.save(order);
        return createStatus(true);
    }

    /**
     * 设置订单可用
     * @param session_key
     * @param order_id
     * @return
     */

    @RequestMapping("/set_order_to_abled")
    String setOrderToAbled(@RequestParam("session_key")String session_key,@RequestParam("order_id")String order_id){
        String admin_id=adminService.getAdminId(session_key);
        if(admin_id==null)
            return createStatus(false);
        Order order=orderRepository.findByOrderId(order_id);
        if(order==null)
            return createStatus(false);
        order.setAbled(true);
        orderRepository.save(order);
        return createStatus(true);
    }

    /**
     * 订单退款
     * @param session_key
     * @param order_id
     * @return
     */

    @RequestMapping("/refund_order")
    String refundOrder(@RequestParam("session_key")String session_key,@RequestParam("order_id")String order_id){
        String admin_id=adminService.getAdminId(session_key);
        if(admin_id==null)
            return createStatus(false);
        Order order=orderRepository.findByOrderId(order_id);
        if(order==null)
            return createStatus(false);
        int i=preorderRepository.findAllByOrderIdAndStatus(order_id,1).size();  //检查总订单里预付单完结的个数
        if(i!=0)
            return createStatus(false);
        //此处写退款函数
        if(!orderService.refundOrder(order_id)){
            return createStatus(false);
        }
        order.setPayed(2);  //payed为2则为退款状态
        orderRepository.save(order);
        return createStatus(true);
    }


    /**
     * 设置预付单完结
     * @param session_key
     * @param preorder_id
     * @return
     */
    @RequestMapping("/set_preorder_to_finished")
    String setPreorderToFinished(@RequestParam("session_key")String session_key,@RequestParam("preorder_id")String preorder_id){
        String admin_id=adminService.getAdminId(session_key);
        if(admin_id==null)
            return createStatus(false);
        Preorder preorder=preorderRepository.findById(preorder_id);
        preorder.setStatus(1);
        return createStatus(true);
    }

}
