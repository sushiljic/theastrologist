package com.theastrologist.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.jayway.restassured.module.mockmvc.response.MockMvcResponse;
import com.theastrologist.domain.Degree;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.get;
import static org.hamcrest.Matchers.equalTo;

public class ThemeControllerTest {

    @BeforeClass
    public static void classSetUp() throws Exception {
        RestAssured.basePath = "/theastrologist/rest";
        RestAssuredMockMvc.standaloneSetup(new ThemeController());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSimpleTheme() {
        MockMvcResponse response = get("/theme/{latitude}/{longitude}/{datetime}", new Degree(48, 39)
                                               .getBaseDegree(), new Degree(2, 25)
                                               .getBaseDegree(), "1985-01-04T11:20:00+01:00");
        // Ce test vérifie
        response.then().statusCode(200).body("positionMap.MERCURE.sign", equalTo("SAGITTAIRE"));
    }

    @Test
    public void testSimpleDateFormat() {
        MockMvcResponse response = get("/theme/{latitude}/{longitude}/{datetime}", new Degree(48, 39)
                                               .getBaseDegree(), new Degree(2, 25)
                                               .getBaseDegree(), "1985-01-04T11:20:00+01:00");
        // Ce test vérifie que la date est bien prise en compte et que le format est correct
        response.then().statusCode(200).body("date", equalTo("1985-01-04T11:20:00+01:00"));
    }

    @Test
    public void testSimpleLatitude() {
        MockMvcResponse response = get("/theme/{latitude}/{longitude}/{datetime}", new Degree(32, 39)
                                               .getBaseDegree(), new Degree(4, 5)
                                               .getBaseDegree(), "1985-01-04T11:20:00+01:00");
        // Ce test vérifie que la latitude est bien prise en compte
        response.then().statusCode(200)
                .body("latitude.degree", equalTo(32))
                .body("latitude.minutes", equalTo(39));
    }

    @Test
    public void testSimpleLatitudeAutreFormat() {
        MockMvcResponse response = get("/theme/{latitude}/{longitude}/{datetime}", 48.6456630, 2.4104510,
                                       "1985-01-04T11:20:00+01:00");
        // Ce test vérifie que la latitude est bien prise en compte
        response.then().statusCode(200)
                .body("latitude.degree", equalTo(48))
                .body("latitude.minutes", equalTo(38));
    }

    @Test
    public void testSimpleLongitude() {
        MockMvcResponse response = get("/theme/{latitude}/{longitude}/{datetime}", new Degree(12, 59)
                                               .getBaseDegree(), new Degree(4, 41)
                                               .getBaseDegree(), "1985-01-04T11:20:00+01:00");
        // Ce test vérifie que la longitude est bien prise en compte
        response.then().statusCode(200)
                .body("longitude.degree", equalTo(4))
                .body("longitude.minutes", equalTo(41));
    }

    @Test
    public void testSimpleLongitudeAutreFormat() {
        MockMvcResponse response = get("/theme/{latitude}/{longitude}/{datetime}", 48.6456630, 2.4104510,
                                       "1985-01-04T11:20:00+01:00");
        // Ce test vérifie que la longitude est bien prise en compte
        response.then().statusCode(200)
                .body("longitude.degree", equalTo(2))
                .body("longitude.minutes", equalTo(24));
    }

    @Test
    public void testWrongDate() {
        MockMvcResponse response = get("/theme/{latitude}/{longitude}/{datetime}", new Degree(48, 39)
                                               .getBaseDegree(), new Degree(2, 25)
                                               .getBaseDegree(), "Mauvaise date");
        response.then().statusCode(400);
    }

    @Test
    public void testWrongLatitude() {
        MockMvcResponse response = get("/theme/{latitude}/{longitude}/{datetime}", "truc", new Degree(2, 25).getBaseDegree(),
                                       "1985-01-04T11:20:00+01:00");
        response.then().statusCode(400);
    }

    @Test
    public void testWrongLongitude() {
        MockMvcResponse response = get("/theme/{latitude}/{longitude}/{datetime}", new Degree(2, 25).getBaseDegree(), "truc",
                                       "1985-01-04T11:20:00+01:00");
        response.then().statusCode(400);
    }
}