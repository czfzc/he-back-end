package hour.service;

public interface ExpressPriceService {

    String editExpressPrice(Integer mainkey, String dest_building_id, String express_point_id,
                            Double price, String size_id);

    String addExpressPrice(String dest_building_id, String express_point_id,
                           Double price, String size_id);

    String deleteExpressPrice(Integer mainkey);
}
