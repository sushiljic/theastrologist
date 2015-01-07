package com.theastrologist.rest.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAM on 16/11/2014.
 */
public enum Sign {
    BELIER(1),
    TAUREAU(2),
    GEMEAUX(3),
    CANCER(4),
    LION(5),
    VIERGE(6),
    BALANCE(7),
    SCORPION(8),
    SAGITTAIRE(9),
    CAPRICORNE(10),
    VERSEAU(11),
    POISSONS(12);
    private int signNumber;

    Sign(int signNumber) {
        this.signNumber = signNumber;
    }

    public int getSignNumber() {
        return signNumber;
    }

    private static List<Sign> signs = new ArrayList<Sign>(12);

    static {
        for (Sign sign : Sign.values()) {
            signs.add(sign.signNumber - 1, sign);
        }
    }

    public static Sign getSign(int i) {
        return signs.get(i - 1);
    }
}
