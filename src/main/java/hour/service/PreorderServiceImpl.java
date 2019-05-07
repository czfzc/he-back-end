package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.Express;
import hour.model.Preorder;
import hour.repository.AddressRepository;
import hour.repository.ExpressRepository;
import hour.repository.PreorderRepository;
import hour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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
        boolean toret=true;
        for(int i=0;i<arr.size();i++){
            JSONObject jo=arr.getJSONObject(i);
            if(jo.getInteger("service_id")==1){ //快递代取业务预付单
                JSONArray express=jo.getJSONArray("express");
                String preorder_id= UUID.randomUUID().toString().replace("-","");
                String address_id=jo.getString("address_id");

                Preorder preorder=new Preorder();
                Integer send_method_id=jo.getInteger("send_method_id");


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

                if(expressService.addExpress(express,preorder_id,address_id,user_id)){
                    Double total=expressService.getTotalPrice(preorder_id);
                    preorder.setTotalFee(total);
                    preorderRepository.save(preorder);
                }else toret=false;
            }
        }
        return toret;
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
}
