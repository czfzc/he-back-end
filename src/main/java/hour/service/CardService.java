package hour.service;

import hour.model.CardGroup;

import java.util.List;

public interface CardService {
    List<CardGroup> getCards(String user_id);

    String buyCard(String user_id, String card_type_id);

    boolean reNew(String user_id, String card_type_id);

    int getRemainsTime(String user_id, String card_id);
}
