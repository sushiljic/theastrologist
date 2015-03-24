package com.theastrologist.rest.domain;


import util.CalcUtil;

public class AspectPosition {
    private Aspect aspect;
    private Planet planet;
    private Planet planetComparison;
    private PlanetPosition planetPosition;
    private PlanetPosition planetComparisonPosition;
    private Degree orbDelta;

    public static AspectPosition createAspectPosition(Planet planet, Planet planetComparison, PlanetPosition planetPosition, PlanetPosition planetComparisonPosition) {
        AspectPosition aspectPosition = new AspectPosition(planet, planetComparison, planetPosition, planetComparisonPosition);
        boolean shouldDestroy = aspectPosition.calculateAspectPosition();

        return shouldDestroy ? null : aspectPosition;
    }

    private boolean calculateAspectPosition() {
        boolean shouldDestroy = true;

        double planetDegree = planetPosition.getDegree().getBaseDegree();
        double planetComparisonDegree = planetComparisonPosition.getDegree().getBaseDegree();
        double comparison = planetComparisonDegree - planetDegree;

        if (comparison < -180) {
            comparison = CalcUtil.equilibrate(comparison);
        } else if (comparison > 180) {
            comparison -= 180;
        }

        for (Aspect aspect : Aspect.values()) {
            int angleSeparation = aspect.getAngleSeparation();
            double orbDelta;
            if (comparison < angleSeparation) {
                orbDelta = angleSeparation - comparison;
            } else {
                orbDelta = comparison - angleSeparation;
            }
            orbDelta = comparison - angleSeparation;
            double aspectOrbe = aspect.getOrbe();

            if (orbDelta >= 0 && orbDelta < aspectOrbe || orbDelta < 0 && aspectOrbe + orbDelta >= 0) {
                this.aspect = aspect;
                this.orbDelta = new Degree(orbDelta);
                shouldDestroy = false;
                break;
            }
        }

        return shouldDestroy;
    }

    private AspectPosition(Planet planet, Planet planetComparison, PlanetPosition planetPosition, PlanetPosition planetComparisonPosition) {
        this.planet = planet;
        this.planetComparison = planetComparison;
        this.planetPosition = planetPosition;
        this.planetComparisonPosition = planetComparisonPosition;
    }

    public Aspect getAspect() {
        return aspect;
    }

    public Planet getPlanet() {
        return planet;
    }

    public Planet getPlanetComparison() {
        return planetComparison;
    }

    public PlanetPosition getPlanetPosition() {
        return planetPosition;
    }

    public PlanetPosition getPlanetComparisonPosition() {
        return planetComparisonPosition;
    }

    public Degree getOrbDelta() {
        return orbDelta;
    }
}
