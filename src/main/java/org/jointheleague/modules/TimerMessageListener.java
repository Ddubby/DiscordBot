package org.jointheleague.modules;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.event.message.MessageCreateEvent;

public class TimerMessageListener extends CustomMessageCreateListener {

	private boolean running = true;
	private boolean waiting = true;
	private long minutes;
	private long startTime;
	private long current;
	private String time;
	private Message botEditMessage;
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
				run(event, time);
			}
		} else if (event.getMessageContent().equals("!help")) {
			event.getChannel().sendMessage("!timer<minutes>: Initial command to set the timer\n!snooze<minutes>: Snoozes timer for set minutes");
		} else if (!running) {
			if (event.getMessageContent().startsWith("!snooze")) {
				botEditMessage.delete();
				String snooze = event.getMessageContent().substring(7);
				if (snooze.equals("")) {
					run(event, time);
				} else {
					run(event, snooze);
				}
			} else if (event.getMessageContent().equalsIgnoreCase("No")) {
				running = true;
				botEditMessage.delete();
				event.getChannel().sendMessage("Timer has been stopped");
			}
		}
	}
	
	public void run(MessageCreateEvent event, String time) {
			minutes = Integer.parseInt(time);	
			if (running) {
				event.getChannel().sendMessage("Timer set for " + minutes + " minute(s) [" + event.getMessageAuthor().getDisplayName() + "].");	
			} else {
				event.getChannel().sendMessage("Timer snoozed for " + minutes + " minute(s) [" + event.getMessageAuthor().getDisplayName() + "].");
			}
			startTime = System.currentTimeMillis();
			running = true;
			while (running) {
				if (System.currentTimeMillis() - startTime == minutes * 60000) {
					event.getChannel().sendMessage("TIMER IS UP!!! [" + event.getMessageAuthor().getDisplayName() + "].\nhttps://media1.giphy.com/media/AfyEB4T0Io4BW/source.gif\nSnooze?");
					CompletableFuture<MessageSet> set = event.getChannel().getMessages(1);
					try {
						botEditMessage = set.get().first();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					running = false;
				}
			}
			current = System.currentTimeMillis();
			while (waiting) {
				if ((System.currentTimeMillis() - current)/1000 == 1) {
					botEditMessage.delete();
					event.getChannel().sendMessage("Timer has automatically stopped");
					running = true;
					waiting = false;
				}
			}
	}
}