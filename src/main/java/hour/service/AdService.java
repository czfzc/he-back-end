package hour.service;

import hour.model.Ad;

import java.util.List;

public interface AdService {

    List<Ad> getAllAdsByAbleTrue();

    List<Ad> getALlAds();

    boolean addAd(String imgs, String uri, boolean abled);

    boolean updateAdById(String id, String imgs, String uri, boolean abled);

    boolean deleteAdById(String id);
}
