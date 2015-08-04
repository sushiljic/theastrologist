package com.theastrologist.core;

import com.theastrologist.domain.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.TimeZone;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by SAM on 30/06/2015.
 */
public class TransitPeriodTest {

    private Degree asDegree = new Degree(341, 46);
    private PlanetPosition asPosition = PlanetPosition.createPlanetPosition(asDegree, asDegree);

    public static final DateTimeZone DATE_TIME_ZONE = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris"));
    private final DateTime TEST_NATAL_DATE = new DateTime(1985, 1, 4, 11, 20, DATE_TIME_ZONE);
    private final DateTime TEST_DATE_TODAY = new DateTime(2015, 6, 23, 20, 30, DATE_TIME_ZONE);
    private final Degree LATITUDE = new Degree(48, 39);
    private final Degree LONGITUDE = new Degree(2, 25);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    @Ignore
    public void testConcatTransits() throws Exception {

        /*TransitPeriodMap map = new TransitPeriodMap();
        Planet planet = Planet.LUNE;
        Degree luneDegree = new Degree(71, 41);
        Degree asDegree = new Degree(341, 46);
        PlanetPosition lunePosition = PlanetPosition.createPlanetPosition(luneDegree, asDegree);
        PlanetPosition asPosition = PlanetPosition.createPlanetPosition(asDegree, asDegree);

        AspectPosition aspectPosition = AspectPosition.createAspectPosition(Planet.LUNE, Planet.ASCENDANT, lunePosition, asPosition);
        map.appendTransit(planet, aspectPosition, new DateTime(2015, 6, 23, 20, 30, DATE_TIME_ZONE));

        // La lune a boug� entre temps
        Degree newLuneDegree = new Degree(71, 41);

        PlanetPosition newLunePosition = PlanetPosition.createPlanetPosition(luneDegree, asDegree);

        AspectPosition aspectPositionAfter = AspectPosition.createAspectPosition(Planet.LUNE, Planet.ASCENDANT, newLunePosition, asPosition);
        map.appendTransit(planet, aspectPosition, new DateTime(2015, 6, 24, 20, 30, DATE_TIME_ZONE));

        assertThat(map, hasKey(Planet.LUNE));*/

    }
}
