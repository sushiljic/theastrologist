package com.theastrologist.core;

import com.theastrologist.domain.Degree;
import com.theastrologist.domain.SkyPosition;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import swisseph.*;

public class ThemeCalculator {

	private static final Logger LOG = Logger.getLogger(ThemeCalculator.class);

	private ThemeCalculator() {}

	private static class ThemeCalculatorHolder {
		private final static ThemeCalculator instance = new ThemeCalculator();
	}

	public static ThemeCalculator getInstance() {
		return ThemeCalculatorHolder.instance;
	}

	public SkyPosition getSkyPosition(DateTime dateTime, Degree latitude, Degree longitude) {
		SkyPosition skyPosition = new SkyPosition(dateTime, latitude, longitude);
		LOG.debug("Calculating sky position for Date = " + dateTime + ", with Latitude = " + latitude +
				  " and Longitude = " + longitude);
		skyPosition.calculate(Swieph.getInstance().value());
		return skyPosition;
	}
}
