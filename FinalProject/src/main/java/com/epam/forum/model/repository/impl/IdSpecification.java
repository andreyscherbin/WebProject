package com.epam.forum.model.repository.impl;

import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.Specification;

public class IdSpecification implements Specification<User> {

	private Long id;

	public IdSpecification(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
