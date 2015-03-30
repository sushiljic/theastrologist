package com.theastrologist.rest.domain;

import org.junit.Before;
import org.junit.Test;
import util.CalcUtil;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


public class AspectPositionTest {

    private Degree asDegree = new Degree(341, 46);
    private PlanetPosition asPosition = PlanetPosition.createPlanetPosition(asDegree, asDegree);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSextileSunAsc() throws Exception {
        Degree sunDegree = new Degree(284, 0);
        PlanetPosition sunPosition = PlanetPosition.createPlanetPosition(sunDegree, asDegree);
        AspectPosition aspectPosition = AspectPosition.createAspectPosition(Planet.SOLEIL, Planet.ASCENDANT, sunPosition, asPosition);

        assertThat(aspectPosition, notNullValue());
        assertThat(aspectPosition.getAspect(), is(Aspect.SEXTILE));
        assertThat(aspectPosition.getOrbDelta().getDegree(), equalTo(-2));
        assertThat(aspectPosition.getOrbDelta().getMinutes(), equalTo(-13));
    }

    @Test
    public void testConjAscMars() throws Exception {
        Degree marsDegree = new Degree(337, 45);
        PlanetPosition marsPosition = PlanetPosition.createPlanetPosition(marsDegree, asDegree);
        AspectPosition aspectPosition = AspectPosition.createAspectPosition(Planet.ASCENDANT, Planet.MARS, asPosition, marsPosition);

        assertThat(aspectPosition, notNullValue());
        assertThat(aspectPosition.getAspect(), is(Aspect.CONJONCTION));
        assertThat(aspectPosition.getOrbDelta().getDegree(), equalTo(-4));
        assertThat(aspectPosition.getOrbDelta().getMinutes(), equalTo(0));
    }

    @Test
    public void testConjMarsAsc() throws Exception {
        Degree marsDegree = new Degree(337, 45);
        PlanetPosition marsPosition = PlanetPosition.createPlanetPosition(marsDegree, asDegree);
        AspectPosition aspectPosition = AspectPosition.createAspectPosition(Planet.MARS, Planet.ASCENDANT, marsPosition, asPosition);

        assertThat(aspectPosition, notNullValue());
        assertThat(aspectPosition.getAspect(), is(Aspect.CONJONCTION));
        assertThat(aspectPosition.getOrbDelta().getDegree(), equalTo(4));
        assertThat(aspectPosition.getOrbDelta().getMinutes(), equalTo(1));
    }

    @Test
    public void testCarreLuneAsc() throws Exception {
        Degree luneDegree = new Degree(71, 41);
        PlanetPosition lunePosition = PlanetPosition.createPlanetPosition(luneDegree, asDegree);
        AspectPosition aspectPosition = AspectPosition.createAspectPosition(Planet.LUNE, Planet.ASCENDANT, lunePosition, asPosition);

        assertThat(aspectPosition, notNullValue());
        assertThat(aspectPosition.getAspect(), is(Aspect.CARRE));
        assertThat(aspectPosition.getOrbDelta().getDegree(), equalTo(0));
        assertThat(aspectPosition.getOrbDelta().getMinutes(), equalTo(-4));
    }

    @Test
    public void testCarreVenusSaturne() throws Exception {
        Degree venusDegree = new Degree(330, 10);
        Degree saturneDegree = new Degree(235, 3);
        PlanetPosition venusPosition = PlanetPosition.createPlanetPosition(venusDegree, asDegree);
        PlanetPosition saturnePosition = PlanetPosition.createPlanetPosition(saturneDegree, asDegree);
        AspectPosition aspectPosition = AspectPosition.createAspectPosition(Planet.VENUS, Planet.SATURNE, venusPosition, saturnePosition);

        assertThat(aspectPosition, notNullValue());
        assertThat(aspectPosition.getAspect(), is(Aspect.CARRE));
        assertThat(aspectPosition.getOrbDelta().getDegree(), equalTo(5));
        assertThat(aspectPosition.getOrbDelta().getMinutes(), equalTo(7));
    }
}