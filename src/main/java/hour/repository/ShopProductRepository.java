package hour.repository;

import hour.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopProductRepository extends JpaRepository<Product,String> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByBuildingId(String buildingId,Pageable pageable);
    Page<Product> findAllByTypeId(String typeId,Pageable pageable);
    Page<Product> findAllByBuildingIdAndTypeId(String buildingId,String typeId,Pageable pageable);
    Page<Product> findAllByBuildingIdAndTypeIdAndAbledTrue(String buildingId,String typeId,Pageable pageable);
}
