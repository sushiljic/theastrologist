package com.theastrologist.core;

import com.theastrologist.domain.Degree;
import com.theastrologist.domain.Planet;
import com.theastrologist.domain.Sign;
import com.theastrologist.domain.SkyPosition;
import com.theastrologist.util.CalcUtil;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class LunarRevolutionCalculatorTest {
	private final DateTime TEST_NATAL_DATE = new DateTime(1985, 1, 4, 11, 20, CalcUtil.DATE_TIME_ZONE);
	private final DateTime TEST_REV_DATE = new DateTime(2017, 5, 12, 0, 0, CalcUtil.DATE_TIME_ZONE);
	private final Degree LATITUDE = new Degree(48, 39);
	private final Degree LONGITUDE = new Degree(2, 25);
	private SkyPosition testSkyPosition;

	@Before
	public void setup() {
		testSkyPosition = ThemeCalculator.getInstance().getSkyPosition(TEST_NATAL_DATE, LATITUDE, LONGITUDE);
	}

	@Test
	public void testLunarRevolutionUTSAM201704() throws Exception {
		DateTime lunarRevolutionTime = RevolutionCalculator.getInstance()
				.getLunarRevolutionUT(testSkyPosition, TEST_REV_DATE.minusMonths(1));

		assertThat(lunarRevolutionTime, notNullValue());
		assertThat(lunarRevolutionTime.toString(), equalTo("2017-04-28T20:14:18.456Z"));
	}

	@Test
	public void testLunarRevolutionSAM201704() throws Exception {
		SkyPosition lunarRevolution = RevolutionCalculator.getInstance()
				.getLunarRevolution(testSkyPosition, TEST_REV_DATE.minusMonths(1), LATITUDE, LONGITUDE);

		assertThat(lunarRevolution, notNullValue());
		assertThat(lunarRevolution.getPlanetPosition(Planet.ASCENDANT).getSign(), is(Sign.SCORPION));
	}

	@Test
	public void testLunarRevolutionUTSAM201705() throws Exception {
		DateTime lunarRevolutionTime = RevolutionCalculator.getInstance()
				.getLunarRevolutionUT(testSkyPosition, TEST_REV_DATE);

		assertThat(lunarRevolutionTime, notNullValue());
		assertThat(lunarRevolutionTime.toString(), equalTo("2017-05-26T06:33:39.626Z"));
	}

	@Test
	public void testLunarRevolutionSAM201705() throws Exception {
		SkyPosition lunarRevolution = RevolutionCalculator.getInstance()
				.getLunarRevolution(testSkyPosition, TEST_REV_DATE, LATITUDE, LONGITUDE);

		assertThat(lunarRevolution, notNullValue());
		assertThat(lunarRevolution.getAscendantPosition().getDegreeInSign().getDegree(), equalTo(12));
		assertThat(lunarRevolution.getAscendantPosition().getSign(), is(Sign.CANCER));
	}

	@Test
	public void testLunarRevolutionUTSAM201706() throws Exception {
		DateTime lunarRevolutionTime = RevolutionCalculator.getInstance()
				.getLunarRevolutionUT(testSkyPosition, TEST_REV_DATE.plusMonths(1));

		assertThat(lunarRevolutionTime, notNullValue());
		assertThat(lunarRevolutionTime.toString(), equalTo("2017-06-22T17:15:10.130Z"));
	}

	@Test
	public void testLunarRevolutionSAM201706() throws Exception {
		SkyPosition lunarRevolution = RevolutionCalculator.getInstance()
				.getLunarRevolution(testSkyPosition, TEST_REV_DATE.plusMonths(1), LATITUDE, LONGITUDE);

		assertThat(lunarRevolution, notNullValue());
		assertThat(lunarRevolution.getAscendantPosition().getSign(), is(Sign.SCORPION));
	}
}