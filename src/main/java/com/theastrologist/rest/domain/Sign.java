package com.theastrologist.rest.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAM on 16/11/2014.
 */
public enum Sign {
    BELIER(1, new Planet[]{Planet.MARS, Planet.PLUTON}, new Planet[]{Planet.SOLEIL}, new Planet[]{Planet.VENUS}, new Planet[]{Planet.SATURNE, Planet.URANUS}),
    TAUREAU(2, new Planet[]{Planet.VENUS}, new Planet[]{Planet.LUNE}, new Planet[]{Planet.MARS, Planet.PLUTON}, new Planet[]{Planet.SATURNE, Planet.URANUS}),
    GEMEAUX(3, new Planet[]{Planet.MERCURE}, new Planet[]{Planet.VENUS}, new Planet[]{Planet.JUPITER, Planet.NEPTUNE}, new Planet[]{Planet.MERCURE, Planet.NEPTUNE}),
    CANCER(4, new Planet[]{Planet.LUNE}, new Planet[]{Planet.JUPITER}, new Planet[]{Planet.SATURNE, Planet.URANUS}, new Planet[]{Planet.MARS, Planet.PLUTON}),
    LION(5, new Planet[]{Planet.SOLEIL}, new Planet[]{Planet.MARS, Planet.PLUTON}, new Planet[]{Planet.SATURNE, Planet.URANUS}, new Planet[]{Planet.JUPITER}),
    VIERGE(6, new Planet[]{Planet.MERCURE}, new Planet[]{Planet.MERCURE, Planet.NEPTUNE}, new Planet[]{Planet.JUPITER, Planet.NEPTUNE}, new Planet[]{Planet.VENUS}),
    BALANCE(7, new Planet[]{Planet.VENUS}, new Planet[]{Planet.SATURNE, Planet.URANUS}, new Planet[]{Planet.MARS, Planet.PLUTON}, new Planet[]{Planet.SOLEIL}),
    SCORPION(8, new Planet[]{Planet.MARS, Planet.PLUTON}, new Planet[]{Planet.SATURNE, Planet.URANUS}, new Planet[]{Planet.VENUS}, new Planet[]{Planet.LUNE}),
    SAGITTAIRE(9, new Planet[]{Planet.JUPITER, Planet.NEPTUNE}, new Planet[]{Planet.MERCURE, Planet.NEPTUNE}, new Planet[]{}, new Planet[]{Planet.VENUS}),
    CAPRICORNE(10, new Planet[]{Planet.SATURNE, Planet.URANUS}, new Planet[]{Planet.MARS, Planet.PLUTON}, new Planet[]{Planet.LUNE}, new Planet[]{Planet.JUPITER}),
    VERSEAU(11, new Planet[]{Planet.SATURNE, Planet.URANUS}, new Planet[]{Planet.JUPITER}, new Planet[]{Planet.SOLEIL}, new Planet[]{Planet.MARS, Planet.PLUTON}),
    POISSONS(12, new Planet[]{Planet.JUPITER, Planet.NEPTUNE}, new Planet[]{Planet.VENUS}, new Planet[]{Planet.MERCURE}, new Planet[]{Planet.MERCURE, Planet.NEPTUNE});

    private int signNumber;
    private final Planet[] masterPlanets;
    private final Planet[] exaltedPlanets;
    private final Planet[] exilPlanets;
    private final Planet[] chutePlanets;

    Sign(int signNumber, Planet[] masterPlanets, Planet[] exaltedPlanets, Planet[] exilPlanets, Planet[] chutePlanets) {
        this.signNumber = signNumber;
        this.masterPlanets = masterPlanets;
        this.exaltedPlanets = exaltedPlanets;
        this.exilPlanets = exilPlanets;
        this.chutePlanets = chutePlanets;
    }

    public int getSignNumber() {
        return signNumber;
    }

    public Planet[] getMasterPlanets() {
        return masterPlanets;
    }

    public Planet[] getExaltedPlanets() {
        return exaltedPlanets;
    }

    public Planet[] getExilPlanets() {
        return exilPlanets;
    }

    public Planet[] getChutePlanets() {
        return chutePlanets;
    }

    private static List<Sign> signs = new ArrayList<Sign>(12);

    static {
        for (Sign sign : Sign.values()) {
            signs.add(sign.signNumber - 1, sign);
        }
    }

    public static Sign getSign(int i) {
        return signs.get(i - 1);
    }
}
