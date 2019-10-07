package hour.controller;

import com.alibaba.fastjson.JSONObject;
import hour.model.Product;
import hour.model.ProductType;
import hour.repository.ShopProductTypeRepository;
import hour.service.ShopProductService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@ComponentScan(basePackages = "hour")
public class ShopProductController {

    @Autowired
    UserService userService;

    @Autowired
    ShopProductService shopProductService;

    @Autowired
    ShopProductTypeRepository shopProductTypeRepository;

    @RequestMapping("/get_product")
    Page<Product> getProduct(@RequestBody JSONObject data){
        String productTypeId = data.getString("product_type_id");
        String mysession = data.getString("mysession");
        String buildingId = data.getString("building_id");
        int size = data.getInteger("size");
        int page = data.getInteger("page");
        String user_id=userService.getUserId(mysession);
        if(user_id==null)
            throw new RuntimeException("invalid userid");
        return shopProductService.findByBuildingIdAndTypeId(buildingId,productTypeId,size,page);
    }

    @RequestMapping("/get_product_type")
    List<ProductType> getProductType(@RequestBody JSONObject data){
        String mysession = data.getString("mysession");
        String user_id=userService.getUserId(mysession);
        if(user_id==null)
            throw new RuntimeException("invalid userid");
        String buildingId = data.getString("building_id");
        return shopProductTypeRepository.findAllByBuildingIdAndAbledTrue(buildingId);
    }

}
