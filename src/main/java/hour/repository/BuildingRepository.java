package hour.repository;

import hour.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building,String> {
    Building findFirstById(String id);
    List<Building> findAllByAbledTrue();
}
