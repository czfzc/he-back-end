package hour.service;

import hour.model.ExpressMonthCard;

public interface ExpressMonthCardService {
    boolean isAbled(ExpressMonthCard expressMonthCard);

    boolean payIt(String user_id, String preorder_id);
}
