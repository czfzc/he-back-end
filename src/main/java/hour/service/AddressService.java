package hour.service;

import hour.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AddressService {

    String addAddress(String mysession, String name, String phone_num,
                       String room_num, String build_id, String addition);

    boolean setDefault(String mysession, String address_id);

    List<Address> getAllAddress(String mysession);


    boolean editAddress(String address_id, String mysession, String name,
                        String phone_num, String room_num, String build_id, String addition);

    boolean deleteAddress(String address_id, String mysession);
}
