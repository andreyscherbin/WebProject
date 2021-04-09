package com.epam.forum.model.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.SearchCriteria;
import com.epam.forum.model.repository.Specification;

public class UserNameSpecification implements Specification<User> {

	private static final String SQL_SELECT_USERS_BY_USERNAME = "SELECT user_id, username, password, email, register_date, last_login_date, "
			+ "is_email_verifed, is_active, role FROM users WHERE ";

	private List<SearchCriteria> criterias;

	public UserNameSpecification(SearchCriteria searchCriteria) {
		criterias = new ArrayList<>();
		criterias.add(searchCriteria);
	}

	@Override
	public String toSqlQuery() {
		SearchCriteria criterion = criterias.get(0);
		String key = criterion.getKey();
		String operation = criterion.getOperation();
		return String.format(SQL_SELECT_USERS_BY_USERNAME + "%s%s?", key, operation);
	}

	@Override
	public List<SearchCriteria> getSearchCriterias() {
		return criterias;
	}
}
