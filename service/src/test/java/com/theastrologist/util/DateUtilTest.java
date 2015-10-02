package com.theastrologist.util;

import org.joda.time.DateTime;
import org.junit.Test;
import swisseph.SweDate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by Samy on 03/10/2015.
 */
public class DateUtilTest {

	@Test
	public void testGetSweDateUTC() throws Exception {
		DateTime dateTime = DateTime.parse("1985-01-04T21:00:00+06:00");
		SweDate sweDateUTC = DateUtil.getSweDateUTC(dateTime);
		assertThat(sweDateUTC, notNullValue());
		assertThat((int) sweDateUTC.getHour(), equalTo(15));
	}
}