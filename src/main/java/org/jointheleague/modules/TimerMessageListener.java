package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;


public class TimerMessageListener extends CustomMessageCreateListener {

	private boolean running = true;
	private final static String COMMAND = "!timer";

	public TimerMessageListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		if (event.getMessageContent().startsWith(COMMAND)) {
			String time = event.getMessageContent().substring(6);
			if (time.equals("")) {
				event.getChannel().sendMessage("Time not entered! Please try '!time<minutes>'");
			} else {
				int minutes = Integer.parseInt(time);
				long startTime = System.currentTimeMillis();
				event.getChannel().sendMessage("Timer set for " + minutes + " minute(s).");
				while (running) {
					if (System.currentTimeMillis() - startTime == minutes * 60000) {
						if (!event.getMessageContent().contains("STOP")) {
							for (int i = 0; i < 1000; i++) {
								event.getChannel().sendMessage("TIMER IS UP!!!");
							}
						} else {
							running = false;
						}
	
					}
				}
			}
		}
	}

}