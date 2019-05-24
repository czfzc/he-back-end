package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.Address;
import hour.model.Express;
import hour.model.ExpressPrice;
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

    @Value("${express.voucher-type-id}")
    String type_id;
    @Override
    public boolean addExpress(JSONArray expresses,String preorder_id,
                              String address_id,String user_id, String send_method_id) {
        System.out.println("hahahahaha");
        if(expresses==null) return false;
        if(expresses.size()==0)
            return false;
        for(int i=0;i<expresses.size();i++){
            JSONObject jo=expresses.getJSONObject(i);
            double total_fee=0;

            String name=jo.getString("name");
            String phone_num=jo.getString("phone_num");
            String sms_content=jo.getString("sms_content");
            String receive_code=jo.getString("receive_code");
            String express_point_id=jo.getString("express_point_id");
            String size_id=jo.getString("size_id");

            if(jo.getBoolean("use_voucher")==null||jo.getBoolean("use_voucher")!=true||!voucherService.useVoucher(user_id,type_id)){
                //如果用户不选择使用代金卷或者选择使用代金劵但是代金劵无效 则计费
                total_fee=this.getTotal(express_point_id,address_id,size_id,send_method_id);
            }



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

            expressRepository.save(express);
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

    @Override
    public Page<Express> getAllExpress(Integer page, Integer size){
        Pageable pageable=new PageRequest(page,size, Sort.Direction.DESC,"time");
        return expressRepository.findAll(pageable);
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
     * 根据取货点获取快递
     */
    @Override
    public Page<Express> getExpressByExpressPoint(String express_point_id,Integer page,Integer size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Express> express=expressRepository.findAllByExpressPointId(express_point_id,pageable);
        return express;
    }

    /**
     * 计算一个JSONObject的express的总价
     * @param express
     * @return
     */

    @Override
    public double getTotalByObject(JSONObject express){
        if(express.getBoolean("use_voucher")!=null&&
                express.getBoolean("use_voucher")==true) return 0;
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
        ExpressPrice expressPrice=expressPriceRepository.
                findFirstByDestBuildingIdAndExpressPointIdAndSizeIdAndSendMethodId(dest_building_id,
                        expressPointId,sizeId,sendMethodId);
        if(expressPrice==null) return total;
        return expressPrice.getPrice();
    }


}
