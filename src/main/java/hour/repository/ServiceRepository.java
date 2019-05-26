package hour.repository;

import hour.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Integer> {
    List<Service> findAllByShowTrue();
}
