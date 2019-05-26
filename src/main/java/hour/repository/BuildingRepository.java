package hour.repository;

import hour.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building,String> {
    Building findFirstById(String id);
}
