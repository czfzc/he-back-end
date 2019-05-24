package hour.controller;

import hour.model.Address;
import hour.repository.AddressRepository;
import hour.service.AddressService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hour.util.StringUtil.createStatus;

@RestController
@RequestMapping("/address")
@ComponentScan(basePackages = "hour")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    UserService userService;

    @Autowired
    AddressRepository addressRepository;

    @RequestMapping("/get_address")
    @ResponseBody
    List<Address> getAddress(@RequestParam("mysession")String mysession){
        return addressService.getAllAddress(mysession);
    }

    @RequestMapping("/set_default")
    @ResponseBody
    String setDefault(@RequestParam("mysession") String mysession,@RequestParam("address_id") String address_id){
        boolean bool=addressService.setDefault(mysession,address_id);
        return createStatus(bool);
    }

    @RequestMapping("/add_address")
    @ResponseBody
    String addAddress(@RequestParam("mysession") String mysession,@RequestParam("name") String name,
                      @RequestParam("phone_num")String phone_num,@RequestParam("room_num")String room_num,
                      @RequestParam("build_id")String build_id,@RequestParam("addition")String addition){
        return addressService.addAddress(mysession,name,phone_num,room_num,build_id,addition);
    }

    @RequestMapping("/edit_address")
    @ResponseBody
    String editAddress(@RequestParam("address_id")String address_id,
                         @RequestParam("mysession") String mysession,@RequestParam("name") String name,
                         @RequestParam("phone_num")String phone_num,@RequestParam("room_num")String room_num,
                         @RequestParam("build_id")String build_id,@RequestParam("addition")String addition){
        return createStatus(addressService.editAddress(address_id,mysession,name,phone_num,room_num,build_id,addition));
    }

    @RequestMapping("/delete_address")
    @ResponseBody
    String deleteAddress(@RequestParam("address_id")String address_id,@RequestParam("mysession")String mysession){
        return createStatus(addressService.deleteAddress(address_id, mysession));
    }

}
