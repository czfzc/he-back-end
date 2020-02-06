package hour.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hour.model.RcmdCard;
import hour.model.RcmdCardImg;
import hour.repository.RcmdCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("RcmdCardService")
public class RcmdCardServiceImpl implements RcmdCardService {

    @Autowired
    AddressService addressService;

    @Autowired
    RcmdCardRepository rcmdCardRepository;

    @Override
    public List<RcmdCard> getAllRcmdCardByBuildingId(String building_id){
        List<RcmdCard> cards = new ArrayList<RcmdCard>();
        if(building_id == null){
            /*数据库里存储了的主页卡片*/
            cards.addAll(rcmdCardRepository.findAllByAbledTrueAndVisibleTrueOrderByTimeDesc());
        }else{
            cards.addAll(rcmdCardRepository.
                    findAllByBuildingIdAndAbledTrueAndVisibleTrueOrderByTimeDesc(building_id));
        }
        /*热门商品推荐卡片*/
        cards.addAll(this.getHotProductRcmdCard(building_id));
        /*用户喜好商品推荐卡片*/
        cards.addAll(this.getUserPreferRcmdCard(building_id));
        return cards;
    }

    /**
     * 获取热门商品
     * @param building_id
     * @return
     */
    private List<RcmdCard> getHotProductRcmdCard(String building_id){
        return null;
    }


    /**
     * 获取用户喜好商品
     * @param building_id
     * @return
     */
    private List<RcmdCard> getUserPreferRcmdCard(String building_id){
        return null;
    }

    /**
     * 获取所有推荐卡片
     * @return
     */

    @Override
    public List<RcmdCard> getAllRcmdCard(){
        return rcmdCardRepository.findAllByOrderByTimeDesc();
    }

    @Override
    public boolean delRcmdCardById(String id){
        rcmdCardRepository.deleteById(id);
        return true;
    }

    /**
     *          {
     *             "type":"ad_1", //类型
     *             "building_id":"",
     *             "tip":"广告" 		//角标 标记此卡片的类型
     *             "title":"",		//标题
     *             "addition":"",	//补充
     *             "pageuri":"",	//点击主卡片的uri
     *             "top":true,		//是否置顶
     *             "abled":true,
     *             "linkpic":[	//链接图片
     *                 {
     *                 	"img":"",	//图片链接 必须
     *                 	"uri":"",	//单击此图片的uri 非必须
     *                 	"size":"small"  //图片大小 large middle small 默认small
     *                 }
     *             ]
     *         }
     * @param jsonObject
     * @return
     */

    @Override
    public boolean addRcmdCard(JSONObject jsonObject){
        RcmdCard rcmdCard = new RcmdCard();
        String id = UUID.randomUUID().toString().replace("-","");
        rcmdCard.setId(id);
        rcmdCard.setBuildingId(jsonObject.getString("building_id"));
        rcmdCard.setType(jsonObject.getString("type"));
        rcmdCard.setTip(jsonObject.getString("tip"));
        rcmdCard.setTitle(jsonObject.getString("title"));
        rcmdCard.setAddition(jsonObject.getString("addition"));
        rcmdCard.setTop(jsonObject.getBoolean("top"));
        rcmdCard.setPageuri(jsonObject.getString("pageuri"));
        rcmdCard.setTime(new Date());
        JSONArray linkpics = jsonObject.getJSONArray("linkpic");
        rcmdCard.setAbled(jsonObject.getBoolean("abled"));
        List<RcmdCardImg> links = new ArrayList<RcmdCardImg>();
        for(int i=0;i<linkpics.size();i++){
            JSONObject linkpic = linkpics.getJSONObject(i);
            RcmdCardImg rcmdCardImg = new RcmdCardImg();
            rcmdCardImg.setCard(rcmdCard);
            rcmdCardImg.setImg(linkpic.getString("img"));
            rcmdCardImg.setUri(linkpic.getString("uri"));
            rcmdCardImg.setSize(linkpic.getString("size")==null?"small":linkpic.getString("size"));
            links.add(rcmdCardImg);
        }
        rcmdCard.setLinkpic(links);
        return rcmdCardRepository.save(rcmdCard).getId()!=null;
    }

    /**
     *          {
     *             "id":"",
     *             "type":"ad_1", //类型
     *             "tip":"广告" 		//角标 标记此卡片的类型
     *             "title":"",		//标题
     *             "addition":"",	//补充
     *             "pageuri":"",	//点击主卡片的uri
     *             "top":true,		//是否置顶
     *             "abled":true,
     *             "linkpic":[	//链接图片
     *                 {
     *                 	"img":"",	//图片链接 必须
     *                 	"uri":"",	//单击此图片的uri 非必须
     *                 	"size":"small"  //图片大小 large middle small 默认small
     *                 }
     *             ]
     *         }
     * @param jsonObject
     * @return
     */

    @Override
    public boolean updateRcmdCard(JSONObject jsonObject){
        RcmdCard rcmdCard = rcmdCardRepository.findById(jsonObject.getString("id")).get();
        if(rcmdCard==null)
            throw new RuntimeException("invalid rcmdcard id");
        rcmdCard.setBuildingId(jsonObject.getString("building_id"));
        rcmdCard.setType(jsonObject.getString("type"));
        rcmdCard.setTip(jsonObject.getString("tip"));
        rcmdCard.setTitle(jsonObject.getString("title"));
        rcmdCard.setAddition(jsonObject.getString("addition"));
        rcmdCard.setTop(jsonObject.getBoolean("top"));
        rcmdCard.setTime(new Date());
        rcmdCard.setAbled(jsonObject.getBoolean("abled"));
        JSONArray linkpics = jsonObject.getJSONArray("linkpic");
        List<RcmdCardImg> links = new ArrayList<RcmdCardImg>();
        for(int i=0;i<linkpics.size();i++){
            JSONObject linkpic = linkpics.getJSONObject(i);
            RcmdCardImg rcmdCardImg = new RcmdCardImg();
            rcmdCardImg.setImg(linkpic.getString("img"));
            rcmdCardImg.setUri(linkpic.getString("uri"));
            rcmdCardImg.setSize(linkpic.getString("size"));
            links.add(rcmdCardImg);
        }
        rcmdCard.setLinkpic(links);
        return rcmdCardRepository.save(rcmdCard).getId()!=null;
    }
}
