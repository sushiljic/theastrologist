package com.theastrologist.rest.core;


import com.theastrologist.rest.domain.AspectPosition;
import com.theastrologist.rest.domain.Planet;
import com.theastrologist.rest.domain.PlanetPosition;
import com.theastrologist.rest.domain.SkyPosition;

import java.util.HashMap;
import java.util.Map;

public class AspectCalculator {

    public static final AspectCalculator INSTANCE = new AspectCalculator();

    /**
     * Calcul des aspects sur une carte du ciel
     *
     * @param skyPosition la carte du ciel de référence
     * @return une map contenant par planète une map avec correspondance entre l'aspect et la planète comparée
     */
    public Map<Planet, Map<Planet, AspectPosition>> createAspectsForSkyPosition(SkyPosition skyPosition) {

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
    public Map<Planet, Map<Planet, AspectPosition>> createAspectsForComparison(SkyPosition skyPosition, SkyPosition skyPositionComparison, boolean transit) {

        Map<Planet, Map<Planet, AspectPosition>> aspectMapForPlanet = new HashMap<Planet, Map<Planet, AspectPosition>>();

        for (Planet planet : Planet.values()) {
            PlanetPosition planetPosition = skyPosition.getPlanetPosition(planet);
            Map<Planet, AspectPosition> aspectMap;
            if (!aspectMapForPlanet.containsKey(planet)) {
                aspectMap = new HashMap<Planet, AspectPosition>();
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
    public Map<Planet, Map<Planet, AspectPosition>> createAspectsForTransit(SkyPosition skyPosition, SkyPosition transitPosition) {

        return createAspectsForComparison(skyPosition, transitPosition, true);
    }

    public Map<Planet, Map<Planet, AspectPosition>> createAspectsForSynastry(SkyPosition testSkyPosition, SkyPosition testSkyPositionComparison) {
        return createAspectsForComparison(testSkyPosition, testSkyPositionComparison, false);
    }
}
