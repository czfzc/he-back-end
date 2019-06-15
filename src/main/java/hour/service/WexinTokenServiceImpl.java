package hour.service;

import com.alibaba.fastjson.JSONObject;
import hour.model.*;
import hour.repository.*;
import hour.util.NetUtil;
import hour.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("WexinTokenService")
public class WexinTokenServiceImpl implements WexinTokenService {

    @Autowired
    WexinTokenRepository wexinTokenRepository;

    @Autowired
    ExpressAdminRepository expressAdminRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ExpressRepository expressRepository;

    @Autowired
    PreorderRepository preorderRepository;

    @Autowired
    ExpressService expressService;

    @Autowired
    UserService userService;

    @Override
    public String getAccessToken(String appid,String appkey){
        WexinToken wexinToken=wexinTokenRepository.findFirstByAppid(appid);
        if(wexinToken==null) throw new RuntimeException("access_token fail");
        Date date=wexinToken.getEndTime();
        String access_token=wexinToken.getAccessToken();
        if(TimeUtil.getTimeDiffMin(date,new Date())<0||access_token==null||"".equals(access_token)){
            JSONObject jsonObject=JSONObject.parseObject(NetUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token",
                    "grant_type=client_credential&appid="+appid+"&secret="+appkey));
            System.out.println(jsonObject.toJSONString());
            access_token=jsonObject.getString("access_token");
            int expires_in=jsonObject.getInteger("expires_in");
            wexinToken.setAccessToken(access_token);
            wexinToken.setEndTime(TimeUtil.addMin(new Date(),expires_in/60-30));
            return wexinTokenRepository.save(wexinToken).getAccessToken();
        }else return access_token;
    }

    /**
     * 获取用户信息（公众号）
     * @param openid
     * @param appid
     * @param appkey
     * @return
     */
    @Override
    public JSONObject getInfoByOpenid(String openid, String appid, String appkey){
        String access_token=this.getAccessToken(appid,appkey);
        JSONObject jo=JSONObject.parseObject(NetUtil.sendGet("https://api.weixin.qq.com/cgi-bin/user/info",
                "access_token="+access_token+"&openid="+openid+"&lang=zh_CN"));

        System.out.println(jo.toJSONString());

        if(jo.getInteger("errcode")!=null&&jo.getInteger("errcode")==40001){
            this.getAccessToken(appid,appkey);
            try {
                Thread.sleep(100); //防止死递归
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return this.getInfoByOpenid(openid, appid, appkey);
        }

        return jo;
    }

    @Value("${wexin.push.templateid.on-finish-pay}")
    String payedTemplateId;

    @Value("${wexin.appid}")
    private String appid;

    @Value("${wexin.key}")
    private String appkey;

    @Override
    public boolean pushFinishPayed(String prepay_id, String name, Double price, Date time,
                                   String order_id, String openid){
        String access_token= getAccessToken(appid,appkey);
        JSONObject jo=new JSONObject();
        jo.put("touser",openid);
        jo.put("template_id",payedTemplateId);
        jo.put("data",new JSONObject(){
            {
                this.put("keyword1",new JSONObject() {
                    {
                        this.put("value",name);
                    }
                });
                this.put("keyword2",new JSONObject() {
                    {
                        this.put("value",String.valueOf(price));
                    }
                });
                this.put("keyword3",new JSONObject() {
                    {
                        this.put("value", TimeUtil.formatDate(time));
                    }
                });
                this.put("keyword4",new JSONObject() {
                    {
                        this.put("value", order_id);
                    }
                });
            }
        });
        jo.put("form_id",prepay_id);
        String result=NetUtil.sendPost("https://api.weixin.qq.com/cgi-bin/message/" +
                "wxopen/template/send?access_token="+access_token, jo.toJSONString());
        System.out.println( jo.toJSONString());
        System.out.println(result);
        if(JSONObject.parseObject(result).getInteger("errcode")==0)
            return true;
        else return false;
    }

    @Value("${wexin.push.templateid.on-express-withdraw}")
    String withdrawTemplateId;

    @Value("${wexin.push.defaultPhonenum}")
    String phoneNum;

    @Override
    public boolean pushWithdrawExpressInfo(String express_id){

        ExpressAdmin expressAdmin=expressAdminRepository.findByExpressId(express_id);
        if(expressAdmin==null) return false;

        String prepay_id=expressService.getPrepayIdByExpressId(express_id);

        String openid=userService.getOpenidByUserId(expressAdmin.getUserId());

        String access_token= getAccessToken(appid,appkey);
        JSONObject jo=new JSONObject();
        jo.put("touser",openid);
        jo.put("template_id",withdrawTemplateId);
        jo.put("data",new JSONObject(){
            {
                this.put("keyword1",new JSONObject() { //订单号
                    {
                        this.put("value",express_id.substring(24,32));
                    }
                });
                this.put("keyword2",new JSONObject() {  //订单类型
                    {
                        this.put("value","快递代取");
                    }
                });
                this.put("keyword3",new JSONObject() {  //骑手电话
                    {
                        this.put("value", (expressAdmin.getSenderAdminId()==null||"1".equals(expressAdmin.getSenderAdminId()))?
                                phoneNum:expressAdmin.getSenderAdminId());
                    }
                });
                this.put("keyword4",new JSONObject() {  //取件时间
                    {
                        this.put("value", TimeUtil.formatDate(new Date()));
                    }
                });
                this.put("keyword5",new JSONObject() {  //签收码
                    {
                        this.put("value", expressAdmin.getReceiveCode());
                    }
                });
                this.put("keyword6",new JSONObject() {  //取件地址
                    {
                        this.put("value", expressAdmin.getExpressPoint());
                    }
                });
                this.put("keyword7",new JSONObject() {  //送件地址
                    {
                        this.put("value", expressAdmin.getAddress());
                    }
                });
            }
        });
        jo.put("form_id",prepay_id);
        String result=NetUtil.sendPost("https://api.weixin.qq.com/cgi-bin/message/" +
                "wxopen/template/send?access_token="+access_token, jo.toJSONString());
        System.out.println( jo.toJSONString());
        System.out.println(result);
        if(JSONObject.parseObject(result).getInteger("errcode")==0)
            return true;
        else return false;
    }

    @Value("${wexin.push.templateid.on-express-sended}")
    String sendedTemplateId;

    @Override
    public boolean pushSendedExpressInfo(String express_id){

        ExpressAdmin expressAdmin=expressAdminRepository.findByExpressId(express_id);
        if(expressAdmin==null) return false;

        String prepay_id=expressService.getPrepayIdByExpressId(express_id);

        String openid=userService.getOpenidByUserId(expressAdmin.getUserId());

        String access_token= getAccessToken(appid,appkey);
        JSONObject jo=new JSONObject();
        jo.put("touser",openid);
        jo.put("template_id",sendedTemplateId);
        jo.put("data",new JSONObject(){
            {
                this.put("keyword1",new JSONObject() { //商品名称
                    {
                        this.put("value","快递代取");
                    }
                });
                this.put("keyword2",new JSONObject() {  //订单编号
                    {
                        this.put("value",express_id.substring(24,32));
                    }
                });
                this.put("keyword3",new JSONObject() {  //收货时间
                    {
                        this.put("value", TimeUtil.formatDate(new Date()));
                    }
                });
                this.put("keyword4",new JSONObject() {  //联系电话
                    {
                        this.put("value", (expressAdmin.getSenderAdminId()==null||"1".equals(expressAdmin.getSenderAdminId()))?
                                phoneNum:expressAdmin.getSenderAdminId());
                    }
                });
                this.put("keyword5",new JSONObject() {  //收货地址
                    {
                        this.put("value", expressAdmin.getAddress());
                    }
                });
                this.put("keyword6",new JSONObject() {  //收货信息
                    {
                        String str=String.format("您好，%s的快递已送到%s,请注意查收.",expressAdmin.getName(),expressAdmin.getAddress());
                        this.put("value", str);
                    }
                });
            }
        });
        jo.put("form_id",prepay_id);
        String result=NetUtil.sendPost("https://api.weixin.qq.com/cgi-bin/message/" +
                "wxopen/template/send?access_token="+access_token, jo.toJSONString());
        System.out.println( jo.toJSONString());
        System.out.println(result);
        if(JSONObject.parseObject(result).getInteger("errcode")==0)
            return true;
        else return false;
    }



}
