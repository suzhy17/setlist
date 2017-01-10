package com.daou.setlist.common.exception;

public class EmsJsonException extends RuntimeException {

	private static final long serialVersionUID = 7857899715442545893L;

	public EmsJsonException() {
		super();
	}

	public EmsJsonException(String message) {
		super(message);
	}

	public EmsJsonException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmsJsonException(Throwable cause) {
		super(cause);
	}

	protected EmsJsonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
