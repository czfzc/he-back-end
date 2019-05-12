package hour.service;

import hour.model.ExpressMonthCard;

public interface ExpressMonthCardService {
    boolean isAbled(ExpressMonthCard expressMonthCard);

    boolean isAbled(String user_id);

    boolean payIt(String user_id, String preorder_id);

    int getRemainsTime(String user_id);
    
}
