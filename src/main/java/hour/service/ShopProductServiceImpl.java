package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.Product;
import hour.model.UserProduct;
import hour.repository.ShopProductRepository;
import hour.repository.UserProductRepository;
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
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "type_id");
        return shopProductRepository.findAllByBuildingIdAndTypeIdAndAbledTrue(buildingId,typeId,pageable);
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
        shopProductRepository.save(product);
        return true;
    }

    @Override
    public boolean removeProduct(String product_id){
        shopProductRepository.deleteById(product_id);
        return true;
    }

    @Override
    public Page<Product> getAllShopProduct(int page,int size){
        Pageable pageable =
                new PageRequest(page,size, Sort.Direction.DESC,"building_id");
        return shopProductRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getShopProductByBuildingId(String buildingId,int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"type_id");
        return shopProductRepository.findAllByBuildingId(buildingId,pageable);
    }

    @Override
    public Page<Product> getShopProductByTypeId(String typeId,int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"building_id");
        return shopProductRepository.findAllByTypeId(typeId,pageable);
    }

    @Override
    public Page<Product> getShopProductByBuildingIdAndTypeId(String buildingId,String typeId,
                                                             int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"time");
        return shopProductRepository.findAllByBuildingIdAndTypeId(buildingId,typeId,pageable);
    }
}
