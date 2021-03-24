package com.epam.forum.model.service;

public class LogInService {
	private final static String ADMIN_LOGIN = "admin";
	private final static String ADMIN_PASS = "Qwe123";

	public boolean checkLogin(String enterLogin, String enterPass) {
		return ADMIN_LOGIN.equals(enterLogin) && ADMIN_PASS.equals(enterPass);
	}
}