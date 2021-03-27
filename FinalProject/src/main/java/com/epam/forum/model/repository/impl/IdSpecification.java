package com.epam.forum.model.repository.impl;

import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.Specification;

public class IdSpecification implements Specification {

	private long id;

	public IdSpecification(long id) {
		this.id = id;
	}

	@Override
	public boolean specify(User user) {
		boolean result = user.getUserId() == id;
		return result;
	}
}
