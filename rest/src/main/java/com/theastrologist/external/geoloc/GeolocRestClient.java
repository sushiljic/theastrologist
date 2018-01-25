package com.theastrologist.external.geoloc;

import com.theastrologist.external.GoogleRestClient;
import com.theastrologist.external.GoogleRestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ResourceBundle;

/**
 * Created by Samy on 26/08/2015.
 */
public class GeolocRestClient extends GoogleRestClient<GeoResponse> {

	private final String apiKey;
	private static final String restURL = "https://maps.googleapis.com/maps/api/geocode/json";
	private String address;

	public GeolocRestClient() {
		super(GeoResponse.class, "geocode");
		ResourceBundle bundle = ResourceBundle.getBundle("geoloc");
		this.apiKey = bundle.getString("geoloc.api.key");
	}

	public GeolocRestClient(String address) {
		this();
		this.address = address;
	}

	public GeolocRestClient(String address, RestTemplate template) {
		this(address);
		this.setRestTemplate(template);
	}

	public GeoResponse getGeocoding() throws GeolocException {
		URI uri = getUri(this.address);

		GeoResponse response;
		try {
			response = getResponse(uri);
		} catch (Exception e) {
			throw new GeolocException(e);
		}
		return response;
	}

	public GeoResponse getGeocoding(String address) throws GeolocException {
		this.address = address;
		return getGeocoding();
	}

	public URI getUri(String address) {
		return UriComponentsBuilder.fromHttpUrl(restURL)
								   .queryParam("address", address)
								   .queryParam("key", apiKey).build().encode().toUri();
	}

}
