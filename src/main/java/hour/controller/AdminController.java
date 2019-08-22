package hour.controller;

import hour.model.*;
import hour.repository.*;
import hour.service.*;
import jdk.internal.jline.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
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

    @Autowired
    ExpressPointRepository expressPointRepository;

    @Autowired
    ExpressSizeRepository expressSizeRepository;

    @Autowired
    ExpressPriceRepository expressPriceRepository;

    @Autowired
    ExpressPriceService expressPriceService;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    SendMethodService sendMethodService;

    @Autowired
    SendMethodRepository sendMethodRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    WexinTokenService wexinTokenService;

    @Autowired
    ShopProductService shopProductService;

    @Autowired
    ShopProductTypeService shopProductTypeService;

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

 /*   @RequestMapping("/send_sms")
    String sendSms(@RequestParam("admin_id")String admin_id){
        return adminService.send(admin_id);
    }*/

    /**
     * 获取总订单
     * @param session_key
     * @param page
     * @param size
     * @return
     */

    @RequestMapping("/get_order")
    Page<Order> getOrder(@RequestParam("session_key")String session_key,
                         @RequestParam("page")Integer page,@RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return orderService.getOrder(page,size);
    }

    /**
     * 获取快递单
     * @param session_key
     * @param page
     * @param size
     * @return
     */

    @RequestMapping("/get_express")
    HashMap getExpress(@RequestParam("session_key")String session_key,
                             @RequestParam("page")Integer page,@RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return expressService.getAllExpressByPayed(page, size);
    }

    /**
     * 根据快递点获取快递单
     * @param session_key
     * @param page
     * @param size
     * @return
     */

    @RequestMapping("/get_express_by_point")
    HashMap getExpressByPoint(@RequestParam("session_key")String session_key,
                             @RequestParam("page")Integer page,@RequestParam("size")Integer size,
                                    @RequestParam("express_point_id")String express_point_id){
        if(adminService.getAdminId(session_key)==null) return null;
        return expressService.getExpressByExpressPointAndPayed(express_point_id, page, size);
    }

    /**
     * 获取取货点
     */
    @RequestMapping("/get_express_point")
    List<ExpressPoint> getExpressPoint(@RequestParam("session_key")String session_key){
        if(adminService.getAdminId(session_key)==null) return null;
        return expressPointRepository.findAll();
    }

    /**
     * 获取预付单
     * @param session_key
     * @param page
     * @param size
     * @return
     */

    @RequestMapping("/get_express_preorder")
    Page<Preorder> getPreorder(@RequestParam("session_key")String session_key,
                               @RequestParam("page")Integer page,@RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return preorderService.getAllPreorder(page,size);
    }

    /**
     * 查看退款列表
     */
    @RequestMapping("/get_refund")
    Page<Refund> getRefund(@RequestParam("session_key")String session_key,
                           @RequestParam("page")Integer page, @RequestParam("size")Integer size){
        String admin_id=adminService.getAdminId(session_key);
        if(admin_id==null)
            return null;
        return refundService.getRefund(page,size);
    }

    /**
     * 获取快递大小
     * @return
     */

    @RequestMapping("/get_express_size")
    List<ExpressSize> getExpressSize(@RequestParam("session_key")String session_key){
        String admin_id=adminService.getAdminId(session_key);
        if(admin_id==null)
            return null;
        return expressSizeRepository.findAll();
    }

    @RequestMapping("/get_send_method")
    List<SendMethod> getSendMethod(@RequestParam("session_key")String session_key){
        String admin_id=adminService.getAdminId(session_key);
        if(admin_id==null)
            return null;
        return sendMethodService.getSendMethodByServiceId("1");
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
        express.setSenderAdminId(admin_id);
        express.setStatus(1);
        expressRepository.save(express);
        wexinTokenService.pushWithdrawExpressInfo(express_id);
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
        wexinTokenService.pushSendedExpressInfo(express_id);
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
        return createStatus(refundService.adminRefund(order_id));
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
        Preorder preorder=preorderRepository.findById(preorder_id).get();
        preorder.setStatus(1);
        return createStatus(true);
    }

    /**
     * 拒绝退款
     */
    @RequestMapping("/refuse_refund")
    String refuseRefund(@RequestParam("refund_id")String refund_id,@RequestParam("session_key")String session_key){
        String admin_id=adminService.getAdminId(session_key);
        //控制无效条件
        if(admin_id==null) return createStatus(false);
        return createStatus(refundService.refuseRefund(refund_id));
    }

    /**
     * 通过
     */
    @RequestMapping("/accept_refund")
    String acceptRefund(@RequestParam("refund_id")String refund_id,@RequestParam("session_key")String session_key){
        String admin_id=adminService.getAdminId(session_key);
        //控制无效条件
        if(admin_id==null) return createStatus(false);
        return createStatus(refundService.acceptRefund(refund_id));
    }



    /**
     * 根据订单号搜索订单
     */
    @RequestMapping("/search_order_by_order_id")
    Page<Order> searchOrderByOrderId(@RequestParam("session_key")String session_key,
                                     @RequestParam("value")String value, @RequestParam("page")Integer page,
                                     @RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return orderService.searchOrderById(value, page, size);
    }

    /**
     * 根据用户账号搜索订单
     */
    @RequestMapping("/search_order_by_user_id")
    Page<Order> searchOrderByUserId(@RequestParam("session_key")String session_key,
                                     @RequestParam("value")String value, @RequestParam("page")Integer page,
                                     @RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return orderService.searchOrderByUserId(value, page, size);
    }

    /**
     * 根据快递号搜索快递
     */
    @RequestMapping("/search_express_by_express_id")
    Page<Express> searchExpressByExpressId(@RequestParam("session_key")String session_key,
                                     @RequestParam("value")String value, @RequestParam("page")Integer page,
                                     @RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return expressService.searchExpressById(value, page, size);
    }

    /**
     * 根据用户账号搜索快递
     */
    @RequestMapping("/search_express_by_user_id")
    Page<Express> searchExpressByUserId(@RequestParam("session_key")String session_key,
                                    @RequestParam("value")String value, @RequestParam("page")Integer page,
                                    @RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return expressService.searchExpressByUserId(value, page, size);
    }


    /**
     * 根据预付单号搜索预付单
     */
    @RequestMapping("/search_preorder_by_id")
    Page<Preorder> searchPreorderById(@RequestParam("session_key")String session_key,
                                           @RequestParam("value")String value, @RequestParam("page")Integer page,
                                           @RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return preorderService.searchPreorderByUserId(value, page, size);
    }

    /**
     * 根据用户账号搜索预付单
     */
    @RequestMapping("/search_preorder_by_user_id")
    Page<Preorder> searchPreorderByUserId(@RequestParam("session_key")String session_key,
                                        @RequestParam("value")String value, @RequestParam("page")Integer page,
                                        @RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return preorderService.searchPreorderByUserId(value, page, size);
    }


    @RequestMapping("/get_express_price")
    Page<ExpressPrice> getExpressPrice(@RequestParam("session_key")String session_key,
        @RequestParam("page")Integer page,@RequestParam("size")Integer size){
        if(adminService.getAdminId(session_key)==null) return null;
        return expressPriceService.getExpressPrice(page, size);
    }

    @RequestMapping("/edit_express_price")
    String editExpressPrice(@RequestParam("session_key")String session_key,
                            @RequestParam("mainkey")Integer mainkey,
                            @RequestParam("dest_building_id")String dest_building_id,
                            @RequestParam("express_point_id")String express_point_id,
                            @RequestParam("price")Double price,
                            @RequestParam("size_id")String size_id,
                            @RequestParam("send_method_id")String send_method_id){
        if(adminService.getAdminId(session_key)==null) return createStatus(false);
        return expressPriceService.editExpressPrice(mainkey, dest_building_id, express_point_id, price, size_id,send_method_id);
    }

    @RequestMapping("/add_express_price")
    String addExpressPrice(@RequestParam("session_key")String session_key,
                            @RequestParam("dest_building_id")String dest_building_id,
                            @RequestParam("express_point_id")String express_point_id,
                            @RequestParam("price")Double price,
                            @RequestParam("size_id")String size_id,
                           @RequestParam("send_method_id")String send_method_id){
        if(adminService.getAdminId(session_key)==null) return createStatus(false);
        return expressPriceService.addExpressPrice(dest_building_id, express_point_id, price, size_id,send_method_id);
    }

    @RequestMapping("/delete_express_price")
    String deleteExpressPrice(@RequestParam("session_key")String session_key,
                              @RequestParam("mainkey")Integer mainkey){
        if(adminService.getAdminId(session_key)==null) return createStatus(false);
        return expressPriceService.deleteExpressPrice(mainkey);
    }

    @RequestMapping("/get_building")
    List<Building> getBuilding(@RequestParam("session_key")String session_key){
        if(adminService.getAdminId(session_key)==null) return null;
        return buildingRepository.findAll();
    }

    @RequestMapping("/add_send_method")
    String addSendMethod(@RequestParam("session_key")String session_key,
                         @RequestParam("service_id")String service_id,
                         @RequestParam("value")String value,
                        @RequestParam("abled")Boolean abled){
        if(adminService.getAdminId(session_key)==null) return createStatus(false);
        SendMethod sendMethod=new SendMethod();
        sendMethod.setValue(value);
        sendMethod.setServiceId(service_id);
        sendMethod.setTypeStr("");
        sendMethod.setAbled(abled);
        return createStatus(sendMethodRepository.save(sendMethod).getId()!=null);
    }

    @RequestMapping("/edit_send_method")
    String editSendMethod(@RequestParam("session_key")String session_key,
                          @RequestParam("id")String id,
                          @RequestParam("service_id")String service_id,
                          @RequestParam("value")String value,
                          @RequestParam("abled")Boolean abled){
        if(adminService.getAdminId(session_key)==null) return createStatus(false);
        SendMethod sendMethod=sendMethodRepository.findById(id).get();
        sendMethod.setServiceId(service_id);
        sendMethod.setValue(value);
        sendMethod.setAbled(abled);
        return createStatus(sendMethodRepository.save(sendMethod).getValue().equals(value));
    }

    @RequestMapping("/get_service")
    List<Service> getService(@RequestParam("session_key")String session_key){
        if(adminService.getAdminId(session_key)==null) return null;
        return serviceRepository.findAllByShowTrue();
    }

    @RequestMapping("/add_express_point")
    String addExpressPoint(@RequestParam("session_key")String session_key,
                           @RequestParam("position")String position,
                           @RequestParam("name")String name,
                           @RequestParam("sms_temp")String sms_temp,
                           @RequestParam("code_format")String code_format,
                           @RequestParam("abled")Boolean abled){
        if(adminService.getAdminId(session_key)==null) return null;
        ExpressPoint expressPoint=new ExpressPoint();
        expressPoint.setPosition(position);
        expressPoint.setName(name);
        expressPoint.setSmsTemp(sms_temp);
        expressPoint.setCodeFormat(code_format);
        expressPoint.setAbled(abled);
        return createStatus(expressPointRepository.save(expressPoint).getExpressPointId()!=null);
    }

    @RequestMapping("/edit_express_point")
    String editExpressPoint(@RequestParam("session_key")String session_key,
                           @RequestParam("express_point_id")String express_point_id,
                           @RequestParam("position")String position,
                           @RequestParam("name")String name,
                           @RequestParam("sms_temp")String sms_temp,
                           @RequestParam("code_format")String code_format,
                            @RequestParam("abled")Boolean abled){
        if(adminService.getAdminId(session_key)==null) return null;
        ExpressPoint expressPoint=expressPointRepository.findById(express_point_id).get();
        if(expressPoint==null) return null;
        expressPoint.setPosition(position);
        expressPoint.setName(name);
        expressPoint.setSmsTemp(sms_temp);
        expressPoint.setCodeFormat(code_format);
        expressPoint.setAbled(abled);
        return createStatus(expressPointRepository.save(expressPoint).getName().equals(name));
    }

    @RequestMapping("/add_express_size")
    String addExpressSize(@RequestParam("session_key")String session_key,
                          @RequestParam("size_name")String size_name,
                          @RequestParam("abled")Boolean abled){
        if(adminService.getAdminId(session_key)==null) return null;
        ExpressSize expressSize=new ExpressSize();
        expressSize.setSizeName(size_name);
        expressSize.setAbled(abled);
        return createStatus(expressSizeRepository.save(expressSize).getSizeId()!=null);
    }

    @RequestMapping("/edit_express_size")
    String editExpressSize(@RequestParam("session_key")String session_key,
                           @RequestParam("size_id")String size_id,
                           @RequestParam("size_name")String size_name,
                           @RequestParam("abled")Boolean abled){
        if(adminService.getAdminId(session_key)==null) return null;
        ExpressSize expressSize=expressSizeRepository.findById(size_id).get();
        if(expressSize==null) return createStatus(false);
        expressSize.setSizeName(size_name);
        expressSize.setAbled(abled);
        return createStatus(expressSizeRepository.save(expressSize).getSizeName().equals(size_name));
    }

    @RequestMapping("/add_dest_point")
    String addDestPoint(@RequestParam("session_key")String session_key,
                        @RequestParam("name")String name,
                        @RequestParam("position")String position,
                        @RequestParam("abled")Boolean abled){
        if(adminService.getAdminId(session_key)==null) return null;
        Building building=new Building();
        building.setName(name);
        building.setPosition(position);
        building.setAbled(abled);
        return createStatus(buildingRepository.save(building).getId()!=null);
    }

    @RequestMapping("/edit_dest_point")
    String editDestPoint(@RequestParam("session_key")String session_key,
                        @RequestParam("id")String id,
                        @RequestParam("name")String name,
                        @RequestParam("position")String position,
                        @RequestParam("abled")Boolean abled){
        if(adminService.getAdminId(session_key)==null) return null;
        Building building=buildingRepository.findById(id).get();
        if(building==null) return createStatus(false);
        building.setName(name);
        building.setPosition(position);
        building.setAbled(abled);
        return createStatus(buildingRepository.save(building).getName().equals(name));
    }

    /*
        以下是对商品的增删改查
     */

    /**
     * 添加商品
     * @param session_key
     * @param building_id
     * @param name
     * @param price
     * @param rest
     * @param type_id
     * @param img_link
     * @param addition
     * @return
     */
    @RequestMapping("/add_shop_product")
    String addShopProduct(@RequestParam("session_key")@NotNull
                                  String session_key,
                          @RequestParam("building_id")@NotNull
                                  String building_id,
                          @RequestParam("name")@NotNull
                                  String name,
                          @RequestParam("price")@NotNull
                                  Double price,
                          @RequestParam("rest")@NotNull
                                  Integer rest,
                          @RequestParam("type_id")@NotNull
                                  String type_id,
                          @RequestParam("img_link")@NotNull
                                  String img_link,
                          @RequestParam("abled")@NotNull
                                  Boolean abled,
                          @RequestParam("addition")@Nullable
                                  String addition){
        if(adminService.getAdminId(session_key)==null)
            throw new RuntimeException("invalid admin session_key");
        boolean status = shopProductService.addProduct(building_id,name,price,abled,rest,type_id,img_link,addition);
        return createStatus(status);
    }

    /**
     * 更改商品
     * @param session_key
     * @param building_id
     * @param name
     * @param price
     * @param rest
     * @param type_id
     * @param img_link
     * @param addition
     * @return
     */
    @RequestMapping("edit_shop_product")
    String editShopProduct(
                          @RequestParam("product_id")@NotNull
                                  String product_id,
                          @RequestParam("session_key")@NotNull
                                  String session_key,
                          @RequestParam("building_id")@NotNull
                                  String building_id,
                          @RequestParam("name")@NotNull
                                  String name,
                          @RequestParam("price")@NotNull
                                  Double price,
                          @RequestParam("rest")@NotNull
                                  Integer rest,
                          @RequestParam("type_id")@NotNull
                                  String type_id,
                          @RequestParam("img_link")@NotNull
                                  String img_link,
                          @RequestParam("abled")@NotNull
                                  Boolean abled,
                          @RequestParam("addition")@Nullable
                                  String addition){
        if(adminService.getAdminId(session_key)==null)
            throw new RuntimeException("invalid admin session_key");
        boolean status = shopProductService.editProduct(product_id,building_id,name,price,abled,rest,type_id,img_link,addition);
        return createStatus(status);
    }

    /**
     * 删除商品
     * @param product_id
     * @param session_key
     * @return
     */
    @RequestMapping("remove_shop_product")
    String removeShopProduct(@RequestParam("product_id")@NotNull String product_id,
                             @RequestParam("session_key")@NotNull String session_key){
        if(adminService.getAdminId(session_key)==null)
            throw new RuntimeException("invalid admin session_key");
        boolean status = shopProductService.removeProduct(product_id);
        return createStatus(status);
    }

    /**
     * 获取商品
     * 无building_id或type_id字段则不查询这些字段
     * @param session_key
     * @param page
     * @param size
     * @param building_id
     * @param type_id
     * @return
     */
    @RequestMapping("/get_shop_product")
    Page<Product> getShopProduct(@RequestParam("session_key")@NotNull String session_key,
                                 @RequestParam("page")@NotNull int page,
                                 @RequestParam("size")@NotNull int size,
                                 @RequestParam("building_id")@Nullable String building_id,
                                 @RequestParam("type_id")@Nullable String type_id){
        if(adminService.getAdminId(session_key)==null)
            throw new RuntimeException("invalid admin session_key");
        if(building_id==null&&type_id==null){
            return shopProductService.getAllShopProduct(page, size);
        }else if(building_id!=null&&type_id!=null){
            return shopProductService.
                    getShopProductByBuildingIdAndTypeId(building_id,type_id,page,size);
        }else if(type_id!=null){
            return shopProductService.getShopProductByTypeId(type_id,page,size);
        }else if(building_id!=null){
            return shopProductService.getShopProductByBuildingId(building_id,page,size);
        }
        throw new RuntimeException("no result");
    }

    /* 以下是对商品类型的增删改查*/

    /**
     * 添加商品类型
     * @param session_key
     * @param name
     * @param building_id
     * @param abled
     * @param addition
     * @return
     */
    @RequestMapping("add_shop_product_type")
    String addShopProductType(@RequestParam("session_key")@NotNull String session_key,
                              @RequestParam("name")@NotNull String name,
                              @RequestParam("building_id")@NotNull String building_id,
                              @RequestParam("abled")@NotNull Boolean abled,
                              @RequestParam("addition")@Nullable String addition){
        if(adminService.getAdminId(session_key)==null)
            throw new RuntimeException("invalid admin session_key");
        boolean status = shopProductTypeService.addProductType(name,building_id,abled,addition);
        return createStatus(status);
    }

    /**
     * 更改商品类型
     * @param session_key
     * @param name
     * @param building_id
     * @param abled
     * @param addition
     * @return
     */
    @RequestMapping("edit_shop_product_type")
    String editShopProductType(@RequestParam("session_key")@NotNull String session_key,
                              @RequestParam("type_id")@NotNull String type_id,
                              @RequestParam("name")@NotNull String name,
                              @RequestParam("building_id")@NotNull String building_id,
                              @RequestParam("abled")@NotNull Boolean abled,
                              @RequestParam("addition")@Nullable String addition){
        if(adminService.getAdminId(session_key)==null)
            throw new RuntimeException("invalid admin session_key");
        boolean status = shopProductTypeService.editProductType(type_id,name,building_id,abled,addition);
        return createStatus(status);
    }

    /**
     * 删除商品类型
     * @param type_id
     * @return
     */
    @RequestMapping("remove_product_type")
    String removeProductType(@RequestParam("session_key")@NotNull String session_key,
                             @RequestParam("type_id")@Nullable String type_id){
        if(adminService.getAdminId(session_key)==null)
            throw new RuntimeException("invalid admin session_key");
        boolean status = shopProductTypeService.removeProductType(type_id);
        return createStatus(status);
    }

    @RequestMapping("get_shop_product_type")
    List<ProductType> getShopProductType(@RequestParam("session_key")@NotNull String session_key,
                                         @RequestParam("building_id")@Nullable String building_id){
        if(adminService.getAdminId(session_key)==null)
            throw new RuntimeException("invalid admin session_key");
        boolean status = false;
        if(building_id!=null) {
            return shopProductTypeService.getShopProductTypeByBuildingId(building_id);
        }else return shopProductTypeService.getShopProductType();
    }


}
