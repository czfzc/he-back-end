package hour.controller;

import com.alibaba.fastjson.JSONObject;
import hour.model.RcmdCard;
import hour.model.Search;
import hour.model.SearchTable;
import hour.service.SearchService;
import hour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@ComponentScan(basePackages = "hour")
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    UserService userService;

    @RequestMapping("/search_by_value")
    List<RcmdCard> searchByValue(String mysession,String search_value,String building_id,
                                 Integer page,Integer size){
        String userid = userService.getUserId(mysession);
        searchService.incCountOfSearchValue(search_value,building_id);
        return searchService.getSearchOfRcmdCardFromProduct(search_value,building_id,page,size);
    }

    @RequestMapping("/get_search_hot")
    List<Search> getSearchHot(String mysession, String building_id, Integer size){
        String userid = userService.getUserId(mysession);
        return searchService.getHotSearch(size,building_id);
    }

    @RequestMapping("/get_search_proposal")
    List<SearchTable> getSearchProposal(String mysession, String building_id, String value){
        String userid = userService.getUserId(mysession);
        List<SearchTable> list = new ArrayList<>();
        list.addAll(searchService.getSearchFromSearchTable(value,building_id,10));
        list.addAll(searchService.getSearchProposalFromShopProduct(value,building_id,10));
        return list;
    }
}
