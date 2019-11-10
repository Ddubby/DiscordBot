package org.jointheleague.modules;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;
import org.w3c.dom.events.Event;

public class ChatMessageListener extends CustomMessageCreateListener{

	private int karma = 0;
	private boolean cursed = false;
	private ArrayList<String> gQuestions = new ArrayList<String>();
	private ArrayList<String> bQuestions = new ArrayList<String>();
	private ArrayList<String> gAnswers = new ArrayList<String>();
	private ArrayList<String> bAnswers = new ArrayList<String>();
	private ArrayList<String> curseWords = new ArrayList<String>();
	public ChatMessageListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		String message = event.getMessageContent().trim();
		if (message.startsWith("//")) {
			message.equals(message.substring(2));
			gQuestions.add("Hello");
			gQuestions.add("Hi");
			gQuestions.add("How was your day?");
			gQuestions.add("Search for ");
			gAnswers.add("Hello " + event.getMessage().getAuthor().getDisplayName() + "!");
			bAnswers.add("Hey there");
			gAnswers.add("Hi " + event.getMessage().getAuthor().getDisplayName() + "!");
			bAnswers.add("Hey there");
			gAnswers.add("Good! How about you?");
			bAnswers.add("Was doing better before you showed up");
			if (message.startsWith("//Search for")) {
				gAnswers.add("Here's what I found: https://www.google.com/search?q=" + message.substring(13) + "&source=lnms&tbm=isch&sa=X&ved=0ahUKEwior6LzhLvlAhVP1qwKHdh8BrAQ_AUIEigB&biw=1920&bih=890\n");
			}
			curseWords.add("test");
			event.getChannel().type();
			event.getChannel().sendMessage(answer(message));
			if (cursed) {
				//event.getMessage().edit("[DELETED BY CHATBOT]");
				cursed = false;
			}
		}
	}

	public String answer(String message) {
		for (String curse : curseWords) {
			if (message.contains(curse)) {
				cursed = true;
				karma--;
				message = "";
				return "Don't curse! :angry:";
			}
		}
		for (int i = 0; i < gQuestions.size(); i++) {
			if (message.contains(gQuestions.get(i))) {
				if (karma >= 0) {
					karma++;
					return gAnswers.get(i);
				} else {
					karma++;
					return bAnswers.get(i);
				}
			}/* else if(message.contains(bQuestions.get(i))){
				if (karma >= 0) {
					message = bAnswers.get(i);
					karma--;
					return message;
				} else {
					karma--;
					return "";
				}
			}*/
		}
		return "Sorry, but it seems like I cannot generate a logical response\n`Most likely scenario, you typed nonsense`";
	}
}
