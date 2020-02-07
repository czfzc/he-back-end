package hour.repository;

import hour.model.SearchTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchTableRepository extends JpaRepository<SearchTable,String> {

    Page<SearchTable> findAllByBuildingIdAndKeywordsContainsAndAbledTrueOrderByTimeDesc(Pageable pageable,
                                                                         String building_id,
                                                                         String keyword);
    Page<SearchTable> findAllByKeywordsContainsAndAbledTrueOrderByTimeDesc(Pageable pageable,
                                                                                        String keyword);
    Page<SearchTable> findAllByOrderByTimeDesc(Pageable pageable);
}
