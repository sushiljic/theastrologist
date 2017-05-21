package com.theastrologist.controller.exception;

/**
 * Created by Samy on 21/05/2017.
 */
public class ErrorResponse {
	private int errorCode;
	private String message;

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
