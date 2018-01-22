package com.theastrologist.core;

import com.theastrologist.domain.Degree;
import com.theastrologist.domain.SkyPosition;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.cache.annotation.Cacheable;
import swisseph.*;

public class ThemeCalculator {

	private static final Logger LOG = Logger.getLogger(ThemeCalculator.class);

	public static final ThemeCalculator INSTANCE = new ThemeCalculator();

	public ThemeCalculator() {
	}

	@Cacheable(cacheNames="skyposition", key="{#dateTime, #latitude, #longitude}")
	public SkyPosition getSkyPosition(DateTime dateTime, Degree latitude, Degree longitude) {
		SkyPosition skyPosition = new SkyPosition(dateTime, latitude, longitude);
		LOG.debug("*** Calculating sky position for Date = " + dateTime + ", with Latitude = " + latitude +
				  " and Longitude = " + longitude);
		skyPosition.calculate(Swieph.getInstance().value());
		return skyPosition;
	}
}
