package util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.theastrologist.rest.domain.Degree;
import com.theastrologist.rest.domain.House;
import com.theastrologist.rest.domain.Planet;
import com.theastrologist.rest.domain.Sign;

import java.util.*;

public class CalcUtil {
    public static final double DELTA = 1e-5;
    public static final double DELTAORBS = 1e-2;

    public static Sign getSign(Degree degree) {
        int signNumber = (int) (degree.getBaseDegree() / 30) + 1;
        return Sign.getSign(signNumber);
    }

    public static Degree getDegreeInSign(Degree degree) {
        return new Degree(degree.getBaseDegree() % 30);
    }

    public static Degree shiftDegreeWithHouse(Degree degree, Degree ascendantDegree) {
        double baseDegree = degree.getBaseDegree() - ascendantDegree.getBaseDegree();
        return CalcUtil.equilibrate(new Degree(baseDegree));
    }

    public static Degree getDegreeInHouse(Degree degree, Degree ascendantDegree) {
        Degree baseDegreeInHouse = shiftDegreeWithHouse(degree, ascendantDegree);
        return getDegreeInSign(baseDegreeInHouse);
    }

    public static House getHouse(Degree degree, Degree ascendantDegree) {
        Degree baseDegreeInHouse = shiftDegreeWithHouse(degree, ascendantDegree);
        int houseNumber = (int) (baseDegreeInHouse.getBaseDegree() / 30) + 1;
        return House.getHouse(houseNumber);
    }

    public static Degree equilibrate(Degree degree) {
        return new Degree(equilibrate(degree.getBaseDegree()));
    }

    public static double equilibrate(double degree) {
        return degree < 0 ? degree + 360 : degree;
    }

    public static double getOpposite(double baseDegree) {
        return baseDegree < 180 ? baseDegree + 180 : baseDegree - 180;
    }

    public static Degree getOpposite(Degree degree) {
        double baseDegree = degree.getBaseDegree();
        return new Degree(getOpposite(baseDegree));
    }

    public static Sign getOpposite(Sign sign) {
        int signNumber = sign.getSignNumber();
        return signNumber > 6 ? Sign.getSign(signNumber - 6) : Sign.getSign(signNumber + 6);
    }

    public static House getOpposite(House house) {
        int houseNumber = house.getHouseNumber();
        return houseNumber > 6 ? House.getHouse(houseNumber - 6) : House.getHouse(houseNumber + 6);
    }

    public static Degree calculatePartDeFortune(Degree ascDegree, Degree sunDegree, Degree moonDegree) {
        double partDegree = ascDegree.getBaseDegree() + moonDegree.getBaseDegree() - sunDegree.getBaseDegree();

        if (partDegree < 0) {
            partDegree += 360;
        } else if (partDegree >= 360) {
            partDegree -= 360;
        }

        return new Degree(partDegree);
    }

    public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
                new Comparator<Map.Entry<K, V>>() {
                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        if (e1.getKey().equals(e2.getKey())) {
                            return res; // Code will now handle equality properly
                        } else {
                            return res != 0 ? res : 1; // While still adding all entries
                        }
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    public static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> reverseSet(SortedSet<Map.Entry<K, V>> entrySet) {
        List<Map.Entry<K, V>> elements = Lists.newArrayList(entrySet);
        List<Map.Entry<K, V>> truc = Lists.reverse(elements);
        return truc;
    }
}
