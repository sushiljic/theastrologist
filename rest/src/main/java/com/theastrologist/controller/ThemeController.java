package com.theastrologist.controller;

import com.theastrologist.core.ThemeCalculator;
import com.theastrologist.domain.Degree;
import com.theastrologist.domain.SkyPosition;
import com.theastrologist.util.ControllerUtil;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.*;

/**
 * Created by SAM on 16/11/2014.
 */
@RestController
@RequestMapping("/theme")
public class ThemeController extends AbstractController {

	@RequestMapping(value = "/{datetime}/{latitude:.+}/{longitude:.+}", method = RequestMethod.GET)
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
