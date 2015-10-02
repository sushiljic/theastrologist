package com.theastrologist.util;

import com.theastrologist.controller.exception.WrongDateRestException;
import org.joda.time.DateTime;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Samy on 16/09/2015.
 */
public class ControllerUtil {
	public static DateTime parseDateTime(String datetime) throws WrongDateRestException {
		DateTime parsedDate = null;
		try {
			String decodedDateTime = UriUtils.decode(datetime, "UTF-8");
			parsedDate = DateTime.parse(decodedDateTime);
		} catch (IllegalArgumentException e) {
			throw new WrongDateRestException(datetime, e);
		} catch (UnsupportedEncodingException e) {
			throw new WrongDateRestException(datetime, e);
		}
		return parsedDate;
	}
}
