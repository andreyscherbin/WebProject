package com.epam.forum.model.repository;

import java.util.List;

import com.epam.forum.model.entity.Entity;

public interface Specification<T extends Entity> { // need think and make not interface

	public String getQuery();

	public void setQuery(String query);

	public List<SearchCriteria> getSearchCriterias();

	default Specification<T> and(Specification<T> other) {

		List<SearchCriteria> criterias = other.getSearchCriterias();
		SearchCriteria criterion = criterias.get(0);
		String key = criterion.getKey();
		String operation = criterion.getOperation();
		this.getSearchCriterias().addAll(criterias);
		this.setQuery(" AND " + key + operation + "?");
		return this;
	}
}
