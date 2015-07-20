package com.theastrologist.rest.core;

import com.theastrologist.rest.domain.SkyPosition;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by SAM on 15/07/2015.
 */
public class TransitPeriodCalculator {
    private static final Logger LOG = Logger.getLogger(TransitPeriodCalculator.class);

    public static final TransitPeriodCalculator INSTANCE = new TransitPeriodCalculator();

    public TransitPeriodCalculator() {
    }

    public void createTransitPeriod(SkyPosition natalTheme, DateTime startDate, DateTime endDate) {

    }
}
