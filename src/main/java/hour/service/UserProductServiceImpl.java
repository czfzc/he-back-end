package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.Product;
import hour.model.UserProduct;
import hour.repository.ShopProductRepository;
import hour.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Service("UserProductService")
public class UserProductServiceImpl implements UserProductService  {


    @Autowired
    UserProductRepository userProductRepository;

    @Autowired
    ShopProductRepository shopProductRepository;

    @Autowired
    EntityManager entityManager;

    /**
     * 用户下单添加商品至用户已购买的表
     * @param jsonArray
     * @param preorder_id
     * @return
     */

    @Override
    public boolean addUserProducts(JSONArray jsonArray, String preorder_id, String userid,Date time) {
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jo = jsonArray.getJSONObject(i);
            UserProduct userProduct = new UserProduct();
            Product product = shopProductRepository.findById(jo.getString("product_id")).get();
            userProduct.setAbled(true);
            userProduct.setNum(jo.getInteger("num"));
            userProduct.setTotalFee(product.getPrice()*jo.getInteger("num"));
            userProduct.setPreorderId(preorder_id);
            userProduct.setUserid(userid);
            userProduct.setTime(new Date());
            userProduct.setProductId(jo.getString("product_id"));
            userProductRepository.save(userProduct);
        }
        return false;
    }

    @Override
    public double getTotalPrice(String preorder_id){
        String jpql="select sum(userProduct.totalFee) from UserProduct userProduct where userProduct.preorderId='"+preorder_id+"'";
        Query query=entityManager.createQuery(jpql);
        double sum=(double)query.getSingleResult();
        return sum;
    }

    @Override
    public List<UserProduct> getUserProducts(String preorderId){
        List<UserProduct> list = userProductRepository.findAllByPreorderId(preorderId);
        for(int i=0;i<list.size();i++){
            UserProduct userProduct = list.get(i);
            userProduct.setProduct(shopProductRepository.findById(userProduct.getProductId()).get());
        }
        return list;
    }


}
