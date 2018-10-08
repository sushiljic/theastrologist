package com.theastrologist.domain;

import com.google.gson.annotations.JsonAdapter;
import com.theastrologist.domain.aspect.AspectPosition;
import com.theastrologist.util.CalcUtil;
import com.theastrologist.util.DateUtil;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * @author SAM
 * @since 16/11/2014.
 * Classe permettant de calculer la position des planètes dans le ciel à une date donnée
 */
public class SkyPosition {

	static final Logger LOG = Logger.getLogger(SkyPosition.class);
	@JsonAdapter(DateTimeJSONAdapter.class)
	private DateTime date;
	private final Degree latitude;
	private final Degree longitude;
	private String address;
	private SortedMap<Planet, PlanetPosition> positions = new TreeMap<Planet, PlanetPosition>();

	private transient Map<House, HousePosition> houseMap = new HashMap<House, HousePosition>();

	// Champs pour le calcul des aspects
	private SortedMap<Planet, SortedMap<Planet, AspectPosition>> aspects;

	public SkyPosition(DateTime dateTime, Degree latitude, Degree longitude) {
		this.date = dateTime;

		this.latitude = latitude;
		this.longitude = longitude;
	}

	public void setAspects(SortedMap<Planet, SortedMap<Planet, AspectPosition>> aspectsForSkyPosition) {
		this.aspects = aspectsForSkyPosition;
	}

	public SortedMap<Planet, SortedMap<Planet, AspectPosition>> getAspects() {
		return aspects;
	}

	public void calculate(SwissEph sw) {
		SweDate sd = DateUtil.getSweDateUTC(date);

		LOG.debug("Calculating sky position for Date = " + sd + ", with Latitude = " + latitude + " and Longitude = "
				  + longitude);

		fillHousesAndAscendant(sw, sd);
		fillPlanets(sw, sd);
	}

	private void fillPlanets(SwissEph sw, SweDate sd) {
		int flags = SweConst.SEFLG_MOSEPH;
		//int flags = 0;
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
						LOG.error(String.format("Warning, different flags used (0x%x)", ret));
					}
				}

				double position = xp[0];
				boolean retrograde = (xp[3] < 0);

				Degree degree = new Degree(position);

				PlanetPosition planetPosition = PlanetPosition.createPlanetPosition(degree, ascendantDegree);
				planetPosition.setRetrograde(retrograde);

				this.positions.put(planet, planetPosition);
			} else if (planet == Planet.NOEUD_SUD_MOYEN) {
				PlanetPosition noeudNord = this.positions.get(Planet.NOEUD_NORD_MOYEN);
				Degree noeudSudDegree = CalcUtil.getOpposite(noeudNord.getDegree());

				PlanetPosition planetPosition = PlanetPosition.createPlanetPosition(noeudSudDegree, ascendantDegree);
				planetPosition.setRetrograde(true);

				this.positions.put(planet, planetPosition);
			} else if (planet == Planet.PART_DE_FORTUNE) {
				Degree sunDegree = this.positions.get(Planet.SOLEIL).getDegree();
				Degree moonDegree = this.positions.get(Planet.LUNE).getDegree();

				Degree partDeFortune = CalcUtil.calculatePartDeFortune(ascendantDegree, sunDegree, moonDegree);
				PlanetPosition planetPosition = PlanetPosition.createPlanetPosition(
						partDeFortune, ascendantDegree);

				this.positions.put(planet, planetPosition);
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
		this.positions.put(Planet.ASCENDANT, asPosition);

		Degree mcDegree = new Degree(mc);
		this.positions.put(Planet.MILIEU_DU_CIEL, PlanetPosition.createPlanetPosition(mcDegree, asDegree));
	}

	public PlanetPosition getAscendantPosition() {
		return getPlanetPosition(Planet.ASCENDANT);
	}

	public PlanetPosition getLunePosition() {
		return getPlanetPosition(Planet.LUNE);
	}

	public PlanetPosition getSoleilPosition() {
		return getPlanetPosition(Planet.SOLEIL);
	}

	public PlanetPosition getNoeudSudPosition() {
		return getPlanetPosition(Planet.NOEUD_SUD_MOYEN);
	}

	public PlanetPosition getPlanetPosition(Planet planet) {
		return positions.get(planet);
	}

	public HousePosition getHousePosition(House house) {
		return houseMap.get(house);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}


}
