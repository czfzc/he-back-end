package hour.repository;

import hour.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    Address findByName(String name);
    List<Address> findByUserId(String user_id);
    Address findFirstByUserIdAndIsDefault(String user_id,Integer is_default);
    Address findByUserIdAndId(String user_id,String id);
    Address findById(String id);
    Address findFirstByUserId(String user_id);
    void deleteAddressByUserIdAndIdAndIsDefaultFalse(String user_id,String id);
}
