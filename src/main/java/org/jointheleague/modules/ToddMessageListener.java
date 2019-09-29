package org.jointheleague.modules;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class ToddMessageListener extends CustomMessageCreateListener{

	private ArrayList<String> todds = new ArrayList<String>();
	private ArrayList<String> quotes = new ArrayList<String>();
	private final static String COMMAND = "!todd";
	
	public ToddMessageListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().startsWith("!todd")) {
			
		}
	}

}
