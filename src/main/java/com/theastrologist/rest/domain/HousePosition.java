package com.theastrologist.rest.domain;

import util.CalcUtil;

/**
 * Created by SAM on 16/11/2014.
 */
public class HousePosition {
    private House house;

    private Degree absoluteStartCusp;
    private Degree absoluteEndCusp;

    private Sign startSign;
    private Sign endSign;
    private Degree startCuspInSign;
    private Degree endCuspInSign;

    public HousePosition(House house, Degree absoluteStartCusp, Degree absoluteEndCusp) {
        this.house = house;
        this.absoluteStartCusp = absoluteStartCusp;
        this.absoluteEndCusp = absoluteEndCusp;
        this.startSign = CalcUtil.getSign(this.absoluteStartCusp);
        this.endSign = CalcUtil.getSign(this.absoluteEndCusp);
        this.startCuspInSign = CalcUtil.getDegreeInSign(this.absoluteStartCusp);
        this.endCuspInSign = CalcUtil.getDegreeInSign(this.absoluteEndCusp);
    }

    public House getHouse() {
        return house;
    }

    public Sign getStartSign() {
        return startSign;
    }

    public Sign getEndSign() {
        return endSign;
    }

    public Degree getStartCuspInSign() {
        return startCuspInSign;
    }

    public Degree getEndCuspInSign() {
        return endCuspInSign;
    }

    public Degree getAbsoluteStartCusp() {
        return absoluteStartCusp;
    }

    public Degree getAbsoluteEndCusp() {
        return absoluteEndCusp;
    }
}
