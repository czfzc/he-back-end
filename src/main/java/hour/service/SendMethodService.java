package hour.service;

import hour.model.SendMethod;

import java.util.List;

public interface SendMethodService {
    List<SendMethod> getSendMethodByServiceId(String service_id);
}
