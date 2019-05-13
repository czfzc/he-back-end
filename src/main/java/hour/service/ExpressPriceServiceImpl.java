package hour.service;

import hour.model.ExpressPrice;
import hour.repository.ExpressPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static hour.util.StringUtil.createStatus;

@Service("ExpressPriceService")
public class ExpressPriceServiceImpl implements  ExpressPriceService{

    @Autowired
    ExpressPriceRepository expressPriceRepository;

    @Override
    public String editExpressPrice(Integer mainkey, String dest_building_id, String express_point_id,
                                   Double price, String size_id){
        ExpressPrice expressPrice=expressPriceRepository.findById(mainkey).get();
        if(expressPrice==null) return createStatus(false);
        expressPrice.setDestBuildingId(dest_building_id);
        expressPrice.setExpressPointId(express_point_id);
        expressPrice.setPrice(price);
        expressPrice.setSizeId(size_id);
        expressPriceRepository.save(expressPrice);
        return createStatus(true);
    }

    @Override
    public String addExpressPrice(String dest_building_id, String express_point_id,
                                  Double price, String size_id){
        ExpressPrice expressPrice=new ExpressPrice();
        expressPrice.setDestBuildingId(dest_building_id);
        expressPrice.setExpressPointId(express_point_id);
        expressPrice.setPrice(price);
        expressPrice.setSizeId(size_id);
        expressPriceRepository.save(expressPrice);
        return createStatus(true);
    }

    @Override
    public String deleteExpressPrice(Integer mainkey){
        expressPriceRepository.deleteById(mainkey);
        return createStatus(true);
    }
}
