package com.theastrologist.external.geoloc;

/**
 * Created by Samy on 26/08/2015.
 */
public enum GeoStatus {
    OK,                 //indicates that no errors occurred; the address was successfully parsed and at least one geocode was returned.
    ZERO_RESULTS,       //indicates that the geocode was successful but returned no results. This may occur if the geocoder was passed a non-existent address.
    OVER_QUERY_LIMIT,   //indicates that you are over your quota.
    REQUEST_DENIED,     //indicates that your request was denied.
    INVALID_REQUEST,    //generally indicates that the query (address, components or latlng) is missing.
    UNKNOWN_ERROR;      //indicates that the request could not be processed due to a server error. The request may succeed if you try again.
}
