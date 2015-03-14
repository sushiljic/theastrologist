package util;

import com.theastrologist.rest.domain.Degree;
import com.theastrologist.rest.domain.House;
import com.theastrologist.rest.domain.Sign;

/**
 * Created by SAM on 16/11/2014.
 */
public class CalcUtil {
    public static final double DELTA = 1e-5;

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
        return degree.getBaseDegree() < 0 ? new Degree(degree.getBaseDegree() + 360) : degree;
    }

    public static Degree getOpposite(Degree degree) {
        double baseDegree = degree.getBaseDegree();
        return baseDegree < 180 ? new Degree(baseDegree + 180) : new Degree(baseDegree - 180);
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
}
