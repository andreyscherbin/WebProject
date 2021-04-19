package com.epam.forum.model.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.epam.forum.model.entity.Topic;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.Specification;

public class HeaderTopicSpecification implements Specification<Topic> {
	private static final String SQL_SELECT_TOPICS_BY_HEADER = "SELECT topics.topic_id, topics.header, topics.content, topics.is_pinned, topics.is_closed, topics.creation_date, sections.section_id, sections.header, sections.description, users.user_id, users.username, users.password, users.email, users.register_date, users.last_login_date, "
			+ "users.is_email_verifed, users.is_active, users.role "
			+ "FROM topics JOIN sections ON topics.section_id = sections.section_id JOIN users ON topics.user_id = users.user_id  WHERE ";

	private List<SearchCriterion> criterions;

	public HeaderTopicSpecification(SearchCriterion searchCriteria) {
		criterions = new ArrayList<>();
		criterions.add(searchCriteria);
	}

	@Override
	public String toSqlQuery() {
		StringBuilder query = new StringBuilder();
		query.append(SQL_SELECT_TOPICS_BY_HEADER);
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
