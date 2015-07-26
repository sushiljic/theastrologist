package com.theastrologist.rest.domain;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import swisseph.SweDate;
import swisseph.SwissEph;
import util.CalcUtil;
import util.DateUtil;

import java.util.*;

import static com.theastrologist.rest.domain.PlanetPosition.createPlanetPosition;

/**
 * @author SAM
 * @since 16/11/2014.
 * Classe permettant de calculer la position des planètes dans le ciel à une date donnée
 */
public class SkyPosition {

    static final Logger LOG = Logger.getLogger(SkyPosition.class);
    private final Degree latitude;
    private final Degree longitude;
    private DateTime date;
    private Map<Planet, PlanetPosition> positionMap = new HashMap<Planet, PlanetPosition>();

    private Map<House, HousePosition> houseMap = new HashMap<House, HousePosition>();

    // Champs pour le calcul des dominances
    private SortedSet<Planet> dominantPlanetList = null;
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

                PlanetPosition planetPosition = createPlanetPosition(degree, ascendantDegree);
                planetPosition.setRetrograde(retrograde);

                this.positionMap.put(planet, planetPosition);
            } else if (planet == Planet.NOEUD_SUD_MOYEN) {
                PlanetPosition noeudNord = this.positionMap.get(Planet.NOEUD_NORD_MOYEN);
                Degree noeudSudDegree = CalcUtil.getOpposite(noeudNord.getDegree());

                PlanetPosition planetPosition = createPlanetPosition(noeudSudDegree, ascendantDegree);
                planetPosition.setRetrograde(true);

                this.positionMap.put(planet, planetPosition);
            } else if (planet == Planet.PART_DE_FORTUNE) {
                Degree sunDegree = this.positionMap.get(Planet.SOLEIL).getDegree();
                Degree moonDegree = this.positionMap.get(Planet.LUNE).getDegree();

                Degree partDeFortune = CalcUtil.calculatePartDeFortune(ascendantDegree, sunDegree, moonDegree);
                PlanetPosition planetPosition = createPlanetPosition(
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
        this.positionMap.put(Planet.MILIEU_DU_CIEL, createPlanetPosition(mcDegree, asDegree));

        Degree fcDegree = CalcUtil.getOpposite(mcDegree);
        this.positionMap.put(Planet.FOND_DU_CIEL, createPlanetPosition(fcDegree, asDegree));

        Degree dsDegree = CalcUtil.getOpposite(asDegree);
        this.positionMap.put(Planet.DESCENDANT, createPlanetPosition(dsDegree, asDegree));
    }

    public SortedSet<Planet> getDominantPlanets() {
        if (dominantPlanetList == null) {
            // Calcul des dominantes

            final SkyPosition skyPosition = this;

            dominantPlanetList = new TreeSet<Planet>(new Comparator<Planet>() {
                public int compare(Planet planet, Planet planetToCompare) {
                    return - skyPosition.compare(planet, planetToCompare);
                }
            });

            // TODO enlever les planètes qui n'en sont pas

            Collections.addAll(dominantPlanetList, Planet.values());
        }
        return dominantPlanetList;
    }

    public int compare(Planet planet, Planet planetToCompare) {

        int dominantPoints = calculateDominant(planet);
        int dominantPointsToCompare = calculateDominant(planetToCompare);

        return dominantPoints - dominantPointsToCompare;
    }

    private int calculateDominant(Planet planet) {
        PlanetPosition planetPosition = positionMap.get(planet);
        Sign sign = planetPosition.getSign();
        House house = planetPosition.getHouse();

        int points = 0;

        if (isPlanetMaitrePrincipalSign(planet)) {
            points += 20;
        }

        if (isPlanetMaitrePrincipalHouse(planet)) {
            points += 1;
        }

        if (hasConjunctionWithLuminaire(planet)) {
            points += 10;
        } else {
            if (isInPrincipaleSign(planetPosition)) {
                points += 1;
            }

            if (isInPrincipaleHouse(planet)) {
                points += 1;
            }
        }

        if (sign.isMasterPlanet(planet)) {
            points += 4;
        }

        if (sign.isExaltedPlanet(planet)) {
            points += 2;
        }

        if (sign.isExilPlanet(planet)) {
            points -= 4;
        }

        if (sign.isChutePlanet(planet)) {
            points -= 2;
        }

        if (house.isMasterPlanet(planet)) {
            points += 4;
        }

        if (house.isExaltedPlanet(planet)) {
            points += 2;
        }

        if (house.isExilPlanet(planet)) {
            points -= 4;
        }

        if (house.isChutePlanet(planet)) {
            points -= 2;
        }

        if (house.isChutePlanet(planet)) {
            points -= 2;
        }

        return points;
    }



    private boolean hasConjunctionWithLuminaire(Planet planet) {
        boolean returnValue = false;
        Map<Planet, Map<Planet, AspectPosition>> aspectsMap = getAspects();
        Map<Planet, AspectPosition> aspects = aspectsMap.get(planet);

        for (Planet principalePlanet : Planet.getPrincipalePlanets()) {
            if (aspects.containsKey(principalePlanet) && aspects.get(principalePlanet).getAspect() == Aspect.CONJONCTION) {
                returnValue = true;
                break;
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

    private boolean isPlanetMaitrePrincipalSign(Planet planet) {
        return isPlanetMasterSoleil(planet) || isPlanetMasterAscendant(planet) || isPlanetMasterLune(planet) || isPlanetMasterNoeudSud(planet);
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

    private boolean isInPrincipaleSign(PlanetPosition planetPosition) {
        Sign sign = planetPosition.getSign();
        return sign == ascendantPosition.getSign() || sign == lunePosition.getSign() ||
                sign == soleilPosition.getSign() || sign == noeudSudPosition.getSign();
    }

    private boolean isPlanetMaitrePrincipalHouse(Planet planet) {
        return isPlanetMasterSoleilHouse(planet) || isPlanetMasterLuneHouse(planet) || isPlanetMasterNoeudSudHouse(planet);
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

    private boolean isInPrincipaleHouse(Planet planet) {
        // TODO is principale house
        return false;
    }

    public PlanetPosition getPlanetPosition(Planet planet) {
        return positionMap.get(planet);
    }

    public HousePosition getHousePosition(House house) {
        return houseMap.get(house);
    }
}
