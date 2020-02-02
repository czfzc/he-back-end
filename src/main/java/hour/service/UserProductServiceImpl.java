package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.Preorder;
import hour.model.Product;
import hour.model.UserProduct;
import hour.model.WriteList;
import hour.repository.PreorderRepository;
import hour.repository.ShopProductRepository;
import hour.repository.UserProductRepository;
import hour.repository.WriteListRepository;
import hour.util.StringUtil;
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

    @Autowired
    PreorderRepository preorderRepository;

    @Autowired
    WriteListRepository writeListRepository;

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
            int num = jo.getInteger("num");
            if(num>product.getRest())
                return false;
            userProduct.setAbled(true);
            userProduct.setNum(jo.getInteger("num"));
            userProduct.setTotalFee(Math.abs(product.getPrice()*jo.getInteger("num")));
            userProduct.setPreorderId(preorder_id);
            userProduct.setUserid(userid);
            userProduct.setTime(new Date());
            userProduct.setProductId(jo.getString("product_id"));
            userProduct.setName(product.getName());
            if(writeListRepository.existsStringByUserId(userid)){
                WriteList writeList = writeListRepository.findFirstByUserId(userid);
                userProduct.setTotalFee(Math.abs(userProduct.getTotalFee()*writeList.getDiscount()));
            }
            userProductRepository.save(userProduct);
        }
        return true;
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

    /**
     * 完成service id为2的预付单
     * @param preorder
     * @return
     */

    @Override
    public boolean finishPayed(Preorder preorder){
        if(preorder == null && preorder.getServiceId() != 2)
            return false;
        List<UserProduct> list = userProductRepository.findAllByPreorderId(preorder.getId());
        for(int i=0;i<list.size();i++){
            UserProduct userProduct = list.get(i);
            String productId = userProduct.getProductId();
            int num = userProduct.getNum();
            Product product = shopProductRepository.findById(productId).get();
            product.setRest(product.getRest()-num);
            if(product.getRest()<0)
                product.setRest(0);
            shopProductRepository.save(product);
        }
        if(preorder.getStatus()==1){
            preorder.setStatusData(StringUtil.getRandom(6));
            preorder.setPayed(1);
            preorderRepository.save(preorder);
        }
        return true;
    }

    @Override
    public boolean setProductPreorderSended(String preorder_id){
        Preorder preorder = preorderRepository.findById(preorder_id).get();
        if(preorder == null||preorder.getServiceId()==3) throw new RuntimeException("invalid preorder_id");
        preorder.setStatus(3);
        return preorderRepository.save(preorder).getStatus()==3;
    }

}
