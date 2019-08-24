package br.com.leonardofiedler.challenge.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Leonardo Fiedler
 */
public class DateUtils {

    /**
     * Gets the expiration date
     * @param date
     * @return
     */
    public static Long getExpirationDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 5);
        return calendar.getTimeInMillis();
    }

}
