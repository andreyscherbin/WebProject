package com.epam.forum.tag.function;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.CommandName;
import com.epam.forum.model.entity.Role;

public class HasAnyRoleFunction {
	private static Logger logger = LogManager.getLogger();
	private static final String DEFAULT_ROLE = "GUEST";
	private static final String ATTRIBUTE_NAME_ROLE = "role";

	public static Boolean hasAnyRole(JspContext jspContext, String... roles) {
		PageContext pageContext = (PageContext) jspContext;
		HttpSession session = pageContext.getSession();
		String stringRole = (String) session.getAttribute(ATTRIBUTE_NAME_ROLE);
		Role role;
		if (stringRole == null) {
			stringRole = DEFAULT_ROLE;
		}
		try {
			role = Role.valueOf(stringRole);
		} catch (IllegalArgumentException e) {
			logger.error("no such role {}", stringRole);
			throw new EnumConstantNotPresentException(CommandName.class, stringRole);
		}
		for (String str : roles) {
			if (role == Role.valueOf(str))
				return true;
		}
		return false;
	}
}
