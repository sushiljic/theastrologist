package com.theastrologist.external.geoloc;

import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ResourceBundle;

/**
 * Created by Samy on 26/08/2015.
 */
public class GeolocRestClient {

    private final String apiKey;
    private static final String restURL = "https://maps.googleapis.com/maps/api/geocode/json";
    private final String address;
    private RestTemplate restTemplate;

    public GeolocRestClient(String address) {
        ResourceBundle bundle = ResourceBundle.getBundle("geoloc");
        this.apiKey = bundle.getString("geoloc.api.key");
        this.address = address;
        this.restTemplate = new RestTemplate();
    }

    public GeolocRestClient(String address, RestTemplate template) {
        this(address);
        this.restTemplate = template;
    }

    public GeoResponse getGeocoding() throws GeolocException {
        URI uri = getUri(this.address);

        ResponseEntity<GeoResponse> response;
        try {
            response = restTemplate.getForEntity(uri, GeoResponse.class);
        } catch (RestClientException e) {
            throw new GeolocException(e);
        }

        return response.getBody();
    }

    public URI getUri(String address) {
        return UriComponentsBuilder.fromHttpUrl(restURL)
                    .queryParam("address", address)
                    .queryParam("key", apiKey).build().encode().toUri();
    }

}
