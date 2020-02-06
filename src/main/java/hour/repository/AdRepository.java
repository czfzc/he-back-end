package hour.repository;

import hour.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad,String> {
    List<Ad> findAllByAbledTrue();
}
