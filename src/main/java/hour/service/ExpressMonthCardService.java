package hour.service;

import hour.model.ExpressMonthCard;

public interface ExpressMonthCardService {

    boolean isAbled(ExpressMonthCard expressMonthCard);

    boolean isAbled(String user_id);

    int getRemainsTime(String user_id);

    boolean reNew(String user_id);

    boolean useItForTimes(String user_id, int use_times);
}
