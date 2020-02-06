package hour.service;

import hour.model.Ad;
import hour.repository.AdRepository;
import hour.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AdService")
public class AdServiceImpl implements AdService{

    @Autowired
    AdRepository adRepository;

    @Override
    public List<Ad> getAllAdsByAbleTrue(){
        return adRepository.findAllByAbledTrue();
    }

    @Override
    public List<Ad> getALlAds(){
        return adRepository.findAll();
    }

    @Override
    public boolean addAd(String imgs, String uri,boolean abled){
        Ad ad = new Ad();
        ad.setImgs(imgs);
        ad.setUri(uri);
        ad.setAbled(true);
        adRepository.save(ad);
        return true;
    }

    @Override
    public boolean updateAdById(String id, String imgs, String uri, boolean abled){
        Ad ad = adRepository.findById(id).get();
        if(ad == null)
            throw new RuntimeException("invalid ad id");
        ad.setImgs(imgs);
        ad.setUri(uri);
        ad.setAbled(abled);
        adRepository.save(ad);
        return true;
    }

    @Override
    public boolean deleteAdById(String id){
        adRepository.deleteById(id);
        return adRepository.findById(id).get()==null;
    }

}
