package com.epam.forum.creator;

import java.util.ArrayList;
import java.util.List;

import com.epam.forum.model.entity.Message;

public class MessageFactory {

	public static List<Message> getMessages() {
		List<Message> list = new ArrayList<>();
		list.add(new Message(1, "hello"));
		list.add(new Message(8, "how are you"));
		list.add(new Message(7, "i'm fine"));
		list.add(new Message(6, "do you have any trouble?"));
		list.add(new Message(2, "no"));
		list.add(new Message(3, "would you like come with me on date?"));
		list.add(new Message(4, "yes, that's would be very great"));
		list.add(new Message(10, "Ok, so let's go on Friday 26"));
		list.add(new Message(9, "Fine"));
		list.add(new Message(5, "Bye"));
		return list;
	}
}
