package com.theastrologist.domain.transitperiod;

import com.theastrologist.domain.Planet;
import lombok.Data;

import java.util.Map;
import java.util.SortedSet;

/**
 * Created by Samy on 16/09/2015.
 */
public class TransitPeriods {
	private Map<Planet, SortedSet<TransitPeriod>> periods;

	public Map<Planet, SortedSet<TransitPeriod>> getPeriods() {
		return periods;
	}

	public void setPeriods(
			Map<Planet, SortedSet<TransitPeriod>> periods) {
		this.periods = periods;
	}
}
