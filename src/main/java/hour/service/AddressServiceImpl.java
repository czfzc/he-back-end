package hour.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hour.model.Address;
import hour.model.Building;
import hour.repository.AddressRepository;
import hour.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static hour.util.StringUtil.createStatus;

@Service("AddressService")
public class AddressServiceImpl implements AddressService{

    @Autowired
    UserService userService;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Override
    public String addAddress(String mysession, String name, String phone_num,
                              String room_num, String build_id, String addition){
        Address address=new Address();
        String id= UUID.randomUUID().toString().replace("-","");
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return createStatus(false);
        address.setId(id);
        address.setUserId(user_id);
        address.setName(name);
        address.setPhoneNum(phone_num);
        address.setRoomNum(room_num);
        address.setBuildId(build_id);
        address.setAddition(addition);
        address.setAbled(true);;
        address.setDefault(true);
        addressRepository.save(address);
        this.setDefaultDAO(user_id,id);
        return new JSONObject(){
            {
                this.put("address_id",id);
                this.put("status",true);
            }
        }.toJSONString();
    }

    @Override
    public boolean setDefault(String mysession, String address_id){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return false;
        return this.setDefaultDAO(user_id,address_id);
    }

    @Override
    public List<Address> getAllAddress(String mysession){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return null;
        List<Address> list=addressRepository.findByUserId(user_id);
        return addressRepository.findByUserId(user_id);
    }

    @Override
    public boolean editAddress(String address_id, String mysession, String name,
                               String phone_num, String room_num, String build_id, String addition) {
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return false;
        Address address=addressRepository.findByUserIdAndId(user_id,address_id);
        if(address==null) return false;
        Building building=buildingRepository.findFirstById(build_id);
        if(build_id==null) return false;
        address.setName(name);
        address.setPhoneNum(phone_num);
        address.setRoomNum(room_num);
        address.setBuildId(build_id);
        address.setAddition(addition);
        addressRepository.save(address);
        return true;
    }

    @Override
    public boolean deleteAddress(String address_id, String mysession){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return false;
        addressRepository.deleteAddressByUserIdAndIdAndIsDefaultFalse(user_id,address_id);
        return true;
    }

    private boolean setDefaultDAO(String user_id, String address_id){
        Address ad1=addressRepository.findFirstByUserIdAndIsDefault(user_id,1);
        if(ad1!=null) {
            ad1.setDefault(true);
            addressRepository.save(ad1);
        }
        Address ad2=addressRepository.findByUserIdAndId(user_id,address_id);
        ad2.setDefault(false);
        addressRepository.save(ad2);
        return true;
    }
}
