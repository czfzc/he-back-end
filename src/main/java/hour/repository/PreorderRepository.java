package hour.repository;

import hour.model.Preorder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreorderRepository extends JpaRepository<Preorder,Integer> {
}
