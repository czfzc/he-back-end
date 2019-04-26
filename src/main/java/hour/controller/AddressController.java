package hour.controller;

import hour.model.Address;
import hour.repository.AddressRepository;
import hour.service.AddressService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static hour.Util.StringUtil.createStatus;

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
    String addAddress(@RequestParam("name") String name,@RequestParam("mysession") String mysession,
                      @RequestParam("phone_num")String phone_num,@RequestParam("room_num")String room_num,
                      @RequestParam("build_id")String build_id,@RequestParam("addition")String addition){
        addressService.addAddress(mysession,name,phone_num,room_num,build_id,addition);
        return createStatus(true);
    }

}
