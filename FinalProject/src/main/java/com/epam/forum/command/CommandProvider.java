package com.epam.forum.command;

import java.util.EnumMap;
import com.epam.forum.command.impl.EmptyCommand;
import com.epam.forum.command.impl.LogInCommand;
import com.epam.forum.command.impl.LogOutCommand;
import com.epam.forum.command.impl.SortByIdCommand;
import com.epam.forum.command.impl.ViewByIdCommand;
import com.epam.forum.command.impl.ViewCommand;
import com.epam.forum.model.service.impl.UserServiceImpl;

public class CommandProvider {
	private static CommandProvider instance = null;
	EnumMap<CommandName, Command> commands = new EnumMap<>(CommandName.class);

	private CommandProvider() {
		commands.put(CommandName.LOGIN, new LogInCommand(UserServiceImpl.getInstance()));
		commands.put(CommandName.VIEW, new ViewCommand(UserServiceImpl.getInstance()));
		commands.put(CommandName.VIEW_BY_ID, new ViewByIdCommand(UserServiceImpl.getInstance()));
		commands.put(CommandName.SORT_BY_ID, new SortByIdCommand(UserServiceImpl.getInstance())); // fix me
		commands.put(CommandName.LOGOUT, new LogOutCommand());
	}

	public Command getCommand(String commandName) {
		CommandName enumCommandName = CommandName.valueOf(commandName.toUpperCase());
		Command command = commands.get(enumCommandName);
		if (command == null) {
			command = new EmptyCommand();
		}
		return command;
	}

	public static CommandProvider getInstance() {
		if (instance == null) {
			instance = new CommandProvider();
		}
		return instance;
	}
}