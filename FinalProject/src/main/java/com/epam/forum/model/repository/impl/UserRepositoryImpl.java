package com.epam.forum.model.repository.impl;

import java.util.List;

import com.epam.forum.exception.DaoException;
import com.epam.forum.model.dao.impl.UserDaoImpl;
import com.epam.forum.model.entity.User;
import com.epam.forum.model.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
	private static final UserRepositoryImpl INSTANCE = new UserRepositoryImpl();
	private UserDaoImpl userDaoImpl;
	
	private UserRepositoryImpl() {
		userDaoImpl = new UserDaoImpl();
		
	}
	
	public static UserRepositoryImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public User get(Long id) {
		 return null;		
	}

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getUserByUserName(String userName) {
		List<User> users = null; //FIX THIS PLZ
		try {
			users = userDaoImpl.findUserByUserName(userName);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return users;
	}
}
