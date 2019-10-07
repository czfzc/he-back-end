package hour.service;

import hour.model.SendMethod;

import java.util.List;

public interface SendMethodService {
    List<SendMethod> getSendMethodByServiceId(Integer service_id);
}
