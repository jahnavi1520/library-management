package library.util;

import java.util.Date;

public class Helper {
    public static Long getDaysBetweenDates(Date date1, Date date2) {
        long diffInMilliseconds = date1.getTime() - date2.getTime();
        return diffInMilliseconds / (1000 * 60 * 60 * 24);
    }
}
