package com.epam.forum.model.repository;

/**
 * The {@code Operation} class contains operations which are used to build query 
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public final class Operation {
	public static final String EQUAL = "=";
	public static final String GREATER_THAN = ">";
	public static final String LESS_THAN = "<";
	public static final String GREATER_THAN_OR_EQUAL = ">=";
	public static final String LESS_THAN_OR_EQUAL = "<=";
	public static final String BEETWEN = " BETWEEN ";
	public static final String LIKE = " LIKE ";
	public static final String IN = " IN ";
	public static final String ANY_SEQUENCE = "%";

	private Operation() {
	}
}
