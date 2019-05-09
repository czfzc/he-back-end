package hour.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages = "hour")
@RequestMapping("express_month_card")
public class ExpressMonthCardController {
}
