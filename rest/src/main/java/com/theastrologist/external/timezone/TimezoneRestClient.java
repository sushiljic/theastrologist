package com.theastrologist.external.timezone;

import com.theastrologist.external.GoogleRestClient;
import com.theastrologist.external.GoogleRestException;
import com.theastrologist.external.timezone.TimezoneResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by Samy on 26/08/2015.
 */
public class TimezoneRestClient extends GoogleRestClient<TimezoneResponse> {
	private final double latitude;
	private final double longitude;
	private final long time;

	public TimezoneRestClient(double latitude, double longitude, long time) {
		super(TimezoneResponse.class, "timezone");
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
	}

	public TimezoneResponse getTimezone() throws GoogleRestException {
		URI uri = getUri(this.latitude, this.longitude, this.time);
		return getResponse(uri);
	}

	public URI getUri(double latitude, double longitude, long time) {
		return getBaseUri().queryParam("location", latitude + "," + longitude)
						   .queryParam("timestamp", time)
						   .build()
						   .encode().toUri();
	}

}
