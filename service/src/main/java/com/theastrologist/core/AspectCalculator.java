package com.theastrologist.core;


import com.google.common.collect.Maps;
import com.theastrologist.domain.Planet;
import com.theastrologist.domain.PlanetPosition;
import com.theastrologist.domain.SkyPosition;
import com.theastrologist.domain.aspect.AspectPosition;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class AspectCalculator {

    public static final AspectCalculator INSTANCE = new AspectCalculator();

    /**
     * Calcul des aspects sur une carte du ciel
     *
     * @param skyPosition la carte du ciel de référence
     * @return une map contenant par planète une map avec correspondance entre l'aspect et la planète comparée
     */
    public SortedMap<Planet, SortedMap<Planet, AspectPosition>> createAspectsForSkyPosition(SkyPosition skyPosition) {

        return createAspectsForComparison(skyPosition, skyPosition, false);
    }

    /**
     * Calcul des aspects pour une synastrie
     *
     * @param skyPosition           la carte du ciel servant de référence
     * @param skyPositionComparison la carte du ciel à comparer
     * @param transit
     * @return une map contenant par planète une map avec correspondance entre l'aspect et la planète comparée
     */
    public SortedMap<Planet, SortedMap<Planet, AspectPosition>> createAspectsForComparison(SkyPosition skyPosition, SkyPosition skyPositionComparison, boolean transit) {

        SortedMap<Planet, SortedMap<Planet, AspectPosition>> aspectMapForPlanet = Maps.newTreeMap();

        for (Planet planet : Planet.values()) {
            PlanetPosition planetPosition = skyPosition.getPlanetPosition(planet);
            SortedMap<Planet, AspectPosition> aspectMap;
            if (!aspectMapForPlanet.containsKey(planet)) {
                aspectMap = Maps.newTreeMap();
                aspectMapForPlanet.put(planet, aspectMap);
            } else {
                aspectMap = aspectMapForPlanet.get(planet);
            }

            for (Planet planetComparison : Planet.values()) {
                if (planet != planetComparison || skyPosition != skyPositionComparison) {
                    PlanetPosition planetComparisonPosition = skyPositionComparison.getPlanetPosition(planetComparison);
                    AspectPosition aspectPosition = AspectPosition.createAspectPosition(planet, planetComparison, planetPosition, planetComparisonPosition, transit);
                    if (aspectPosition != null) {
                        aspectMap.put(planetComparison, aspectPosition);
                    }
                }
            }
        }

        return aspectMapForPlanet;

    }


    /**
     * Calcul des aspects pour un transit (orbes différentes)
     *
     * @param skyPosition     la carte du ciel servant de référence
     * @param transitPosition la carte du ciel à comparer
     * @return une map contenant par planète une map avec correspondance entre l'aspect et la planète comparée
     */
    public SortedMap<Planet, SortedMap<Planet, AspectPosition>> createAspectsForTransit(SkyPosition skyPosition, SkyPosition transitPosition) {

        return createAspectsForComparison(skyPosition, transitPosition, true);
    }

    public SortedMap<Planet, SortedMap<Planet, AspectPosition>> createAspectsForSynastry(SkyPosition testSkyPosition, SkyPosition testSkyPositionComparison) {
        return createAspectsForComparison(testSkyPosition, testSkyPositionComparison, false);
    }
}
