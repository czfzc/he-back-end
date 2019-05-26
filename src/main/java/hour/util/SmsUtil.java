package hour.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class SmsUtil {

    @Value("${tx.sms.appid}")
    private static int appid;

    @Value("${tx.sms.appkey}")
    private static String appkey;

    @Value("${tx.sms.name}")
    private static String smsname;

    public static boolean sendSingleSms(int templateId,String phoneNumber,String[] params){
        try {
            SmsSingleSender sender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = sender.sendWithParam("86", phoneNumber,
                    templateId, params, smsname, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
        return true;
    }


    public static void main(String[] args){
      //  SmsUtil.sendSingleSms(339568,"17709201921",new String[]{"1","2","3"});
        String access_token=JSONObject.parseObject(NetUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token",
                "grant_type=client_credential&appid=wxa29ce004daf82d48&secret=b496aabfc1e41f8f0679867fcd10e0bb"))
                .getString("access_token");
        JSONObject jo=new JSONObject();
        jo.put("touser","owdWH5DUOjonvHzlVHwP_sdJvj1c");
        jo.put("template_id","K9vpfc9NIVknYqDDthqpwdl50EOdojmAr5QlG8tXE44");
        jo.put("data",new JSONObject(){
            {
                this.put("keyword1","梨子五斤");
                this.put("keyword2","15.9元");
                this.put("keyword3","2019.5.25");
                this.put("keyword4","3476130");
            }
        });
        jo.put("form_id","123");
        String result=NetUtil.sendPost("https://api.weixin.qq.com/cgi-bin/message/" +
                        "wxopen/template/send?access_token="+access_token, jo.toJSONString());
        System.out.println(result);
        if(JSONObject.parseObject(result).getInteger("errcode")==0)
            return;
    }
}
