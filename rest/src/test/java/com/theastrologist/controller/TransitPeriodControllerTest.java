package com.theastrologist.controller;

import com.google.common.collect.Maps;
import com.google.common.primitives.Longs;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.jayway.restassured.module.mockmvc.response.MockMvcResponse;
import com.theastrologist.domain.Degree;
import org.apache.http.client.utils.URIBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by Samy on 16/09/2015.
 */
public class TransitPeriodControllerTest {
	@BeforeClass
	public static void classSetUp() throws Exception {
		RestAssuredMockMvc.standaloneSetup(new TransitPeriodController());
	}

	@Test
	public void testRequest() throws URISyntaxException {

		MockMvcResponse response = get("/transitperiod/{natalDate}/{startDate}/{endDate}/{latitude:.+}/{longitude:.+}",
									   "1985-01-04T11:20:00+01:00",
									   "2014-01-01T11:20:00+02:00",
									   "2016-01-01T11:20:00+02:00",
									   Double.toString(new Degree(48, 39).getBaseDegree()),
									   Double.toString(new Degree(2, 25).getBaseDegree()));

		response.then().statusCode(200).body("periods.PLUTON", hasSize(4));
	}

	@Test
	public void testRequestShortDates() throws URISyntaxException {

		MockMvcResponse response = get("/transitperiod/{natalDate}/{startDate}/{endDate}/{latitude:.+}/{longitude:.+}",
									   "1985-01-04T11:20:00+01:00",
									   "2014-01-01", "2016-01-01",
									   Double.toString(new Degree(48, 39).getBaseDegree()),
									   Double.toString(new Degree(2, 25).getBaseDegree()));

		response.then().statusCode(200).body("periods.PLUTON", hasSize(4));
	}
}
