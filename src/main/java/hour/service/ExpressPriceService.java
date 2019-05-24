package hour.service;

import hour.model.ExpressPrice;
import org.springframework.data.domain.Page;

public interface ExpressPriceService {

    String editExpressPrice(Integer mainkey, String dest_building_id, String express_point_id,
                            Double price, String size_id,String send_method_id);

    String addExpressPrice(String dest_building_id, String express_point_id,
                           Double price, String size_id);

    String deleteExpressPrice(Integer mainkey);

    Page<ExpressPrice> getExpressPrice(Integer page, Integer size);
}
