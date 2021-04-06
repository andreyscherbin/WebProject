package com.epam.forum.model.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.SearchCriteria;
import com.epam.forum.model.repository.Specification;

public class IdSpecification implements Specification<User> {

	private String query = "SELECT user_id, username, password, email, register_date, last_login_date, "
			+ "is_email_verifed, is_active, role FROM users WHERE ";

	private List<SearchCriteria> criterias;

	public IdSpecification(SearchCriteria searchCriteria) {
		criterias = new ArrayList<>();
		criterias.add(searchCriteria);
		SearchCriteria criterion = criterias.get(0);
		String key = criterion.getKey();
		String operation = criterion.getOperation();
		this.query += key + operation + "?";
	}

	@Override
	public String getQuery() {
		return query;
	}

	@Override
	public void setQuery(String query) {
		this.query += query;
	}

	@Override
	public List<SearchCriteria> getSearchCriterias() {
		return criterias;
	}
}
