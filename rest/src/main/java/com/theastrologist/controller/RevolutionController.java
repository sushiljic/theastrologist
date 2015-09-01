package com.theastrologist.controller;

import com.theastrologist.core.RevolutionCalculator;
import com.theastrologist.core.ThemeCalculator;
import com.theastrologist.domain.Degree;
import com.theastrologist.domain.DateTimeJson;
import com.theastrologist.domain.SkyPosition;
import com.theastrologist.external.geoloc.GeoResult;
import com.theastrologist.external.geoloc.GeolocException;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Samy on 15/05/2017.
 */
@RestController
public class RevolutionController extends AbstractController {

	private SkyPosition calculateSkyPosition(DateTime natalDate, double natalLatitude, double natalLongitude) {
		Degree latitudeDegree = new Degree(natalLatitude);
		Degree longitudeDegree = new Degree(natalLongitude);
		return ThemeCalculator.INSTANCE.getSkyPosition(natalDate, latitudeDegree, longitudeDegree);
	}

	private SkyPosition getSolarRevolutionTheme(String fromDate, double anniversaryLatitude,
												double anniversaryLongitude,
												SkyPosition natalTheme) {
		DateTime fromDateTime = new DateTime(fromDate);
		DateTime solarRevolutionUT = RevolutionCalculator.INSTANCE.getSolarRevolutionUT(natalTheme, fromDateTime);
		DateTime zonedDateTime = controllerUtil
				.convertUTDateTime(solarRevolutionUT, anniversaryLatitude, anniversaryLongitude);
		return calculateSkyPosition(zonedDateTime, anniversaryLatitude, anniversaryLongitude);
	}

	private SkyPosition getNatalTheme(String natalDate, double natalLatitude, double natalLongitude) {
		DateTime natalDateTime = controllerUtil.parseDateTime(natalDate, natalLatitude, natalLongitude);
		return calculateSkyPosition(natalDateTime, natalLatitude, natalLongitude);
	}

	private DateTimeJson getSolarDateTimeJson(String natalDate, double natalLatitude, double natalLongitude,
											  String fromDate) {
		SkyPosition natalTheme = getNatalTheme(natalDate, natalLatitude, natalLongitude);
		DateTime fromDateTime = new DateTime(fromDate);
		DateTime solarRevolutionUT = RevolutionCalculator.INSTANCE.getSolarRevolutionUT(natalTheme, fromDateTime);
		return new DateTimeJson(solarRevolutionUT);
	}

	private DateTimeJson getLunarDateTimeJson(String natalDate, double natalLatitude, double natalLongitude,
											  String fromDate) {
		SkyPosition natalTheme = getNatalTheme(natalDate, natalLatitude, natalLongitude);

		DateTime fromDateTime = new DateTime(fromDate);
		DateTime lunarRevolutionUT = RevolutionCalculator.INSTANCE.getLunarRevolutionUT(natalTheme, fromDateTime);
		return new DateTimeJson(lunarRevolutionUT);
	}

	private SkyPosition getLunarRevolutionTheme(String fromDate, double anniversaryLatitude,
												double anniversaryLongitude, SkyPosition natalTheme) {
		DateTime fromDateTime = new DateTime(fromDate);
		DateTime lunarRevolutionUT = RevolutionCalculator.INSTANCE.getLunarRevolutionUT(natalTheme, fromDateTime);
		DateTime zonedDateTime = controllerUtil
				.convertUTDateTime(lunarRevolutionUT, anniversaryLatitude, anniversaryLongitude);
		return calculateSkyPosition(zonedDateTime, anniversaryLatitude, anniversaryLongitude);
	}

	@GetMapping(value = "/{natalDate}/{natalLatitude:.+}/{natalLongitude:.+}/revolution/solar/{fromDate}/{anniversaryLatitude:.+}/{anniversaryLongitude:.+}")
	public SkyPosition getSolarRevolution(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable double natalLatitude,
			@PathVariable double natalLongitude,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate,
			@PathVariable double anniversaryLatitude,
			@PathVariable double anniversaryLongitude) {
		SkyPosition natalTheme = getNatalTheme(natalDate, natalLatitude, natalLongitude);

		return getSolarRevolutionTheme(fromDate, anniversaryLatitude,
									   anniversaryLongitude, natalTheme);
	}

	@GetMapping(value = "/{natalDate}/{natalLatitude:.+}/{natalLongitude:.+}/revolution/solar/{fromDate}/{anniversaryAddress}")
	public SkyPosition getSolarRevolutionWithAnniversaryAddress(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable double natalLatitude,
			@PathVariable double natalLongitude,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate,
			@PathVariable String anniversaryAddress) throws GeolocException {
		SkyPosition natalTheme = getNatalTheme(natalDate, natalLatitude, natalLongitude);

		GeoResult anniversaryGeo = queryForGeoloc(anniversaryAddress);

		return getSolarRevolutionTheme(fromDate,
									   anniversaryGeo.getGeometry().getLocation().getLat(),
									   anniversaryGeo.getGeometry().getLocation().getLng(), natalTheme);
	}

	@GetMapping(value = "/{natalDate}/{natalAddress}/revolution/solar/{fromDate}/{anniversaryLatitude:.+}/{anniversaryLongitude:.+}")
	public SkyPosition getSolarRevolutionWithNatalAddress(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable String natalAddress,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate,
			@PathVariable double anniversaryLatitude,
			@PathVariable double anniversaryLongitude) throws GeolocException {

		GeoResult geoResult = queryForGeoloc(natalAddress);
		double natalLatitude = geoResult.getGeometry().getLocation().getLat();
		double natalLongitude = geoResult.getGeometry().getLocation().getLng();

		SkyPosition natalTheme = getNatalTheme(natalDate, natalLatitude, natalLongitude);

		return getSolarRevolutionTheme(fromDate, anniversaryLatitude, anniversaryLongitude, natalTheme);
	}

	@GetMapping(value = "/{natalDate}/{natalAddress}/revolution/solar/{fromDate}/{anniversaryAddress}")
	public SkyPosition getSolarRevolutionWithBothAddress(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable String natalAddress,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate,
			@PathVariable String anniversaryAddress) throws GeolocException {

		GeoResult natalGeo = queryForGeoloc(natalAddress);

		SkyPosition natalTheme = getNatalTheme(natalDate,
											   natalGeo.getGeometry().getLocation().getLat(),
											   natalGeo.getGeometry().getLocation().getLng());

		GeoResult anniversaryGeo = queryForGeoloc(anniversaryAddress);

		return getSolarRevolutionTheme(fromDate,
									   anniversaryGeo.getGeometry().getLocation().getLat(),
									   anniversaryGeo.getGeometry().getLocation().getLng(), natalTheme);
	}

	@GetMapping(value = "/{natalDate}/{natalLatitude:.+}/{natalLongitude:.+}/revolution/solar/{fromDate}/date")
	public DateTimeJson getSolarRevolutionDate(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable double natalLatitude,
			@PathVariable double natalLongitude,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate) {

		return getSolarDateTimeJson(natalDate, natalLatitude, natalLongitude, fromDate);
	}

	@GetMapping(value = "/{natalDate}/{address}/revolution/solar/{fromDate}/date")
	public DateTimeJson getSolarRevolutionDate(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable String address,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate) throws GeolocException {
		GeoResult geoResult = queryForGeoloc(address);
		double natalLatitude = geoResult.getGeometry().getLocation().getLat();
		double natalLongitude = geoResult.getGeometry().getLocation().getLng();
		return getSolarDateTimeJson(natalDate, natalLatitude, natalLongitude, fromDate);
	}

	@GetMapping(value = "/{natalDate}/{natalLatitude:.+}/{natalLongitude:.+}/revolution/lunar/{fromDate}/{anniversaryLatitude:.+}/{anniversaryLongitude:.+}")
	public SkyPosition getLunarRevolution(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable double natalLatitude,
			@PathVariable double natalLongitude,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate,
			@PathVariable double anniversaryLatitude,
			@PathVariable double anniversaryLongitude) {
		SkyPosition natalTheme = getNatalTheme(natalDate, natalLatitude, natalLongitude);

		return getLunarRevolutionTheme(fromDate, anniversaryLatitude, anniversaryLongitude,
									   natalTheme);
	}

	@GetMapping(value = "/{natalDate}/{natalAddress}/revolution/lunar/{fromDate}/{anniversaryLatitude:.+}/{anniversaryLongitude:.+}")
	public SkyPosition getLunarRevolutionWithNatalAddress(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable String natalAddress,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate,
			@PathVariable double anniversaryLatitude,
			@PathVariable double anniversaryLongitude) throws GeolocException {
		GeoResult natalGeo = queryForGeoloc(natalAddress);

		SkyPosition natalTheme = getNatalTheme(natalDate,
											   natalGeo.getGeometry().getLocation().getLat(),
											   natalGeo.getGeometry().getLocation().getLng());

		return getLunarRevolutionTheme(fromDate, anniversaryLatitude,
									   anniversaryLongitude, natalTheme);
	}

	@GetMapping(value = "/{natalDate}/{natalLatitude:.+}/{natalLongitude:.+}/revolution/lunar/{fromDate}/{anniversaryAddress}")
	public SkyPosition getLunarRevolutionWithAnniversaryAddress(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable double natalLatitude,
			@PathVariable double natalLongitude,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate,
			@PathVariable String anniversaryAddress) throws GeolocException {
		SkyPosition natalTheme = getNatalTheme(natalDate, natalLatitude, natalLongitude);

		GeoResult anniversaryGeo = queryForGeoloc(anniversaryAddress);

		return getLunarRevolutionTheme(fromDate, anniversaryGeo.getGeometry().getLocation().getLat(),
									   anniversaryGeo.getGeometry().getLocation().getLng(), natalTheme);
	}

	@GetMapping(value = "/{natalDate}/{natalAddress}/revolution/lunar/{fromDate}/{anniversaryAddress}")
	public SkyPosition getLunarRevolutionWithBothAddress(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable String natalAddress,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate,
			@PathVariable String anniversaryAddress) throws GeolocException {
		GeoResult natalGeo = queryForGeoloc(natalAddress);
		SkyPosition natalTheme = getNatalTheme(natalDate,
											   natalGeo.getGeometry().getLocation().getLat(),
											   natalGeo.getGeometry().getLocation().getLng());

		GeoResult anniversaryGeo = queryForGeoloc(anniversaryAddress);

		return getLunarRevolutionTheme(fromDate,
									   anniversaryGeo.getGeometry().getLocation().getLat(),
									   anniversaryGeo.getGeometry().getLocation().getLng(),
									   natalTheme);
	}

	@GetMapping(value = "/{natalDate}/{natalLatitude:.+}/{natalLongitude:.+}/revolution/lunar/{fromDate}/date")
	public DateTimeJson getLunarRevolutionDate(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable double natalLatitude,
			@PathVariable double natalLongitude,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate) {
		return getLunarDateTimeJson(natalDate, natalLatitude, natalLongitude, fromDate);
	}

	@GetMapping(value = "/{natalDate}/{address}/revolution/lunar/{fromDate}/date")
	public DateTimeJson getLunarRevolutionDateWithAddress(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable String address,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate) throws GeolocException {
		GeoResult geoResult = queryForGeoloc(address);
		double natalLatitude = geoResult.getGeometry().getLocation().getLat();
		double natalLongitude = geoResult.getGeometry().getLocation().getLng();

		return getLunarDateTimeJson(natalDate, natalLatitude, natalLongitude, fromDate);
	}
}
