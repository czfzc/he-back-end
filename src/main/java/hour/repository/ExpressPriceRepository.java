package hour.repository;

import hour.model.ExpressPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressPriceRepository extends JpaRepository<ExpressPrice,Integer> {
    ExpressPrice findFirstByDestBuildingIdAndExpressPointIdAndSizeId(String dest_building_id,
                                                            String express_point_id,String size_id);
}
