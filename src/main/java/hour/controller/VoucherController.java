package hour.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages = "hour")
@RequestMapping("/voucher")
public class VoucherController {

    /**
     * 领取代取劵
     */
    @RequestMapping("/varify_voucher")
    String varifyVoucher(@RequestParam("mysession")String mysession,
                         @RequestParam("service_id")Integer service_id){
        return null;
    }

}
