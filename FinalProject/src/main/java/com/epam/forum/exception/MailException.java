package com.epam.forum.exception;

public class MailException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MailException() {
	}

	public MailException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailException(String message) {
		super(message);
	}

	public MailException(Throwable cause) {
		super(cause);
	}
}
