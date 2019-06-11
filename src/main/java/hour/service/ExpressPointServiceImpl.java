package hour.service;

import hour.model.ExpressPoint;
import hour.repository.ExpressPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ExpressPointService")
public class ExpressPointServiceImpl implements  ExpressPointService{

    @Autowired
    ExpressPointRepository expressPointRepository;

    /**
     * 根据短信获取模板模型
     * @param sms_content
     * @return
     */
    @Override
    public ExpressPoint getExpressPointIdBySms(String sms_content){
        sms_content=sms_content.replaceAll("[A-Za-z0-9]","").replaceAll("\\s+","");
        List<ExpressPoint> list=expressPointRepository.findAll();
        for(int i=0;i<list.size();i++){
            String str=list.get(i).getSmsTemp().replaceAll("[A-Za-z0-9]","").replaceAll("\\s+","");
            if(str.equals(sms_content)){
                return list.get(i);
            }
        }
        return null;
    }

    /**
     * 根据模型和短信获取code
     * @param expressPoint
     * @param sms_content
     * @return
     */
    @Override
    public String getCode(ExpressPoint expressPoint, String sms_content){
        if(expressPoint==null||sms_content==null) return null;
        String code_format=expressPoint.getCodeFormat();
        return this.getCodeByCodeFormatAndSmsContent(sms_content,code_format);
    }


    /**
     * 用取货码格式字符串从取货短信里提取取货码
     * 例如 nnnn-nn 可匹配1234-22
     * aa-nnnn 可匹配xy-1234
     * n代表数字
     * a代表字母
     * @param sms_content
     * @param code_format
     * @return
     */

    private String getCodeByCodeFormatAndSmsContent(String sms_content,String code_format){
        String toret="";
        sms_content=sms_content.toLowerCase();
        char[] sms=sms_content.toCharArray();
        char[] code=code_format.toCharArray();
        for(int i=0;i<sms.length;i++){
            if((sms[i]<='9'&&sms[i]>='0')||(sms[i]<='z'&&sms[i]>='a')){
                for(int j=0;j<code.length&&j+i<sms.length;j++) {
                    if (code[j]=='n'&&(sms[i+j]>'9'||sms[i+j]<'0')){
                        toret="";
                        break;
                    }
                    if (code[j]=='a'&&(sms[i+j]>'z'||sms[i+j]<'a')){
                        toret="";
                        break;
                    }
                    if(code[j]!='a'&&code[j]!='n'&&sms[i+j]!=code[j]){
                        toret="";
                        break;
                    }
                    toret+=sms[i+j];
                    if(toret.length()==code_format.length())
                        return toret;
                }
            }
        }
        return toret;
    }


    public static void main(String[] args){
        String sms="【菜鸟驿站】您的百世快递包裹到东大校内七舍北侧菜鸟驿站，" +
                "hahaHAHA请17:30前凭1-1-6200及时取，询19904053317";
        sms=sms.replaceAll("[A-Za-z0-9]","");
      //  ExpressPointServiceImpl expressPointService=new ExpressPointServiceImpl();
        System.out.println(sms);
    }
}
