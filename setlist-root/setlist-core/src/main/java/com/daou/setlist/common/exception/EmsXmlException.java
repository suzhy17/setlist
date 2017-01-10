package com.daou.setlist.common.exception;

public class EmsXmlException extends RuntimeException {

	private static final long serialVersionUID = 5545317856221651792L;

	private String code;
	
	public String getCode() {
		return code;
	}

	public EmsXmlException() {
		super();
	}

	public EmsXmlException(String message) {
		super(message);
	}

	public EmsXmlException(String code, String message) {
		super(message);
		this.code = code;
	}

	public EmsXmlException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmsXmlException(Throwable cause) {
		super(cause);
	}

	protected EmsXmlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
