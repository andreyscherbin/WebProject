package com.epam.forum.model.service;

import java.util.Comparator;
import java.util.List;
import com.epam.forum.model.entity.Message;

public interface UserService {
	boolean checkLogin(String username, String password);
	List<Message> sort(Comparator<Message> comparator);
	List<Message> findById(int id);
	List<Message> findAll();	
}
	

