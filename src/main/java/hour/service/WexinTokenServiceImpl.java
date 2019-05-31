package hour.service;

import com.alibaba.fastjson.JSONObject;
import hour.model.WexinToken;
import hour.repository.WexinTokenRepository;
import hour.util.NetUtil;
import hour.util.PushUtil;
import hour.util.StringUtil;
import hour.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("WexinTokenService")
public class WexinTokenServiceImpl implements WexinTokenService {

    @Autowired
    WexinTokenRepository wexinTokenRepository;

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
            wexinToken.setEndTime(TimeUtil.addMin(new Date(),expires_in/60-5));
            return wexinTokenRepository.save(wexinToken).getAccessToken();
        }else return access_token;
    }

    @Override
    public String getUnionidByOpenid(String openid, String appid, String appkey){
        String access_token=this.getAccessToken(appid,appkey);
        JSONObject jo=JSONObject.parseObject(NetUtil.sendGet("https://api.weixin.qq.com/cgi-bin/user/info",
                "access_token="+access_token+"&openid="+openid+"&lang=zh_CN"));
        System.out.println(jo.toJSONString());
        if(jo.getInteger("errcode")!=null) return null;
        String unionid=jo.getString("unionid");
        return unionid;
    }

    @Override
    public boolean pushFinishPayed(String prepay_id, String name, Double price, Date time,
                                   String order_id, String openid, String appid, String appkey){
        String access_token= getAccessToken(appid,appkey);
        JSONObject jo=new JSONObject();
        jo.put("touser",openid);
        jo.put("template_id","K9vpfc9NIVknYqDDthqpwdl50EOdojmAr5QlG8tXE44");
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
}
