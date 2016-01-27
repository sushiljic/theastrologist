package com.theastrologist.controller;

import com.theastrologist.core.ThemeCalculator;
import com.theastrologist.core.TransitPeriodCalculator;
import com.theastrologist.domain.Degree;
import com.theastrologist.domain.SkyPosition;
import com.theastrologist.domain.transitperiod.TransitPeriods;
import com.theastrologist.util.ControllerUtil;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Samy on 16/09/2015.
 */
@RestController
@RequestMapping("/transitperiod")
public class TransitPeriodController {

	/*@Autowired
	ThemeController themeController;*/

	@RequestMapping(value = "/{natalDate}/{startDate}/{endDate}/{latitude:.+}/{longitude:.+}", method = RequestMethod.GET)
	public TransitPeriods getTransitPeriod(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String natalDate,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String startDate,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String endDate,
			@PathVariable double latitude,
			@PathVariable double longitude) {
		//SkyPosition natalPosition = themeController.getTheme(latitude, longitude, natalDate);
		DateTime parsedNatalDate = ControllerUtil.parseDateTime(natalDate, latitude, longitude);
		DateTime parsedStartDate = ControllerUtil.parseDateTime(startDate, latitude, longitude);
		DateTime parsedEndDate = ControllerUtil.parseDateTime(endDate, latitude, longitude);
		Degree parsedLatitude = new Degree(latitude);
		Degree parsedLongitude = new Degree(longitude);
		SkyPosition natalPosition = ThemeCalculator.INSTANCE
				.getSkyPosition(parsedNatalDate, parsedLatitude, parsedLongitude);
		TransitPeriods transitPeriods = TransitPeriodCalculator.INSTANCE
				.createTransitPeriod(natalPosition, parsedStartDate, parsedEndDate, parsedLatitude, parsedLongitude);
		return transitPeriods;
	}
}
