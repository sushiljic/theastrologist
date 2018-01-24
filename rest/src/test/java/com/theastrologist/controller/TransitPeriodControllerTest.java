package com.theastrologist.controller;

import com.theastrologist.domain.Degree;
import com.theastrologist.util.TimeService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.replay;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by Samy on 16/09/2015.
 */
public class TransitPeriodControllerTest {
	private TimeService timeService;
	private TransitPeriodController transitPeriodController;

	@Before
	public void setUp() throws Exception {
		transitPeriodController = new TransitPeriodController();
		timeService = createMockBuilder(TimeService.class).addMockedMethod("queryGoogleForTimezone").createMock();
		transitPeriodController.setTimeService(timeService);

		RestAssuredMockMvc.standaloneSetup(transitPeriodController);
	}

	@Test
	public void testRequest() throws URISyntaxException {

		expect(timeService.queryGoogleForTimezone(anyDouble(), anyDouble(), anyLong()))
				.andReturn(DateTimeZone.forID("Europe/Paris"));
		replay(timeService);

		MockMvcResponse response = get("/{natalDate}/{latitude:.+}/{longitude:.+}/transitperiod/{startDate}/{endDate}",
									   "1985-01-04T11:20:00",
									   Double.toString(new Degree(48, 39).getBaseDegree()),
									   Double.toString(new Degree(2, 25).getBaseDegree()),
									   "2014-01-01T11:20:00",
									   "2016-01-01T11:20:00");

		response.then().statusCode(200)
				.body("planetPeriods.PLUTON", hasSize(4))
				.body("housePeriods.NOEUD_NORD_MOYEN", hasSize(2))
				.body("housePeriods.MARS", hasSize(14));

		verify(timeService);
	}

	@Test
	public void testRequestCompleterPeriodes() throws URISyntaxException {

		expect(timeService.queryGoogleForTimezone(anyDouble(), anyDouble(), anyLong()))
				.andReturn(DateTimeZone.forID("Europe/Paris"));
		replay(timeService);

		MockMvcResponse response = get("/{natalDate}/{latitude:.+}/{longitude:.+}/transitperiod/{startDate}/{endDate}",
									   "1985-01-04T11:20:00",
									   Double.toString(new Degree(48, 39).getBaseDegree()),
									   Double.toString(new Degree(2, 25).getBaseDegree()),
									   "2014-01-01T11:20:00",
									   "2016-01-01T11:20:00");

		response.then().statusCode(200)
				.body("planetPeriods.PLUTON", hasSize(4))
				.body("housePeriods.NOEUD_NORD_MOYEN", hasSize(2))
				.body("housePeriods.MARS", hasSize(14));

		verify(timeService);
	}

	@Test
	public void testRequestShortDates() throws URISyntaxException {

		expect(timeService.queryGoogleForTimezone(anyDouble(), anyDouble(), anyLong()))
				.andReturn(DateTimeZone.forID("Europe/Paris"));
		replay(timeService);

		MockMvcResponse response = get("/{natalDate}/{latitude:.+}/{longitude:.+}/transitperiod/{startDate}/{endDate}",
									   "1985-01-04T11:20:00",
									   Double.toString(new Degree(48, 39).getBaseDegree()),
									   Double.toString(new Degree(2, 25).getBaseDegree()),
									   "2014-01-01",
									   "2016-01-01");

		response.then().statusCode(200).body("planetPeriods.PLUTON", hasSize(4));

		verify(timeService);
	}

	@Test
	public void testTransitPeriodRequestWithAddress() {

		MockMvcResponse response = get("/{natalDate}/{address}/transitperiod/{startDate}/{endDate}",
									   "1985-01-04T11:20:00", "Ris-Orangis", "2014-01-01", "2016-01-01");

		response.then().statusCode(200)
				.body("planetPeriods.PLUTON", hasSize(5))
				.body("housePeriods.NOEUD_NORD_MOYEN", hasSize(2))
				.body("housePeriods.MARS", hasSize(14));
	}
}
