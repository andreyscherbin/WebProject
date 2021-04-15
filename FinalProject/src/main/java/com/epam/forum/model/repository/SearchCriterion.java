package com.epam.forum.model.repository;

public class SearchCriterion {

	private static final String AND = " AND ";
	private static final String OR = " OR ";
	private String key;
	private String operation;
	private Object value;
	private boolean isAnd;
	private boolean isOr;

	public SearchCriterion(String key, String operation, Object value) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setAnd() {
		this.isAnd = true;
	}

	public void setOr() {
		this.isOr = true;
	}

	@Override
	public String toString() {
		StringBuilder criterion = new StringBuilder();
		criterion.append(String.format("%s%s?", key, operation));
		if (isAnd) {
			criterion.append(AND);
		}
		if (isOr) {
			criterion.append(OR);
		}
		return criterion.toString();
	}
}
