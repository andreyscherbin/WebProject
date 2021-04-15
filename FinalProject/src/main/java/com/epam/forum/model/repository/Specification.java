package com.epam.forum.model.repository;

import java.util.List;
import com.epam.forum.model.entity.Entity;

public interface Specification<T extends Entity> {

	String toSqlQuery();

	List<SearchCriterion> getSearchCriterions();

	default Specification<T> and(Specification<T> other) {
		List<SearchCriterion> criterias = other.getSearchCriterions();
		this.getSearchCriterions().get(0).setAnd();
		this.getSearchCriterions().addAll(criterias);
		return this;
	}

	default Specification<T> or(Specification<T> other) {
		List<SearchCriterion> criterias = other.getSearchCriterions();
		this.getSearchCriterions().get(0).setOr();
		this.getSearchCriterions().addAll(criterias);
		return this;
	}
}
