package hour.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class PushUtil {
    public static boolean pushFinishPayed(String prepay_id, String name, Double price, Date time, String order_id){
        String access_token= JSONObject.parseObject(NetUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token",
                "grant_type=client_credential&appid=wxa29ce004daf82d48&secret=b496aabfc1e41f8f0679867fcd10e0bb"))
                .getString("access_token");
        JSONObject jo=new JSONObject();
        jo.put("touser","owdWH5DUOjonvHzlVHwP_sdJvj1c");
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
        System.out.println("https://api.weixin.qq.com/cgi-bin/message/" +
                "wxopen/template/send?access_token="+access_token);
        System.out.println( jo.toJSONString());
        System.out.println(result);
        if(JSONObject.parseObject(result).getInteger("errcode")==0)
            return true;
        else return false;
    }
}
