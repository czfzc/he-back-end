package hour.repository;

import hour.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,String> {
    Address findByName(String name);
    List<Address> findByUserIdAndAbledTrue(String user_id);
    Address findFirstByUserIdAndIsDefaultTrue(String user_id);
    Address findByUserIdAndId(String user_id,String id);
    Address findFirstByUserId(String user_id);
    Address findFirstByUserIdAndIsDefaultFalse(String user_id);
}
