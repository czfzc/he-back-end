package hour.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

import static hour.util.CodeUtil.md5;

public class StringUtil {
    /**
     * 创建状态json字符串
     * @param bool
     * @return
     */
    public static String createStatus(boolean bool){
        return "{\"status\":"+bool+"}";
    }

    /**
     * 传入map 根据map制作xml格式化字符串
     * @param map key的第一个字符为'!'则内容为CDATA
     * @return
     */

    public static String xmlCreater(Map<String,String> map){
        String toret="";
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("xml");
            for(String key:map.keySet()){
                boolean a = key.charAt(0)=='!';
                Element rss = root.addElement(key.replaceFirst("!", ""));
                if(!a)
                    rss.setText(String.valueOf(map.get(key)));   //无cdata
                else{
                    rss.addCDATA(String.valueOf(map.get(key)));  //有cdata
                }
            }
            StringWriter sw=new StringWriter();
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setSuppressDeclaration(true);
            XMLWriter xmlWriter = new XMLWriter(sw, format);
            xmlWriter.write(document);
            toret=sw.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return toret.replaceFirst("\n", "");
    }

    public static String getRawContent(HttpServletRequest request){
        try {
            InputStream is=request.getInputStream();
            byte[] b=new byte[request.getContentLength()];
            is.read(b);
            is.close();
            return new String(b,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readFileFromResources(String filename){
        String toret="";
        Resource res = new ClassPathResource("data.json");
        InputStream is= null;
        try {
            is = res.getInputStream();
            BufferedReader bf=new BufferedReader(new InputStreamReader(is));
            String line=null;
            while((line=bf.readLine())!=null){
                toret+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toret;
    }

    public static String getRandom(int length){
        return String.valueOf((int)(new Random().nextInt((int)((Math.pow(10,length)-Math.pow(10, length-1))))+Math.pow(10, length-1)));
    }

    /**
     * 计算微信接口的sign
     * @param map
     * @param mykey
     * @return
     */

    public static String calculateSign(Map map,String mykey){
        String toret="";
        Collection<String> keyset=map.keySet();
        List<String> list=new ArrayList<String>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            toret+=(list.get(i).replaceFirst("!", "")+"="+map.get(list.get(i))+"&");
        }

        toret+="key="+mykey;
        System.out.println(toret);
        return md5(toret).toUpperCase();
    }

    public static String getFromXml(String xml,String name){
        try {
            Document document= DocumentHelper.parseText(xml);
            Element element=document.getRootElement();
            return element.element(name).getText();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

}
