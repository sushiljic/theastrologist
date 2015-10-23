package com.theastrologist.core;

import com.theastrologist.core.ThemeCalculator;
import com.theastrologist.domain.Degree;
import com.theastrologist.domain.Planet;
import com.theastrologist.domain.PlanetPosition;
import com.theastrologist.domain.SkyPosition;
import com.theastrologist.domain.aspect.Aspect;
import com.theastrologist.domain.aspect.AspectPosition;
import com.theastrologist.domain.transitperiod.TransitPeriod;
import com.theastrologist.domain.transitperiod.TransitPeriods;
import com.theastrologist.domain.transitperiod.TransitPeriodsBuilder;
import com.theastrologist.core.TransitPeriodCalculator;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TimeZone;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Created by SAM on 30/06/2015.
 */
public class TransitPeriodTest {

	private Degree asDegree = new Degree(341, 46);
	private PlanetPosition asPosition = PlanetPosition.createPlanetPosition(asDegree, asDegree);

	public static final DateTimeZone DATE_TIME_ZONE = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris"));
	private final DateTime TEST_NATAL_DATE = new DateTime(1985, 1, 4, 11, 20, DATE_TIME_ZONE);
	private final DateTime TEST_DATE_TODAY = new DateTime(2015, 6, 23, 20, 30, DATE_TIME_ZONE);
	private final Degree LATITUDE = new Degree(48, 39);
	private final Degree LONGITUDE = new Degree(2, 25);

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testMapTransitOneElement() throws Exception {
		// Given
		TransitPeriodsBuilder builder = new TransitPeriodsBuilder();
		Degree asDegree = new Degree(341, 46);

		Planet planetInTransit = Planet.SOLEIL;
		PlanetPosition planetInTransitPosition = PlanetPosition.createPlanetPosition(new Degree(172, 4), asDegree);

		Planet natalPlanet = Planet.MERCURE;
		PlanetPosition natalPlanetPosition = PlanetPosition.createPlanetPosition(new Degree(261, 14), asDegree);
		DateTime dateTime = DateTime.parse("2015-09-15");

		AspectPosition aspectPosition = AspectPosition
				.createTransitPosition(natalPlanet, planetInTransit, natalPlanetPosition, planetInTransitPosition);

		// When
		builder.startNewPeriod(dateTime);
		builder.appendTransit(natalPlanet, planetInTransit, aspectPosition.getAspect());
		TransitPeriods build = builder.build();
		Map<Planet, SortedSet<TransitPeriod>> map = build.getPeriods();

		// Then
		assertThat(map, hasKey(Planet.SOLEIL));
		SortedSet<TransitPeriod> transitPeriodList = map.get(Planet.SOLEIL);

		assertThat(transitPeriodList, notNullValue());
		assertThat(transitPeriodList, hasSize(1));

		TransitPeriod first = transitPeriodList.first();
		assertThat(first, notNullValue());
		assertThat(first.getStartDate(), equalTo(dateTime));
		assertThat(first.getEndDate(), equalTo(dateTime));
	}

	@Test
	public void testMapTransitTwoElementsMerge() throws Exception {
		// Given
		TransitPeriodsBuilder builder = new TransitPeriodsBuilder();
		Degree asDegree = new Degree(341, 46);

		Planet natalPlanet = Planet.SATURNE;
		PlanetPosition natalPlanetPosition = PlanetPosition.createPlanetPosition(new Degree(235, 3), asDegree);
		Planet planetInTransit = Planet.SATURNE;

		PlanetPosition planetInTransitPosition = PlanetPosition.createPlanetPosition(new Degree(238, 25), asDegree);
		DateTime dateTime = DateTime.parse("2015-08-15");
		AspectPosition aspectPosition = AspectPosition
				.createTransitPosition(natalPlanet, planetInTransit, natalPlanetPosition, planetInTransitPosition);

		builder.startNewPeriod(dateTime);
		builder.appendTransit(planetInTransit, natalPlanet, aspectPosition.getAspect());

		PlanetPosition planetInTransitSecondPosition = PlanetPosition
				.createPlanetPosition(new Degree(239, 50), asDegree);
		DateTime secondDateTime = DateTime.parse("2015-09-15");
		AspectPosition aspectSecondPosition = AspectPosition
				.createTransitPosition(natalPlanet, planetInTransit, natalPlanetPosition,
									   planetInTransitSecondPosition);

		builder.startNewPeriod(secondDateTime);
		builder.appendTransit(planetInTransit, natalPlanet, aspectSecondPosition.getAspect());

		// When
		TransitPeriods build = builder.build();
		Map<Planet, SortedSet<TransitPeriod>> map = build.getPeriods();


		// Then
		assertThat(map, hasKey(Planet.SATURNE));
		SortedSet<TransitPeriod> transitPeriodList = map.get(Planet.SATURNE);

		assertThat(transitPeriodList, notNullValue());
		assertThat(transitPeriodList, hasSize(1));

		TransitPeriod second = transitPeriodList.last();
		assertThat(second, notNullValue());
		assertThat(second.getStartDate(), equalTo(dateTime));
		assertThat(second.getEndDate(), equalTo(secondDateTime));
	}

	@Test
	public void testMapTransitTwoElementsDifferentAspect() throws Exception {
		// Given
		TransitPeriodsBuilder builder = new TransitPeriodsBuilder();
		Degree asDegree = new Degree(341, 46);

		Planet natalPlanet = Planet.MERCURE;
		PlanetPosition natalPlanetPosition = PlanetPosition.createPlanetPosition(new Degree(261, 14), asDegree);
		Planet planetInTransit = Planet.SOLEIL;

		PlanetPosition planetInTransitPosition = PlanetPosition.createPlanetPosition(new Degree(172, 4), asDegree);
		DateTime dateTime = DateTime.parse("2015-09-15");
		AspectPosition aspectPosition = AspectPosition
				.createTransitPosition(natalPlanet, planetInTransit, natalPlanetPosition, planetInTransitPosition);

		builder.startNewPeriod(dateTime);
		builder.appendTransit(natalPlanet, planetInTransit, aspectPosition.getAspect());

		PlanetPosition planetInTransitSecondPosition = PlanetPosition
				.createPlanetPosition(new Degree(201, 36), asDegree);
		DateTime secondDateTime = DateTime.parse("2015-10-15");
		AspectPosition aspectSecondPosition = AspectPosition
				.createTransitPosition(natalPlanet, planetInTransit, natalPlanetPosition,
									   planetInTransitSecondPosition);

		builder.startNewPeriod(secondDateTime);
		builder.appendTransit(natalPlanet, planetInTransit, aspectSecondPosition.getAspect());

		// When
		TransitPeriods build = builder.build();
		Map<Planet, SortedSet<TransitPeriod>> map = build.getPeriods();


		// Then
		assertThat(map, hasKey(Planet.SOLEIL));
		SortedSet<TransitPeriod> transitPeriodList = map.get(Planet.SOLEIL);

		assertThat(transitPeriodList, notNullValue());
		assertThat(transitPeriodList, hasSize(2));

		TransitPeriod first = transitPeriodList.first();
		assertThat(first, notNullValue());
		assertThat(first.getStartDate(), equalTo(dateTime));
		assertThat(first.getEndDate(), equalTo(dateTime));

		TransitPeriod second = transitPeriodList.last();
		assertThat(second, notNullValue());
		assertThat(second.getStartDate(), equalTo(secondDateTime));
		assertThat(second.getEndDate(), equalTo(secondDateTime));
	}

	@Test
	public void testFullTransitPeriod() throws Exception {
		// Given
		SkyPosition natalPosition = ThemeCalculator.INSTANCE.getSkyPosition(TEST_NATAL_DATE, LATITUDE, LONGITUDE);

		DateTime startDate = DateTime.parse("2014-01-01");
		DateTime endDate = DateTime.parse("2016-01-01");

		// When
		TransitPeriods build = TransitPeriodCalculator.INSTANCE
				.createTransitPeriod(natalPosition, startDate, endDate, LATITUDE, LONGITUDE);
		Map<Planet, SortedSet<TransitPeriod>> map = build.getPeriods();

		// Then
		assertThat(map, hasKey(Planet.PLUTON));
		SortedSet<TransitPeriod> transitPeriodList = map.get(Planet.PLUTON);

		assertThat(transitPeriodList, notNullValue());
		assertThat(transitPeriodList, not(emptyIterableOf(TransitPeriod.class)));

		Iterator<TransitPeriod> iterator = transitPeriodList.iterator();
		iterator.next();
		TransitPeriod second = iterator.next();
		assertThat(second, notNullValue());
		assertThat(second.getNatalPlanet(), is(Planet.SOLEIL));
		assertThat(second.getAspect(), is(Aspect.CONJONCTION));
		assertThat(second.getStartDate(), equalTo(startDate));
		assertThat(second.getEndDate(), equalTo(DateTime.parse("2015-12-30")));
	}

	@Test
	public void testSupprTwoElementsNoLength() throws Exception {
		// Given
		SkyPosition natalPosition = ThemeCalculator.INSTANCE.getSkyPosition(TEST_NATAL_DATE, LATITUDE, LONGITUDE);

		DateTime startDate = DateTime.parse("2016-08-01");
		DateTime endDate = DateTime.parse("2016-09-10");

		// When
		TransitPeriods build = TransitPeriodCalculator.INSTANCE
				.createTransitPeriod(natalPosition, startDate, endDate, LATITUDE, LONGITUDE);
		Map<Planet, SortedSet<TransitPeriod>> map = build.getPeriods();

		// Then
		assertThat(map, hasKey(Planet.JUPITER));
		SortedSet<TransitPeriod> transitPeriodList = map.get(Planet.JUPITER);

		assertThat(transitPeriodList, notNullValue());
		assertThat(transitPeriodList, not(emptyIterableOf(TransitPeriod.class)));
		assertThat(transitPeriodList, hasSize(5));
	}
}
