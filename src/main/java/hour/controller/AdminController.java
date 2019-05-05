package hour.controller;

import com.alibaba.fastjson.JSONObject;
import hour.model.Express;
import hour.model.Order;
import hour.model.Preorder;
import hour.model.Refund;
import hour.repository.ExpressRepository;
import hour.repository.OrderRepository;
import hour.repository.PreorderRepository;
import hour.repository.RefundRepository;
import hour.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

import static hour.util.StringUtil.createStatus;

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

    @Autowired
    RefundRepository refundRepository;

    @Autowired
    ExpressService expressService;

    @Autowired
    RefundService refundService;

    /**
     * 登录
     * @param admin_id
     * @param raw_password
     * @return
     */

    @RequestMapping("/login")
    String login(@RequestParam("admin_id") String admin_id,@RequestParam("raw_password")String raw_password){
        return adminService.login(admin_id,raw_password);
    }

    /**
     * 注册
     * @param admin_id
     * @param raw_password
     * @param name
     * @param sms_code
     * @return
     */

    @RequestMapping("/regist")
    String regist(@RequestParam("admin_id")String admin_id,@RequestParam("raw_pawword")String raw_password,
                  @RequestParam("name")String name,@RequestParam("sms_code")Integer sms_code){
        return adminService.regist(admin_id,raw_password,sms_code,name);
    }

    /**
     * 验证session_key是否可用
     */
    @RequestMapping("/validate")
    String validate(@RequestParam("session_key")String session_key){
        return createStatus(adminService.validateSession(session_key));
    }

    /**
     * 发送短信并预注册
     * @param admin_id
     * @return
     */

    @RequestMapping("/send_sms")
    String sendSms(@RequestParam("admin_id")String admin_id){
        return adminService.send(admin_id);
    }

    /**
     * 获取总订单
     * @param session_key
     * @param page
     * @param size
     * @return
     */

    @RequestMapping("/get_order")
    List<Order> getOrder(@RequestParam("session_key")String session_key,
                         @RequestParam("page")Integer page,@RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return orderService.getOrder(page,size);
    }

    /**
     * 获取总订单数
     */
    @RequestMapping("/get_total_order")
    String getTotalOrder(@RequestParam("session_key")String session_key){
        return new JSONObject(){
            {
                this.put("status",true);
                this.put("total",orderService.getCount());
            }
        }.toJSONString();
    }

    /**
     * 获取总订单数
     */
    @RequestMapping("/get_total_express_preorder")
    String getTotalExpressPreOrder(@RequestParam("session_key")String session_key){
        if(adminService.getAdminId(session_key)==null) return createStatus(false);
        return new JSONObject(){
            {
                this.put("status",true);
                this.put("total",preorderService.getExpressCount());
            }
        }.toJSONString();
    }

    /**
     * 获取总代取快递数
     */
    @RequestMapping("/get_total_express")
    String getTotalExpress(@RequestParam("session_key")String session_key){
        if(adminService.getAdminId(session_key)==null) return createStatus(false);
        return new JSONObject(){
            {
                this.put("status",true);
                this.put("total",expressService.getCount());
            }
        }.toJSONString();
    }

    /**
     * 获取快递单
     * @param session_key
     * @param page
     * @param size
     * @return
     */

    @RequestMapping("/get_express")
    List<Express> getExpress(@RequestParam("session_key")String session_key,
                             @RequestParam("page")Integer page,@RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return expressService.getAllExpress(page, size);
    }

    /**
     * 获取预付单
     * @param session_key
     * @param page
     * @param size
     * @return
     */

    @RequestMapping("/get_preorder")
    List<Preorder> getPreorder(@RequestParam("session_key")String session_key,
                               @RequestParam("page")Integer page,@RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return preorderService.getAllPreorder(page,size);
    }

    /**
     * 获取预付单
     * @param session_key
     * @param page
     * @param size
     * @return
     */

    @RequestMapping("/get_express_preorder")
    List<Preorder> getExpressPreorder(@RequestParam("session_key")String session_key,
                               @RequestParam("page")Integer page,@RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return preorderService.getExpressPreorder(page,size);
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

    /**
     * 拒绝订单
     */
    @RequestMapping("/refuse_order")
    String refuseOrder(@RequestParam("session_key")String session_key,@RequestParam("order_id")String order_id){
        String admin_id=adminService.getAdminId(session_key);
        if(admin_id==null)
            return createStatus(false);
        Order order=orderRepository.findByOrderId(order_id);
        //加入事务
        //应在此给用户发送推送提醒退款到账
        order.setAbled(false);
        orderRepository.save(order);
        return createStatus(orderService.refundOrder(order_id));
    }

    /**
     * 查看退款列表
     */
    @RequestMapping("/get_refund")
    List<Refund> getRefund(@RequestParam("session_key")String session_key,
        @RequestParam("page")Integer page, @RequestParam("size")Integer size){
        String admin_id=adminService.getAdminId(session_key);
        if(admin_id==null)
            return null;
        return refundService.getRefund(page,size);
    }

    /**
     * 获取总退款数
     */
    @RequestMapping("/get_total_refund")
    String getTotalRefund(@RequestParam("session_key")String session_key){
        if(adminService.getAdminId(session_key)==null) return createStatus(false);
        return new JSONObject(){
            {
                this.put("status",true);
                this.put("total",refundService.getCount());
            }
        }.toJSONString();
    }

    /**
     * 拒绝退款
     */
    @RequestMapping("/refuse_refund")
    String refuseRefund(@RequestParam("refund_id")String refund_id,@RequestParam("session_key")String session_key){
        String admin_id=adminService.getAdminId(session_key);
        //控制无效条件
        if(admin_id==null) return createStatus(false);
        Refund refund=refundRepository.findByRefundId(refund_id);
        if(refund==null) return createStatus(false);
        if(refund.isSucceed()) return createStatus(false);
        if(!refund.isAbled()) return createStatus(false);
        if(refund.isRefused()) return createStatus(false);

        refund.setSucceed(false);
        refund.setRefused(true);

        refundRepository.save(refund);

        return createStatus(true);
    }

    /**
     * 拒绝退款
     */
    @RequestMapping("/accept_refund")
    String acceptRefund(@RequestParam("refund_id")String refund_id,@RequestParam("session_key")String session_key){
        String admin_id=adminService.getAdminId(session_key);
        //控制无效条件
        if(admin_id==null) return createStatus(false);
        Refund refund=refundRepository.findByRefundId(refund_id);
        if(refund==null) return createStatus(false);
        if(refund.isSucceed()) return createStatus(false);
        if(!refund.isAbled()) return createStatus(false);
        if(refund.isRefused()) return createStatus(false);

        refund.setSucceed(true);
        refund.setRefused(false);

        refundRepository.save(refund);

        orderService.refundOrder(refund.getOrderId());

        return createStatus(true);
    }

}
