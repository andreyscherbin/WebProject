package com.epam.forum.model.repository.spec;

/**
 * The {@code SearchCriterion} class represents search criterion on which WHERE clause will be builded. For example:
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAnd ? 1231 : 1237);
		result = prime * result + (isOr ? 1231 : 1237);
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchCriterion other = (SearchCriterion) obj;
		if (isAnd != other.isAnd)
			return false;
		if (isOr != other.isOr)
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}	
}
