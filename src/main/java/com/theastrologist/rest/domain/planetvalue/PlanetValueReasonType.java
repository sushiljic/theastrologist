package com.theastrologist.rest.domain.planetvalue;

/**
 * Created by Samy on 26/07/2015.
 */
public enum PlanetValueReasonType {
    PRINCIPAL_SIGN(10, true);


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
