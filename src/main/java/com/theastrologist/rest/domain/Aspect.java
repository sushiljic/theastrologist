package com.theastrologist.rest.domain;


public enum Aspect {
    CONJONCTION(0, 10.0, 3.0),
    OPPOSITION(180, 10.0, 3.0),
    TRIGONE(120, 7.0, 3.0),
    CARRE(90, 5.0, 3.0),
    SEXTILE(60, 4.0, 3.0);


    private int angleSeparation;
    private double orbe;
    private double orbeTransit;

    public int getAngleSeparation() {
        return angleSeparation;
    }

    Aspect(int angleSeparation, double orbe, double orbeTransit) {
        this.angleSeparation = angleSeparation;
        this.orbe = orbe;
        this.orbeTransit = orbeTransit;
    }

    public double getOrbeTransit() {
        return orbeTransit;
    }

    public double getOrbe() {
        return orbe;
    }
}
