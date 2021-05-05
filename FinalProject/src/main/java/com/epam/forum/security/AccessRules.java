package com.epam.forum.security;

public final class AccessRules {
	
	 public static final String[] FOR_GUESTS = {
		        "go_to_login_page",
		        "go_to_registration_page",
		        "go_to_home_page",
		        "login",
		        "registration",
		        "view_topic",
		        "view_section",
		        "language",
		        "view_topic_by_header",
		        "view_topic_by_section",
		        "view_topic_by_id",
		        "activation"
		    };

	 public static final String[] FOR_USERS = {
		        "logout",
		        "create_post",
		        "create_topic",
		        "go_to_new_topic_page",
		        "delete_post_by_id",  
		        "edit_post_by_id",		        
		    };

	public static final String[] FOR_MODERS = {
			 "delete_topic",
			 "pin_topic",
			 "close_topic"
			};

	public static final String[] FOR_ADMINS = {
			    "view_user",
		        "view_user_by_id",
		        "sort_user_by_id",
		        "view_user_by_username"
			};
	public static final String[] ALL_COMMANDS = {
			    "go_to_login_page",
		        "go_to_registration_page",
		        "go_to_home_page",
		        "go_to_new_topic_page",
		        "create_topic",
		        "login",
		        "registration",
		        "view_topic",
		        "view_section",
		        "language",
		        "view_topic_by_header",
		        "view_topic_by_section",
		        "view_topic_by_id",
		        "logout",
		        "create_post",		        
		        "delete_post_by_id", 
		        "edit_post_by_id",
		        "activation",
		        "view_user",
		        "view_user_by_id",
		        "sort_user_by_id",
			    "view_user_by_username",
			    "delete_topic",
				"pin_topic",
				"close_topic"
		};
	
	private AccessRules() {}
}

