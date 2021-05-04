package com.epam.forum.function;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;

import com.epam.forum.model.entity.Role;

public class HasRoleFunction {
	private static final String ATTRIBUTE_NAME_ROLE = "role";

	public static Boolean hasRole(String parameterRole, JspContext jspContext) {
		PageContext pageContext = (PageContext) jspContext; // need to think why
		HttpSession session = pageContext.getSession();
		String stringRole = (String) session.getAttribute(ATTRIBUTE_NAME_ROLE);
		Role role = Role.valueOf(stringRole);
		return role == Role.valueOf(parameterRole);
	}
}
