package hour.repository;

import hour.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopProductRepository extends JpaRepository<Product,String> {
    Page<Product> findAllByDeledFalse(Pageable pageable);
    Page<Product> findAllByBuildingIdAndDeledFalse(String buildingId,Pageable pageable);
    Page<Product> findAllByTypeIdAndDeledFalse(String typeId,Pageable pageable);
    Page<Product> findAllByBuildingIdAndTypeIdAndDeledFalse(String buildingId,String typeId,Pageable pageable);
    Page<Product> findAllByBuildingIdAndTypeIdAndRestGreaterThanAndAbledTrueAndDeledFalse(String buildingId,String typeId,Integer rest,Pageable pageable);
    Page<Product> findAllByNameIsContainingAndBuildingId(String name,String buildingId,Pageable pageable);
}
