package hour.service;

import hour.model.ProductType;
import hour.repository.ShopProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ShopProductTypeService")
public class ShopProductTypeServiceImpl implements ShopProductTypeService {

    @Autowired
    ShopProductTypeRepository shopProductTypeRepository;

    @Override
    public boolean addProductType(String name, String buildingId, boolean abled, String addition){
        ProductType productType = new ProductType();
        productType.setName(name);
        productType.setBuildingId(buildingId);
        productType.setAbled(abled);
        productType.setAddition(addition);
        return shopProductTypeRepository.save(productType).getId()!=null;
    }

    @Override
    public boolean editProductType(String typeId, String name, String buildingId, boolean abled, String addition){
        ProductType productType = shopProductTypeRepository.findById(typeId).get();
        productType.setName(name);
        productType.setBuildingId(buildingId);
        productType.setAbled(abled);
        productType.setAddition(addition);
        shopProductTypeRepository.save(productType);
        return true;
    }

    @Override
    public boolean removeProductType(String typeId){
        shopProductTypeRepository.deleteById(typeId);
        return true;
    }

    @Override
    public List<ProductType> getShopProductTypeByBuildingId(String building_id) {
        return shopProductTypeRepository.findAllByBuildingId(building_id);
    }

    @Override
    public List<ProductType> getShopProductType() {
        return shopProductTypeRepository.findAll();
    }
}
