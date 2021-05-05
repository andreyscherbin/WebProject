package com.epam.forum.function;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;

import com.epam.forum.model.entity.Role;

public class HasAnyRoleFunction {
	private static final String ATTRIBUTE_NAME_ROLE = "role";

	public static Boolean hasAnyRole(JspContext jspContext, String... roles) {
		PageContext pageContext = (PageContext) jspContext;
		HttpSession session = pageContext.getSession();
		String stringRole = (String) session.getAttribute(ATTRIBUTE_NAME_ROLE);
		Role role = Role.valueOf(stringRole);
		for (String str : roles) {
			if (role == Role.valueOf(str))
				return true;
		}
		return false;
	}
}
