package com.epam.forum.model.repository.impl;

import com.epam.forum.model.entity.Message;
import com.epam.forum.model.repository.Specification;

public class IdSpecification implements Specification {

	private long id;

	public IdSpecification(long id) {
		this.id = id;
	}

	@Override
	public boolean specify(Message message) {
		boolean result = message.getId() == id;
		return result;
	}
}
