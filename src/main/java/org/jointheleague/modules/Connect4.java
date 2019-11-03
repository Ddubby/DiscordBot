package org.jointheleague.modules;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;



public class Connect4 extends CustomMessageCreateListener{
	
	private String rules = "Rules:\n1. Each player (Two) has respective colors Red and Blue\n2. When placed, circles will obey laws of gravity and fall down\n3. Place 4 circles in a row to win the game\n4. 4 in a row can be achieved horizontally, vertically, and diagonally";
	private String commands = "Commands:\n!join: Joins the active Connect4 game\n!place<column>: Places circle on given column\n!stats<username>: Displays statistics of given user\n!rules: Displays all rules\n!commands: Displays all commands";
	private boolean load = false;
	private boolean start = false;
	private String player1 = "";
	private String player2 = "";
	private String circle = "";
	private int row;
	private int column;
	private int turn;
	private String[] spaces = new String[42];
	private String board = "|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|";
			
	public Connect4(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		if (!load) {
			for (int i = 0; i < spaces.length; i++) {
				spaces[i] = "        ";
			}	
		}
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
				event.getChannel().sendMessage(player2 + " has joined the game\nGame starting...\nBegin by doing your first move!");
				event.getChannel().sendMessage(board);
				start = true;
			}
		} else if (load && message.startsWith("!end")) {
			load = false;
			start = false;
			event.getChannel().sendMessage("Game stopped");
		} else if (start && message.startsWith("!place")) {
			turn++;
			if (turn % 2 == 1) {
				if (event.getMessageAuthor().getDisplayName().equals(player1)) {
					circle = " :red_circle: ";	
					String move = message.substring(6);
					column = Integer.parseInt(move);
					if (column < 1 || column > 7) {
						event.getChannel().sendMessage("Column index invalid! Please try your move again");
						turn--;
					} else {
						spaces[row * 7 + column - 1] = circle;
					    board = "|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[0] + "|" + spaces[1] + "|" + spaces[2] + "|" + spaces[3] + "|" + spaces[4] + "|" + spaces[5] + "|" + spaces[6] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[7] + "|" + spaces[8] + "|" + spaces[9] + "|" + spaces[10] + "|" + spaces[11] + "|" + spaces[12] + "|" + spaces[13] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[14] + "|" + spaces[15] + "|" + spaces[16] + "|" + spaces[17] + "|" + spaces[18] + "|" + spaces[19] + "|" + spaces[20] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[21] + "|" + spaces[22] + "|" + spaces[23] + "|" + spaces[24] + "|" + spaces[25] + "|" + spaces[26] + "|" + spaces[27] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[28] + "|" + spaces[29] + "|" + spaces[30] + "|" + spaces[31] + "|" + spaces[32] + "|" + spaces[33] + "|" + spaces[34] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[35] + "|" + spaces[36] + "|" + spaces[37] + "|" + spaces[38] + "|" + spaces[39] + "|" + spaces[40] + "|" + spaces[41] + "|\n|-----|-----|-----|-----|-----|-----|-----|";
						event.getChannel().sendMessage(board);
					}
				} else {
					event.getChannel().sendMessage("It is not your turn yet!");
				}
			} else {
				if (event.getMessageAuthor().getDisplayName().equals(player2)) {
					circle = " :large_blue_circle: ";	
					String move = message.substring(6);
					column = Integer.parseInt(move);
					if (column < 1 || column > 7) {
						event.getChannel().sendMessage("Column index invalid! Please try your move again");
						turn--;
					} else {
						spaces[row * 7 + column - 1] = circle;
					    board = "|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[0] + "|" + spaces[1] + "|" + spaces[2] + "|" + spaces[3] + "|" + spaces[4] + "|" + spaces[5] + "|" + spaces[6] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[7] + "|" + spaces[8] + "|" + spaces[9] + "|" + spaces[10] + "|" + spaces[11] + "|" + spaces[12] + "|" + spaces[13] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[14] + "|" + spaces[15] + "|" + spaces[16] + "|" + spaces[17] + "|" + spaces[18] + "|" + spaces[19] + "|" + spaces[20] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[21] + "|" + spaces[22] + "|" + spaces[23] + "|" + spaces[24] + "|" + spaces[25] + "|" + spaces[26] + "|" + spaces[27] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[28] + "|" + spaces[29] + "|" + spaces[30] + "|" + spaces[31] + "|" + spaces[32] + "|" + spaces[33] + "|" + spaces[34] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[35] + "|" + spaces[36] + "|" + spaces[37] + "|" + spaces[38] + "|" + spaces[39] + "|" + spaces[40] + "|" + spaces[41] + "|\n|-----|-----|-----|-----|-----|-----|-----|";
						event.getChannel().sendMessage(board);
					}
				} else {
					event.getChannel().sendMessage("It is not your turn yet!");
				}
			}
		}
	}


}
