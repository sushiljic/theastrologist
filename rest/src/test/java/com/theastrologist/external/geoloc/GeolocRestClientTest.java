package com.theastrologist.external.geoloc;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

/**
 * Created by Samy on 05/09/2015.
 */
@RunWith(EasyMockRunner.class)
public class GeolocRestClientTest {

    @Mock
    private RestTemplate mockedRestTemplate;

    @Test
    public void testGeoClientMultipleResults() throws GeolocException {
        GeolocRestClient geolocRestClient = new GeolocRestClient("Chi");
        GeoResponse response = geolocRestClient.getGeocoding();
        assertThat(response, notNullValue());
        assertThat(response.getStatus(), is(GeoStatus.OK));
        assertThat(response.getResults(), not(emptyCollectionOf(GeoResult.class)));
        assertThat(response.getResults(), hasSize(greaterThan(1)));
    }

    @Test
    public void testGeoClientOneResult() throws GeolocException {
        GeolocRestClient geolocRestClient = new GeolocRestClient("Paris");
        GeoResponse response = geolocRestClient.getGeocoding();
        assertThat(response, notNullValue());
        assertThat(response.getStatus(), is(GeoStatus.OK));
        assertThat(response.getResults(), hasSize(1));
        GeoResult geoResult = response.getResults().get(0);
        GeoLocation location = geoResult.getGeometry().getLocation();
        assertThat(location.getLat(), equalTo(48.856614));
        assertThat(location.getLng(), equalTo(2.3522219));
        assertThat(geoResult.getFormatted_address(), equalTo("Paris, France"));
    }

    @Test
    public void testGeoClientOneResultAddress() throws GeolocException {
        GeolocRestClient geolocRestClient = new GeolocRestClient("142 Lecourbe");
        GeoResponse response = geolocRestClient.getGeocoding();
        assertThat(response, notNullValue());
        assertThat(response.getStatus(), is(GeoStatus.OK));
        GeoResult geoResult = response.getResults().get(0);
        assertThat(geoResult.getFormatted_address(), equalTo("142 Rue Lecourbe, 75015 Paris, France"));
    }

    @Test
    public void testGeoClientZeroResult() throws GeolocException {
        GeolocRestClient geolocRestClient = new GeolocRestClient("Choudavoir");
        GeoResponse response = geolocRestClient.getGeocoding();
        assertThat(response, notNullValue());
        assertThat(response.getStatus(), is(GeoStatus.ZERO_RESULTS));
        assertThat(response.getResults(), emptyCollectionOf(GeoResult.class));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGeoClientError() throws GeolocException {
        // Given
        String address = "Choudavoir";
        GeolocRestClient geolocRestClient = new GeolocRestClient(address, mockedRestTemplate);

        // When
        expect(mockedRestTemplate.getForEntity(geolocRestClient.getUri(address), GeoResponse.class)).andThrow(new RestClientException("Chouchou"));
        replay(mockedRestTemplate);
        thrown.expect(GeolocException.class);
        thrown.expectMessage("Connectivity with Google Geoloc API");
        GeoResponse response = geolocRestClient.getGeocoding();

        // Then
        verify(mockedRestTemplate);
    }
}
