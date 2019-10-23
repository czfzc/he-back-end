package hour.controller;

import hour.model.Building;
import hour.repository.BuildingRepository;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static hour.util.StringUtil.createStatus;

@RestController
@RequestMapping("/building")
@ComponentScan(basePackages = "hour")
public class BuildingController {

    @Autowired
    BuildingRepository buildingRepository;

    @RequestMapping("/get_building")
    List<Building> getBuilding(){

        return buildingRepository.findAllByAbledTrue();
    }
}
