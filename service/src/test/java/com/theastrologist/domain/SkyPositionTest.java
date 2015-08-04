package com.theastrologist.domain;

import com.theastrologist.core.ThemeCalculator;
import com.theastrologist.domain.planetvalue.PlanetValue;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by Samy on 25/07/2015.
 */
public class SkyPositionTest {

    public static final DateTimeZone DATE_TIME_ZONE = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris"));
    private final DateTime SAMY_DATE = new DateTime(1985, 1, 4, 11, 20, DATE_TIME_ZONE);
    private final DateTime LAURENT_DATE = new DateTime(1980, 8, 9, 14, 25, DATE_TIME_ZONE);
    private final DateTime EMILIE_DATE = new DateTime(1983, 7, 1, 13, 20, DATE_TIME_ZONE);
    private final DateTime VANESSA_DATE = new DateTime(1974, 10, 4, 4, 20, DATE_TIME_ZONE);
    private final Degree LATITUDE = new Degree(48, 39);
    private final Degree LONGITUDE = new Degree(2, 25);
    private SkyPosition samySkyPosition;
    private SkyPosition laurentSkyPosition;
    private SkyPosition emilieSkyPosition;
    private SkyPosition vanessaSkyPosition;

    @Before
    public void setUp() throws Exception {
        samySkyPosition = ThemeCalculator.INSTANCE.getSkyPosition(SAMY_DATE, LATITUDE, LONGITUDE);
        laurentSkyPosition = ThemeCalculator.INSTANCE.getSkyPosition(LAURENT_DATE, LATITUDE, LONGITUDE);
        emilieSkyPosition = ThemeCalculator.INSTANCE.getSkyPosition(EMILIE_DATE, LATITUDE, LONGITUDE);
        vanessaSkyPosition = ThemeCalculator.INSTANCE.getSkyPosition(VANESSA_DATE, LATITUDE, LONGITUDE);
    }

    @Test
    public void testGetDominantPlanetsSamy() throws Exception {
        SortedSet<PlanetValue> testSet = samySkyPosition.getDominantPlanets();
        assertThat(testSet, notNullValue());
        assertThat(testSet, not(empty()));
        List<PlanetValue> testList = new ArrayList<PlanetValue>(testSet);
        Iterator<PlanetValue> iter = testList.iterator();
        assertThat(iter.next().getPlanet(), is(Planet.SATURNE));
        assertThat(iter.next().getPlanet(), is(Planet.JUPITER));
        assertThat(iter.next().getPlanet(), is(Planet.URANUS));
        assertThat(iter.next().getPlanet(), is(Planet.NEPTUNE));
        assertThat(iter.next().getPlanet(), is(Planet.MERCURE));
        assertThat(iter.next().getPlanet(), is(Planet.PLUTON));
        assertThat(iter.next().getPlanet(), is(Planet.MARS));
        assertThat(iter.next().getPlanet(), is(Planet.VENUS));
    }

    @Test
    public void testGetDominantPlanetsLaurent() throws Exception {
        SortedSet<PlanetValue> testSet = laurentSkyPosition.getDominantPlanets();
        assertThat(testSet, notNullValue());
        assertThat(testSet, not(empty()));

        List<PlanetValue> testList = new ArrayList<PlanetValue>(testSet);
        Iterator<PlanetValue> iter = testList.iterator();
        assertThat(iter.next().getPlanet(), is(Planet.SOLEIL));
        assertThat(iter.next().getPlanet(), is(Planet.SATURNE));
        assertThat(iter.next().getPlanet(), is(Planet.URANUS));
        assertThat(iter.next().getPlanet(), is(Planet.NEPTUNE));
        assertThat(iter.next().getPlanet(), is(Planet.LUNE));
        assertThat(iter.next().getPlanet(), is(Planet.JUPITER));
        assertThat(iter.next().getPlanet(), is(Planet.PLUTON));
        assertThat(iter.next().getPlanet(), is(Planet.MARS));
    }

    @Test
    public void testGetDominantPlanetsEmilie() throws Exception {
        SortedSet<PlanetValue> testSet = emilieSkyPosition.getDominantPlanets();
        assertThat(testSet, notNullValue());
        assertThat(testSet, not(empty()));
        List<PlanetValue> testList = new ArrayList<PlanetValue>(testSet);
        Iterator<PlanetValue> iter = testList.iterator();
        assertThat(iter.next().getPlanet(), is(Planet.NEPTUNE));
        assertThat(iter.next().getPlanet(), is(Planet.MERCURE));
        assertThat(iter.next().getPlanet(), is(Planet.JUPITER));
        assertThat(iter.next().getPlanet(), is(Planet.URANUS));
        assertThat(iter.next().getPlanet(), is(Planet.SATURNE));
        assertThat(iter.next().getPlanet(), is(Planet.VENUS));
        assertThat(iter.next().getPlanet(), is(Planet.LUNE));
        assertThat(iter.next().getPlanet(), is(Planet.MARS));
        assertThat(iter.next().getPlanet(), is(Planet.PLUTON));
    }

    @Test
    public void testGetDominantPlanetsVanessa() throws Exception {
        SortedSet<PlanetValue> testSet = vanessaSkyPosition.getDominantPlanets();
        assertThat(testSet, notNullValue());
        assertThat(testSet, not(empty()));
        List<PlanetValue> testList = new ArrayList<PlanetValue>(testSet);
        Iterator<PlanetValue> iter = testList.iterator();
        assertThat(iter.next().getPlanet(), is(Planet.VENUS));
        assertThat(iter.next().getPlanet(), is(Planet.MERCURE));
        assertThat(iter.next().getPlanet(), is(Planet.PLUTON));
        assertThat(iter.next().getPlanet(), is(Planet.URANUS));
        assertThat(iter.next().getPlanet(), is(Planet.SATURNE));
        assertThat(iter.next().getPlanet(), is(Planet.NEPTUNE));
        assertThat(iter.next().getPlanet(), is(Planet.MARS));
        assertThat(iter.next().getPlanet(), is(Planet.JUPITER));
        assertThat(iter.next().getPlanet(), is(Planet.LUNE));

        SortedSet<PlanetValue> filleVanessa = ThemeCalculator.INSTANCE.getSkyPosition(new DateTime(2005, 2, 4, 17, 35, DATE_TIME_ZONE), LATITUDE, LONGITUDE).getDominantPlanets();
        assertThat(filleVanessa, notNullValue());
    }

    public int compare(SkyPosition skyPosition, Planet planet, Planet planetToCompare) {

        int dominantPoints = skyPosition.calculateDominant(planet).getValue();
        int dominantPointsToCompare = skyPosition.calculateDominant(planetToCompare).getValue();

        return dominantPoints - dominantPointsToCompare;
    }

    @Test
    public void testComparePlanetePrincipaleEtPlaneteNormale() throws Exception {
        // Ascendant Poissons
        // Rien pour Soleil
        int comparison = compare(samySkyPosition, Planet.JUPITER, Planet.SOLEIL);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testComparePlanetePrincipaleEtPlaneteNormale2() throws Exception {
        // Noeud sud conjoint à saturne, Soleil Capricorne, Soleil Maison XI
        int comparison = compare(samySkyPosition, Planet.SATURNE, Planet.LUNE);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testComparePlanetePrincipaleEtPlaneteNormale3() throws Exception {
        // Soleil en XI et en capricorne, Uranus en X
        // Venus dans signe de naissance
        int comparison = compare(samySkyPosition, Planet.URANUS, Planet.LUNE);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesConjonctionLuminaire() throws Exception {
        int comparison = compare(samySkyPosition, Planet.URANUS, Planet.JUPITER);
        assertThat(comparison, lessThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesMaitre() throws Exception {
        // Soleil en Cancer
        // Noeud sud en sagittaire (exil) et en III, mercure en gémeaux en IX (exil)
        int comparison = compare(emilieSkyPosition, Planet.MERCURE, Planet.LUNE);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesMaitrePlusieurs() throws Exception {
        int comparison = compare(laurentSkyPosition, Planet.SOLEIL, Planet.URANUS);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesMaitreExaltationPlusieurs() throws Exception {
        int comparison = compare(laurentSkyPosition, Planet.SOLEIL, Planet.URANUS);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesExaltation() throws Exception {
        int comparison = compare(laurentSkyPosition, Planet.URANUS, Planet.SATURNE);
        assertThat(comparison, lessThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesExil() throws Exception {
        int comparison = compare(laurentSkyPosition, Planet.MARS, Planet.SATURNE);
        assertThat(comparison, lessThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesSigneDeNaissance() throws Exception {
        int comparison = compare(samySkyPosition, Planet.NEPTUNE, Planet.MERCURE);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testCompareConjonctionLuminaireNormale() throws Exception {
        int comparison = compare(laurentSkyPosition, Planet.LUNE, Planet.JUPITER);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testComparePlaneteDansSigneDeNaissance() throws Exception {
        int comparison = compare(samySkyPosition, Planet.VENUS, Planet.LUNE);
        assertThat(comparison, greaterThan(0));
    }

}