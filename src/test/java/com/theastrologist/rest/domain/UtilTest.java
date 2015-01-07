package com.theastrologist.rest.domain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import swisseph.SweDate;
import util.CalcUtil;
import util.DateUtil;

import java.util.TimeZone;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void testHouses() {
        assertThat(House.I.getHouseNumber(), equalTo(1));
        assertThat(House.II.getHouseNumber(), equalTo(2));
        assertThat(House.III.getHouseNumber(), equalTo(3));
    }

    @Test
    public void testDate() {
        DateTimeZone DATE_TIME_ZONE = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        DateTime TEST_DATE = new DateTime(1985, 1, 4, 11, 20, DATE_TIME_ZONE);

        SweDate sweDate = DateUtil.getSweDateUTC(TEST_DATE);
        assertThat(sweDate.checkDate(), is(true));
        assertThat(sweDate.getDay(), equalTo(4));
        assertThat(sweDate.getMonth(), equalTo(1));
        assertThat(sweDate.getYear(), equalTo(1985));
        double hour = sweDate.getHour();
        assertThat(hour, closeTo(10.333333333, CalcUtil.DELTA));
    }

    @Test
    public void testCalculateSign() {
        Sign sign = CalcUtil.getSign(new Degree(341.));
        assertThat(sign, equalTo(Sign.POISSONS));
    }

    @Test
    public void testCalculateDegreeInSign() {
        Degree degree = CalcUtil.getDegreeInSign(new Degree(341.));
        assertThat(degree.getDegree(), equalTo(11));
    }

    @Test
     public void testCalculateHouse() {
        House house = CalcUtil.getHouse(new Degree(262.), new Degree(341.));
        assertThat(house, equalTo(House.X));
    }

    @Test
    public void testCalculateDegreeInHouse() {
        Degree degree = CalcUtil.getDegreeInHouse(new Degree(262.), new Degree(341.));
        assertThat(degree.getDegree(), equalTo(11));
        assertThat(degree.getMinutes(), equalTo(0));
    }

    @Test
    public void testGetOpposite() {
        Degree degree = CalcUtil.getOpposite(new Degree(262.));
        assertThat(degree.getDegree(), equalTo(82));
    }

    @Test
    public void testGetOppositeMinus() {
        Degree degree = CalcUtil.getOpposite(new Degree(11.));
        assertThat(degree.getDegree(), equalTo(191));
    }
}