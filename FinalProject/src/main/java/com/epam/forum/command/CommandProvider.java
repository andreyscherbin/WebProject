package com.epam.forum.command;

import java.util.EnumMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.forum.command.impl.EmptyCommand;
import com.epam.forum.command.impl.LogInCommand;
import com.epam.forum.command.impl.LogOutCommand;
import com.epam.forum.command.impl.SortUserByIdCommand;
import com.epam.forum.command.impl.ViewUserByIdCommand;
import com.epam.forum.command.impl.ViewUserByUserNameCommand;
import com.epam.forum.command.impl.ViewUserCommand;
import com.epam.forum.model.service.UserService;
import com.epam.forum.model.service.impl.UserServiceImpl;

public class CommandProvider {
	private static Logger logger = LogManager.getLogger();
	private static CommandProvider instance = null;
	EnumMap<CommandName, Command> commands = new EnumMap<>(CommandName.class);

	private CommandProvider() {
		UserService userService = UserServiceImpl.getInstance();
		commands.put(CommandName.LOGIN, new LogInCommand(userService));
		commands.put(CommandName.VIEW_USER, new ViewUserCommand(userService));
		commands.put(CommandName.VIEW_USER_BY_ID, new ViewUserByIdCommand(userService));
		commands.put(CommandName.SORT_USER_BY_ID, new SortUserByIdCommand(userService));
		commands.put(CommandName.VIEW_USER_BY_USERNAME, new ViewUserByUserNameCommand(userService));
		commands.put(CommandName.LOGOUT, new LogOutCommand());
	}

	public Command getCommand(String commandName) {
		Command command;
		CommandName enumCommandName;
		if (commandName == null) {
			command = new EmptyCommand();
			return command;
		}
		try {
			enumCommandName = CommandName.valueOf(commandName.toUpperCase());
		} catch (IllegalArgumentException e) {
			logger.error("no such command {}", commandName);
			throw new EnumConstantNotPresentException(CommandName.class, commandName);
		}
		command = commands.get(enumCommandName);
		return command;
	}

	public static CommandProvider getInstance() {
		if (instance == null) {
			instance = new CommandProvider();
		}
		return instance;
	}
}