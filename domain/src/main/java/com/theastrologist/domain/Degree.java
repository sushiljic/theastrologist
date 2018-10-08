package com.theastrologist.domain;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SAM on 16/11/2014.
 */
public class Degree {
    private transient double baseDegree;
    private int degree;
    private int minutes;
    private transient double seconds;
    private static Pattern pattern = Pattern.compile("(.+)°(.+)'");

    public Degree(double decimalDegree) {
        this.baseDegree = decimalDegree;
        generateDegreeDetail();
    }

    public Degree(int degree, int minutes) {
        createDegree(degree, minutes);
    }

    private void createDegree(int degree, int minutes) {
        createDegree(degree, minutes, 0.);
    }

    public Degree(int degree, int minutes, double seconds) {
        createDegree(degree, minutes, seconds);
    }

    private void createDegree(int degree, int minutes, double seconds) {
        this.degree = degree;
        this.minutes = minutes;
        this.seconds = seconds;
        generateBaseDegree();
    }

    public Degree(String nextString) throws IllegalArgumentException {
        String exceptionMessage = nextString + " is not parseable to this format : xx°xx'";
        Matcher matcher = pattern.matcher(nextString);
        List<Integer> integers = new ArrayList<Integer>(2);
        if (matcher.find()) {
            for (int i = 0; i < 2; i++) {
                String string = matcher.group(i + 1);
                Integer integer = Ints.tryParse(string);
                if (integer != null) {
                    if (i == 1 && integer >= 60) {
                        throw new IllegalArgumentException(integer + " is bigger than 59");
                    }
                    integers.add(integer);
                } else {
                    throw new IllegalArgumentException(exceptionMessage);
                }
            }
        } else {
            throw new IllegalArgumentException(exceptionMessage);
        }
        createDegree(integers.get(0), integers.get(1));
    }

    private void generateBaseDegree() {
        baseDegree = degree + minutes / 60. + seconds / 3600.;
    }

    private void generateDegreeDetail() {
        double otherBaseDegree = baseDegree;
        otherBaseDegree += 0.5 / 3600. / 10000.;    // round to 1/1000 of a second
        degree = (int) otherBaseDegree;
        otherBaseDegree = (otherBaseDegree - degree) * 60;
        minutes = (int) otherBaseDegree;
        otherBaseDegree = (otherBaseDegree - minutes) * 60;
        seconds = otherBaseDegree;
    }

    @Override
    public String toString() {
        String string = degree + "° " + minutes + "'";
        /*if (seconds > 0) {
            string += " " + Math.ceil(seconds) + "\"";
        }*/
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
