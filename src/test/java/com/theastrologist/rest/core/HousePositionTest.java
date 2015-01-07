package com.theastrologist.rest.core;

import com.theastrologist.rest.domain.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.CalcUtil;

import java.util.TimeZone;

import static org.hamcrest.Matchers.*;

public class HousePositionTest {

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
    public void testGetAsPosition() throws Exception {
        PlanetPosition asPosition = testSkyPosition.getPlanetPosition(Planet.ASCENDANT);
        Assert.assertThat(asPosition, notNullValue());

        Degree degree = asPosition.getDegree();
        Degree degreeInSign = asPosition.getDegreeInSign();
        Degree degreeInHouse = asPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(341));
        Assert.assertThat(degree.getMinutes(), equalTo(46));

        Assert.assertThat(asPosition.getSign(), equalTo(Sign.POISSONS));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(11));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(46));

        Assert.assertThat(asPosition.getHouse(), equalTo(House.I));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(0));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(0));
    }

    @Test
    public void testGetDsPosition() throws Exception {
        PlanetPosition asPosition = testSkyPosition.getPlanetPosition(Planet.DESCENDANT);
        Assert.assertThat(asPosition, notNullValue());

        Degree degree = asPosition.getDegree();
        Degree degreeInSign = asPosition.getDegreeInSign();
        Degree degreeInHouse = asPosition.getDegreeInHouse();

        Assert.assertThat(degree.getDegree(), equalTo(161));
        Assert.assertThat(degree.getMinutes(), equalTo(46));

        Assert.assertThat(asPosition.getSign(), equalTo(Sign.VIERGE));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(11));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(46));

        Assert.assertThat(asPosition.getHouse(), equalTo(House.VII));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(0));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(0));
    }

    @Test
    public void testGetMcPosition() throws Exception {
        PlanetPosition asPosition = testSkyPosition.getPlanetPosition(Planet.MILIEU_DU_CIEL);
        Assert.assertThat(asPosition, notNullValue());

        Degree degree = asPosition.getDegree();
        Degree degreeInSign = asPosition.getDegreeInSign();
        Degree degreeInHouse = asPosition.getDegreeInHouse();

        Assert.assertThat(degree.getDegree(), equalTo(262));
        Assert.assertThat(degree.getMinutes(), equalTo(5));

        Assert.assertThat(asPosition.getSign(), equalTo(Sign.SAGITTAIRE));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(22));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(5));

        Assert.assertThat(asPosition.getHouse(), equalTo(House.X));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(10));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(18));
    }

    @Test
    public void testGetFcPosition() throws Exception {
        PlanetPosition asPosition = testSkyPosition.getPlanetPosition(Planet.FOND_DU_CIEL);
        Assert.assertThat(asPosition, notNullValue());

        Degree degree = asPosition.getDegree();
        Degree degreeInSign = asPosition.getDegreeInSign();
        Degree degreeInHouse = asPosition.getDegreeInHouse();

        Assert.assertThat(degree.getDegree(), equalTo(82));
        Assert.assertThat(degree.getMinutes(), equalTo(5));

        Assert.assertThat(asPosition.getSign(), equalTo(Sign.GEMEAUX));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(22));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(5));

        Assert.assertThat(asPosition.getHouse(), equalTo(House.IV));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(10));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(18));
    }

    @Test
    public void testGetHousePositions() throws Exception {
        HousePosition firstHousePosition = testSkyPosition.getHousePosition(House.I);
        Assert.assertThat(firstHousePosition, notNullValue());
        Assert.assertThat(firstHousePosition.getStartSign(), equalTo(Sign.POISSONS));
        Assert.assertThat(firstHousePosition.getStartCuspInSign().getDegree(), equalTo(11));
        Assert.assertThat(firstHousePosition.getEndSign(), equalTo(Sign.BELIER));
        Assert.assertThat(firstHousePosition.getEndCuspInSign().getDegree(), equalTo(11));

        HousePosition elevenHousePosition = testSkyPosition.getHousePosition(House.XI);
        Assert.assertThat(elevenHousePosition, notNullValue());
        Assert.assertThat(elevenHousePosition.getStartSign(), equalTo(Sign.CAPRICORNE));
        Assert.assertThat(elevenHousePosition.getStartCuspInSign().getDegree(), equalTo(11));
        Assert.assertThat(elevenHousePosition.getEndSign(), equalTo(Sign.VERSEAU));
        Assert.assertThat(elevenHousePosition.getEndCuspInSign().getDegree(), equalTo(11));

        HousePosition twelveHousePosition = testSkyPosition.getHousePosition(House.XII);
        Assert.assertThat(twelveHousePosition, notNullValue());
        Assert.assertThat(twelveHousePosition.getStartSign(), equalTo(Sign.VERSEAU));
        Assert.assertThat(twelveHousePosition.getStartCuspInSign().getDegree(), equalTo(11));
        Assert.assertThat(twelveHousePosition.getEndSign(), equalTo(Sign.POISSONS));
        Assert.assertThat(twelveHousePosition.getEndCuspInSign().getDegree(), equalTo(11));
    }
}