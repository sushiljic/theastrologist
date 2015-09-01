package com.theastrologist.external.geoloc;


/**
 * Created by Samy on 26/08/2015.
 */
public class GeoResult {
    private String formatted_address;
    private GeoGeometry geometry;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public GeoGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(GeoGeometry geometry) {
        this.geometry = geometry;
    }
}
