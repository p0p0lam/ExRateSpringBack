package com.p0p0lam.back.exrate.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sergey on 24.02.2016.
 */
public class DateUtil {

    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    public static int getDateDiff(Calendar date1, Calendar date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTimeInMillis() - date1.getTimeInMillis();
        return (int) timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
    public static int getMinutesDiffFromNow(Calendar date) {
        Calendar now = GregorianCalendar.getInstance(UTC);
       return getDateDiff(date, now, TimeUnit.MINUTES);
    }
    public static int getMinutesDiffFromNow(Date date) {
        Calendar now = GregorianCalendar.getInstance(UTC);
        Calendar dateCal = GregorianCalendar.getInstance(UTC);
        dateCal.setTimeInMillis(date.getTime());
        return getDateDiff(dateCal, now, TimeUnit.MINUTES);
    }

    public static Calendar now(){
        return GregorianCalendar.getInstance(UTC);
    }
}
