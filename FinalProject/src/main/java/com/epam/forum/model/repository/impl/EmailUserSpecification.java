package com.epam.forum.model.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.Specification;

public class EmailUserSpecification implements Specification<User> {
	private static final String SQL_SELECT_USERS_BY_EMAIL = "SELECT user_id, username, password, email, register_date, last_login_date, "
			+ "is_email_verifed, is_active, role FROM users WHERE ";

	private List<SearchCriterion> criterions;

	public EmailUserSpecification(SearchCriterion searchCriteria) {
		criterions = new ArrayList<>();
		criterions.add(searchCriteria);
	}

	@Override
	public String toSqlQuery() {
		StringBuilder query = new StringBuilder();
		query.append(SQL_SELECT_USERS_BY_EMAIL);
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
