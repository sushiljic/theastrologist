package com.theastrologist.core;

import com.theastrologist.domain.SkyPosition;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

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
