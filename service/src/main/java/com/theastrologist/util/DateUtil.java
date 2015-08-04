package com.theastrologist.util;

import org.joda.time.DateTime;
import swisseph.SweDate;

/**
 * Created by SAM on 16/11/2014.
 */
public class DateUtil {

    public static SweDate getSweDateUTC(DateTime dateTime) {
        DateTime date = new DateTime(dateTime.getZone().convertLocalToUTC(dateTime.getMillis(), false));
        int year = date.getYear();
        int month = date.getMonthOfYear();
        int day = date.getDayOfMonth();
        double hour = date.getHourOfDay() + date.getMinuteOfHour() / 60.;
        return new SweDate(year,month,day,hour);
    }
}
