package com.theastrologist.rest.domain;

/**
 * Created by SAM on 16/11/2014.
 */
public class Degree {
    private double baseDegree;
    private int degree;
    private int minutes;
    private double seconds;

    public Degree(double decimalDegree) {
        this.baseDegree = decimalDegree;
        generateDegreeDetail();
    }

    public Degree(int degree, int minutes) {
        this(degree, minutes, 0.);
    }


    public Degree(int degree, int minutes, double seconds) {
        this.degree = degree;
        this.minutes = minutes;
        this.seconds = seconds;
        generateBaseDegree();
    }

    private void generateBaseDegree() {
        baseDegree = degree + minutes/60. + seconds / 3600.;
    }

    private void generateDegreeDetail() {
        double otherBaseDegree = baseDegree;
        otherBaseDegree += 0.5/3600./10000.;	// round to 1/1000 of a second
        degree = (int) otherBaseDegree;
        otherBaseDegree = (otherBaseDegree - degree) * 60;
        minutes = (int) otherBaseDegree;
        otherBaseDegree = (otherBaseDegree - minutes) * 60;
        seconds = otherBaseDegree;
    }

    @Override
    public String toString() {
        String string = degree + "° " + minutes + "'";
        if(seconds > 0) {
            string += " " + seconds + "\"";
        }
        return string;
    }

    public double getBaseDegree() {
        return baseDegree;
    }

    public int getDegree() {
        return degree;
    }

    public int getMinutes() {
        return minutes;
    }

    public double getSeconds() {
        return seconds;
    }
}
