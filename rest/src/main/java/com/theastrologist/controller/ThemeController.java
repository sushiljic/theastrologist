package com.theastrologist.controller;

import com.theastrologist.core.ThemeCalculator;
import com.theastrologist.domain.Degree;
import com.theastrologist.domain.SkyPosition;
import com.theastrologist.external.geoloc.*;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by SAM on 16/11/2014.
 */
@RestController
@RequestMapping("/{datetime}")
public class ThemeController extends AbstractController {

	private SkyPosition getSkyPosition(String datetime, double latitude, double longitude, String address) {
		DateTime parse = timeService.parseDateTime(datetime, latitude, longitude);
		Degree latitudeDegree = new Degree(latitude);
		Degree longitudeDegree = new Degree(longitude);
		SkyPosition skyPosition = ThemeCalculator.INSTANCE.getSkyPosition(parse, latitudeDegree, longitudeDegree);
		if (address != null) {
			skyPosition.setAddress(address);
		}
		return skyPosition;
	}

	@GetMapping(value = "/{latitude:.+}/{longitude:.+}/theme")
	public SkyPosition getTheme(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String datetime,
								@PathVariable double latitude,
								@PathVariable double longitude) {
		return getSkyPosition(datetime, latitude, longitude, null);
	}

	@GetMapping(value = "/{address}/theme")
	public ResponseEntity<SkyPosition> getTheme(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String datetime,
			@PathVariable String address) throws GeolocException {

		GeoResult geoResult = queryForGeoloc(address);
		double latitude = geoResult.getGeometry().getLocation().getLat();
		double longitude = geoResult.getGeometry().getLocation().getLng();

		SkyPosition skyPosition = getSkyPosition(datetime, latitude, longitude, geoResult.getFormatted_address());
		return new ResponseEntity<SkyPosition>(skyPosition, HttpStatus.OK);
	}
}
