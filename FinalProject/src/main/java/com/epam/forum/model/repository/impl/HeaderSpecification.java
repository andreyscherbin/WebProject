package com.epam.forum.model.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.Specification;

public class HeaderSpecification implements Specification<Topic> {
	private static final String SQL_SELECT_TOPICS_BY_HEADER = "SELECT topic_id, header, content, is_pinned, is_closed, creation_date, section_id, user_id "
			+ "FROM topics WHERE ";

	private List<SearchCriterion> criterions;

	public HeaderSpecification(SearchCriterion searchCriteria) {
		criterions = new ArrayList<>();
		criterions.add(searchCriteria);
	}

	@Override
	public String toSqlQuery() {
		SearchCriterion criterion = criterions.get(0);
		String key = criterion.getKey();
		String operation = criterion.getOperation();
		return String.format(SQL_SELECT_TOPICS_BY_HEADER + "%s%s?", key, operation);
	}

	@Override
	public List<SearchCriterion> getSearchCriterions() {
		return criterions;
	}
}
