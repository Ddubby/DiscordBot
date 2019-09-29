package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class TimerMessageListener extends CustomMessageCreateListener {

	private boolean running = true;
	private long minutes;
	private String time;
	private long startTime;
	private final static String COMMAND = "!timer";

	public TimerMessageListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		if (event.getMessageContent().startsWith(COMMAND)) {
			time = event.getMessageContent().substring(6);
			if (time.equals("")) {
				event.getChannel().sendMessage("Time not entered! Please try '!timer<minutes>'.");
			} else {
				minutes = Integer.parseInt(time);
				startTime = System.currentTimeMillis();
				event.getChannel().sendMessage("Timer set for " + minutes + " minute(s).");
				while (running) {
					if (System.currentTimeMillis() - startTime == minutes * 60000) {
						event.getChannel().sendMessage("TIMER IS UP!!!");
						running = false;
						event.getChannel().sendMessage("Snooze?");
					}
				}
			}
		} else if (event.getMessageContent().contains("!help")) {
			event.getChannel().sendMessage("!timer<minutes>: Initial command to set the timer \n!snooze<minutes>: Snoozes timer for set minutes");
		} else if (!running) {
			if (event.getMessageContent().startsWith("!snooze")) {
				String snooze = event.getMessageContent().substring(7);
				if (snooze.equals("")) {
					startTime = System.currentTimeMillis();
					running = true;
					event.getChannel().sendMessage("Timer snoozed for " + minutes + " minutes");
					while (running) {
						if (System.currentTimeMillis() - startTime == minutes * 60000) {
							event.getChannel().sendMessage("TIMER IS UP!!!");
							running = false;
							event.getChannel().sendMessage("Snooze?");
						}
					}
				} else {
					minutes = Integer.parseInt(snooze);
					startTime = System.currentTimeMillis();
					running = true;
					event.getChannel().sendMessage("Timer snoozed for " + minutes + " minutes");
					while (running) {
						if (System.currentTimeMillis() - startTime == minutes * 60000) {
							event.getChannel().sendMessage("TIMER IS UP!!!");
							running = false;
							event.getChannel().sendMessage("Snooze?");
						}
					}
				}
			} else if (event.getMessageContent().contains("No")) {
				event.getChannel().sendMessage("Timer has been stopped.");
			}
		}
	}
	/*
	 * if (event.getMessageContent().contains("!stop")) {
	 * event.getChannel().sendMessage("Timer has been stopped."); running = false; }
	 * else if (event.getMessageContent().contains("!pause")) {
	 * event.getChannel().sendMessage("Timer has been paused."); pauseTime =
	 * System.currentTimeMillis(); minutes = 0; } else if
	 * (event.getMessageContent().contains("!continue")) { minutes =
	 * Integer.parseInt(time);
	 * event.getChannel().sendMessage("Timer is now running with " + (minutes -
	 * (pauseTime * 60000 - startTime * 60000)) + " minutes left"); }
	 */
}