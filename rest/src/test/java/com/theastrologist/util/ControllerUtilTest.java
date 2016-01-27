package com.theastrologist.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Created by Samy on 27/01/2016.
 */
public class ControllerUtilTest {

	@Test
	public void parseDateTimeFr() throws Exception {
		DateTime dateTime = ControllerUtil.parseDateTime("1985-01-04T11:20", 48.64566300000001, 2.410451);
		assertThat(dateTime, notNullValue());
		assertThat(dateTime, hasToString("1985-01-04T11:20:00.000+01:00"));
	}

	@Test
	public void parseDateTimeFrEte() throws Exception {
		DateTime dateTime = ControllerUtil.parseDateTime("1979-08-15T21:30", 48.64566300000001, 2.410451);
		assertThat(dateTime, notNullValue());
		assertThat(dateTime, hasToString("1979-08-15T21:30:00.000+02:00"));
	}

	@Test
	public void parseDateTimeChili() throws Exception {
		DateTime dateTime = ControllerUtil.parseDateTime("1962-04-07T1:15", -26.3902488, -70.0475302);
		assertThat(dateTime, notNullValue());
		assertThat(dateTime, hasToString("1962-04-07T01:15:00.000-04:00"));
	}

	@Test
	public void parseDateTimeColombie() throws Exception {
		DateTime dateTime = ControllerUtil.parseDateTime("1979-03-24T17:00", 4.710988599999999, -74.072092);
		assertThat(dateTime, notNullValue());
		//assertThat(dateTime.toDateTime(DateTimeZone.UTC), hasToString("1979-03-24T22:00:00.000Z"));
		assertThat(dateTime, hasToString("1979-03-24T17:00:00.000-05:00"));
	}
}