package hour.service;

import hour.model.ExpressPoint;

public interface ExpressPointService {


    ExpressPoint getExpressPointIdBySms(String sms_content);

    String getCode(ExpressPoint expressPoint, String sms);
}
