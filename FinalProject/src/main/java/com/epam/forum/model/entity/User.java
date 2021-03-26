package com.epam.forum.model.entity;

import java.time.LocalDateTime;

public class User extends Entity {
	
	private static final long serialVersionUID = 2L;	
	
	private Long userId;	
	private String userName;
	private String password;
	private String email;
	private LocalDateTime registerDate;
    private LocalDateTime lastLoginDate;
    private boolean isEmailVerifed;
    private boolean isActive;
    private Role role;   
    
	public User(Long userId, String userName, String password, String email, LocalDateTime registerDate,
			LocalDateTime lastLoginDate, boolean isEmailVerifed, boolean isActive, Role role) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.registerDate = registerDate;
		this.lastLoginDate = lastLoginDate;
		this.isEmailVerifed = isEmailVerifed;
		this.isActive = isActive;
		this.role = role;
	}
	
	public User() {		
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
		return isEmailVerifed;
	}
	public void setEmailVerifed(boolean isEmailVerifed) {
		this.isEmailVerifed = isEmailVerifed;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + (isEmailVerifed ? 1231 : 1237);
		result = prime * result + ((lastLoginDate == null) ? 0 : lastLoginDate.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((registerDate == null) ? 0 : registerDate.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		if (isActive != other.isActive)
			return false;
		if (isEmailVerifed != other.isEmailVerifed)
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
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
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
		builder.append("User [userId=");
		builder.append(userId);
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
		builder.append(isEmailVerifed);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", role=");
		builder.append(role);
		builder.append("]");
		return builder.toString();
	}	
}
