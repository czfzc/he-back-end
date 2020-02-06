package hour.service;

import hour.model.RcmdCard;
import hour.model.Search;
import hour.model.SearchTable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SearchService {


    List<Search> getHotSearch(int size, String building_id);

    List<SearchTable> getSearchProposalFromShopProduct(String value, String building_id, int size);

    List<SearchTable> getSearchFromSearchTable(String value, String building_id, int size);

    List<RcmdCard> getSearchOfRcmdCardFromProduct(String value, String building_id, int page, int size);

    boolean incCountOfSearchValue(String value, String building_id);

    boolean addSearchTable(String keywords, String uri
            , String building_id, String rcmd_card_id, boolean abled);

    boolean updateSearchTable(String id, String keywords, String uri
            , String building_id, String rcmd_card_id, boolean abled);

    boolean deleteSearchTable(String id);

    Page<SearchTable> getSearchTable(int page, int size);
}
