package com.epam.forum.model.repository.impl;

import java.util.ArrayList;
import java.util.List;
import com.epam.forum.model.entity.Section;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.Specification;

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
}
