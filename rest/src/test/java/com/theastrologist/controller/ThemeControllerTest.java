package com.theastrologist.controller;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.jayway.restassured.module.mockmvc.response.MockMvcResponse;
import com.theastrologist.domain.Degree;
import com.theastrologist.util.ControllerUtil;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.get;
import static org.hamcrest.Matchers.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;


/**
 * Classes de test pour tester les URL de type "theme/48.6456630/2.4104510/1985-01-04T11:20:00+01:00"
 */
@RunWith(EasyMockRunner.class)
public class ThemeControllerTest {

	private static ThemeController themeController;

	private ControllerUtil controllerUtil;

	@Before
	public void setUp() throws Exception {
		themeController = new ThemeController();
		controllerUtil = createMockBuilder(ControllerUtil.class).addMockedMethod("queryGoogleForTimezone").createMock();
		themeController.setControllerUtil(controllerUtil);
		RestAssuredMockMvc.standaloneSetup(themeController);
	}

	@Test
	public void testSimpleTheme() {
		double latitude = new Degree(48, 39).getBaseDegree();
		double longitude = new Degree(2, 25).getBaseDegree();
		String dateTime = "1985-01-04T11:20:00";

		expect(controllerUtil.queryGoogleForTimezone(anyDouble(), anyDouble(), anyLong()))
				.andReturn(DateTimeZone.forID("Europe/Paris"));
		replay(controllerUtil);

		MockMvcResponse response = get("/{datetime}/{latitude}/{longitude}/theme", dateTime,
									   latitude, longitude);

		response.then().statusCode(200)
				.body("positions.MERCURE.sign", equalTo("SAGITTAIRE"))
				.body("positions.LUNE.house", equalTo("III"))
				.body("positions.ASCENDANT.sign", equalTo("POISSONS"));

		verify(controllerUtil);
	}

	@Test
	public void testSimpleDateFormat() {
		expect(controllerUtil.queryGoogleForTimezone(anyDouble(), anyDouble(), anyLong()))
				.andReturn(DateTimeZone.forID("Europe/Paris"));
		replay(controllerUtil);

		MockMvcResponse response = get("/{datetime}/{latitude}/{longitude}/theme", "1985-01-04T11:20:00",
									   new Degree(48, 39).getBaseDegree(), new Degree(2, 25).getBaseDegree());

		// Ce test vérifie que la date est bien prise en compte et que le format est correct
		response.then().statusCode(200).body("date", equalTo("1985-01-04T11:20:00+01:00"));

		verify(controllerUtil);
	}

	@Test
	public void testSimpleLatitude() {
		MockMvcResponse response = get("/{datetime}/{latitude}/{longitude}/theme", "1985-01-04T11:20:00",
									   new Degree(32, 39).getBaseDegree(), new Degree(4, 5).getBaseDegree());
		// Ce test vérifie que la latitude est bien prise en compte
		response.then().statusCode(200)
				.body("latitude.degree", equalTo(32))
				.body("latitude.minutes", equalTo(39));
	}

	@Test
	public void testSimpleLatitudeAutreFormat() {
		MockMvcResponse response = get("/{datetime}/{latitude}/{longitude}/theme", "1985-01-04T11:20:00",
									   48.6456630, 2.4104510);
		// Ce test vérifie que la latitude est bien prise en compte
		response.then().statusCode(200)
				.body("latitude.degree", equalTo(48))
				.body("latitude.minutes", equalTo(38));
	}

	@Test
	public void testSimpleLongitude() {
		MockMvcResponse response = get("/{datetime}/{latitude}/{longitude}/theme", "1985-01-04T11:20:00",
									   new Degree(12, 59).getBaseDegree(), new Degree(4, 41).getBaseDegree());
		// Ce test vérifie que la longitude est bien prise en compte
		response.then().statusCode(200)
				.body("longitude.degree", equalTo(4))
				.body("longitude.minutes", equalTo(41));
	}

	@Test
	public void testSimpleLongitudeAutreFormat() {
		MockMvcResponse response = get("/{datetime}/{latitude}/{longitude}/theme", "1985-01-04T11:20:00",
									   48.6456630, 2.4104510);
		// Ce test vérifie que la longitude est bien prise en compte
		response.then().statusCode(200)
				.body("longitude.degree", equalTo(2))
				.body("longitude.minutes", equalTo(24));
	}

	@Test
	public void testWrongDate() {
		MockMvcResponse response = get("/{datetime}/{latitude}/{longitude}/theme", "Mauvaise date",
									   new Degree(48, 39).getBaseDegree(), new Degree(2, 25).getBaseDegree());
		response.then().statusCode(400);
	}

	@Test
	public void testWrongLatitude() {
		MockMvcResponse response = get("/{datetime}/{latitude}/{longitude}/theme", "1985-01-04T11:20:00", "truc",
									   new Degree(2, 25).getBaseDegree());
		response.then().statusCode(400);
	}

	@Test
	public void testWrongLongitude() {
		MockMvcResponse response = get("/{datetime}/{latitude}/{longitude}/theme", "1985-01-04T11:20:00",
									   new Degree(2, 25).getBaseDegree(), "truc");
		response.then().statusCode(400);
	}
}