package hour.service;

import hour.model.*;
import hour.repository.RcmdCardRepository;
import hour.repository.SearchRepository;
import hour.repository.SearchTableRepository;
import hour.repository.ShopProductRepository;
import hour.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("SearchService")
public class SearchServiceImpl implements SearchService {

    @Autowired
    SearchRepository searchRepository;
    @Autowired
    SearchTableRepository searchTableRepository;
    @Autowired
    ShopProductService shopProductService;
    @Autowired
    RcmdCardRepository rcmdCardRepository;

    /**
     * 获取热门搜索结果
     * @param size
     * @param building_id
     * @return
     */
    @Override
    public List<Search> getHotSearch(int size, String building_id){
        PageRequest pageRequest = new PageRequest(0,size);
        if(building_id!=null)
            return searchRepository.findAllByBuildingIdOrderByCountDesc(pageRequest,building_id).getContent();
        else return searchRepository.findAllByOrderByCountDesc(pageRequest).getContent();
    }

    /**
     * 在商品列表里获取搜索建议
     * @param value
     * @param building_id
     * @param size
     * @return
     */
    @Override
    public List<SearchTable> getSearchProposalFromShopProduct(String value, String building_id, int size){
        /*搜索商品*/
        List<Product> list = shopProductService.searchProduct(building_id,value,0,size).getContent();
        List<SearchTable> searchTables = new ArrayList<>();
        for(Product product:list){
            SearchTable searchTable = new SearchTable();
            searchTable.setId(UUID.randomUUID().toString().replace("-",""));
            searchTable.setKey(product.getName());
            searchTable.setAbled(true);
            searchTable.setTime(product.getTime());
            searchTable.setBuildingId(product.getBuildingId());
            searchTable.setKeywords(product.getName());
            searchTables.add(searchTable);
        }
        return searchTables;
    }

    /**
     * 在search_table里获取搜索建议
     * @param value
     * @param building_id
     * @param size
     * @return
     */

    @Override
    public List<SearchTable> getSearchProposalFromSearchTable(String value, String building_id, int size){
        PageRequest pageRequest = new PageRequest(0,size);
        List<SearchTable> list = null;
        if (building_id != null) {
            list = searchTableRepository.
                    findAllByBuildingIdAndKeywordsContainsAndAbledTrueOrderByTimeDesc(pageRequest,building_id,value).getContent();
        }else{
            list = searchTableRepository.
                    findAllByKeywordsContainsAndAbledTrueOrderByTimeDesc(pageRequest,value).getContent();
        }
        if(list==null)
            return null;
        for(SearchTable searchTable:list){
            searchTable.setKey(getKeyFromKeywordsByValue(searchTable.getKeywords(),value));
        }
        return list;
    }

    /**
     * 在商品列表里获取搜索结果
     * @param value
     * @param building_id
     * @param page
     * @param size
     * @return
     */

    @Override
    public List<RcmdCard> getSearchOfRcmdCardFromProduct(String value, String building_id, int page, int size){
        List<Product> products = shopProductService.searchProduct(building_id,value,page,size).getContent();
        List<RcmdCard> list = new ArrayList<>();
        for(Product product:products){
            list.add(productToRcmdCard(product,building_id));
        }
        return list;
    }


    @Override
    public boolean incCountOfSearchValue(String value, String building_id){
        Search search = searchRepository.findFirstByKeyContains(value);
        if(search == null){
            search = new Search();
            search.setCount(1);
            search.setKey(value);
            search.setValue(value);
            search.setBuildingId(building_id);
            search.setAbled(true);
            search.setUri("");
            return searchRepository.save(search).getId()!=null;
        }else{
            search.setCount(search.getCount()+1);
            searchRepository.save(search);
            return true;
        }
    }

    @Override
    public boolean addSearchTable(String keywords, String uri
            , String building_id, String rcmd_card_id, boolean abled){
        SearchTable searchTable = new SearchTable();
        RcmdCard rcmdCard = rcmdCardRepository.findById(rcmd_card_id).get();
        if(rcmdCard == null)
            throw new RuntimeException("invalid rcmd_card_id");
        searchTable.setKeywords(keywords);
        searchTable.setUri(uri);
        searchTable.setBuildingId(building_id);
        searchTable.setRcmdCard(rcmdCard);
        searchTable.setTime(new Date());
        searchTable.setAbled(true);
        return searchTableRepository.saveAndFlush(searchTable).getId()!=null;
    }

    @Override
    public boolean updateSearchTable(String id, String keywords, String uri
            , String building_id, String rcmd_card_id, boolean abled){
        SearchTable searchTable = searchTableRepository.findById(id).get();
        if(searchTable == null)
            throw new RuntimeException("invalib search_table id");
        RcmdCard rcmdCard = rcmdCardRepository.findById(rcmd_card_id).get();
        if(rcmdCard == null)
            throw new RuntimeException("invalid rcmd_card_id");
        searchTable.setKeywords(keywords);
        searchTable.setUri(uri);
        searchTable.setBuildingId(building_id);
        searchTable.setRcmdCard(rcmdCard);
        searchTable.setTime(new Date());
        searchTable.setAbled(true);
        return searchTableRepository.saveAndFlush(searchTable).getId()!=null;
    }

    @Override
    public boolean deleteSearchTable(String id){
        searchTableRepository.deleteById(id);
        return true;
    }

    @Override
    public Page<SearchTable> getSearchTable(int page, int size){
        PageRequest pageRequest = new PageRequest(page,size);
        return searchTableRepository.findAllByOrderByTimeDesc(pageRequest);
    }

    private RcmdCard productToRcmdCard(Product product,String building_id){
        RcmdCardImg rcmdCardImg = new RcmdCardImg();
        RcmdCard rcmdCard = new RcmdCard();
        rcmdCardImg.setCard(rcmdCard);
        rcmdCardImg.setSize("small");
        rcmdCardImg.setUri(String.format("hour://shop/%s/%s/%s/",
                building_id,product.getTypeId(),product.getId()));
        rcmdCardImg.setImg(product.getImgLink());
        rcmdCardImg.setAbled(true);
        rcmdCardImg.setId(UUID.randomUUID().toString().replace("-",""));

        rcmdCard.setId(UUID.randomUUID().toString().replace("-",""));
        rcmdCard.setTime(new Date());
        rcmdCard.setAbled(true);
        rcmdCard.setBuildingId(building_id);
        rcmdCard.setType("cu");
        rcmdCard.setTitle(product.getName());
        rcmdCard.setAddition("￥"+product.getPrice());
        rcmdCard.setTop(true);
        rcmdCard.setVisible(true);
        rcmdCard.setLinkpic(new ArrayList<RcmdCardImg>(){
            {
                this.add(rcmdCardImg);
            }
        });
        return rcmdCard;
    }

    private String getKeyFromKeywordsByValue(String keywords,String value){
        String[] keys = keywords.split("\\+");
        for(int i=0;i<keys.length;i++){
            if(keys[i].contains(value))
                return keys[i];
        }
        return null;
    }
}
