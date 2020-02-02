package hour.repository;

import hour.model.WriteList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriteListRepository extends JpaRepository<WriteList,String> {
    boolean existsStringByUserId(String userId);
    WriteList findFirstByUserId(String userId);
}
