package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

public class Fortune extends CustomMessageCreateListener {

	private final static String COMMAND = "!fortune";
	private String a1 = "A terrible fate lies around the corner...";
	private String a2 = "The experience of a life changing moment will occur today...";
	private String a3 = "An unexpected turn of events will play out in the next hour...";
	ArrayList<String> list = new ArrayList<String>();

	public Fortune(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		list.add(a1);
		list.add(a2);
		list.add(a3);
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {
			event.getChannel().sendMessage(list.get(new Random().nextInt(3)));
		}
	}

}
