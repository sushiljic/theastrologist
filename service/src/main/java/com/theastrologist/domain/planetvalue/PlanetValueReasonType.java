package com.theastrologist.domain.planetvalue;

/**
 * Created by Samy on 26/07/2015.
 */
public enum PlanetValueReasonType {
    PRINCIPAL_SIGN(15, true),
    PRINCIPAL_HOUSE(15, false),
    IS_IN_PRINCIPAL_SIGN(2, true),
    MASTER_SIGN(4, true),
    EXIL_SIGN(-4, true),
    EXALTED_SIGN(2, true),
    CHUTE_SIGN(-2, true),
    MASTER_HOUSE(4, false),
    EXIL_HOUSE(-4, false),
    EXALTED_HOUSE(2, false),
    CHUTE_HOUSE(-2, false),
    CONJONCTION_LUMINAIRE(8, true),
    IS_IN_PRINCIPAL_HOUSE(2, false);


    private final int points;
    private final boolean signReason;

    PlanetValueReasonType(int points, boolean signReason) {

        this.points = points;
        this.signReason = signReason;
    }

    public boolean isSignReason() {
        return signReason;
    }

    public boolean isHouseReason() {
        return !signReason;
    }

    public int getPoints() {
        return points;
    }
}
