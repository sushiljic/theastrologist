package com.theastrologist.external.timezone;

import com.theastrologist.external.GoogleRestClient;
import com.theastrologist.external.GoogleRestException;
import org.springframework.cache.annotation.Cacheable;

import java.net.URI;

/**
 * Created by Samy on 26/08/2015.
 */
public class TimezoneRestClient extends GoogleRestClient<TimezoneResponse> {

	private TimezoneRestClient() {
		super(TimezoneResponse.class, "timezone");
	}

	public static TimezoneRestClient newInstance() {
		return new TimezoneRestClient();
	}

	@Cacheable(cacheNames = "timezone", key = "{#latitude, #longitude, #time}")
	public TimezoneResponse getTimezone(double latitude, double longitude, long time) throws GoogleRestException {
		URI uri = getUri(latitude, longitude, time);
		return getResponse(uri);
	}

	public URI getUri(double latitude, double longitude, long time) {
		return getBaseUri().queryParam("location", latitude + "," + longitude)
						   .queryParam("timestamp", time)
						   .build()
						   .encode().toUri();
	}
}
