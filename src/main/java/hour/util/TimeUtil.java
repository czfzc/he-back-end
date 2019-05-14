package hour.util;

import java.util.Date;

public class TimeUtil {
    public static long getTimeDiffMin(Date after, Date before){
        return (after.getTime()-before.getTime())/(1000*60);
    }
}
