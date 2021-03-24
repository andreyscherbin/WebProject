package com.epam.forum.model.service;

import java.util.Comparator;
import java.util.List;

import com.epam.forum.model.entity.Message;
import com.epam.forum.model.repository.Repository;

public class SortByParameterService {
	
	public List<Message> sort(Comparator<Message> comparator) {
		Repository repository = Repository.getInstance();
		List<Message> list = repository.sortByParameter(comparator);	
		return list;
	}
}
