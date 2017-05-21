package com.theastrologist.external.geoloc;

import lombok.Data;

/**
 * Created by Samy on 26/08/2015.
 */
@Data
public class GeoGeometry {
    private GeoLocation location;

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }
}
