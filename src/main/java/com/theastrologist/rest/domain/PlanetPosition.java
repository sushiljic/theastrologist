package com.theastrologist.rest.domain;

/**
 * Created by SAM on 16/11/2014.
 */
public class PlanetPosition {
    private Degree degree;
    private Sign sign;
    private House house;
    private Degree degreeInSign;
    private Degree degreeInHouse;
    private boolean retrograde;

    public PlanetPosition(Degree degree, Sign sign, House house, Degree degreeInSign, Degree degreeInHouse) {
        this.degree = degree;
        this.sign = sign;
        this.house = house;
        this.degreeInSign = degreeInSign;
        this.degreeInHouse = degreeInHouse;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Degree getDegreeInSign() {
        return degreeInSign;
    }

    public void setDegreeInSign(Degree degreeInSign) {
        this.degreeInSign = degreeInSign;
    }

    public Degree getDegreeInHouse() {
        return degreeInHouse;
    }

    public void setDegreeInHouse(Degree degreeInHouse) {
        this.degreeInHouse = degreeInHouse;
    }

    public void setRetrograde(boolean retrograde) {
        this.retrograde = retrograde;
    }

    public boolean isRetrograde() {
        return retrograde;
    }
}
