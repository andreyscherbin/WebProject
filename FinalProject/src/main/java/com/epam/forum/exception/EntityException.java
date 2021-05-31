package com.epam.forum.exception;

public class EntityException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public EntityException() {
	}

	public EntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityException(String message) {
		super(message);
	}

	public EntityException(Throwable cause) {
		super(cause);
	}
}
