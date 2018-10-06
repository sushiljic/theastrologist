package com.theastrologist.core;

import com.theastrologist.domain.Degree;
import com.theastrologist.domain.PlanetPosition;
import com.theastrologist.domain.SkyPosition;
import com.theastrologist.util.DateUtil;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import swisseph.*;

/**
 * Created by Samy on 11/05/2017.
 */
public class RevolutionCalculator {
	private static final Logger LOG = Logger.getLogger(RevolutionCalculator.class);

	private RevolutionCalculator() {}

	private static class RevolutionCalculatorHolder {
		private final static RevolutionCalculator instance = new RevolutionCalculator();
	}

	public static RevolutionCalculator getInstance() {
		return RevolutionCalculatorHolder.instance;
	}

	public SkyPosition getSolarRevolution(SkyPosition natalTheme, DateTime from, Degree latitude, Degree longitude) {
		DateTime solarRevolutionUT = getSolarRevolutionUT(natalTheme, from);
		return ThemeCalculator.getInstance().getSkyPosition(solarRevolutionUT, latitude, longitude);
	}

	public SkyPosition getLunarRevolution(SkyPosition natalTheme, DateTime from, Degree latitude, Degree longitude) {
		DateTime moonRevolutionUT = getLunarRevolutionUT(natalTheme, from);
		return ThemeCalculator.getInstance().getSkyPosition(moonRevolutionUT, latitude, longitude);
	}

	public DateTime getSolarRevolutionUT(SkyPosition natalTheme, DateTime from) {
		PlanetPosition soleilPosition = natalTheme.getSoleilPosition();
		return getRevolution(soleilPosition, from, SweConst.SE_SUN);
	}

	public DateTime getLunarRevolutionUT(SkyPosition natalTheme, DateTime from) {
		PlanetPosition lunePosition = natalTheme.getLunePosition();
		return getRevolution(lunePosition, from, SweConst.SE_MOON);
	}

	private DateTime getRevolution(PlanetPosition position, DateTime from, int swPlanet) {
		SweDate jDate = DateUtil.getSweDateUTC(from);

		int flags = SweConst.SEFLG_MOSEPH | SweConst.SEFLG_TRANSIT_LONGITUDE;

		TransitCalculator tc = new TCPlanet(
				Swieph.getInstance().value(),
				swPlanet,
				flags,
				position.getDegree().getBaseDegree());

		double nextTransitUT = Swieph.getInstance().value().getTransitUT(tc, jDate.getJulDay(), false);

		long dateMillis = DateTimeUtils.fromJulianDay(nextTransitUT);
		return new DateTime(dateMillis);
	}
}
