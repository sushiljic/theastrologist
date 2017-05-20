package com.theastrologist.controller;

import com.theastrologist.core.RevolutionCalculator;
import com.theastrologist.core.ThemeCalculator;
import com.theastrologist.domain.Degree;
import com.theastrologist.domain.DateTimeJson;
import com.theastrologist.domain.SkyPosition;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Samy on 15/05/2017.
 */
@RestController
@RequestMapping("/{natalDate}/{natalLatitude:.+}/{natalLongitude:.+}/revolution")
public class RevolutionController extends AbstractController {

	private SkyPosition calculateSkyPosition(String natalDate, double natalLatitude, double natalLongitude) {
		DateTime parse = controllerUtil.parseDateTime(natalDate, natalLatitude, natalLongitude);
		Degree latitudeDegree = new Degree(natalLatitude);
		Degree longitudeDegree = new Degree(natalLongitude);
		return ThemeCalculator.INSTANCE.getSkyPosition(parse, latitudeDegree, longitudeDegree);
	}

	@GetMapping(value = "/solar/{fromDate}/{anniversaryLatitude:.+}/{anniversaryLongitude:.+}")
	public SkyPosition getSolarRevolution(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable double natalLatitude,
			@PathVariable double natalLongitude,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate,
			@PathVariable double anniversaryLatitude,
			@PathVariable double anniversaryLongitude) {
		SkyPosition natalTheme = calculateSkyPosition(natalDate, natalLatitude, natalLongitude);

		DateTime fromDateTime = new DateTime(fromDate);
		Degree latitudeDegree = new Degree(anniversaryLatitude);
		Degree longitudeDegree = new Degree(anniversaryLongitude);

		DateTime solarRevolutionUT = RevolutionCalculator.INSTANCE.getSolarRevolutionUT(natalTheme, fromDateTime);
		DateTime zonedDateTime = controllerUtil.convertUTDateTime(solarRevolutionUT, anniversaryLatitude, anniversaryLongitude);
		return ThemeCalculator.INSTANCE.getSkyPosition(zonedDateTime, latitudeDegree, longitudeDegree);
	}

	@GetMapping(value = "/solar/{fromDate}/date")
	public DateTimeJson getSolarRevolutionDate(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable double natalLatitude,
			@PathVariable double natalLongitude,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate) {
		SkyPosition natalTheme = calculateSkyPosition(natalDate, natalLatitude, natalLongitude);

		DateTime fromDateTime = new DateTime(fromDate);
		DateTime solarRevolutionUT = RevolutionCalculator.INSTANCE.getSolarRevolutionUT(natalTheme, fromDateTime);
		return new DateTimeJson(solarRevolutionUT);
	}

	@GetMapping(value = "/lunar/{fromDate}/{anniversaryLatitude:.+}/{anniversaryLongitude:.+}")
	public SkyPosition getLunarRevolution(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable double natalLatitude,
			@PathVariable double natalLongitude,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate,
			@PathVariable double anniversaryLatitude,
			@PathVariable double anniversaryLongitude) {
		SkyPosition natalTheme = calculateSkyPosition(natalDate, natalLatitude, natalLongitude);

		DateTime fromDateTime = new DateTime(fromDate);
		Degree latitudeDegree = new Degree(anniversaryLatitude);
		Degree longitudeDegree = new Degree(anniversaryLongitude);

		DateTime lunarRevolutionUT = RevolutionCalculator.INSTANCE.getLunarRevolutionUT(natalTheme, fromDateTime);
		DateTime zonedDateTime = controllerUtil.convertUTDateTime(lunarRevolutionUT, anniversaryLatitude, anniversaryLongitude);
		return ThemeCalculator.INSTANCE.getSkyPosition(zonedDateTime, latitudeDegree, longitudeDegree);
	}

	@GetMapping(value = "/lunar/{fromDate}/date")
	public DateTimeJson getLunarRevolutionDate(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable double natalLatitude,
			@PathVariable double natalLongitude,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate) {
		SkyPosition natalTheme = calculateSkyPosition(natalDate, natalLatitude, natalLongitude);

		DateTime fromDateTime = new DateTime(fromDate);
		DateTime lunarRevolutionUT = RevolutionCalculator.INSTANCE.getLunarRevolutionUT(natalTheme, fromDateTime);
		return new DateTimeJson(lunarRevolutionUT);
	}
}
