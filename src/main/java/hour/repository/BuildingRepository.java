package hour.repository;

import hour.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building,Integer> {
    Building findFirstById(String id);
}
