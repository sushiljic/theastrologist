package com.theastrologist.rest.core;

import com.theastrologist.rest.domain.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.util.TimeZone;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


public class AspectCalculatorTest {

    public static final DateTimeZone DATE_TIME_ZONE = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris"));
    private final DateTime TEST_DATE = new DateTime(1985, 1, 4, 11, 20, DATE_TIME_ZONE);
    private final Degree LATITUDE = new Degree(48, 39);
    private final Degree LONGITUDE = new Degree(2, 25);
    private SkyPosition testSkyPosition;
    //private final double LATITUDE = 48.6456630;
    //private final double LONGITUDE = 2.4104510;

    @Before
    public void setup() {
        testSkyPosition = ThemeCalculator.INSTANCE.getSkyPosition(TEST_DATE, LATITUDE, LONGITUDE);
    }

    @Test
    public void testGetSunJupiterAspect() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.SOLEIL);
        assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        assertThat(degree.getDegree(), equalTo(284));
        assertThat(degree.getMinutes(), equalTo(0));

        assertThat(planetPosition.getSign(), equalTo(Sign.CAPRICORNE));
        assertThat(degreeInSign.getDegree(), equalTo(14));
        assertThat(degreeInSign.getMinutes(), equalTo(0));


        assertThat(planetPosition.getHouse(), equalTo(House.XI));
        assertThat(degreeInHouse.getDegree(), equalTo(2));
        assertThat(degreeInHouse.getMinutes(), equalTo(13));

        SignDecan decanInSign = planetPosition.getDecanInSign();
        HouseDecan decanInHouse = planetPosition.getDecanInHouse();

        assertThat(decanInSign.getRelatedSign(), equalTo(Sign.TAUREAU));
        assertThat(decanInSign.getDecanNumber(), equalTo(2));

        assertThat(decanInHouse.getRelatedHouse(), equalTo(House.XI));
        assertThat(decanInHouse.getDecanNumber(), equalTo(1));
    }
}
