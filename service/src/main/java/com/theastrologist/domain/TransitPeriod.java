package com.theastrologist.domain;

import org.joda.time.DateTime;

/**
 * Created by SAM on 20/07/2015.
 */
public class TransitPeriod {
    Planet planet;
    AspectPosition aspectPosition;
    DateTime startDate;
    DateTime endDate;

    public TransitPeriod(Planet planet, AspectPosition aspectPosition, DateTime startDate) {
        this.planet = planet;
        this.aspectPosition = aspectPosition;
        this.startDate = startDate;
    }

    public TransitPeriod(Planet planet, AspectPosition aspectPosition, DateTime startDate, DateTime endDate) {
        this.planet = planet;
        this.aspectPosition = aspectPosition;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Planet getPlanet() {
        return planet;
    }

    public AspectPosition getAspectPosition() {
        return aspectPosition;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }
}
