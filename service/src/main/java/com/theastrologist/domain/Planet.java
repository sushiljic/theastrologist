package com.theastrologist.domain;

import swisseph.SweConst;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SAM
 * @since 16/11/2014.
 * Enumération des différentes planètes et indicateurs stellaires
 */
public enum Planet implements SkyArtefact {
    ASCENDANT(-1),
    SOLEIL(SweConst.SE_SUN),
    LUNE(SweConst.SE_MOON),
    MERCURE(SweConst.SE_MERCURY),
    VENUS(SweConst.SE_VENUS),
    MARS(SweConst.SE_MARS),
    JUPITER(SweConst.SE_JUPITER),
    SATURNE(SweConst.SE_SATURN),
    URANUS(SweConst.SE_URANUS),
    NEPTUNE(SweConst.SE_NEPTUNE),
    PLUTON(SweConst.SE_PLUTO),
    NOEUD_NORD_MOYEN(SweConst.SE_MEAN_NODE),
    NOEUD_SUD_MOYEN(-1),
    LILITH_EXACTE(SweConst.SE_OSCU_APOG),
    LILITH_MOYENNE(SweConst.SE_MEAN_APOG),
    PART_DE_FORTUNE(-1),
    DESCENDANT(-1),
    MILIEU_DU_CIEL(-1),
    FOND_DU_CIEL(-1);

    private static List<Planet> realPlanets;
    private final int sweConst;
    private static List<Planet> principalePlanets;

    public int getSweConst() {
        return sweConst;
    }

    Planet(int sweConst) {
        this.sweConst = sweConst;
    }

    public static List<Planet> getPrincipalePlanets() {
        if (principalePlanets == null) {
            principalePlanets = new ArrayList<Planet>();
            principalePlanets.add(ASCENDANT);
            principalePlanets.add(SOLEIL);
            principalePlanets.add(LUNE);
            principalePlanets.add(NOEUD_SUD_MOYEN);
        }
        return principalePlanets;
    }

    public static List<Planet> getRealPlanets() {
        if (realPlanets == null) {
            realPlanets = new ArrayList<Planet>();
            realPlanets.add(SOLEIL);
            realPlanets.add(LUNE);
            realPlanets.add(MERCURE);
            realPlanets.add(VENUS);
            realPlanets.add(MARS);
            realPlanets.add(JUPITER);
            realPlanets.add(SATURNE);
            realPlanets.add(URANUS);
            realPlanets.add(NEPTUNE);
            realPlanets.add(PLUTON);
        }
        return realPlanets;
    }
}
