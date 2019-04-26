package hour.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.Preorder;
import hour.model.User;
import hour.repository.PreorderRepository;
import hour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Override
    public double calculateTotal(String order_id) {
        String sql="select sum(u.totalFee) " +
                "from Preorder u where u.orderId='"+order_id+"'";
        Query query=entityManager.createQuery(sql);
        double sum=(double)query.getSingleResult();
        return sum;
    }

    @Override
    public boolean preorderIt(JSONArray arr,String order_id,String user_id) {
        boolean toret=true;
        for(int i=0;i<arr.size();i++){
            JSONObject jo=arr.getJSONObject(i);
            if(jo.getInteger("service_id")==1){ //快递代取业务预付单
                JSONArray express=jo.getJSONArray("express");
                String preorder_id= UUID.randomUUID().toString().replace("-","");
                String address_id=jo.getString("address_id");

                Preorder preorder=new Preorder();
                Date time=new Date(System.currentTimeMillis());
                Integer send_method_id=jo.getInteger("send_method_id");


                preorder.setId(preorder_id);
                preorder.setAddressId(address_id);
                preorder.setTime(time);
                preorder.setOrderId(order_id);
                preorder.setUserId(user_id);
                preorder.setServiceId(1);
                preorder.setStatus(0);
                preorder.setPayed(0);
                preorder.setAbled(1);
                preorder.setSendMethodId(send_method_id);
                preorder.setTotalFee(0D);
                preorderRepository.save(preorder);

                if(expressService.addExpress(express,preorder_id,address_id)){
                    Double total=expressService.getTotalPrice(preorder_id);
                    preorder.setTotalFee(total);
                    preorderRepository.save(preorder);
                }else toret=false;
            }
        }
        return toret;
    }
}
