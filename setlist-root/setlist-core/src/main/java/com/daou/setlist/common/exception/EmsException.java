package com.daou.setlist.common.exception;

public class EmsException extends RuntimeException {

	private static final long serialVersionUID = 5947253546989409259L;

	public EmsException() {
		super();
	}

	public EmsException(String message) {
		super(message);
	}

	public EmsException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmsException(Throwable cause) {
		super(cause);
	}

	protected EmsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
