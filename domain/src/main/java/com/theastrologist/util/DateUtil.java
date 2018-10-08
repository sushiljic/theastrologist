package com.theastrologist.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import swisseph.SweDate;

import java.util.TimeZone;

/**
 * Created by SAM on 16/11/2014.
 */
public class DateUtil {

    public static SweDate getSweDateUTC(DateTime dateTime) {
        DateTimeZone.setDefault(DateTimeZone.UTC);
        DateTimeZone zone = dateTime.getZone();
        long millis = dateTime.getMillis();
        DateTime utcDate = dateTime.withZone(DateTimeZone.UTC);
        int year = utcDate.getYear();
        int month = utcDate.getMonthOfYear();
        int day = utcDate.getDayOfMonth();
        double hour = utcDate.getHourOfDay() + utcDate.getMinuteOfHour() / 60.;
        return new SweDate(year,month,day,hour);
    }
}
