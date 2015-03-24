package com.theastrologist.rest.domain;

import org.junit.Before;
import org.junit.Test;
import util.CalcUtil;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


public class AspectPositionTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testPasAspectSunAsc() throws Exception {
        Degree sunDegree = new Degree(284, 0);
        Degree asDegree = new Degree(341, 46);
        PlanetPosition asPosition = PlanetPosition.createPlanetPosition(asDegree, asDegree);
        PlanetPosition sunPosition = PlanetPosition.createPlanetPosition(sunDegree, asDegree);
        AspectPosition aspectPosition = AspectPosition.createAspectPosition(Planet.ASCENDANT, Planet.SOLEIL, asPosition, sunPosition);

        assertThat(aspectPosition, nullValue());
    }

    @Test
    public void testConjMarsAsc() throws Exception {
        Degree marsDegree = new Degree(337, 45);
        Degree asDegree = new Degree(341, 46);
        PlanetPosition asPosition = PlanetPosition.createPlanetPosition(asDegree, asDegree);
        PlanetPosition marsPosition = PlanetPosition.createPlanetPosition(marsDegree, asDegree);
        AspectPosition aspectPosition = AspectPosition.createAspectPosition(Planet.ASCENDANT, Planet.MARS, asPosition, marsPosition);

        assertThat(aspectPosition, notNullValue());
        assertThat(aspectPosition.getAspect(), is(Aspect.CONJONCTION));
        assertThat(aspectPosition.getOrbDelta().getDegree(), equalTo(-4));
        assertThat(aspectPosition.getOrbDelta().getMinutes(), equalTo(5));
    }

    @Test
    public void testConjMarsAscNeg() throws Exception {
        Degree marsDegree = new Degree(337, 45);
        Degree asDegree = new Degree(341, 46);
        PlanetPosition asPosition = PlanetPosition.createPlanetPosition(asDegree, asDegree);
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
        Degree asDegree = new Degree(341, 46);
        PlanetPosition asPosition = PlanetPosition.createPlanetPosition(asDegree, asDegree);
        PlanetPosition lunePosition = PlanetPosition.createPlanetPosition(luneDegree, asDegree);
        AspectPosition aspectPosition = AspectPosition.createAspectPosition(Planet.ASCENDANT, Planet.SOLEIL, asPosition, lunePosition);

        assertThat(aspectPosition, notNullValue());
        assertThat(aspectPosition.getAspect(), is(Aspect.CARRE));
        assertThat(aspectPosition.getOrbDelta().getDegree(), equalTo(0));
        assertThat(aspectPosition.getOrbDelta().getMinutes(), equalTo(5));
    }
}