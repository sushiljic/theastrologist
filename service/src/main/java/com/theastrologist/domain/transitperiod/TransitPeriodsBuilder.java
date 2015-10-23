package com.theastrologist.domain.transitperiod;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.theastrologist.domain.Planet;
import com.theastrologist.domain.aspect.Aspect;
import org.joda.time.DateTime;

import java.util.Map;
import java.util.SortedSet;

/**
 * Created by Samy on 15/09/2015.
 */
public class TransitPeriodsBuilder {

	Map<Planet, SortedSet<TransitPeriod>> map = Maps.newHashMap();

	private DateTime previousTime = null;
	private DateTime currentTime = null;

	public void appendTransit(Planet natalPlanet, Planet planetInTransit, Aspect transitType) {
		if (currentTime == null) {
			throw new IllegalStateException("Cannot add transit if period not set");
		}

		if (map.containsKey(planetInTransit)) {
			// on récupère le dernier élément et on le compare avec l'élément actuel
			SortedSet<TransitPeriod> transitPeriods = map.get(planetInTransit);

			TransitPeriod lastTransitPeriod = findLastTransitPeriod(natalPlanet, transitType, transitPeriods);

			if (lastTransitPeriod != null) {
				// On met à jour l'ancienne période de transit pour qu'elle se fusionne avec la nouvelle
				lastTransitPeriod.setEndDate(currentTime);
			} else {
				// Sinon on en crée une nouvelle mais on l'ajoute à la liste
				TransitPeriod newPeriod = new TransitPeriod(planetInTransit, natalPlanet, transitType, currentTime);
				transitPeriods.add(newPeriod);
			}
		} else {
			insertNewTransitPeriod(planetInTransit, natalPlanet, transitType);
		}
	}

	private void insertNewTransitPeriod(Planet planetInTransit, Planet natalPlanet, Aspect transitType) {
		TransitPeriod period = new TransitPeriod(planetInTransit, natalPlanet, transitType, currentTime);
		SortedSet<TransitPeriod> sortedPeriods = Sets.newTreeSet();
		sortedPeriods.add(period);
		map.put(planetInTransit, sortedPeriods);
	}

	private TransitPeriod findLastTransitPeriod(final Planet natalPlanet, final Aspect transitType, SortedSet<TransitPeriod> transitPeriods) {
		TransitPeriod transitPeriod = null;
		SortedSet<TransitPeriod> filteredSet = Sets.filter(transitPeriods, new Predicate<TransitPeriod>() {
			public boolean apply(TransitPeriod transitPeriod) {
				return transitPeriod.getNatalPlanet() == natalPlanet &&
					   transitPeriod.getEndDate().equals(previousTime) && transitType == transitPeriod.getAspect();
			}
		});

		if (filteredSet.size() > 0) {
			transitPeriod = filteredSet.last();
		}
		return transitPeriod;
	}

	public void startNewPeriod(DateTime dateTime) {
		endPeriod();
		currentTime = dateTime;
	}

	private void endPeriod() {
		previousTime = currentTime;
	}

	public TransitPeriods build() {
		endPeriod();
		TransitPeriods returnedValue = new TransitPeriods();
		returnedValue.setPeriods(map);
		return returnedValue;
	}

	public void cleanTransitsWithNoLength(Planet planetInTransit) {
		SortedSet<TransitPeriod> transitPeriods = map.get(planetInTransit);
		SortedSet<TransitPeriod> newPeriods = Sets.filter(transitPeriods, new Predicate<TransitPeriod>() {
			public boolean apply(TransitPeriod transitPeriod) {
				return !transitPeriod.getStartDate().equals(transitPeriod.getEndDate());
			}
		});
		map.put(planetInTransit, newPeriods);
	}
}
