package com.theastrologist.core;

import com.theastrologist.domain.Degree;
import com.theastrologist.domain.SkyPosition;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import swisseph.*;

/**
 * Created by SAM on 16/11/2014.
 */
public class ThemeCalculator {

    private static final Logger LOG = Logger.getLogger(ThemeCalculator.class);

    public static final ThemeCalculator INSTANCE = new ThemeCalculator();
    private final SwissEph sw;

    public ThemeCalculator() {
        SwissEph sw = new SwissEph();
        this.sw = sw;
    }

    public SkyPosition getSkyPosition(DateTime dateTime, Degree latitude, Degree longitude) {
        SkyPosition skyPosition = new SkyPosition(dateTime, latitude, longitude);
        LOG.debug("Calculating sky position for Date = " + dateTime + ", with Latitude = " + latitude + " and Longitude = " + longitude);
        skyPosition.calculate(sw);
        return skyPosition;
    }


}
