package hour.controller;

import hour.service.UserService;
import hour.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static hour.util.StringUtil.createStatus;

@RestController
@ComponentScan(basePackages = "hour")
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    VoucherService voucherService;

    @Autowired
    UserService userService;

    /**
     * 领取代取劵
     */
    @RequestMapping("/varify_voucher")
    String varifyVoucher(@RequestParam("mysession")String mysession,
                         @RequestParam("card_id")String card_id){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return createStatus(false);
        return voucherService.varifyVoucher(user_id,card_id);
    }

    @RequestMapping("/get_voucher")
    List getVoucher(@RequestParam("mysession")String mysession){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) return null;
        return voucherService.getVoucher(user_id);
    }

}
