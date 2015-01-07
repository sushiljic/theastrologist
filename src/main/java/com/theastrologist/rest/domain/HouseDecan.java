package com.theastrologist.rest.domain;

/**
 * Created by SAM on 07/01/2015.
 */
public class HouseDecan extends Decan {

    private final House baseHouse;
    private House relatedHouse;

    public HouseDecan(Degree relativeDegree, House baseHouse) {
        super(relativeDegree);
        this.baseHouse = baseHouse;
        this.relatedHouse = calculateRelatedHouse(relativeDegree, baseHouse);
    }

    private House calculateRelatedHouse(Degree relativeDegree, House baseHouse) {
                return null;
    }

    public static HouseDecan getDecan(Degree relativeDegree, House baseHouse) {
        return new HouseDecan(relativeDegree, baseHouse);
    }

    public House getRelatedHouse() {
        return relatedHouse;
    }
}
