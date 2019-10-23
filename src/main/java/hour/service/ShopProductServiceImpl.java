package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.Product;
import hour.repository.ShopProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("ShopProductService")
public class ShopProductServiceImpl implements ShopProductService{

    @Autowired
    ShopProductRepository shopProductRepository;

    @Override
    public Page<Product> findByBuildingIdAndTypeId(String buildingId, String typeId, int size, int page) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        return shopProductRepository.findAllByBuildingIdAndTypeIdAndRestGreaterThanAndAbledTrueAndDeledFalse(buildingId,typeId,0,pageable);
    }

    @Override
    public boolean addProduct(String building_id, String name, Double price, boolean abled, int rest,
                              String type_id, String img_link, String addition){
        Product product = new Product();
        product.setBuildingId(building_id);
        product.setName(name);
        product.setPrice(price);
        product.setAbled(abled);
        product.setRest(rest);
        product.setTypeId(type_id);
        product.setImgLink(img_link);
        product.setAddition(addition);
        product.setTime(new Date());
        product.setSalesVomume(0);
        return shopProductRepository.save(product).getId()!=null;
    }

    @Override
    public boolean editProduct(String product_id, String building_id, String name, Double price, boolean abled, int rest,
                               String type_id, String img_link, String addition){
        Product product = shopProductRepository.findById(product_id).get();
        product.setBuildingId(building_id);
        product.setName(name);
        product.setPrice(price);
        product.setAbled(abled);
        product.setRest(rest);
        product.setTypeId(type_id);
        product.setImgLink(img_link);
        product.setAddition(addition);
        product.setTime(new Date());
        shopProductRepository.save(product);
        return true;
    }

    @Override
    public boolean removeProduct(String product_id){
        Product product = shopProductRepository.findById(product_id).get();
        product.setDeled(true);
        shopProductRepository.save(product);
        return true;
    }

    @Override
    public Page<Product> getAllShopProduct(int page,int size){
        Pageable pageable =
                new PageRequest(page,size, Sort.Direction.DESC,"time");
        return shopProductRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getShopProductByBuildingId(String buildingId,int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"time");
        return shopProductRepository.findAllByBuildingIdAndDeledFalse(buildingId,pageable);
    }

    @Override
    public Page<Product> getShopProductByTypeId(String typeId,int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"time");
        return shopProductRepository.findAllByTypeIdAndDeledFalse(typeId,pageable);
    }

    @Override
    public Page<Product> getShopProductByBuildingIdAndTypeId(String buildingId,String typeId,
                                                             int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"time");
        return shopProductRepository.findAllByBuildingIdAndTypeIdAndDeledFalse(buildingId,typeId,pageable);
    }

    @Override
    public Page<Product> searchProduct(String buildingId,String value,int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"time");
        return shopProductRepository.findAllByNameIsContainingAndBuildingId(value,buildingId,pageable);
    }


    @Override
    public double calcuTotal(JSONArray products) {
        double total = 0;
        for(int i=0;i<products.size();i++){
            JSONObject jo = products.getJSONObject(i);
            Product product = shopProductRepository.findById(jo.getString("product_id")).get();
            if(product == null) throw new RuntimeException("no such product!");
            total += product.getPrice()*jo.getInteger("num");
        }
        return total;
    }
}
