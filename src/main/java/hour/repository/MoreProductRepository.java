package hour.repository;

import hour.model.MoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoreProductRepository extends JpaRepository<MoreProduct,String> {
    MoreProduct findFirstByProductId(String product_id);
}
