package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class DiceMessageListener extends CustomMessageCreateListener{

	private int randomNum = 0;
	private Random randomGen = new Random();
	private boolean waiting;
	private long current;
	private Message botEditMessage;
	
	public DiceMessageListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		waiting = true;
		String message = event.getMessageContent().trim();
		if (message.startsWith("!roll")) {
			event.getChannel().sendMessage("Rolling the die...\nhttp://premium-institute.com/die.gif");
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
			if (message.substring(5).equals("")) {
				randomNum = randomGen.nextInt(6);
				current = System.currentTimeMillis();
				while (waiting) {
					if ((System.currentTimeMillis() - current)/1000 == 2) {
						botEditMessage.edit("A " + (randomNum + 1) + " has been rolled!");
						waiting = false;
					}
				}
			} else {
				int bound = Integer.parseInt(message.substring(5));
				randomNum = randomGen.nextInt(bound);
				current = System.currentTimeMillis();
				while (waiting) {
					if ((System.currentTimeMillis() - current)/1000 == 2) {
						botEditMessage.edit("A " + (randomNum + 1) + " has been rolled on the " + bound + "-sided die!");
						waiting = false;
					}
				}
			}
		} 
	}

}
