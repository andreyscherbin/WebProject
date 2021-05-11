package com.epam.forum.template.builder;

import java.time.LocalDateTime;

import com.epam.forum.model.entity.Role;
import com.epam.forum.model.entity.User;

public interface UserBuilder {
	User getUser();

	void buildUsername(String userName);

	void buildPassword(String password);

	void buildEmail(String email);

	void buildRegisterDate(LocalDateTime registerDate);

	void buildLastLoginDate(LocalDateTime lastLoginDate);

	void buildIsEmailVerifed(boolean isEmailVerifed);

	void buildIsActive(boolean isActive);

	void buildRole(Role role);
}
