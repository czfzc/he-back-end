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
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return createStatus(false);

        if(name==null||phone_num==null||room_num==null)
            return createStatus(false);;

        address.setUserId(user_id);
        address.setName(name);
        address.setPhoneNum(phone_num);
        address.setRoomNum(room_num);
        address.setBuildId(build_id);
        address.setAddition(addition);
        address.setAbled(true);
        address.setDefault(false);
        final Address address1=addressRepository.save(address);
        this.setDefaultDAO(user_id,address1.getId());
        return new JSONObject(){
            {
                this.put("address",address1);
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
        return addressRepository.findByUserIdAndAbledTrue(user_id);
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

        if(name==null||phone_num==null||room_num==null)
            return false;
        address.setName(name);
        address.setPhoneNum(phone_num);
        address.setRoomNum(room_num);
        address.setBuildId(build_id);
        address.setAddition(addition);
        return addressRepository.save(address).getPhoneNum().equals(phone_num);
    }

    /**
     * 删除收货地址
     * @param address_id
     * @param mysession
     * @return
     */

    @Override
    public boolean deleteAddress(String address_id, String mysession){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return false;
        Address address=addressRepository.findByUserIdAndId(user_id,address_id);
        address.setAbled(false);
        if(address.isDefault()){
            Address ad2=addressRepository.findFirstByUserIdAndIsDefaultFalse(user_id);
            return (!addressRepository.save(address).isAbled())&&
                    this.setDefaultDAO(user_id,ad2.getId());
        }
        return !addressRepository.save(address).isAbled();
    }

    @Override
    public String getDefaultBuildingIdByUserId(String userId){
        Address address = addressRepository.findFirstByUserIdAndIsDefaultTrue(userId);
        return address==null?null:address.getBuildId();
    }

    /**
     * 设置默认收货地址的私有方法
     * @param user_id
     * @param address_id
     * @return
     */

    private boolean setDefaultDAO(String user_id, String address_id){
        Address ad1=addressRepository.findFirstByUserIdAndIsDefaultTrue(user_id);
        if(ad1!=null) {
            ad1.setDefault(false);
            addressRepository.save(ad1);
        }
        Address ad2=addressRepository.findByUserIdAndId(user_id,address_id);
        if(ad2==null) return false;
        ad2.setDefault(true);
        return addressRepository.save(ad2).isDefault();
    }
}
