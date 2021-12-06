package com.vsnm.framework.exception;

public class AppException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2450164930086158896L;

	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
