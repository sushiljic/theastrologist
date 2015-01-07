package com.theastrologist.rest.domain;

import swisseph.SweConst;

/**
 * Created by SAM on 16/11/2014.
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
    NOEUD_NORD(SweConst.SE_MEAN_NODE),
    LILITH_EXACTE(SweConst.SE_OSCU_APOG),
    LILITH_MOYENNE(SweConst.SE_MEAN_APOG);

    private final int sweConst;

    public int getSweConst() {
        return sweConst;
    }

    Planet(int sweConst) {
        this.sweConst = sweConst;
    }
}
