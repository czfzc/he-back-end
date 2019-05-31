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
import java.util.*;

@Service("PreorderService")
public class PreorderServiceImpl implements PreorderService{

    @Autowired
    PreorderRepository preorderRepository;

    @Autowired
    ExpressService expressService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ExpressRepository expressRepository;

    @Autowired
    ExpressMonthCardRepository expressMonthCardRepository;

    @Autowired
    ExpressMonthCardService expressMonthCardService;

    @Autowired
    MoreProductRepository moreProductRepository;

    @Autowired
    ServiceRepository serviceRepository;


    @Override
    public double calculateTotal(String order_id) {
        String sql="select sum(u.totalFee) " +
                "from Preorder u where u.orderId='"+order_id+"'";
        Query query=entityManager.createQuery(sql);
        double sum=(double)query.getSingleResult();
        return sum;
    }

    @Override
    public List<Preorder> getAllPreorderByOrderId(String order_id){
        List<Preorder> list=preorderRepository.findAllByOrderId(order_id);
        for(Iterator<Preorder> i=list.iterator();i.hasNext();){
            Preorder preorder=i.next();
            if(!serviceRepository.findById(preorder.getServiceId()).get().isShow()) {
                i.remove();
                continue;
            }

            preorder.setExpress(expressService.getExpress(preorder.getId()));
            preorder.setAddress(addressRepository.findById(preorder.getAddressId()).get());
        }

        return list;
    }

    @Override
    public Page<Preorder> getAllPreorder(Integer page, Integer size){
        PageRequest pageable=new PageRequest(page,size, Sort.Direction.DESC,"time");

        Page<Preorder> pages=preorderRepository.findAll(pageable);

        for(Iterator<Preorder> i=pages.iterator();i.hasNext();){
            Preorder p=i.next();
            if(p.getAddressId()==null) continue;
            p.setAddress(addressRepository.findById(p.getAddressId()).get());
            p.setExpress(expressRepository.findAllByPreorderId(p.getId()));
        }
        return pages;
    }

    @Value("${express.max-express-count}")
    Integer max_express_count;

    @Value("${express.card.product-id}")
    String addressCardProductId;

    @Value("${express.product-id}")
    String addressProductId;

    @Value("${express.card.first-card-price}")
    Double firstCardPrice;

    @Override
    public boolean preorderIt(JSONArray arr,String order_id,String user_id) {
        if(arr==null) return false;
        if(arr.size()==0)
            return false;
        for(int i=0;i<arr.size();i++){
            JSONObject jo=arr.getJSONObject(i);
            if(jo==null) continue;
            if(!serviceRepository.findById(jo.getInteger("service_id")).get().isAbled()) //判断业务不可用则下预付单失败
                continue;
            if(jo.getInteger("service_id")==1){ //快递代取业务预付单

                JSONArray express=jo.getJSONArray("express");
                if(express.size()>max_express_count)             //快递数目不得超过指定件数
                    continue;
                String address_id=jo.getString("address_id");

                Preorder preorder=new Preorder();
                String send_method_id=jo.getString("send_method_id");

                preorder.setAddressId(address_id);
                preorder.setTime(new Date());
                preorder.setOrderId(order_id);
                preorder.setUserId(user_id);
                preorder.setServiceId(1);
                preorder.setStatus(0);
                preorder.setPayed(0);
                preorder.setAbled(true);
                preorder.setSendMethodId(send_method_id);
                preorder.setTotalFee(0D);
                preorder.setProductId(addressProductId);
                String preorder_id=preorderRepository.save(preorder).getId();

                if(expressService.addExpress(express,preorder_id,address_id,user_id,send_method_id)){
                    Double total = expressService.getTotalPrice(preorder_id);
                    preorder.setTotalFee(total);
                    preorderRepository.save(preorder);
                }else throw new RuntimeException("unify order fail");
            }else if(jo.getInteger("service_id")==9){   //购买月代取卡

                MoreProduct moreProduct=moreProductRepository.
                        findFirstByProductId("09201921"); //月卡产品的产品id为09201921
                if(moreProduct==null) return false;
                Double total=0D;
                if(expressMonthCardService.isNoCard(user_id)){
                    total=firstCardPrice;
                }else total=moreProduct.getSum();

                Preorder preorder=new Preorder();
                preorder.setTime(new Date());
                preorder.setOrderId(order_id);
                preorder.setUserId(user_id);
                preorder.setServiceId(9);
                preorder.setStatus(0);
                preorder.setPayed(0);
                preorder.setProductId(addressCardProductId);
                preorder.setAbled(true);
                preorder.setTotalFee(total);
                preorder.setSendMethodId("4");
                if (preorderRepository.save(preorder).getId()==null)
                    throw new RuntimeException("unify order fail");;
            }
        }
        return true;
    }

    /**
     *根据预付单号搜索预付单
     */

    @Override
    public Page<Preorder> searchPreorderById(String value, Integer page, Integer size){
        org.springframework.data.domain.Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Preorder> preorder=preorderRepository.findAllByIdContaining(value,pageable);
        return preorder;
    }

    /**
     * 根据用户账号搜索预付单
     */
    @Override
    public Page<Preorder> searchPreorderByUserId(String value, Integer page, Integer size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Preorder> preorder=preorderRepository.findAllByUserIdContaining(value,pageable);
        return preorder;
    }

    /**
     * 计算该快递预付单的价格，传入一个preorder的JSONObject
     */
    @Value("${express.max-express-count}")
    Integer expressMaxExpressCount;
    @Override
    public double cacuExpressTotalByObject(JSONObject preorder){
        double sum=0;
        JSONArray express=preorder.getJSONArray("express");
        if(express.size()<=expressMaxExpressCount)
            for(int i=0;i<express.size();i++)
                sum+=expressService.getTotalByObject(express.getJSONObject(i));
        return sum;
    }

}
