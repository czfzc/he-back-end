package hour.repository;

import hour.model.MoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoreProductRepository extends JpaRepository<MoreProduct,String> {
    MoreProduct findFirstByProductId(String product_id);
    List<MoreProduct> findAllByServiceIdAndAddition(Integer service_id, String addition);
    List<MoreProduct> findAllByServiceIdAndAdditionNot(Integer service_id,String addition);
}
