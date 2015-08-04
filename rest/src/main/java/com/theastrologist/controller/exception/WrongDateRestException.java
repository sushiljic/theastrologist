package com.theastrologist.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by SAM on 04/08/2015.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Wrong date format")
public class WrongDateRestException extends RuntimeException {
    private final String datetime;

    public WrongDateRestException(String datetime) {
        this.datetime = datetime;
    }

    public String getDatetime() {
        return datetime;
    }

    // TODO ajouter la gestion des erreurs
}
