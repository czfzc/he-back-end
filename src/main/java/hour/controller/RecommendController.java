package hour.controller;

import hour.model.Ad;
import hour.model.RcmdCard;
import hour.service.AdService;
import hour.service.RcmdCardService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommends")
@ComponentScan(basePackages = "hour")
public class RecommendController {

    @Autowired
    RcmdCardService rcmdCardService;

    @Autowired
    UserService userService;

    @Autowired
    AdService adService;

    @RequestMapping("/get_recommend_cards")
    List<RcmdCard> getRecommendCards(String mysession,String building_id){
        String userid = userService.getUserId(mysession);
        return rcmdCardService.getAllRcmdCardByBuildingId(building_id);
    }

    @RequestMapping("/get_ads")
    List<Ad> getAds(String mysession,String building_id){
        String userid = userService.getUserId(mysession);
        return adService.getAllAdsByAbleTrue();
    }
}
