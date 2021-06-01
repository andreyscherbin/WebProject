package com.epam.forum.model.repository.spec.impl;

import java.util.ArrayList;
import java.util.List;
import com.epam.forum.model.entity.Section;
import com.epam.forum.model.repository.spec.SearchCriterion;
import com.epam.forum.model.repository.spec.Specification;

public class IdSectionSpecification implements Specification<Section> {
	private static final String SQL_SELECT_SECTIONS_BY_ID = "SELECT section_id, header, description FROM sections WHERE ";

	private List<SearchCriterion> criterions;

	public IdSectionSpecification(SearchCriterion searchCriteria) {
		criterions = new ArrayList<>();
		criterions.add(searchCriteria);
	}

	@Override
	public String toSqlQuery() {
		StringBuilder query = new StringBuilder();
		query.append(SQL_SELECT_SECTIONS_BY_ID);
		for (SearchCriterion criteria : criterions) {
			query.append(criteria);
		}
		return query.toString();
	}

	@Override
	public List<SearchCriterion> getSearchCriterions() {
		return criterions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((criterions == null) ? 0 : criterions.hashCode());
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
		IdSectionSpecification other = (IdSectionSpecification) obj;
		if (criterions == null) {
			if (other.criterions != null)
				return false;
		} else if (!criterions.equals(other.criterions))
			return false;
		return true;
	}	
}
