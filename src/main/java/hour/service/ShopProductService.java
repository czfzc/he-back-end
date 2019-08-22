package hour.service;

import com.alibaba.fastjson.JSONArray;
import hour.model.Product;
import org.springframework.data.domain.Page;

public interface ShopProductService {

    Page<Product> findByBuildingIdAndTypeId(String buildingId,String typeId,int size,int page);

    boolean addProduct(String building_id, String name, Double price, boolean abled, int rest,
                       String type_id, String img_link, String addition);

    boolean editProduct(String product_id, String building_id, String name, Double price, boolean abled, int rest,
                        String type_id, String img_link, String addition);

    boolean removeProduct(String product_id);

    Page<Product> getAllShopProduct(int page, int size);

    Page<Product> getShopProductByBuildingId(String buildingId, int page, int size);

    Page<Product> getShopProductByTypeId(String typeId, int page, int size);

    Page<Product> getShopProductByBuildingIdAndTypeId(String buildingId, String typeId,
                                                      int page, int size);
}
