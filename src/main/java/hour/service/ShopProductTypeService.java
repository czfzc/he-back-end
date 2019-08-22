package hour.service;

import hour.model.ProductType;

import java.util.List;

public interface ShopProductTypeService {

    boolean addProductType(String name, String buildingId, boolean abled, String addition);

    boolean editProductType(String typeId, String name, String buildingId, boolean abled, String addition);

    boolean removeProductType(String typeId);

    List<ProductType> getShopProductTypeByBuildingId(String building_id);

    List<ProductType> getShopProductType();
}
