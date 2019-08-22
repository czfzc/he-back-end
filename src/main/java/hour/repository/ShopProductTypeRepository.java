package hour.repository;

import hour.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopProductTypeRepository extends JpaRepository<ProductType,String> {
    List<ProductType> findAllByBuildingId(String buildingId);

    List<ProductType> findAllByBuildingIdAndAbledTrue(String buildingId);
}
