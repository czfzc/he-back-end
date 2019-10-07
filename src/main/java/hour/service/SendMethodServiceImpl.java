package hour.service;

import hour.model.SendMethod;
import hour.repository.SendMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SendMethodService")
public class SendMethodServiceImpl implements SendMethodService {

    @Autowired
    SendMethodRepository sendMethodRepository;

    @Override
    public List<SendMethod> getSendMethodByServiceId(Integer service_id){
        return sendMethodRepository.findAllByServiceId(service_id);
    }
}
