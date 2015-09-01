package com.theastrologist.controller;

import com.theastrologist.core.ThemeCalculator;
import com.theastrologist.core.TransitPeriodCalculator;
import com.theastrologist.domain.Degree;
import com.theastrologist.domain.SkyPosition;
import com.theastrologist.domain.transitperiod.TransitPeriods;
import com.theastrologist.external.geoloc.GeoResult;
import com.theastrologist.external.geoloc.GeolocException;
import com.theastrologist.util.ControllerUtil;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Samy on 16/09/2015.
 */
@RestController
public class TransitPeriodController extends AbstractController {

	@GetMapping(value = "/{natalDate}/{latitude:.+}/{longitude:.+}/transitperiod/{startDate}/{endDate}")
	public TransitPeriods getTransitPeriod(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable double latitude,
			@PathVariable double longitude,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) {
		TransitPeriods transitPeriods = getTransitPeriods(natalDate, latitude, longitude, startDate, endDate);
		return transitPeriods;
	}

	@GetMapping(value = "/{natalDate}/{address}/transitperiod/{startDate}/{endDate}")
	public TransitPeriods getTransitPeriodWithAddress(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable String address,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) throws GeolocException {

		GeoResult geoResult = queryForGeoloc(address);
		double latitude = geoResult.getGeometry().getLocation().getLat();
		double longitude = geoResult.getGeometry().getLocation().getLng();
		TransitPeriods transitPeriods = getTransitPeriods(natalDate, latitude, longitude, startDate, endDate);
		return transitPeriods;
	}

	private TransitPeriods getTransitPeriods(String natalDate, double latitude, double longitude, String startDate,
											 String endDate) {
		DateTime parsedNatalDate = controllerUtil.parseDateTime(natalDate, latitude, longitude);
		DateTime parsedStartDate = new DateTime(startDate);
		DateTime parsedEndDate = new DateTime(endDate);
		Degree parsedLatitude = new Degree(latitude);
		Degree parsedLongitude = new Degree(longitude);
		SkyPosition natalPosition = ThemeCalculator.INSTANCE
				.getSkyPosition(parsedNatalDate, parsedLatitude, parsedLongitude);
		return TransitPeriodCalculator.INSTANCE
				.createTransitPeriod(natalPosition, parsedStartDate, parsedEndDate, parsedLatitude, parsedLongitude);
	}
}
