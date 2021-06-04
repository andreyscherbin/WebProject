package com.epam.forum.command.builder;

import java.time.LocalDateTime;

import com.epam.forum.exception.ServiceException;
import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;

public interface UserBuilder {
	User getUser();

	UserBuilder buildUsername(String userName);

	UserBuilder buildPassword(String password) throws ServiceException;

	UserBuilder buildEmail(String email);

	UserBuilder buildRegisterDate(LocalDateTime registerDate);

	UserBuilder buildLastLoginDate(LocalDateTime lastLoginDate);

	UserBuilder buildIsEmailVerifed(boolean isEmailVerifed);

	UserBuilder buildIsActive(boolean isActive);

	UserBuilder buildRole(Role role);
}
