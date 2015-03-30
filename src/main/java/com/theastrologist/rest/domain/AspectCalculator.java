package com.theastrologist.rest.domain;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AspectCalculator {

    public static final AspectCalculator INSTANCE = new AspectCalculator();

    public Map<Planet, Map<Planet, AspectPosition>> createAspectsForSkyPosition(SkyPosition skyPosition) {

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
                if (planet != planetComparison) {
                    PlanetPosition planetComparisonPosition = skyPosition.getPlanetPosition(planetComparison);
                    AspectPosition aspectPosition = AspectPosition.createAspectPosition(planet, planetComparison, planetPosition, planetComparisonPosition);
                    if(aspectPosition != null) {
                        aspectMap.put(planetComparison, aspectPosition);
                    }
                }
            }
        }

        return aspectMapForPlanet;
    }

    public List<AspectPosition> createAspectsForComparison(SkyPosition skyPosition, SkyPosition skyPositionComparison) {

        return null;
    }

    public List<AspectPosition> createAspectsForTransit(SkyPosition skyPosition, SkyPosition transitPosition) {

        return null;
    }
}
