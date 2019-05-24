package hour.controller;

import hour.model.Service;
import hour.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;

@ComponentScan(basePackages = "hour")
@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    ServiceRepository serviceRepository;

    @RequestMapping("/get_service")
    List<Service> getService(){
        return serviceRepository.findAll();
    }

    @RequestMapping("/get_service_protrol")
    HashMap getServiceProtrol(@RequestParam("service_id")Integer service_id){
        return new HashMap(){
            {
                Service service=serviceRepository.findById(service_id).get();
                if(service==null)
                    this.put("status",false);
                this.put("status",true);
                this.put("protrol",service.getProtrol());
            }
        };
    }

}
