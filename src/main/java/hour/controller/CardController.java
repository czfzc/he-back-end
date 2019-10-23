package hour.controller;

import hour.model.CardGroup;
import hour.repository.CardGroupRepository;
import hour.repository.CardTypeRepository;
import hour.service.CardService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/card")
@ComponentScan(basePackages = "hour")
public class CardController {

    @Autowired
    UserService userService;

    @Autowired
    CardService cardService;


    @RequestMapping("/get_cards")
    List<CardGroup> getCards(@RequestParam("mysession")String mysession){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) throw new RuntimeException("invalid mysession");
        return cardService.getCards(user_id);
    }

    @RequestMapping("/buy_card")
    String buyCard(@RequestParam("mysession")String mysession,@RequestParam("card_type_id")String card_type_id){
        String user_id=userService.getUserId(mysession);
        if(user_id==null) throw new RuntimeException("invalid mysession");
        return cardService.buyCard(user_id,card_type_id);
    }
}
