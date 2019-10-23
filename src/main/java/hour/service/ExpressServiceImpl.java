package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.*;
import hour.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service("ExpressService")
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    ExpressRepository expressRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ExpressPriceRepository expressPriceRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    VoucherService voucherService;

    @Autowired
    ExpressMonthCardService expressMonthCardService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PreorderRepository preorderRepository;

    @Autowired
    ExpressSizeRepository expressSizeRepository;

    @Value("${express.voucher-type-id}")
    String type_id;
    @Value("${express.max-express-count}")
    Integer expressMaxExpressCount;

    @Override
    public boolean addExpress(JSONArray expresses,String preorder_id,
                              String address_id,String user_id, String send_method_id) {
        if(expresses==null) throw new RuntimeException("unify order fail");;
        if(expresses.size()==0||expresses.size()>expressMaxExpressCount)
            throw new RuntimeException("unify order fail");
        for(int i=0;i<expresses.size();i++){
            JSONObject jo=expresses.getJSONObject(i);
            double total_fee=0;

            String name=jo.getString("name");
            String phone_num=jo.getString("phone_num");
            String sms_content=jo.getString("sms_content");
            String receive_code=jo.getString("receive_code");
            String express_point_id=jo.getString("express_point_id");
            String size_id=jo.getString("size_id");

            String discount=jo.getString("discount");  //用户折扣方式 voucher是代金卷 express_card是折扣卡

            System.out.println(jo.toJSONString());

            User user=userRepository.findByUserId(user_id);

            ExpressSize size=expressSizeRepository.findById(size_id).get();

            if(discount!=null&&discount.equals("voucher")&&voucherService.useVoucher(user.getMainId(),type_id)){
                //do nothing
            }else if(discount!=null&&discount.equals("express_card")&&expressMonthCardService.useItForTimes(user_id,size.getCardTimes())){
                //do nothing
            }else total_fee=this.getTotal(express_point_id,address_id,size_id,send_method_id);

            Express express=new Express();
            express.setPreorderId(preorder_id);
            express.setName(name);
            express.setPhoneNum(phone_num);
            express.setTotalFee(total_fee);
            express.setSmsContent(sms_content);
            express.setReceiveCode(receive_code);
            express.setAddressId(address_id);
            express.setExpressPointId(express_point_id);
            express.setUserId(user_id);
            express.setTime(new Date());
            express.setStatus(0);
            express.setAbled(true);
            express.setSizeId(size_id);
            express.setSendMethodId(send_method_id);

            if(expressRepository.save(express).getExpressId()==null)
                throw new RuntimeException("unify order fail");;
        }
        return true;
    }

    @Override
    public List<Express> getExpress(String preorder_id){
        Pageable pageable=new PageRequest(0,100, Sort.Direction.DESC,"time");
        List<Express> list=expressRepository.findAllByPreorderIdAndAbledTrue(preorder_id,pageable).getContent();
        for(int i=0;i<list.size();i++){
            Express express=list.get(i);
            if(express.getSenderAdminId()!=null)
                express.setSender(adminRepository.findFirstByAdminId(express.getSenderAdminId()));
        }
        return list;
    }

    @Autowired
    ExpressAdminRepository expressAdminRepository;

    @Override
    public HashMap getAllExpressByPayed(Integer page, Integer size){
       // return expressRepository.findAllByPayed(pageable,1);
        return new HashMap(){
            {
                this.put("content",expressAdminRepository.findAllByPayed(page*size,size,1));
                this.put("totalElements",countTotalExpressByPayed());
            }
        };
    }


    /**
     * 根据取货点获取快递
     */
    @Override
    public HashMap getExpressByExpressPointAndPayed(String express_point_id,Integer page,Integer size){
        return new HashMap(){
            {
                this.put("content",expressAdminRepository.findAllByPointAndPayed(
                        page*size,size,1,express_point_id));
                this.put("totalElements",countTotalExpressByPointAndPayed(express_point_id));
            }
        };
    }

    /**
     * 计算一件快递的送货价格
     * @param express
     * @return
     */
    private Double calculatePrice(JSONObject express){
        return this.getTotal(express.getString("expressPointId"),
                express.getString("addressId"),express.getString("sizeId"),
                express.getString("sendMethodId"));
    }

    @Override
    public Double getTotalPrice(String preorder_id) {
        String jpql="select sum(express.totalFee) from Express express where express.preorderId='"+preorder_id+"'";
        Query query=entityManager.createQuery(jpql);
        double sum=(double)query.getSingleResult();
        return sum;
    }

    /**
     *根据快递号搜索快递
     */

    @Override
    public Page<Express> searchExpressById(String value, Integer page, Integer size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Express> express=expressRepository.findAllByExpressIdContaining(value,pageable);
        return express;
    }

    /**
     * 根据用户账号搜索快递
     */
    @Override
    public Page<Express> searchExpressByUserId(String value, Integer page, Integer size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Express> express=expressRepository.findAllByUserIdContaining(value,pageable);
        return express;
    }


    /**
     * 计算一个JSONObject的express的总价
     * @param express
     * @return
     */

    @Override
    public double getTotalByObject(JSONObject express){
        String discount=express.getString("discount");
        if(discount!=null&&discount.equals("voucher")) return 0;
        if(discount!=null&&discount.equals("express_card")) return 0;
        else return this.getTotal(express.getString("express_point_id"),
                express.getString("address_id"),express.getString("size_id"),
                express.getString("send_method_id"));
    }

    @Value("${express.default-price}")
    double total;

    private double getTotal(String expressPointId,String addressId,String sizeId,String sendMethodId){
        Address address=addressRepository.findById(addressId).get();
        if(address==null) return total;
        String dest_building_id=address.getBuildId();
        //精准计费代码
       /* ExpressPrice expressPrice=expressPriceRepository.
                findFirstByDestBuildingIdAndExpressPointIdAndSizeIdAndSendMethodId(dest_building_id,
                        expressPointId,sizeId,sendMethodId);*/
        ExpressPrice expressPrice=expressPriceRepository.
                findFirstBySizeId(sizeId);
        if(expressPrice==null) return total;
        return expressPrice.getPrice();
    }

    @Override
    public long countTotalExpressByPayed(){
        String jpql="select count(e) from Express e where e.payed = 1";
        Query query=entityManager.createQuery(jpql);
        Long sum=(Long)query.getSingleResult();
        return sum;
    }

    @Override
    public long countTotalExpressByPointAndPayed(String express_point_id){
        String jpql="select count(e) from Express e where e.payed = 1 and e.expressPointId=?1";
        Query query=entityManager.createQuery(jpql);
        query.setParameter(1,express_point_id);
        Long sum=(Long)query.getSingleResult();
        return sum;
    }

    @Override
    public String getPrepayIdByExpressId(String express_id){

        Express express=expressRepository.findFirstByExpressId(express_id);
        if(express==null) return null;

        Preorder preorder=preorderRepository.findById(express.getPreorderId()).get();
        if(preorder==null) return null;

        Order order=orderRepository.findByOrderId(preorder.getOrderId());
        if(order==null) return null;

        String prepay_id=order.getPrepayId();

        return prepay_id;
    }
}
