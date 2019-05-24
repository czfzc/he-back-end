package hour.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static long getTimeDiffMin(Date after, Date before){
        return (after.getTime()-before.getTime())/(1000 * 60);
    }

    public static long getTimeDiffDate(Date after, Date before){
        return (after.getTime()-before.getTime())/(1000 * 3600 * 24);
    }

    public static Date addDay(Date date,int day){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DATE, day);
        return rightNow.getTime();
    }

}
