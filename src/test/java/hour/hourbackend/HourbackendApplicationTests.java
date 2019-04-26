package hour.hourbackend;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HourbackendApplicationTests {

    public static void main(String[] args){
        String str="[\n" +
                "  {\n" +
                "    \"service_id\": 1,\n" +
                "    \"send_method_id\": 1,\n" +
                "    \"address_id\": \"123\",\n" +
                "    \"express\": [\n" +
                "      {\n" +
                "        \"name\": \"czf\",\n" +
                "        \"phone_num\": \"17709201921\",\n" +
                "        \"sms_content\": \"请去东门领快递，凭号码128743\",\n" +
                "        \"receive_code\": \"128743\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"czf2\",\n" +
                "        \"phone_num\": \"18681805870\",\n" +
                "        \"sms_content\": \"请去南门领快递，凭号码435312\",\n" +
                "        \"receive_code\": \"435312\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";
        JSONArray json= JSONArray.parseArray(str);
        System.out.println(json.toJSONString());
    }

}
