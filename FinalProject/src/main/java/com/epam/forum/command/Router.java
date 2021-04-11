package com.epam.forum.command;

public class Router {
	private String page;
	private Boolean isRedirect = Boolean.FALSE;	

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Boolean isRedirect() {
		return isRedirect;
	}
	
	public void setRedirect() {
		isRedirect = true;
	}	
}
