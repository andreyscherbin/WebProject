package com.epam.forum.exception;

/**
 * The {@code ErrorAttribute} class contains attributes names for request attributes when exception occure
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public final class ErrorAttribute {
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String ERROR_CAUSE = "errorCause";
	public static final String ERROR_LOCATION = "errorLocation";
	public static final String ERROR_CODE = "errorCode";
	
	private ErrorAttribute() {}
}
