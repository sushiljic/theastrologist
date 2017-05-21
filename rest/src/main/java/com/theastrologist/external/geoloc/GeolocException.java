package com.theastrologist.external.geoloc;

import com.google.common.base.Strings;
import org.springframework.web.client.RestClientException;

import java.util.ResourceBundle;

/**
 * Created by Samy on 06/09/2015.
 */
public class GeolocException extends Exception {
    GeoStatus status;

    private ResourceBundle bundle = ResourceBundle.getBundle("errors");
    private static final String PREFIX = "com.theastrologist.external.geoloc";
    private String message;

    public GeolocException(String message, GeoStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public GeolocException(String message, Throwable throwable, GeoStatus status) {
        super(message, throwable);
        this.message = message;
        this.status = status;
    }

    public GeolocException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public GeolocException(Throwable throwable) {
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
