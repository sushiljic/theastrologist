package com.theastrologist.external.geoloc;

import lombok.Data;

/**
 * Created by Samy on 26/08/2015.
 */
@Data
public class GeoLocation {
	private double lat;
	private double lng;

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
}
