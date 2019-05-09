package hour.service;

public interface ExpressPointService {


    String getExpressPointIdBySms(String sms_content);

    String getCodeBySms(String sms_content);
}
