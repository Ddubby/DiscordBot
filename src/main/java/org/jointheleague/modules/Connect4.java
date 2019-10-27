package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;



public class Connect4 extends CustomMessageCreateListener{
	
	private String rules = "Rules:\n1. Each player (Two) has respective colors Red and Blue\n2. When placed, circles will obey laws of gravity and fall down\n3. Place 4 circles in a row to win the game\n4. 4 in a row can be achieved horizontally, vertically, and diagonally";
	private String commands = "Commands:\n!join: Joins the active Connect4 game\n!place<column>: Places circle on given column\n!stats<username>: Displays statistics of given user\n!rules: Displays all rules\n!commands: Displays all commands";
	private boolean load = false;
	private boolean start = false;
	private String player1 = "";
	private String player2 = "";
	private int row;
	private int column;
	private int constant;
	private String board = "|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|";
	public Connect4(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		String message = event.getMessageContent().trim();
		if (message.startsWith("!connect4")) {
			event.getChannel().sendMessage("Loading Connect4...");
			event.getChannel().sendMessage(board);
			event.getChannel().sendMessage(rules + "\n");
			event.getChannel().sendMessage(commands + "\n");
			event.getChannel().sendMessage("\nStart the game by using !join");
			load = true;
		} else if (load && message.startsWith("!join")) {
			if (player1.equals("")) {
				player1 = event.getMessageAuthor().getDisplayName();
				event.getChannel().sendMessage(player1 + " has joined the game\nWaiting for 1 more player...");
			} else {
				player2 = event.getMessageAuthor().getDisplayName();
				event.getChannel().sendMessage(player2 + " has joined the game\nGame starting...");
				event.getChannel().sendMessage(board);
			}
		} else if (load && message.startsWith("!end")) {
			load = false;
			start = false;
			event.getChannel().sendMessage("Game stopped");
		} else if (start && message.startsWith("!place")) {
			String move = message.substring(6);
			column = Integer.parseInt(move);
			if (column > 7) {
				event.getChannel().sendMessage("Column index invalid! Please try your move again");
			} else {
				board = (board.substring(0, 6 * 107 + column * 8 + 1) + ":red_circle:" + board.substring(6 * 107 + column * 8 + 3));
			}
		}
	}

}
