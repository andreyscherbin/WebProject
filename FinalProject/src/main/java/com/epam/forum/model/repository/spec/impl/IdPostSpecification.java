package com.epam.forum.model.repository.spec.impl;

import java.util.ArrayList;
import java.util.List;

import com.epam.forum.model.entity.Post;
import com.epam.forum.model.repository.spec.SearchCriterion;
import com.epam.forum.model.repository.spec.Specification;

public class IdPostSpecification implements Specification<Post> {
	private static final String SQL_SELECT_POSTS_BY_ID = "SELECT posts.post_id, posts.content, posts.creation_date, topics.topic_id, topics.header, topics.content, topics.is_pinned, topics.is_closed, topics.creation_date, users.user_id, users.username, users.password, users.email, users.register_date, users.last_login_date, "
			+ "users.is_email_verifed, users.is_active, users.role "
			+ "FROM posts JOIN topics ON posts.topic_id = topics.topic_id JOIN users ON posts.user_id = users.user_id WHERE ";

	private List<SearchCriterion> criterions;

	public IdPostSpecification(SearchCriterion searchCriteria) {
		criterions = new ArrayList<>();
		criterions.add(searchCriteria);
	}

	@Override
	public String toSqlQuery() {
		StringBuilder query = new StringBuilder();
		query.append(SQL_SELECT_POSTS_BY_ID);
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
		IdPostSpecification other = (IdPostSpecification) obj;
		if (criterions == null) {
			if (other.criterions != null)
				return false;
		} else if (!criterions.equals(other.criterions))
			return false;
		return true;
	}	
}
