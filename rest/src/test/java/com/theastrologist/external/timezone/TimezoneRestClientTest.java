package com.theastrologist.external.timezone;

import com.theastrologist.external.GoogleRestException;
import com.theastrologist.external.GoogleStatus;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Created by Samy on 05/09/2015.
 */
@RunWith(EasyMockRunner.class)
public class TimezoneRestClientTest {

	@Mock
	private RestTemplate mockedRestTemplate;

	@Test
	public void testTimezoneClient() throws GoogleRestException {
		TimezoneRestClient timezoneRestClient = new TimezoneRestClient(48.64566300000001, 2.410451, 473685600000L);
		TimezoneResponse response = timezoneRestClient.getTimezone();
		assertThat(response, notNullValue());
		assertThat(response.status, is(GoogleStatus.OK));
		assertThat(response.timeZoneId, equalTo("Europe/Paris"));
		assertThat(response.dstOffset, equalTo(3600L));
	}


	@Test
	public void testTimezoneClientAilleurs() throws GoogleRestException {
		TimezoneRestClient timezoneRestClient = new TimezoneRestClient(-26.3902488, -70.0475302, -244161900000L);
		TimezoneResponse response = timezoneRestClient.getTimezone();
		assertThat(response, notNullValue());
		assertThat(response.status, is(GoogleStatus.OK));
		assertThat(response.timeZoneId, equalTo("America/Santiago"));
		assertThat(response.dstOffset, equalTo(0L));
		assertThat(response.rawOffset, equalTo(-16966L));
	}


	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testTimezoneClientError() throws GoogleRestException {
		// Given
		String address = "Choudavoir";
		TimezoneRestClient timezoneRestClient = new TimezoneRestClient(48.64566300000001, 2.410451, 473685600000L);
		timezoneRestClient.setRestTemplate(mockedRestTemplate);

		// When
		expect(mockedRestTemplate.getForEntity(timezoneRestClient.getUri(48.64566300000001, 2.410451, 473685600000L),
											   TimezoneResponse.class))
				.andThrow(new RestClientException("Chouchou"));
		replay(mockedRestTemplate);
		thrown.expect(GoogleRestException.class);
		thrown.expectMessage("Connectivity with Google API");
		TimezoneResponse response = timezoneRestClient.getTimezone();

		// Then
		verify(mockedRestTemplate);
	}
}
