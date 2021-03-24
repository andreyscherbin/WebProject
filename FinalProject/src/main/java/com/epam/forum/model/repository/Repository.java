package com.epam.forum.model.repository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.epam.forum.creator.MessageFactory;
import com.epam.forum.model.entity.Message;

public class Repository {
	private static final Repository INSTANCE = new Repository();
	private List<Message> messages = new ArrayList<>();
	
	private Repository() {
		messages = MessageFactory.getMessages();
	}

	public static Repository getInstance() {
		return INSTANCE;
	}

	public List<Message> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	public void addMessage(Message message) {
		messages.add(message);
	}

	public void removeMessage(Message message) {
		messages.remove(message);
	}

	public Message get(int index) {
		return messages.get(index);
	}

	public Message set(int index, Message element) {
		return messages.set(index, element);
	}

	public List<Message> query(Specification specification) {
		List<Message> list = messages.stream().filter(specification::specify).collect(Collectors.toList());
		return list;
	}

	public List<Message> sortByParameter(Comparator<Message> comparator) {
		List<Message> list = new ArrayList(messages);
		list.sort(comparator);
		return list;		
	}
}
