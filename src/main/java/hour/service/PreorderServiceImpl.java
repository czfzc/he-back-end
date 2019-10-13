package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.*;
import hour.repository.*;
import hour.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service("PreorderService")
public class PreorderServiceImpl implements PreorderService{

    @Autowired
    PreorderRepository preorderRepository;

    @Autowired
    ExpressService expressService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ExpressRepository expressRepository;

    @Autowired
    ExpressMonthCardRepository expressMonthCardRepository;

    @Autowired
    ExpressMonthCardService expressMonthCardService;

    @Autowired
    MoreProductRepository moreProductRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ShopProductService shopProductService;

    @Autowired
    UserProductService userProductService;

    @Autowired
    UserProductRepository userProductRepository;

    @Autowired
    BuildingRepository buildingRepository;


    @Override
    public double calculateTotal(String order_id) {
        String sql="select sum(u.totalFee) " +
                "from Preorder u where u.orderId='"+order_id+"'";
        Query query=entityManager.createQuery(sql);
        double sum=(double)query.getSingleResult();
        return sum;
    }

    @Override
    public List<Preorder> getAllPreorderByOrderId(String order_id){
        List<Preorder> list=preorderRepository.findAllByOrderId(order_id);
        for(Iterator<Preorder> i=list.iterator();i.hasNext();){
            Preorder preorder=i.next();

            hour.model.Service service = serviceRepository.findById(preorder.getServiceId()).get();
            if(!service.isShow()) {
                i.remove();
                continue;
            }

            if(preorder.getServiceId()==1){ //快递代取
                preorder.setExpress(expressService.getExpress(preorder.getId()));
                preorder.setAddress(addressRepository.findById(preorder.getAddressId()).get());
            }else if(preorder.getServiceId()==2){ //零食外送
                preorder.setUserProduct(userProductService.getUserProducts(preorder.getId()));
                preorder.setAddress(addressRepository.findById(preorder.getAddressId()).get());
            }


        }

        return list;
    }

    @Override
    public Page<Preorder> getAllPreorder(Integer page, Integer size){
        PageRequest pageable=new PageRequest(page,size, Sort.Direction.DESC,"time");

        Page<Preorder> pages=preorderRepository.findAll(pageable);

        for(Iterator<Preorder> i=pages.iterator();i.hasNext();){
            Preorder preorder=i.next();
            if(preorder.getServiceId()==1){ //快递代取
                preorder.setExpress(expressService.getExpress(preorder.getId()));
                preorder.setAddress(addressRepository.findById(preorder.getAddressId()).get());
            }else if(preorder.getServiceId()==2){ //零食外送
                preorder.setUserProduct(userProductService.getUserProducts(preorder.getId()));
                preorder.setAddress(addressRepository.findById(preorder.getAddressId()).get());
            }
        }
        return pages;
    }

    @Value("${express.max-express-count}")
    Integer max_express_count;

    @Value("${express.card.product-id}")
    String addressCardProductId;

    @Value("${express.product-id}")
    String addressProductId;

    @Value("${shop-product.product-id}")
    String shopProductProductId;

    @Value("${express.card.first-card-price}")
    Double firstCardPrice;

    @Override
    public boolean preorderIt(JSONArray arr,String order_id,String user_id) {
        if(arr==null) return false;
        if(arr.size()==0)
            return false;
        for(int i=0;i<arr.size();i++){
            JSONObject jo=arr.getJSONObject(i);
            if(jo==null) continue;
            if(!serviceRepository.findById(jo.getInteger("service_id")).get().isAbled()) //判断业务不可用则下预付单失败
                continue;
            if(jo.getInteger("service_id")==1){ //快递代取业务预付单

                JSONArray express=jo.getJSONArray("express");
                if(express.size()>max_express_count)             //快递数目不得超过指定件数
                    continue;
                String address_id=jo.getString("address_id");
                String send_method_id=jo.getString("send_method_id");

                Preorder preorder=new Preorder();
                preorder.setAddressId(address_id);
                preorder.setTime(new Date());
                preorder.setOrderId(order_id);
                preorder.setUserId(user_id);
                preorder.setServiceId(1);
                preorder.setStatus(0);
                preorder.setStatusData("0");
                preorder.setPayed(0);
                preorder.setAbled(true);
                preorder.setSendMethodId(send_method_id);
                preorder.setTotalFee(0D);
                preorder.setProductId(addressProductId);
                preorder.setAddition(jo.getString("addition"));

                String preorder_id=preorderRepository.save(preorder).getId();

                if(expressService.addExpress(express,preorder_id,address_id,user_id,send_method_id)){
                    Double total = expressService.getTotalPrice(preorder_id);
                    preorder.setTotalFee(total);
                    preorderRepository.save(preorder);
                }else throw new RuntimeException("unify order fail");
            }else if(jo.getInteger("service_id")==2){   //零食上门业务预付单

                Date time = new Date();
                JSONArray products = jo.getJSONArray("products");
                String address_id = jo.getString("address_id");
                String send_method_id = jo.getString("send_method_id");
                String addition = jo.getString("addition");
                Integer status = jo.getInteger("status");
                Address address = addressRepository.findById(address_id).get();
                if(address==null)
                    throw new RuntimeException("无效的address_id");
                Building building = buildingRepository.findFirstById(address.getBuildId());
                if(!building.isShopAbled())
                    throw new RuntimeException("现在零食店已打烊");
                if(status==0&&!building.isShopSendAbled())
                    throw new RuntimeException("现在不可以提供上门服务");
                if(status==1&&!building.isShopWithdrawAbled())
                    throw new RuntimeException("现在不可以提供自提服务");

                Preorder preorder = new Preorder();
                preorder.setTotalFee(0D);
                preorder.setAddressId(address_id);
                preorder.setTime(time);
                preorder.setOrderId(order_id);
                preorder.setUserId(user_id);
                preorder.setServiceId(2);
                preorder.setStatus(status);
                preorder.setExtraData(address.getBuildId()); //零食预付单的extra_data为楼号
                if(status == 0 || status == 1){
                    preorder.setStatusData("0");
                }else{
                    throw new RuntimeException("Invalid preorder status");
                }
                preorder.setPayed(0);
                preorder.setAbled(true);
                preorder.setSendMethodId(send_method_id);
                preorder.setProductId(shopProductProductId);
                preorder.setAddition(addition);

                String preorder_id=preorderRepository.save(preorder).getId();

                if(userProductService.addUserProducts(products,preorder_id,user_id,time)){
                    Double total = userProductService.getTotalPrice(preorder_id);

                    List<MoreProduct> list = moreProductRepository.
                            findAllByServiceIdAndAddition(2,
                                    String.valueOf(status==null ?
                                            -1:status));
                    for(int j=0;j<list.size();j++){
                        MoreProduct moreProduct = list.get(j);
                        total+=moreProduct.getSum();
                    }

                    preorder.setTotalFee(total);

                    preorderRepository.save(preorder);
                }else throw new RuntimeException("统一下单失败");
            }else if(jo.getInteger("service_id")==9){   //购买月代取卡

                MoreProduct moreProduct=moreProductRepository.
                        findFirstByProductId("09201921"); //月卡产品的产品id为09201921
                if(moreProduct==null) return false;
                Double total=0D;
                if(expressMonthCardService.isNoCard(user_id)){
                    total=firstCardPrice;
                }else total=moreProduct.getSum();

                Preorder preorder=new Preorder();
                preorder.setTime(new Date());
                preorder.setOrderId(order_id);
                preorder.setUserId(user_id);
                preorder.setServiceId(9);
                preorder.setStatus(0);
                preorder.setPayed(0);
                preorder.setProductId(addressCardProductId);
                preorder.setAbled(true);
                preorder.setTotalFee(total);
                preorder.setSendMethodId("4");
                if (preorderRepository.save(preorder).getId()==null)
                    throw new RuntimeException("unify order fail");;
            }
        }
        return true;
    }

    /**
     *根据预付单号搜索预付单
     */

    @Override
    public Page<Preorder> searchPreorderById(String value, Integer page, Integer size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Preorder> preorder=preorderRepository.findAllByIdContaining(value,pageable);
        return preorder;
    }

    /**
     * 根据用户账号搜索预付单
     */
    @Override
    public Page<Preorder> searchPreorderByUserId(String value, Integer page, Integer size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Preorder> preorder=preorderRepository.findAllByUserIdContaining(value,pageable);
        return preorder;
    }

    /**
     * 计算该快递预付单的价格，传入一个preorder的JSONObject
     */
    @Value("${express.max-express-count}")
    Integer expressMaxExpressCount;
    @Override
    public double cacuExpressTotalByObject(JSONObject preorder){
        double sum=0;
        JSONArray express=preorder.getJSONArray("express");
        if(express.size()<=expressMaxExpressCount)
            for(int i=0;i<express.size();i++)
                sum+=expressService.getTotalByObject(express.getJSONObject(i));
        return sum;
    }

    @Override
    public Page<Preorder> getProductSendPreorderByBuildingId(String building_id, Integer page, Integer size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Preorder> ret = null;
        if(building_id == null){
            ret = preorderRepository.findByServiceIdAndStatusAndPayed(2,0,1,pageable);
        }else{
            ret = preorderRepository.findByServiceIdAndStatusAndPayedAndExtraData(2,0,1,building_id,pageable);
        }
        List<Preorder> list = ret.getContent();
        for(Iterator<Preorder> i=list.iterator();i.hasNext();){
            Preorder preorder=i.next();
            preorder.setUserProduct(userProductService.getUserProducts(preorder.getId()));
            preorder.setAddress(addressRepository.findById(preorder.getAddressId()).get());
        }
        return ret;
    }

    @Override
    public Page<Preorder> getProductWithdrawPreorderByBuildingId(String building_id, Integer page, Integer size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Preorder> ret = null;
        if(building_id == null){
            ret = preorderRepository.findByServiceIdAndStatusAndPayed(2,1,1,pageable);
        }else{
            ret = preorderRepository.findByServiceIdAndStatusAndPayedAndExtraData(2,1,1,building_id,pageable);
        }
        List<Preorder> list = ret.getContent();
        for(Iterator<Preorder> i=list.iterator();i.hasNext();){
            Preorder preorder=i.next();
            preorder.setUserProduct(userProductService.getUserProducts(preorder.getId()));
            preorder.setAddress(addressRepository.findById(preorder.getAddressId()).get());
        }
        return ret;
    }

}
