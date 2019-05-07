package hour.repository;

import hour.model.SendMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SendMethodRepository extends JpaRepository<SendMethod, Integer> {
    List<SendMethod> findAllByServiceId(String service_id);
}
