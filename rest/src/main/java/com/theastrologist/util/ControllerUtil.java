package com.theastrologist.util;

import com.theastrologist.controller.exception.WrongDateRestException;
import com.theastrologist.external.GoogleRestException;
import com.theastrologist.external.timezone.TimezoneResponse;
import com.theastrologist.external.timezone.TimezoneRestClient;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Samy on 16/09/2015.
 */
public class ControllerUtil {
	public static DateTime parseDateTime(String datetime, double latitude, double longitude)
			throws WrongDateRestException {
		LocalDateTime parsedDate = null;
		try {
			String decodedDateTime = UriUtils.decode(datetime, "UTF-8");

			parsedDate = LocalDateTime.parse(decodedDateTime);
		} catch (IllegalArgumentException e) {
			throw new WrongDateRestException(datetime, e);
		} catch (UnsupportedEncodingException e) {
			throw new WrongDateRestException(datetime, e);
		}

		DateTime firstUtcDateTime = parsedDate.toDateTime(DateTimeZone.UTC);
		long millis = firstUtcDateTime.getMillis();

		TimezoneRestClient client = new TimezoneRestClient(latitude, longitude, millis);
		TimezoneResponse response = null;
		try {
			response = client.getTimezone();
		} catch (GoogleRestException e) {
			throw new WrongDateRestException(datetime, e);
		}

		DateTimeZone dateTimeZone = DateTimeZone.forID(response.timeZoneId);
		DateTime dateTime = parsedDate.toDateTime(dateTimeZone);
		return dateTime;
	}
}
