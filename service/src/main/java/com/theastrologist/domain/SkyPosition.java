package com.theastrologist.domain;

import com.google.common.collect.Sets;
import com.google.gson.annotations.JsonAdapter;
import com.theastrologist.core.AspectCalculator;
import com.theastrologist.domain.aspect.Aspect;
import com.theastrologist.domain.aspect.AspectPosition;
import com.theastrologist.domain.planetvalue.PlanetValue;
import com.theastrologist.domain.planetvalue.PlanetValueReasonType;
import com.theastrologist.util.CalcUtil;
import com.theastrologist.util.DateUtil;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import swisseph.SweDate;
import swisseph.SwissEph;

import java.util.*;


/**
 * @author SAM
 * @since 16/11/2014.
 * Classe permettant de calculer la position des planètes dans le ciel à une date donnée
 */
public class SkyPosition {

    static final Logger LOG = Logger.getLogger(SkyPosition.class);
    private final Degree latitude;
    private final Degree longitude;
    @JsonAdapter(DateTimeJSONAdapter.class)
    private DateTime date;
    private Map<Planet, PlanetPosition> positionMap = new HashMap<Planet, PlanetPosition>();

    private Map<House, HousePosition> houseMap = new HashMap<House, HousePosition>();

    // Champs pour le calcul des dominances
    private SortedSet<PlanetValue> dominantPlanetSet = null;
    private PlanetPosition ascendantPosition = positionMap.get(Planet.ASCENDANT);
    private PlanetPosition lunePosition = positionMap.get(Planet.LUNE);
    private PlanetPosition soleilPosition = positionMap.get(Planet.SOLEIL);
    private PlanetPosition noeudSudPosition = positionMap.get(Planet.NOEUD_SUD_MOYEN);

    // Champs pour le calcul des aspects
    private Map<Planet, Map<Planet, AspectPosition>> aspects;

    public SkyPosition(DateTime dateTime, Degree latitude, Degree longitude) {
        this.date = dateTime;

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Map<Planet, Map<Planet, AspectPosition>> getAspects() {
        return aspects;
    }

    public void calculate(SwissEph sw) {
        SweDate sd = DateUtil.getSweDateUTC(date);

        LOG.info("Calculating sky position for Date = " + sd + ", with Latitude = " + latitude + " and Longitude = " + longitude);

        fillHousesAndAscendant(sw, sd);
        fillPlanets(sw, sd);

        ascendantPosition = positionMap.get(Planet.ASCENDANT);
        lunePosition = positionMap.get(Planet.LUNE);
        soleilPosition = positionMap.get(Planet.SOLEIL);
        noeudSudPosition = positionMap.get(Planet.NOEUD_SUD_MOYEN);

        aspects = AspectCalculator.INSTANCE.createAspectsForSkyPosition(this);
    }

    private void fillPlanets(SwissEph sw, SweDate sd) {
        int flags = 0;
        double[] xp = new double[6];
        StringBuffer serr = new StringBuffer();

        PlanetPosition ascendant = this.getPlanetPosition(Planet.ASCENDANT);

        for (Planet planet : Planet.values()) {
            Degree ascendantDegree = ascendant.getDegree();
            if (planet.getSweConst() != -1) {


                int ret = sw.swe_calc_ut(sd.getJulDay(),
                        planet.getSweConst(),
                        flags,
                        xp,
                        serr);

                if (ret != flags) {
                    if (serr.length() > 0) {
                        LOG.error("Warning: " + serr);
                    } else {
                        LOG.error(
                                String.format("Warning, different flags used (0x%x)", ret));
                    }
                }

                double position = xp[0];
                boolean retrograde = (xp[3] < 0);

                Degree degree = new Degree(position);

                PlanetPosition planetPosition = PlanetPosition.createPlanetPosition(degree, ascendantDegree);
                planetPosition.setRetrograde(retrograde);

                this.positionMap.put(planet, planetPosition);
            } else if (planet == Planet.NOEUD_SUD_MOYEN) {
                PlanetPosition noeudNord = this.positionMap.get(Planet.NOEUD_NORD_MOYEN);
                Degree noeudSudDegree = CalcUtil.getOpposite(noeudNord.getDegree());

                PlanetPosition planetPosition = PlanetPosition.createPlanetPosition(noeudSudDegree, ascendantDegree);
                planetPosition.setRetrograde(true);

                this.positionMap.put(planet, planetPosition);
            } else if (planet == Planet.PART_DE_FORTUNE) {
                Degree sunDegree = this.positionMap.get(Planet.SOLEIL).getDegree();
                Degree moonDegree = this.positionMap.get(Planet.LUNE).getDegree();

                Degree partDeFortune = CalcUtil.calculatePartDeFortune(ascendantDegree, sunDegree, moonDegree);
                PlanetPosition planetPosition = PlanetPosition.createPlanetPosition(
                        partDeFortune, ascendantDegree);

                this.positionMap.put(planet, planetPosition);
            }
        }
    }

    private void fillHousesAndAscendant(SwissEph sw, SweDate sd) {
        int flags = 0;
        double[] cusps = new double[13];
        double[] acsc = new double[10];
        sw.swe_houses(sd.getJulDay(),
                flags,
                latitude.getBaseDegree(),
                longitude.getBaseDegree(),
                'E',
                cusps,
                acsc);

        // Ascendant
        fillHouses(cusps);
        fillAngles(acsc);
    }

    private void fillHouses(double[] cusps) {
        for (int i = 2; i < cusps.length; i++) {
            House house = House.getHouse(i - 1);
            this.houseMap.put(house, new HousePosition(house, new Degree(cusps[i - 1]), new Degree(cusps[i])));

            if (i == 12) {
                this.houseMap.put(House.XII, new HousePosition(House.XII, new Degree(cusps[12]), new Degree(cusps[1])));
            }
        }
    }

    private void fillAngles(double[] degrees) {
        double as = degrees[0];
        double mc = degrees[1];

        Degree asDegree = new Degree(as);
        PlanetPosition asPosition = new PlanetPosition(
                asDegree,
                CalcUtil.getSign(asDegree),
                House.I,
                CalcUtil.getDegreeInSign(asDegree),
                new Degree(0)
        );
        this.positionMap.put(Planet.ASCENDANT, asPosition);

        Degree mcDegree = new Degree(mc);
        this.positionMap.put(Planet.MILIEU_DU_CIEL, PlanetPosition.createPlanetPosition(mcDegree, asDegree));

        Degree fcDegree = CalcUtil.getOpposite(mcDegree);
        this.positionMap.put(Planet.FOND_DU_CIEL, PlanetPosition.createPlanetPosition(fcDegree, asDegree));

        Degree dsDegree = CalcUtil.getOpposite(asDegree);
        this.positionMap.put(Planet.DESCENDANT, PlanetPosition.createPlanetPosition(dsDegree, asDegree));
    }

    public SortedSet<PlanetValue> getDominantPlanets() {
        if (dominantPlanetSet == null) {
            // Calcul des dominantes

            dominantPlanetSet = Sets.newTreeSet();

            for (Planet planet : Planet.getRealPlanets()) {
                PlanetValue value = calculateDominant(planet);
                dominantPlanetSet.add(value);
            }
        }
        return dominantPlanetSet;
    }

    public PlanetValue calculateDominant(Planet planet) {
        PlanetPosition planetPosition = positionMap.get(planet);
        PlanetValue value = new PlanetValue(planet);
        Sign sign = planetPosition.getSign();
        House house = planetPosition.getHouse();

        appendIfPlanetMaitrePrincipalSign(planet, value);

        appendIfPlanetMaitrePrincipalHouse(planet, value);

        boolean hasConjonctionWithLuminaire = appendIfHasConjunctionWithLuminaire(planet, value);

        if (!hasConjonctionWithLuminaire && planet != Planet.LUNE && planet != Planet.SOLEIL) {
            appendIfIsInPrincipaleSign(planetPosition, value);
            appendIfIsInPrincipaleHouse(planetPosition, value);
        }

        if (sign.isMasterPlanet(planet)) {
            value.appendValue(PlanetValueReasonType.MASTER_SIGN, sign);
        }

        if (sign.isExaltedPlanet(planet)) {
            value.appendValue(PlanetValueReasonType.EXALTED_SIGN, sign);
        }

        if (sign.isExilPlanet(planet)) {
            value.appendValue(PlanetValueReasonType.EXIL_SIGN, sign);
        }

        if (sign.isChutePlanet(planet)) {
            value.appendValue(PlanetValueReasonType.CHUTE_SIGN, sign);
        }

        if (house.isMasterPlanet(planet)) {
            value.appendValue(PlanetValueReasonType.MASTER_HOUSE, house);
        }

        if (house.isExaltedPlanet(planet)) {
            value.appendValue(PlanetValueReasonType.EXALTED_HOUSE, house);
        }

        if (house.isExilPlanet(planet)) {
            value.appendValue(PlanetValueReasonType.EXIL_HOUSE, house);
        }

        if (house.isChutePlanet(planet)) {
            value.appendValue(PlanetValueReasonType.CHUTE_HOUSE, house);
        }

        return value;
    }


    private boolean appendIfHasConjunctionWithLuminaire(Planet planet, PlanetValue value) {
        boolean returnValue = false;
        Map<Planet, Map<Planet, AspectPosition>> aspectsMap = getAspects();
        Map<Planet, AspectPosition> aspects = aspectsMap.get(planet);

        for (Planet principalePlanet : Planet.getPrincipalePlanets()) {
            if (aspects.containsKey(principalePlanet) && aspects.get(principalePlanet).getAspect() == Aspect.CONJONCTION) {
                value.appendValue(PlanetValueReasonType.CONJONCTION_LUMINAIRE, principalePlanet);
                returnValue = true;
            }
        }

        return returnValue;
    }

    public PlanetPosition getAscendantPosition() {
        return ascendantPosition;
    }

    public PlanetPosition getLunePosition() {
        return lunePosition;
    }

    public PlanetPosition getSoleilPosition() {
        return soleilPosition;
    }

    public PlanetPosition getNoeudSudPosition() {
        return noeudSudPosition;
    }

    private void appendIfPlanetMaitrePrincipalSign(Planet planet, PlanetValue value) {
        if(isPlanetMasterSoleil(planet)) {
            value.appendValue(PlanetValueReasonType.PRINCIPAL_SIGN, soleilPosition.getSign());
        }

        if(isPlanetMasterLune(planet)) {
            value.appendValue(PlanetValueReasonType.PRINCIPAL_SIGN, lunePosition.getSign());
        }

        if(isPlanetMasterAscendant(planet)) {
            value.appendValue(PlanetValueReasonType.PRINCIPAL_SIGN, ascendantPosition.getSign());
        }

        if(isPlanetMasterNoeudSud(planet)) {
            value.appendValue(PlanetValueReasonType.PRINCIPAL_SIGN, noeudSudPosition.getSign());
        }
    }

    private boolean isPlanetMasterSoleil(Planet planet) {
        return getSoleilPosition().getSign().isMasterPlanet(planet);
    }

    private boolean isPlanetMasterLune(Planet planet) {
        return getLunePosition().getSign().isMasterPlanet(planet);
    }

    private boolean isPlanetMasterAscendant(Planet planet) {
        return getAscendantPosition().getSign().isMasterPlanet(planet);
    }

    private boolean isPlanetMasterNoeudSud(Planet planet) {
        return getNoeudSudPosition().getSign().isMasterPlanet(planet);
    }

    private void appendIfIsInPrincipaleSign(PlanetPosition planetPosition, PlanetValue value) {
        Sign sign = planetPosition.getSign();

        if(sign == ascendantPosition.getSign()) {
            value.appendValue(PlanetValueReasonType.IS_IN_PRINCIPAL_SIGN, ascendantPosition.getSign());
        }

        if(sign == lunePosition.getSign()) {
            value.appendValue(PlanetValueReasonType.IS_IN_PRINCIPAL_SIGN, lunePosition.getSign());
        }

        if(sign == soleilPosition.getSign()) {
            value.appendValue(PlanetValueReasonType.IS_IN_PRINCIPAL_SIGN, soleilPosition.getSign());
        }

        if(sign == noeudSudPosition.getSign()) {
            value.appendValue(PlanetValueReasonType.IS_IN_PRINCIPAL_SIGN, noeudSudPosition.getSign());
        }
    }

    private void appendIfPlanetMaitrePrincipalHouse(Planet planet, PlanetValue value) {
        if(isPlanetMasterSoleilHouse(planet)) {
            value.appendValue(PlanetValueReasonType.PRINCIPAL_HOUSE, soleilPosition.getHouse());
        }

        if(isPlanetMasterLuneHouse(planet)) {
            value.appendValue(PlanetValueReasonType.PRINCIPAL_SIGN, lunePosition.getHouse());
        }

        if(isPlanetMasterNoeudSudHouse(planet)) {
            value.appendValue(PlanetValueReasonType.PRINCIPAL_SIGN, noeudSudPosition.getHouse());
        }
    }

    private boolean isPlanetMasterSoleilHouse(Planet planet) {
        return getSoleilPosition().getHouse().isMasterPlanet(planet);
    }

    private boolean isPlanetMasterLuneHouse(Planet planet) {
        return getLunePosition().getHouse().isMasterPlanet(planet);
    }

    private boolean isPlanetMasterNoeudSudHouse(Planet planet) {
        return getNoeudSudPosition().getHouse().isMasterPlanet(planet);
    }

    private void appendIfIsInPrincipaleHouse(PlanetPosition planetPosition, PlanetValue value) {
        House house = planetPosition.getHouse();

        if(house == soleilPosition.getHouse()) {
            value.appendValue(PlanetValueReasonType.IS_IN_PRINCIPAL_HOUSE, soleilPosition.getHouse());
        }

        if(house == lunePosition.getHouse()) {
            value.appendValue(PlanetValueReasonType.IS_IN_PRINCIPAL_HOUSE, lunePosition.getHouse());
        }

        if(house == noeudSudPosition.getHouse()) {
            value.appendValue(PlanetValueReasonType.IS_IN_PRINCIPAL_HOUSE, noeudSudPosition.getHouse());
        }
    }

    public PlanetPosition getPlanetPosition(Planet planet) {
        return positionMap.get(planet);
    }

    public HousePosition getHousePosition(House house) {
        return houseMap.get(house);
    }
}
