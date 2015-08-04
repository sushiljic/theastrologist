package com.theastrologist.domain;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAM on 16/11/2014.
 */
public enum House implements SkyArtefact {
    I(1),
    II(2),
    III(3),
    IV(4),
    V(5),
    VI(6),
    VII(7),
    VIII(8),
    IX(9),
    X(10),
    XI(11),
    XII(12);

    private int houseNumber;

    public int getHouseNumber() {
        return houseNumber;
    }

    House(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    private static List<House> houses = new ArrayList<House>(12);

    static {
        for (House house : House.values()) {
            houses.add(house.houseNumber - 1, house);
        }
    }

    public static House getHouse(int i) {
        return houses.get(i - 1);
    }

    public boolean isMasterPlanet(Planet planet) {
        return Sign.getSign(houseNumber).isMasterPlanet(planet);
    }

    public boolean isExaltedPlanet(Planet planet) {
        return Sign.getSign(houseNumber).isExaltedPlanet(planet);
    }

    public boolean isExilPlanet(Planet planet) {
        return Sign.getSign(houseNumber).isExilPlanet(planet);
    }

    public boolean isChutePlanet(Planet planet) {
        return Sign.getSign(houseNumber).isChutePlanet(planet);
    }
}
