package com.epam.forum.model.entity;

import java.time.LocalDateTime;

public class User extends Entity {

	private static final long serialVersionUID = 2L;

	private Long id;
	private String userName;
	private String password;
	private String email;
	private LocalDateTime registerDate;
	private LocalDateTime lastLoginDate;
	private boolean emailVerifed;
	private boolean active;
	private Role role;

	public User() {
	}

	public User(String userName, String email, String password) {
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}

	public LocalDateTime getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(LocalDateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public boolean isEmailVerifed() {
		return emailVerifed;
	}

	public void setEmailVerifed(boolean isEmailVerifed) {
		this.emailVerifed = isEmailVerifed;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean isActive) {
		this.active = isActive;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + (emailVerifed ? 1231 : 1237);
		result = prime * result + ((lastLoginDate == null) ? 0 : lastLoginDate.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((registerDate == null) ? 0 : registerDate.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (active != other.active)
			return false;
		if (emailVerifed != other.emailVerifed)
			return false;
		if (lastLoginDate == null) {
			if (other.lastLoginDate != null)
				return false;
		} else if (!lastLoginDate.equals(other.lastLoginDate))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (registerDate == null) {
			if (other.registerDate != null)
				return false;
		} else if (!registerDate.equals(other.registerDate))
			return false;
		if (role != other.role)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", email=");
		builder.append(email);
		builder.append(", registerDate=");
		builder.append(registerDate);
		builder.append(", lastLoginDate=");
		builder.append(lastLoginDate);
		builder.append(", isEmailVerifed=");
		builder.append(emailVerifed);
		builder.append(", isActive=");
		builder.append(active);
		builder.append(", role=");
		builder.append(role);
		builder.append("]");
		return builder.toString();
	}
}
