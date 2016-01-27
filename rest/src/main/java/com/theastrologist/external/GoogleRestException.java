package com.theastrologist.external;

import com.google.common.base.Strings;
import org.springframework.web.client.RestClientException;

import java.util.ResourceBundle;

/**
 * Created by Samy on 06/09/2015.
 */
public class GoogleRestException extends Exception {

	private ResourceBundle bundle = ResourceBundle.getBundle("errors");
	private static final String PREFIX = "com.theastrologist.external";
	private String message;

	public GoogleRestException(String message) {
		super(message);
		this.message = message;
	}

	public GoogleRestException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public GoogleRestException(Throwable throwable) {
		super(throwable);
		try {
			throw throwable;
		} catch (RestClientException e) {
			this.message = getResourceString("rest.client");
		} catch (Throwable th) {
			// Do nothing
		}
	}

	// Overrides Exception'message getMessage()
	@Override
	public String getMessage(){
		return message;
	}

	private String getResourceString(String key) {
		String returnString;
		String bundleString = bundle.getString(PREFIX + "." + key);
		if(!Strings.isNullOrEmpty(bundleString)) {
			returnString = bundleString;
		} else {
			returnString = "Unknown Error";
		}
		return returnString;
	}
}
