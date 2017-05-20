package com.theastrologist.controller;

import com.theastrologist.core.ThemeCalculator;
import com.theastrologist.domain.Degree;
import com.theastrologist.domain.SkyPosition;
import com.theastrologist.util.ControllerUtil;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.*;

/**
 * Created by SAM on 16/11/2014.
 */
@RestController
public class ThemeController extends AbstractController {

	@GetMapping(value = "/{datetime}/{latitude:.+}/{longitude:.+}/theme")
	public SkyPosition getTheme(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String datetime,
								@PathVariable double latitude,
								@PathVariable double longitude) {
		DateTime parse = controllerUtil.parseDateTime(datetime, latitude, longitude);
		Degree latitudeDegree = new Degree(latitude);
		Degree longitudeDegree = new Degree(longitude);
		SkyPosition position = ThemeCalculator.INSTANCE.getSkyPosition(parse, latitudeDegree, longitudeDegree);
		return position;
	}
}
