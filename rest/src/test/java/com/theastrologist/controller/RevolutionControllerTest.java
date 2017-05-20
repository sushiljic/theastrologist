package com.theastrologist.controller;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.jayway.restassured.module.mockmvc.response.MockMvcResponse;
import com.theastrologist.domain.Degree;
import com.theastrologist.util.CalcUtil;
import com.theastrologist.util.ControllerUtil;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.get;
import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Samy on 15/05/2017.
 */
public class RevolutionControllerTest {

	private static RevolutionController revolutionController;
	private final String TEST_NATAL_DATE = "1985-01-04T11:20:00";
	private final String TEST_REV_SOLAR_DATE = "2016-03-12";
	private final String TEST_REV_LUNAR_DATE = "2017-05-12";
	private final double NATAL_LATITUDE = 48.64566;
	private final double NATAL_LONGITUDE = 2.41045;
	private final double PARIS_LATITUDE = 48.862725;
	private final double PARIS_LONGITUDE = 2.28759;

	private ControllerUtil controllerUtil;

	@Before
	public void setUp() throws Exception {
		revolutionController = new RevolutionController();
		controllerUtil = createMockBuilder(ControllerUtil.class).addMockedMethod("queryGoogleForTimezone").createMock();
		revolutionController.setControllerUtil(controllerUtil);
		RestAssuredMockMvc.standaloneSetup(revolutionController);
	}

	@Test
	public void testSimpleSolarRevolution() {
		expect(controllerUtil.queryGoogleForTimezone(anyDouble(), anyDouble(), anyLong()))
				.andReturn(DateTimeZone.forID("Europe/Paris")).times(2);
		replay(controllerUtil);

		MockMvcResponse response = get(
				"/{natal_date}/{natal_latitude}/{natal_longitude}/revolution/solar/{from_date}/{anniversary_latitude}/{anniversary_longitude}",
				TEST_NATAL_DATE,
				NATAL_LATITUDE,
				NATAL_LONGITUDE,
				TEST_REV_SOLAR_DATE,
				PARIS_LATITUDE,
				PARIS_LONGITUDE);

		response.then().statusCode(200)
				.body("date", equalTo("2017-01-04T05:41:02+01:00"))
				.body("positions.SOLEIL.sign", equalTo("CAPRICORNE"))
				.body("positions.SOLEIL.degreeInSign.degree", equalTo(14))
				.body("positions.SOLEIL.degreeInSign.minutes", equalTo(0))
				.body("positions.ASCENDANT.sign", equalTo("SAGITTAIRE"))
				.body("positions.ASCENDANT.degreeInSign.degree", equalTo(2));

		verify(controllerUtil);
	}

	@Test
	public void testSimpleLunarRevolution() {
		expect(controllerUtil.queryGoogleForTimezone(anyDouble(), anyDouble(), anyLong()))
				.andReturn(DateTimeZone.forID("Europe/Paris")).times(2);
		replay(controllerUtil);

		MockMvcResponse response = get(
				"/{natal_date}/{natal_latitude}/{natal_longitude}/revolution/lunar/{from_date}/{anniversary_latitude}/{anniversary_longitude}",
				TEST_NATAL_DATE,
				NATAL_LATITUDE,
				NATAL_LONGITUDE,
				TEST_REV_LUNAR_DATE,
				PARIS_LATITUDE,
				PARIS_LONGITUDE);

		response.then().statusCode(200)
				.body("date", equalTo("2017-05-26T08:33:39+02:00"))
				.body("positions.LUNE.sign", equalTo("GEMEAUX"))
				.body("positions.LUNE.degreeInSign.degree", equalTo(11))
				.body("positions.LUNE.degreeInSign.minutes", equalTo(40))
				.body("positions.ASCENDANT.sign", equalTo("CANCER"))
				.body("positions.ASCENDANT.degreeInSign.degree", equalTo(12));

		verify(controllerUtil);
	}

}