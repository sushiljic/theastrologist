package com.theastrologist.rest.domain;

import com.theastrologist.rest.core.ThemeCalculator;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TimeZone;

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
    private final Degree LATITUDE = new Degree(48, 39);
    private final Degree LONGITUDE = new Degree(2, 25);
    private SkyPosition samySkyPosition;
    private SkyPosition laurentSkyPosition;
    private SkyPosition emilieSkyPosition;

    @Before
    public void setUp() throws Exception {
        samySkyPosition = ThemeCalculator.INSTANCE.getSkyPosition(SAMY_DATE, LATITUDE, LONGITUDE);
        laurentSkyPosition = ThemeCalculator.INSTANCE.getSkyPosition(LAURENT_DATE, LATITUDE, LONGITUDE);
        emilieSkyPosition = ThemeCalculator.INSTANCE.getSkyPosition(EMILIE_DATE, LATITUDE, LONGITUDE);
    }

    @Test
    public void testGetDominantPlanets() throws Exception {
        SortedSet<Planet> testList = samySkyPosition.getDominantPlanets();
        assertThat(testList, notNullValue());
        assertThat(testList, not(empty()));
        List<Planet> elements = new ArrayList<Planet>(testList);
        assertThat(elements.get(0), is(Planet.SATURNE));
    }

    @Test
    public void testComparePlanetePrincipaleEtPlaneteNormale() throws Exception {
        // Ascendant Poissons
        // Rien pour Soleil
        int comparison = samySkyPosition.compare(Planet.JUPITER, Planet.SOLEIL);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testComparePlanetePrincipaleEtPlaneteNormale2() throws Exception {
        // Noeud sud conjoint à saturne, Soleil Capricorne, Soleil Maison XI
        int comparison = samySkyPosition.compare(Planet.SATURNE, Planet.LUNE);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testComparePlanetePrincipaleEtPlaneteNormale3() throws Exception {
        int comparison = samySkyPosition.compare(Planet.URANUS, Planet.VENUS);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesConjonctionLuminaire() throws Exception {
        int comparison = samySkyPosition.compare(Planet.URANUS, Planet.JUPITER);
        assertThat(comparison, lessThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesMaitre() throws Exception {
        int comparison = laurentSkyPosition.compare(Planet.SOLEIL, Planet.SATURNE);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesMaitreExaltation() throws Exception {
        int comparison = laurentSkyPosition.compare(Planet.SOLEIL, Planet.URANUS);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesExaltation() throws Exception {
        int comparison = laurentSkyPosition.compare(Planet.URANUS, Planet.SATURNE);
        assertThat(comparison, lessThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesExil() throws Exception {
        int comparison = laurentSkyPosition.compare(Planet.MARS, Planet.SATURNE);
        assertThat(comparison, lessThan(0));
    }

    @Test
    public void testCompareDeuxPlanetesPrincipalesSigneDeNaissance() throws Exception {
        int comparison = samySkyPosition.compare(Planet.NEPTUNE, Planet.MERCURE);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testCompareConjonctionLuminaireNormale() throws Exception {
        int comparison = laurentSkyPosition.compare(Planet.MERCURE, Planet.NEPTUNE);
        assertThat(comparison, greaterThan(0));
    }

    @Test
    public void testComparePlaneteDansSigneDeNaissance() throws Exception {
        int comparison = samySkyPosition.compare(Planet.VENUS, Planet.LUNE);
        assertThat(comparison, greaterThan(0));
    }

}