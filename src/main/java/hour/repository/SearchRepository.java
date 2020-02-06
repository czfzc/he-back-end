package hour.repository;

import hour.model.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search,String> {
    Page<Search> findAllByBuildingIdOrderByCountDesc(Pageable pageable,String building_id);
    Page<Search> findAllByOrderByCountDesc(Pageable pageable);
    Search findFirstByKeyContains(String key);
}
