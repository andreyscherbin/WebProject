package com.epam.forum.command;

import java.util.EnumMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.forum.command.impl.ActivationCommand;
import com.epam.forum.command.impl.BanUserCommand;
import com.epam.forum.command.impl.ChangeRoleCommand;
import com.epam.forum.command.impl.CloseTopicCommand;
import com.epam.forum.command.impl.CreatePostCommand;
import com.epam.forum.command.impl.CreateSectionCommand;
import com.epam.forum.command.impl.CreateTopicCommand;
import com.epam.forum.command.impl.DeletePostByIdCommand;
import com.epam.forum.command.impl.DeleteSectionCommand;
import com.epam.forum.command.impl.DeleteTopicCommand;
import com.epam.forum.command.impl.EditPostCommand;
import com.epam.forum.command.impl.EmptyCommand;
import com.epam.forum.command.impl.GoToAdminHomePageCommand;
import com.epam.forum.command.impl.GoToHomePageCommand;
import com.epam.forum.command.impl.GoToLoginPageCommand;
import com.epam.forum.command.impl.GoToNewSectionPageCommand;
import com.epam.forum.command.impl.GoToNewTopicPageCommand;
import com.epam.forum.command.impl.GoToRegistrationPageCommand;
import com.epam.forum.command.impl.LanguageCommand;
import com.epam.forum.command.impl.LogInCommand;
import com.epam.forum.command.impl.LogOutCommand;
import com.epam.forum.command.impl.PinTopicCommand;
import com.epam.forum.command.impl.RegistrationCommand;
import com.epam.forum.command.impl.SearchAjaxCommand;
import com.epam.forum.command.impl.SortUserByIdCommand;
import com.epam.forum.command.impl.ViewSectionCommand;
import com.epam.forum.command.impl.ViewTopicByHeaderCommand;
import com.epam.forum.command.impl.ViewTopicByIdCommand;
import com.epam.forum.command.impl.ViewSectionByIdCommand;
import com.epam.forum.command.impl.ViewTopicCommand;
import com.epam.forum.command.impl.ViewUserByIdCommand;
import com.epam.forum.command.impl.ViewUserByUserNameCommand;
import com.epam.forum.command.impl.ViewUserCommand;
import com.epam.forum.model.service.ActivationSenderService;
import com.epam.forum.model.service.PostService;
import com.epam.forum.model.service.SectionService;
import com.epam.forum.model.service.TopicService;
import com.epam.forum.model.service.UserService;
import com.epam.forum.model.service.impl.ActivationSenderServiceImpl;
import com.epam.forum.model.service.impl.PostServiceImpl;
import com.epam.forum.model.service.impl.SectionServiceImpl;
import com.epam.forum.model.service.impl.TopicServiceImpl;
import com.epam.forum.model.service.impl.UserServiceImpl;

public class CommandProvider {
	private static Logger logger = LogManager.getLogger();
	private EnumMap<CommandName, Command> commands = new EnumMap<>(CommandName.class);

	private CommandProvider() {
		UserService userService = UserServiceImpl.getInstance();
		TopicService topicService = TopicServiceImpl.getInstance();
		SectionService sectionService = SectionServiceImpl.getInstance();
		PostService postService = PostServiceImpl.getInstance();
		ActivationSenderService activationSenderService = ActivationSenderServiceImpl.getInstance();

		commands.put(CommandName.LOGIN, new LogInCommand(userService));
		commands.put(CommandName.REGISTRATION, new RegistrationCommand(userService, activationSenderService));
		commands.put(CommandName.ACTIVATION, new ActivationCommand(userService, activationSenderService));
		commands.put(CommandName.VIEW_USER, new ViewUserCommand(userService));
		commands.put(CommandName.CHANGE_ROLE, new ChangeRoleCommand(userService));
		commands.put(CommandName.BAN_USER, new BanUserCommand(userService));
		commands.put(CommandName.VIEW_USER_BY_ID, new ViewUserByIdCommand(userService));
		commands.put(CommandName.SORT_USER_BY_ID, new SortUserByIdCommand(userService));
		commands.put(CommandName.VIEW_USER_BY_USERNAME, new ViewUserByUserNameCommand(userService));
		commands.put(CommandName.VIEW_TOPIC, new ViewTopicCommand(topicService));
		commands.put(CommandName.CREATE_TOPIC, new CreateTopicCommand(userService, topicService, sectionService));
		commands.put(CommandName.DELETE_TOPIC, new DeleteTopicCommand(topicService));
		commands.put(CommandName.SEARCH, new SearchAjaxCommand(topicService));
		commands.put(CommandName.VIEW_TOPIC_BY_HEADER, new ViewTopicByHeaderCommand(topicService));
		commands.put(CommandName.PIN_TOPIC, new PinTopicCommand(topicService));
		commands.put(CommandName.CLOSE_TOPIC, new CloseTopicCommand(topicService));
		commands.put(CommandName.VIEW_SECTION_BY_ID, new ViewSectionByIdCommand(topicService, sectionService));
		commands.put(CommandName.CREATE_SECTION, new CreateSectionCommand(sectionService));
		commands.put(CommandName.DELETE_SECTION, new DeleteSectionCommand(sectionService));
		commands.put(CommandName.VIEW_TOPIC_BY_ID, new ViewTopicByIdCommand(topicService, postService));
		commands.put(CommandName.VIEW_SECTION, new ViewSectionCommand(sectionService));
		commands.put(CommandName.CREATE_POST, new CreatePostCommand(userService, topicService, postService));
		commands.put(CommandName.DELETE_POST_BY_ID, new DeletePostByIdCommand(postService));
		commands.put(CommandName.EDIT_POST_BY_ID, new EditPostCommand(postService));
		commands.put(CommandName.LOGOUT, new LogOutCommand());
		commands.put(CommandName.LANGUAGE, new LanguageCommand());
		commands.put(CommandName.GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
		commands.put(CommandName.GO_TO_NEW_TOPIC_PAGE, new GoToNewTopicPageCommand());
		commands.put(CommandName.GO_TO_NEW_SECTION_PAGE, new GoToNewSectionPageCommand());
		commands.put(CommandName.GO_TO_HOME_PAGE, new GoToHomePageCommand());
		commands.put(CommandName.GO_TO_ADMIN_HOME_PAGE, new GoToAdminHomePageCommand());
		commands.put(CommandName.LANGUAGE, new LanguageCommand());
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

	private static class LazyHolder {
		private static final CommandProvider instance = new CommandProvider();
	}

	public static CommandProvider getInstance() {
		return LazyHolder.instance;
	}
}