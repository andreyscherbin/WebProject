package com.epam.forum.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class AuthorizeTag extends SimpleTagSupport {
	private boolean access;

	public void setAccess(boolean access) {
		this.access = access;
	}

	@Override
	public void doTag() throws IOException, JspException {
		if (access) {
			getJspBody().invoke(null);
		}
	}
}
