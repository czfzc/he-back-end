package hour.repository;

import hour.model.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProductRepository extends JpaRepository<UserProduct,String> {
    List<UserProduct> findAllByPreorderId(String preorder_id);
}
