package com.epam.forum.command.builder.impl;

import java.time.LocalDateTime;
import com.epam.forum.command.builder.UserBuilder;
import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;
import com.epam.forum.util.encryptor.DataEncryptor;

public class UserBuilderImpl implements UserBuilder {
	private User user = new User();

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public UserBuilder buildUsername(String userName) {
		user.setUserName(userName);
		return this;
	}

	@Override
	public UserBuilder buildPassword(String password) throws ServiceException {
		String encodedPassword = DataEncryptor.encryptData(password);
		user.setPassword(encodedPassword);
		return this;
	}

	@Override
	public UserBuilder buildEmail(String email) {
		user.setEmail(email);
		return this;
	}

	@Override
	public UserBuilder buildRegisterDate(LocalDateTime registerDate) {
		user.setRegisterDate(registerDate);
		return this;
	}

	@Override
	public UserBuilder buildLastLoginDate(LocalDateTime lastLoginDate) {
		user.setLastLoginDate(lastLoginDate);
		return this;
	}

	@Override
	public UserBuilder buildIsEmailVerifed(boolean emailVerifed) {
		user.setEmailVerifed(emailVerifed);
		return this;
	}

	@Override
	public UserBuilder buildIsActive(boolean active) {
		user.setActive(active);
		return this;
	}

	@Override
	public UserBuilder buildRole(Role role) {
		user.setRole(role);
		return this;
	}
}
