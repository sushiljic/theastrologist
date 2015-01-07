package com.theastrologist.rest.core;

import com.theastrologist.rest.domain.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.TimeZone;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PlanetPositionTest {

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
    public void testGetSunPosition() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.SOLEIL);
        Assert.assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(284));
        Assert.assertThat(degree.getMinutes(), equalTo(0));

        Assert.assertThat(planetPosition.getSign(), equalTo(Sign.CAPRICORNE));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(14));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(0));

        Assert.assertThat(planetPosition.getHouse(), equalTo(House.XI));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(2));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(13));
    }

    @Test
    public void testGetMoonPosition() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.LUNE);
        Assert.assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(71));
        Assert.assertThat(degree.getMinutes(), equalTo(41));

        Assert.assertThat(planetPosition.getSign(), equalTo(Sign.GEMEAUX));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(11));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(41));

        Assert.assertThat(planetPosition.getHouse(), equalTo(House.III));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(29));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(54));
    }

    @Test
    public void testGetMercuryPosition() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.MERCURE);
        Assert.assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(261));
        Assert.assertThat(degree.getMinutes(), equalTo(14));

        Assert.assertThat(planetPosition.getSign(), equalTo(Sign.SAGITTAIRE));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(21));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(14));

        Assert.assertThat(planetPosition.getHouse(), equalTo(House.X));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(9));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(27));
    }

    @Test
    public void testGetVenusPosition() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.VENUS);
        Assert.assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(330));
        Assert.assertThat(degree.getMinutes(), equalTo(10));

        Assert.assertThat(planetPosition.getSign(), equalTo(Sign.POISSONS));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(0));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(10));

        Assert.assertThat(planetPosition.getHouse(), equalTo(House.XII));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(18));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(24));
    }

    @Test
    public void testGetMarsPosition() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.MARS);
        Assert.assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(337));
        Assert.assertThat(degree.getMinutes(), equalTo(45));

        Assert.assertThat(planetPosition.getSign(), equalTo(Sign.POISSONS));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(7));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(45));

        Assert.assertThat(planetPosition.getHouse(), equalTo(House.XII));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(25));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(58));
    }

    @Test
    public void testGetJupiterPosition() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.JUPITER);
        Assert.assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(292));
        Assert.assertThat(degree.getMinutes(), equalTo(15));

        Assert.assertThat(planetPosition.getSign(), equalTo(Sign.CAPRICORNE));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(22));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(15));

        Assert.assertThat(planetPosition.getHouse(), equalTo(House.XI));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(10));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(28));
    }

    @Test
    public void testGetSaturnePosition() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.SATURNE);
        Assert.assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(235));
        Assert.assertThat(degree.getMinutes(), equalTo(3));

        Assert.assertThat(planetPosition.getSign(), equalTo(Sign.SCORPION));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(25));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(3));

        Assert.assertThat(planetPosition.getHouse(), equalTo(House.IX));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(13));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(16));
    }

    @Test
    public void testGetUranusPosition() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.URANUS);
        Assert.assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(255));
        Assert.assertThat(degree.getMinutes(), equalTo(33));

        Assert.assertThat(planetPosition.getSign(), equalTo(Sign.SAGITTAIRE));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(15));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(33));

        Assert.assertThat(planetPosition.getHouse(), equalTo(House.X));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(3));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(46));
    }

    @Test
    public void testGetNeptunePosition() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.NEPTUNE);
        Assert.assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(271));
        Assert.assertThat(degree.getMinutes(), equalTo(36));

        Assert.assertThat(planetPosition.getSign(), equalTo(Sign.CAPRICORNE));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(1));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(36));

        Assert.assertThat(planetPosition.getHouse(), equalTo(House.X));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(19));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(49));
    }

    @Test
    public void testGetPlutonPosition() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.PLUTON);
        Assert.assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(214));
        Assert.assertThat(degree.getMinutes(), equalTo(25));

        Assert.assertThat(planetPosition.getSign(), equalTo(Sign.SCORPION));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(4));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(25));

        Assert.assertThat(planetPosition.getHouse(), equalTo(House.VIII));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(22));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(38));
    }

    @Test
    public void testGetNoeudNordPosition() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.NOEUD_NORD_MOYEN);
        Assert.assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(54));
        Assert.assertThat(degree.getMinutes(), equalTo(57));

        Assert.assertThat(planetPosition.getSign(), equalTo(Sign.TAUREAU));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(24));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(57));

        Assert.assertThat(planetPosition.getHouse(), equalTo(House.III));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(13));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(11));
    }

    @Test
    public void testGetNoeudSudPosition() throws Exception {
        PlanetPosition planetPosition = testSkyPosition.getPlanetPosition(Planet.NOEUD_SUD_MOYEN);
        Assert.assertThat(planetPosition, notNullValue());

        Degree degree = planetPosition.getDegree();
        Degree degreeInSign = planetPosition.getDegreeInSign();
        Degree degreeInHouse = planetPosition.getDegreeInHouse();


        Assert.assertThat(degree.getDegree(), equalTo(234));
        Assert.assertThat(degree.getMinutes(), equalTo(57));

        Assert.assertThat(planetPosition.getSign(), equalTo(Sign.SCORPION));
        Assert.assertThat(degreeInSign.getDegree(), equalTo(24));
        Assert.assertThat(degreeInSign.getMinutes(), equalTo(57));

        Assert.assertThat(planetPosition.getHouse(), equalTo(House.IX));
        Assert.assertThat(degreeInHouse.getDegree(), equalTo(13));
        Assert.assertThat(degreeInHouse.getMinutes(), equalTo(11));
    }
}