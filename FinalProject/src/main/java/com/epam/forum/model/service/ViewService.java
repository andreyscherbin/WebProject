package com.epam.forum.model.service;

import java.util.List;

import com.epam.forum.model.entity.Message;
import com.epam.forum.model.repository.Repository;

public class ViewService {

	public List<Message> findAll() {
		Repository repository = Repository.getInstance();
		List<Message> list = repository.getMessages();
		return list;
	}
}
