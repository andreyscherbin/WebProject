package com.epam.forum.model.service.impl;

import java.util.Comparator;
import java.util.List;

import com.epam.forum.model.entity.Message;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.impl.IdSpecification;
import com.epam.forum.model.service.UserService;

public class UserServiceImpl implements UserService {
	private static final UserServiceImpl INSTANCE = new UserServiceImpl();
	private static final String USERNAME = "andrey";
	private static final String PASSWORD = "12345WQESADWQDPDSAP%$^Gfd";
	
	private UserServiceImpl() {}
	
	public static UserServiceImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean checkLogin(String username, String password) {
		return USERNAME.equals(username) && PASSWORD.equals(password);
	}

	@Override
	public List<Message> sort(Comparator<Message> comparator) {
		Repository repository = Repository.getInstance();
		List<Message> list = repository.sortByParameter(comparator);	
		return list;
	}

	@Override
	public List<Message> findById(int id) {
		Repository repository = Repository.getInstance();
		List<Message> list = repository.query(new IdSpecification(id));
		return list;
	}

	@Override
	public List<Message> findAll() {
		Repository repository = Repository.getInstance();
		List<Message> list = repository.getMessages();
		return list;
	}
}
