package com.theastrologist.rest.domain;

import com.sun.javaws.exceptions.InvalidArgumentException;

public abstract class Decan {
    protected int decanNumber;
    protected Degree relativeDegree;

    public Decan(Degree relativeDegree) {
        this.relativeDegree = relativeDegree;
        decanNumber = getDecan(relativeDegree);
    }

    private int getDecan(Degree relativeDegree) {
        double baseDegree = relativeDegree.getBaseDegree();
        if(baseDegree < 0 || baseDegree >= 30) {
            throw new ArrayIndexOutOfBoundsException("Degree must be between 0 and 30 excluded. Actual : " + baseDegree);
        }
        return (int) (baseDegree / 10) + 1;
    }

    public int getDecanNumber() {
        return decanNumber;
    }
}
