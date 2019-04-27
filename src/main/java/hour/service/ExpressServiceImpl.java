package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.Express;
import hour.repository.ExpressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Service("ExpressService")
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    ExpressRepository expressRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public boolean addExpress(JSONArray expresses,String preorder_id,String address_id,String user_id) {
        if(expresses.size()==0)
            return false;
        for(int i=0;i<expresses.size();i++){
            JSONObject jo=expresses.getJSONObject(i);
            double total_fee=this.calculatePrice(jo);
            String name=jo.getString("name");
            String phone_num=jo.getString("phone_num");
            String sms_content=jo.getString("sms_content");
            String receive_code=jo.getString("receive_code");
            String express_id= UUID.randomUUID().toString().replace("-","");

            Express express=new Express();
            express.setExpressId(express_id);
            express.setPreorderId(preorder_id);
            express.setExpressId(express_id);
            express.setName(name);
            express.setPhoneNum(phone_num);
            express.setTotalFee(total_fee);
            express.setSmsContent(sms_content);
            express.setReceiveCode(receive_code);
            express.setAddressId(address_id);
            express.setUserId(user_id);
            express.setTime(new Date(System.currentTimeMillis()));
            express.setStatus(0);

            expressRepository.save(express);
        }
        return true;
    }

    @Override
    public List<Express> getExpress(String preorder_id){
        return expressRepository.findAllByPreorderIdAndAbledTrue(preorder_id);
    }

    /**
     * 计算一件快递的送货价格
     * @param express
     * @return
     */
    private Double calculatePrice(JSONObject express){
        return 1d;
    }

    @Override
    public Double getTotalPrice(String preorder_id) {
        String jpql="select sum(express.totalFee) from Express express where express.preorderId='"+preorder_id+"'";
        Query query=entityManager.createQuery(jpql);
        double sum=(double)query.getSingleResult();
        return sum;
    }
}
