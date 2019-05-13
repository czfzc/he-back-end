package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.*;
import hour.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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
        for(int i=0;i<list.size();i++){
            Preorder preorder=list.get(i);
            preorder.setExpress(expressService.getExpress(preorder.getId(),0,1000));
            preorder.setAddress(addressRepository.findById(preorder.getAddressId()));
        }
        return list;
    }

    @Override
    public Page<Preorder> getAllPreorder(Integer page, Integer size){
        PageRequest pageable=new PageRequest(page,size, Sort.Direction.DESC,"time");

        Page<Preorder> pages=preorderRepository.findAll(pageable);

        for(Iterator<Preorder> i=pages.iterator();i.hasNext();){
            Preorder p=i.next();
            p.setAddress(addressRepository.findById(p.getAddressId()));
            p.setExpress(expressRepository.findAllByPreorderId(p.getId()));
        }
        return pages;
    }

    @Override
    public boolean preorderIt(JSONArray arr,String order_id,String user_id) {
        if(arr.size()==0)
            return false;
        for(int i=0;i<arr.size();i++){
            JSONObject jo=arr.getJSONObject(i);
            if(jo==null) return false;
            if(jo.getInteger("service_id")==1){ //快递代取业务预付单
                JSONArray express=jo.getJSONArray("express");
                String preorder_id= UUID.randomUUID().toString().replace("-","");
                String address_id=jo.getString("address_id");

                Preorder preorder=new Preorder();
                String send_method_id=jo.getString("send_method_id");


                preorder.setId(preorder_id);
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
                preorderRepository.save(preorder);

                if(expressService.addExpress(express,preorder_id,address_id,user_id,send_method_id)){
                    if(!expressMonthCardService.isAbled(user_id)) {
                        Double total = expressService.getTotalPrice(preorder_id);
                        preorder.setTotalFee(total);
                        preorderRepository.save(preorder);
                    }
                }else return false;
            }else if(jo.getInteger("service_id")==9){   //购买月代取卡

                String preorder_id= UUID.randomUUID().toString().replace("-","");

                Address address =addressRepository.findFirstByUserId(user_id);
                if(address==null) return false;
                String address_id=jo.getString(address.getId());

                MoreProduct moreProduct=moreProductRepository.
                        findFirstByProductId("09201921"); //月卡产品的产品id为09201921
                if(moreProduct==null) return false;
                Double total=moreProduct.getSum();

                Preorder preorder=new Preorder();
                preorder.setId(preorder_id);
                preorder.setAddressId(address_id);
                preorder.setTime(new Date());
                preorder.setOrderId(order_id);
                preorder.setUserId(user_id);
                preorder.setServiceId(9);
                preorder.setStatus(0);
                preorder.setPayed(0);
                preorder.setAbled(true);
                preorder.setTotalFee(total);
                preorderRepository.save(preorder);

                return expressMonthCardService.payIt(user_id,preorder_id);
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
    @Override
    public double cacuTotalByObject(JSONObject preorder){
        double sum=0;
        JSONArray express=preorder.getJSONArray("express");
        for(int i=0;i<express.size();i++)
            sum+=expressService.getTotalByObject(express.getJSONObject(i));
        return sum;
    }
}
