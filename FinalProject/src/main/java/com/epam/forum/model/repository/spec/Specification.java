package com.epam.forum.model.repository.spec;

import java.util.List;
import com.epam.forum.model.entity.Entity;

/**
 * Specification
 * 
 * @author Andrey Shcherbin
 * @version 1.0
 * @since 2021-05-30
 *
 * @param <T> the domain type
 */
public interface Specification<T extends Entity> {

	/**
	 * Returns a string represents query
	 * 
	 * @return string represents query
	 */
	String toSqlQuery();

	/**
	 * Returns a list of {@link SearchCriterion}
	 * 
	 * @return list of {@link SearchCriterion}
	 */
	List<SearchCriterion> getSearchCriterions();

	/**
	 * ANDs the given {@link SearchCriterion} to the current one.
	 * @param other
	 * @return The conjunction of the specifications
	 */
	default Specification<T> and(Specification<T> other) {
		List<SearchCriterion> criterias = other.getSearchCriterions();
		this.getSearchCriterions().get(0).setAnd();
		this.getSearchCriterions().addAll(criterias);
		return this;
	}
	
	/**
	 * ORs the given {@link SearchCriterion} to the current one.
	 * 
	 * @param other
	 * @return The disjunction of the specifications
	 */
	default Specification<T> or(Specification<T> other) {
		List<SearchCriterion> criterias = other.getSearchCriterions();
		this.getSearchCriterions().get(0).setOr();
		this.getSearchCriterions().addAll(criterias);
		return this;
	}
}
