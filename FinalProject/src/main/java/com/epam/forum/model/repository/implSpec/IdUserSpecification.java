package com.epam.forum.model.repository.implSpec;

import java.util.ArrayList;
import java.util.List;

import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.SearchCriterion;
import com.epam.forum.model.repository.Specification;

public class IdUserSpecification implements Specification<User> {

	private static final String SQL_SELECT_USERS_BY_ID = "SELECT user_id, username, password, email, register_date, last_login_date, "
			+ "is_email_verifed, is_active, role FROM users WHERE ";

	private List<SearchCriterion> criterions;

	public IdUserSpecification(SearchCriterion searchCriteria) {
		criterions = new ArrayList<>();
		criterions.add(searchCriteria);
	}

	@Override
	public String toSqlQuery() {
		StringBuilder newQuery = new StringBuilder();
		newQuery.append(SQL_SELECT_USERS_BY_ID);
		for (SearchCriterion criteria : criterions) {
			String key = criteria.getKey();
			String operation = criteria.getOperation();
			newQuery.append(key);
			newQuery.append(operation);
			newQuery.append("?");
		}
		return newQuery.toString();
	}

	@Override
	public List<SearchCriterion> getSearchCriterions() {
		return criterions;
	}
}
