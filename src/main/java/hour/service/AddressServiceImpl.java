package hour.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hour.model.Address;
import hour.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("AddressService")
public class AddressServiceImpl implements AddressService{

    @Autowired
    UserService userService;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public boolean addAddress(String mysession, String name, String phone_num,
                              String room_num, String build_id, String addition){
        Address address=new Address();
        String id= UUID.randomUUID().toString().replace("-","");
        String user_id=userService.getUserId(mysession);
        address.setId(id);
        address.setUserId(user_id);
        address.setName(name);
        address.setPhoneNum(phone_num);
        address.setRoomNum(room_num);
        address.setBuildId(build_id);
        address.setAddition(addition);
        address.setAbled(0);;
        address.setIsDefault(0);
        addressRepository.save(address);
        this.setDefaultDAO(user_id,id);
        return true;
    }

    @Override
    public boolean setDefault(String mysession, String address_id){
        String user_id=userService.getUserId(mysession);
        return this.setDefaultDAO(user_id,address_id);
    }

    @Override
    public List<Address> getAllAddress(String mysession){
        String user_id=userService.getUserId(mysession);
        List<Address> list=addressRepository.findByUserId(user_id);
        return addressRepository.findByUserId(user_id);
    }

    private boolean setDefaultDAO(String user_id, String address_id){
        Address ad1=addressRepository.findFirstByUserIdAndIsDefault(user_id,1);
        if(ad1!=null) {
            ad1.setIsDefault(0);
            addressRepository.save(ad1);
        }
        Address ad2=addressRepository.findByUserIdAndId(user_id,address_id);
        ad2.setIsDefault(1);
        addressRepository.save(ad2);
        return true;
    }
}
