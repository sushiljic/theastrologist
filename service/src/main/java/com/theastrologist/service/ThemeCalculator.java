package com.theastrologist.service;

import com.theastrologist.domain.Degree;
import com.theastrologist.domain.SkyPosition;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class ThemeCalculator {

	private static final Logger LOG = Logger.getLogger(ThemeCalculator.class);

	public ThemeCalculator() {}

	public SkyPosition getSkyPosition(DateTime dateTime, Degree latitude, Degree longitude) {
		SkyPosition skyPosition = new SkyPosition(dateTime, latitude, longitude);
		LOG.debug("Calculating sky position for Date = " + dateTime + ", with Latitude = " + latitude +
				  " and Longitude = " + longitude);
		skyPosition.calculate(Swieph.getInstance().value());
		skyPosition.setAspects(AspectCalculator.getInstance().createAspectsForSkyPosition(skyPosition));

		return skyPosition;
	}
}
