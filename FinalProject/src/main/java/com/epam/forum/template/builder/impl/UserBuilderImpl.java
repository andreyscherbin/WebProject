package com.epam.forum.template.builder.impl;

import java.time.LocalDateTime;

import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.template.builder.UserBuilder;

public class UserBuilderImpl implements UserBuilder {
	private User user = new User();

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public void buildUsername(String userName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildPassword(String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildEmail(String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildRegisterDate(LocalDateTime registerDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildLastLoginDate(LocalDateTime lastLoginDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildIsEmailVerifed(boolean isEmailVerifed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildIsActive(boolean isActive) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildRole(Role role) {
		// TODO Auto-generated method stub

	}
}
