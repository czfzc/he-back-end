package hour.repository;

import hour.model.SendMethod;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SendMethodRepository extends JpaRepository<SendMethod, Integer> {
}
