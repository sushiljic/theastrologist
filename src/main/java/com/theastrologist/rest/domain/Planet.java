package com.theastrologist.rest.domain;

import swisseph.SweConst;

/**
 * @author SAM
 * @since 16/11/2014.
 * Enumération des différentes planètes et indicateurs stellaires
 */
public enum Planet {
    ASCENDANT(-1),
    DESCENDANT(-1),
    MILIEU_DU_CIEL(-1),
    FOND_DU_CIEL(-1),
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
    PART_DE_FORTUNE(-1);

    private final int sweConst;

    public int getSweConst() {
        return sweConst;
    }

    Planet(int sweConst) {
        this.sweConst = sweConst;
    }
}
