package com.epam.forum.model.repository.impl;

import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.Specification;

public class UserNameSpecification implements Specification {
	private String userName;

	public UserNameSpecification(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	@Override
	public boolean specify(User user) { //fix me, unused method
		boolean result = user.getUserName().equals(userName);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserNameSpecification [userName=");
		builder.append(userName);
		builder.append("]");
		return builder.toString();
	}
}
