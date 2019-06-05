package hour.controller;

import com.alibaba.fastjson.JSONObject;
import hour.model.User;
import hour.repository.UserRepository;
import hour.service.UserService;
import hour.service.VoucherService;
import hour.service.WexinTokenService;
import hour.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
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

    @Value("${gongzhonghao.key}")
    String gongzhonghaoKey;

    @Autowired
    VoucherService voucherService;

    @Autowired
    WexinTokenService wexinTokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
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

    /**
     * 回复微信公众号
     *
     *关注时:
     <xml>
     <ToUserName><![CDATA[toUser]]></ToUserName>
     <FromUserName><![CDATA[FromUser]]></FromUserName>
     <CreateTime>123456789</CreateTime>
     <MsgType><![CDATA[event]]></MsgType>
     <Event><![CDATA[subscribe]]></Event>
     </xml>
     *
     * @param xml
     * @return
     */

    private String replyIt(String xml){
        System.out.println(xml);
        String me=StringUtil.getFromXml(xml,"ToUserName");
        String gzh_open_id=StringUtil.getFromXml(xml,"FromUserName");
        String msg_type=StringUtil.getFromXml(xml,"MsgType");

        User user=userRepository.findByGzhOpenId(gzh_open_id);

        String unionid=null;

        if (user == null || user.getUnionId() == null) {   //自动注册

            if (!userService.gzhRegister(gzh_open_id)) return null;

            User user2 = userRepository.findByGzhOpenId(gzh_open_id);
            unionid = user2.getUnionId();

        }else unionid=user.getUnionId();

        if("text".equals(msg_type)) {   //文本消息


            String content = StringUtil.getFromXml(xml, "Content");

            String reply = replyText(content, unionid);

            if (reply == null) return "success";

            String msg = StringUtil.xmlCreater(new HashMap() {
                {
                    this.put("!ToUserName", gzh_open_id);
                    this.put("!FromUserName", me);
                    this.put("CreateTime", new Date().getTime());
                    this.put("!MsgType", "text");
                    this.put("!Content", reply);
                }
            });

            System.out.println("msg:"+msg);

            return msg;
        }else if("event".equals(msg_type)){  //事件消息
            String event=StringUtil.getFromXml(xml,"Event");
            if("subscribe".equals(event)){      //关注公众号事件
                String msg = StringUtil.xmlCreater(new HashMap() {
                    {
                        this.put("!ToUserName", gzh_open_id);
                        this.put("!FromUserName", me);
                        this.put("CreateTime", new Date().getTime());
                        this.put("!MsgType", "text");
                        this.put("!Content", "您好啊");
                    }
                });
            }else if("SCAN".equals(event)){

            }
        }

        return "success";
    }

    private String replyText(String Content,String unionid){
        System.out.println(unionid);
        //微信小程序openid
        if("免单卡".equals(Content)) {
            User user=userRepository.findByUnionId(unionid);
            if(user==null) return "您还没有使用小程序呀，快去体验再领取吧";
            String open_id=user.getOpenId();
            if(voucherService.getMoreExpressVoucher(open_id,2))
                return "您的2张快递代取免单卡已经放入您的一小时卡包，请进入小程序查看";
            else return "您已经领取过啦～";
        }else return null;
    }

}
