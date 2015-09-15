package com.theastrologist.core;

import com.theastrologist.domain.Degree;
import com.theastrologist.domain.Planet;
import com.theastrologist.domain.SkyPosition;
import com.theastrologist.domain.aspect.AspectPosition;
import com.theastrologist.domain.transitperiod.TransitPeriodsBuilder;
import com.theastrologist.domain.transitperiod.TransitPeriods;
import org.apache.log4j.Logger;
import org.joda.time.*;

import java.util.SortedMap;

/**
 * Created by SAM on 15/07/2015.
 */
public class TransitPeriodCalculator {
	private static final Logger LOG = Logger.getLogger(TransitPeriodCalculator.class);
	private static final ReadablePeriod PERIOD_TO_ADD = Weeks.ONE;

	public static final TransitPeriodCalculator INSTANCE = new TransitPeriodCalculator();

	public TransitPeriodCalculator() {
	}

	public TransitPeriods createTransitPeriod(SkyPosition natalTheme, DateTime startDate, DateTime endDate, Degree latitude, Degree longitude) {
		TransitPeriodsBuilder builder = new TransitPeriodsBuilder();
		DateTime currentDate = startDate;
		while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
			SkyPosition currentSkyPosition = ThemeCalculator.INSTANCE.getSkyPosition(currentDate, latitude, longitude);
			builder.startNewPeriod(currentDate);

			SortedMap<Planet, SortedMap<Planet, AspectPosition>> aspectsForTransit = AspectCalculator.INSTANCE
					.createAspectsForTransit(natalTheme, currentSkyPosition);

			for (Planet natalPlanet : aspectsForTransit.keySet()) {
				SortedMap<Planet, AspectPosition> aspectsForPlanet = aspectsForTransit.get(natalPlanet);
				for (Planet planetInTransit : aspectsForPlanet.keySet()) {
					AspectPosition aspectPosition = aspectsForPlanet.get(planetInTransit);
					builder.appendTransit(natalPlanet, planetInTransit, aspectPosition.getAspect());
				}
			}

			currentDate = currentDate.plus(PERIOD_TO_ADD);
		}
		return builder.build();
	}
}
