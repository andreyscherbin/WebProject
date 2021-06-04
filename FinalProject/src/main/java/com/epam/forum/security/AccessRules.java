package com.epam.forum.security;

import java.util.EnumSet;
import com.epam.forum.command.CommandName;
import com.epam.forum.model.entity.Role;
import static com.epam.forum.command.CommandName.*;

public class AccessRules {

	private EnumSet<CommandName> guests;
	private EnumSet<CommandName> users;
	private EnumSet<CommandName> moders;
	@SuppressWarnings("unused")
	private EnumSet<CommandName> admins;
	private EnumSet<CommandName> allCommands;

	private AccessRules() {
		guests = EnumSet.range(EMPTY_COMMAND, ACTIVATION);
		users = EnumSet.range(LOGOUT, GO_TO_NEW_TOPIC_PAGE);
		moders = EnumSet.range(DELETE_TOPIC, CLOSE_TOPIC);
		admins = EnumSet.range(CREATE_SECTION, BAN_USER);
		allCommands = EnumSet.allOf(CommandName.class);
	}

	public boolean hasAuthority(Role role, CommandName command) {
		boolean result = false;
		switch (role) {
		case GUEST:
			result = guests.contains(command);
			break;
		case USER:
			result = users.contains(command);
			if (result) {
				break;
			}
			result = guests.contains(command);
			break;
		case MODER:
			result = moders.contains(command);
			if (result) {
				break;
			}
			result = users.contains(command);
			if (result) {
				break;
			}
			result = guests.contains(command);
			break;
		case ADMIN:
			result = true;
			break;
		}
		return result;
	}

	public boolean isValidCommand(CommandName command) {
		return allCommands.contains(command);
	}

	private static class LazyHolder {
		private static final AccessRules instance = new AccessRules();
	}

	public static AccessRules getInstance() {
		return LazyHolder.instance;
	}
}
