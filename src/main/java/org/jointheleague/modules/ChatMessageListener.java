package org.jointheleague.modules;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

public class ChatMessageListener extends CustomMessageCreateListener{

	private int karma = 0;
	private ArrayList<String> questions = new ArrayList<String>();
	private ArrayList<String> gAnswers = new ArrayList<String>();
	private ArrayList<String> bAnswers = new ArrayList<String>();
	
	
	
	public ChatMessageListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		questions.add("Hello");
		questions.add("Hi");
		questions.add("How was your day?");
		gAnswers.add("Hello " + event.getMessageAuthor().getDisplayName() + " !");
		bAnswers.add("Hey there...");
		gAnswers.add("Hi " + event.getMessageAuthor().getDisplayName() + " !");
		bAnswers.add("Hey there...");
		gAnswers.add("Good! How about you?");
		String message = event.getMessageContent().trim();
		if (message.startsWith("//")) {
			message.equals(message.substring(2));
			event.getChannel().sendMessage(answer(message));	
		}
	}

	public String answer(String message) {
		
		for (int i = 0; i < questions.size(); i++) {
			if (message.contains(questions.get(i))) {
				if (karma >= 0) {
					message = gAnswers.get(i);
					return message;
				} else {
					message = bAnswers.get(i);
					return message;
				}
			}
		}
		return "";
	}
}
