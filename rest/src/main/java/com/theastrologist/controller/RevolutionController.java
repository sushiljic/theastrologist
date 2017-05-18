package com.theastrologist.controller;

import com.theastrologist.core.RevolutionCalculator;
import com.theastrologist.core.ThemeCalculator;
import com.theastrologist.domain.Degree;
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
	public SkyPosition getSolarRevolution(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
								@PathVariable double natalLatitude,
								@PathVariable double natalLongitude,
								@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate,
								@PathVariable double anniversaryLatitude,
								@PathVariable double anniversaryLongitude) {
		SkyPosition natalTheme = calculateSkyPosition(natalDate, natalLatitude, natalLongitude);
		DateTime fromDateTime = new DateTime(fromDate);
		Degree latitudeDegree = new Degree(anniversaryLatitude);
		Degree longitudeDegree = new Degree(anniversaryLongitude);
		SkyPosition solarRevolution = RevolutionCalculator.INSTANCE
				.getSolarRevolution(natalTheme, fromDateTime, latitudeDegree, longitudeDegree);
		return solarRevolution;
	}

	@GetMapping(value = "/lunar/{fromDate}/{anniversaryLatitude:.+}/{anniversaryLongitude:.+}")
	public SkyPosition getLunarRevolution(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
								@PathVariable double natalLatitude,
								@PathVariable double natalLongitude,
								@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromDate,
								@PathVariable double anniversaryLatitude,
								@PathVariable double anniversaryLongitude) {
		SkyPosition natalTheme = calculateSkyPosition(natalDate, natalLatitude, natalLongitude);
		// On ne fait pas d'ajustement de la date parce qu'on s'en fout de l'horaire
		DateTime fromDateTime = new DateTime(fromDate);
		Degree latitudeDegree = new Degree(anniversaryLatitude);
		Degree longitudeDegree = new Degree(anniversaryLongitude);
		SkyPosition lunarRevolution = RevolutionCalculator.INSTANCE
				.getLunarRevolution(natalTheme, fromDateTime, latitudeDegree, longitudeDegree);
		return lunarRevolution;
	}
}
