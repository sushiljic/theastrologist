package com.theastrologist.external.geoloc;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Created by Samy on 26/08/2015.
 */
public class GeoResponse {
    private List<GeoResult> results = Lists.newArrayList();
    private GeoStatus status;


	public List<GeoResult> getResults() {
		return results;
	}

	public void setResults(List<GeoResult> results) {
		this.results = results;
	}

	public GeoStatus getStatus() {
		return status;
	}

	public void setStatus(GeoStatus status) {
		this.status = status;
	}
}
