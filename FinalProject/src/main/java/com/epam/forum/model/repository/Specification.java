package com.epam.forum.model.repository;

import java.util.List;

import com.epam.forum.model.entity.Entity;

public interface Specification<T extends Entity> {

	String toSqlQuery();

	List<SearchCriteria> getSearchCriterias();

	default Specification<T> and(Specification<T> other) {
		List<SearchCriteria> criterias = other.getSearchCriterias();
		this.getSearchCriterias().addAll(criterias);
		return this;
	}
}
