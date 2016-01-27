package com.theastrologist.external;

import com.theastrologist.external.timezone.TimezoneResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ResourceBundle;

/**
 * Created by Samy on 27/01/2016.
 */
public class GoogleRestClient<T> {

	protected final String apiKey;
	protected static final String restURLPrefix = "https://maps.googleapis.com/maps/api/";
	protected String restURLPath;
	protected RestTemplate restTemplate;
	protected Class<T> responseClass;

	public GoogleRestClient(Class<T> responseClass, String restURLPath) {
		ResourceBundle bundle = ResourceBundle.getBundle("external-apis");
		this.apiKey = bundle.getString("google.api.key");
		this.restTemplate = new RestTemplate();
		this.responseClass = responseClass;
		this.restURLPath = restURLPath;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public UriComponentsBuilder getBaseUri() {
		return UriComponentsBuilder.fromHttpUrl(restURLPrefix)
								   .pathSegment(restURLPath).pathSegment("json")
								   .queryParam("key", apiKey);
	}

	protected T getResponse(URI uri) throws GoogleRestException {
		ResponseEntity<T> response;
		try {
			response = restTemplate.getForEntity(uri, responseClass);
		} catch (RestClientException e) {
			throw new GoogleRestException(e);
		}
		return response.getBody();
	}
}
