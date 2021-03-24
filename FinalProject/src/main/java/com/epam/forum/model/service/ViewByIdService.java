package com.epam.forum.model.service;

import java.util.List;

import com.epam.forum.model.entity.Message;
import com.epam.forum.model.repository.Repository;
import com.epam.forum.model.repository.impl.IdSpecification;

public class ViewByIdService {

	public List<Message> findById(int id) {
		Repository repository = Repository.getInstance();
		List<Message> list = repository.query(new IdSpecification(id));
		return list;
	}
}
