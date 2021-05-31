package com.epam.forum.model.repository;

/**
 * The {@code SearchCriterion} class represents search criterion on which query will be builded. For example:
 * <blockquote><pre>
 * StringBuilder query = new StringBuilder("SELECT * FROM users WHERE ");
 * SearchCriterion usernameEqualCriterion = new SearchCriterion(UserTable.USERNAME, Operation.EQUAL, userName);
 * query.append(usernameEqualCriterion);
 * </pre></blockquote><p>
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 */
public class SearchCriterion {
		
	private static final String AND = " AND ";
	private static final String OR = " OR ";
	
	/**
	 * used to hold column name - for example: firstName, age, ... etc.
	 */
	private String key;
	
	/**
	 * used to hold the operation - for example: Equality, less than, ... etc.
	 */
	private String operation;
	
	/**
	 * used to hold the column value - for example: john, 25, ... etc.
	 */
	private Object value;
	
	/**
	 * if need add another {@link SearchCriterion} via AND
	 * For example:
     * <blockquote><pre>
     * StringBuilder query = new StringBuilder("SELECT * FROM users WHERE ");
     * SearchCriterion usernameEqualCriterion = new SearchCriterion(UserTable.USERNAME, Operation.EQUAL, userName);
     * usernameEqualCriterion.setAnd();
     * SearchCriterion emailEqualCriterion    = new SearchCriterion(UserTable.EMAIL, Operation.EQUAL, email));
     * query.append(usernameEqualCriterion);
     * query.append(emailEqualCriterion);
     * </pre></blockquote><p>
     * query will be like this:  SELECT * FROM users WHERE username=andrey AND email=andrey123scherbin@gmail.com
	 */
	private boolean isAnd;
	
	/**
	 * if need add another {@link SearchCriterion} via OR
	 * For example:
     * <blockquote><pre>
     * StringBuilder query = new StringBuilder("SELECT * FROM users WHERE ");
     * SearchCriterion usernameEqualCriterion = new SearchCriterion(UserTable.USERNAME, Operation.EQUAL, userName);
     * usernameEqualCriterion.setOr();
     * SearchCriterion emailEqualCriterion    = new SearchCriterion(UserTable.EMAIL, Operation.EQUAL, email));
     * query.append(usernameEqualCriterion);
     * query.append(emailEqualCriterion);
     * </pre></blockquote><p>
     * query will be like this:  SELECT * FROM users WHERE username=andrey OR email=andrey123scherbin@gmail.com
	 */
	private boolean isOr;

	/**
	 * Initializes a newly created {@code SearchCriterion} object
	 * 
	 * @param key column name
	 * @param operation operation name
	 * @param value column value
	 */
	public SearchCriterion(String key, String operation, Object value) {		
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

	/**
	 * Returns key
	 * 
	 * @return key 
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Set key
	 * 
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Returns operation
	 * 
	 * @return operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * Set operation
	 * 
	 * @param operation
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * Returns value
	 * 
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Set value
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Set AND 
	 */
	public void setAnd() {
		this.isAnd = true;
	}

	/**
	 * Set OR 
	 */
	public void setOr() {
		this.isOr = true;
	}

	/**
	 * Returns a string wich represents {@link SearchCriterion} object
	 * 
	 * @return string wich represents {@link SearchCriterion} object
	 */
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
