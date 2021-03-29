package com.epam.forum.model.repository.impl;

import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.Specification;

public class UserNameSpecification implements Specification<User> {
	private String userName;

	public UserNameSpecification(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}	
}
