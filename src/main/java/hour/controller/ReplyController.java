package hour.controller;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import hour.service.VoucherService;
import hour.util.NetUtil;
import hour.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static hour.util.StringUtil.createStatus;

@ComponentScan(basePackages = "hour")
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Value("${gongzhonghao.push.token}")
    String token;

    @Value("${gongzhonghao.push.aeskey}")
    String aeskey;

    @Value("${gongzhonghao.appid}")
    String gongzhonghaoAppid;

    @Autowired
    VoucherService voucherService;
/**
    @RequestMapping("/check")
    String main(@RequestParam("signature")String signature,
                @RequestParam("timestamp")String timestamp,
                @RequestParam("nonce")String nonce,
                @RequestParam("echostr")String echostr){

        ArrayList<String> list=new ArrayList<String>();
        list.add(nonce);
        list.add(timestamp);
        list.add(token);
        Collections.sort(list);

        String signature2=DigestUtils.sha1Hex(list.get(0)+list.get(1)+list.get(2));

        System.out.println(signature2+"  "+signature);

        if(signature.equals(signature2))
            return echostr;
        else return createStatus(false);
    }
**/

    @RequestMapping("/check")
    String main(@RequestParam("signature")String signature,
                @RequestParam("timestamp")String timestamp,
                @RequestParam("nonce")String nonce,
                @RequestParam("openid")String openid,
                HttpServletRequest request) throws Exception {
        String xml=StringUtil.getRawContent(request);
        String reply=replyIt(xml);
        if(reply==null) return "success";
        return reply;
    }


    private String replyIt(String xml){
        System.out.println(xml);
        String me=StringUtil.getFromXml(xml,"ToUserName");
        String user=StringUtil.getFromXml(xml,"FromUserName");
        String Content=StringUtil.getFromXml(xml,"Content");
        String reply=doAction(Content,user);
        if(reply==null) return null;
        String msg=StringUtil.xmlCreater(new HashMap(){
            {
                this.put("!ToUserName",user);
                this.put("!FromUserName",me);
                this.put("CreateTime",new Date().getTime());
                this.put("!MsgType","text");
                this.put("!Content",reply);
            }
        });
        return msg;
    }

    private String doAction(String Content,String open_id){
        if("免单卡".equals(Content)) {
            if(voucherService.getMoreExpressVoucher(open_id,2))
                return "您的2张快递代取免单卡已经放入您的一小时卡包，请进入小程序查看";
            else return "您已经领取过啦～";
        }else return null;
    }

}
