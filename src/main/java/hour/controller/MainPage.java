package hour.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan(basePackages = "hour")
@RestController
@RequestMapping("/")
public class MainPage {
    @RequestMapping("/")
    String main(){
        return "Hello world!";
    }
}
