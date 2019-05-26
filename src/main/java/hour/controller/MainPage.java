package hour.controller;

import com.alibaba.fastjson.JSONObject;
import hour.util.NetUtil;
import hour.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;

import static hour.util.StringUtil.createStatus;

@ComponentScan(basePackages = "hour")
@RestController
@RequestMapping("/")
public class MainPage {

    @Value("${wexin.push.token}")
    String token;

    @RequestMapping("/")
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

        if(signature.equals(signature2))
            return echostr;
        else return createStatus(false);
    }
}
