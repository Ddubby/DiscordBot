package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.javacord.api.event.message.MessageCreateEvent;



public class Connect4 extends CustomMessageCreateListener{
	
	private String rules = "Rules:\n1. Each player (two players) has respective colors Red and Blue\n2. When placed, circles will obey laws of gravity and fall down\n3. Place 4 circles in a row to win the game\n4. 4 in a row can be achieved horizontally, vertically, and diagonally";
	private String commands = "Commands:\n!join connect4: Joins the active Connect4 game\n!place<column>: Places circle on given column\n!end connect4: Ends the current running connect4 game\n!help connect4: Displays this help message";
	private boolean load = false;
	private boolean start = false;
	private boolean win = false;
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
			if (!load) {
				event.getChannel().sendMessage("Loading Connect4...");
				event.getChannel().sendMessage(board);
				event.getChannel().sendMessage("\nStart the game by using !join connect4");
				load = true;	
			} else {
				event.getChannel().sendMessage("A connect4 game is already underway!");
			}
		} else if (load && message.startsWith("!join connect4")) {
			if (player1.equals("")) {
				player1 = event.getMessageAuthor().getDisplayName();
				event.getChannel().sendMessage(player1 + " has joined the game!\nWaiting for 1 more player...");
			} else {
				player2 = event.getMessageAuthor().getDisplayName();
				event.getChannel().sendMessage(player2 + " has joined the game!\nGame starting...\nBegin by doing your first move!");
				board = "|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|";
				event.getChannel().sendMessage(board);
				row = 5;
				start = true;
			}
		} else if (load && message.startsWith("!end connect4")) {
			load = false;
			start = false;
			event.getChannel().sendMessage("Game ended");
		} else if(load && message.startsWith("!help connect4")) { 
			try {
				event.getMessageAuthor().asUser().get().openPrivateChannel().get().sendMessage(rules + "\n" + commands + "\n");
				event.getChannel().sendMessage("Help message sent to your DMs!");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (start && message.startsWith("!place")) {
			turn++;
			if (turn % 2 == 1) {
				if (event.getMessageAuthor().getDisplayName().equals(player1)) {
					String move = message.substring(6);
					column = Integer.parseInt(move);
					if (column < 1 || column > 7) {
						event.getChannel().sendMessage("Column index invalid! Please try your move again");
						turn--;
					} else {
						rowCheck();
						circle = " :red_circle: ";	
						spaces[row * 7 + column - 1] = circle;
					    board = "|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[0] + "|" + spaces[1] + "|" + spaces[2] + "|" + spaces[3] + "|" + spaces[4] + "|" + spaces[5] + "|" + spaces[6] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[7] + "|" + spaces[8] + "|" + spaces[9] + "|" + spaces[10] + "|" + spaces[11] + "|" + spaces[12] + "|" + spaces[13] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[14] + "|" + spaces[15] + "|" + spaces[16] + "|" + spaces[17] + "|" + spaces[18] + "|" + spaces[19] + "|" + spaces[20] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[21] + "|" + spaces[22] + "|" + spaces[23] + "|" + spaces[24] + "|" + spaces[25] + "|" + spaces[26] + "|" + spaces[27] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[28] + "|" + spaces[29] + "|" + spaces[30] + "|" + spaces[31] + "|" + spaces[32] + "|" + spaces[33] + "|" + spaces[34] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[35] + "|" + spaces[36] + "|" + spaces[37] + "|" + spaces[38] + "|" + spaces[39] + "|" + spaces[40] + "|" + spaces[41] + "|\n|-----|-----|-----|-----|-----|-----|-----|";
					    event.getChannel().sendMessage(board);
						row = 5;
					}
				} else {
					event.getChannel().sendMessage("It is not your turn yet " + event.getMessageAuthor().getDisplayName() + "!");
					turn--;
				}
			} else {
				if (event.getMessageAuthor().getDisplayName().equals(player2)) {
					String move = message.substring(6);
					column = Integer.parseInt(move);
					if (column < 1 || column > 8) {
						event.getChannel().sendMessage("Column index invalid! Please try your move again");
						turn--;
					} else {
						rowCheck();
						circle = " :large_blue_circle: ";	
						spaces[row * 7 + column - 1] = circle;
					    board = "|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[0] + "|" + spaces[1] + "|" + spaces[2] + "|" + spaces[3] + "|" + spaces[4] + "|" + spaces[5] + "|" + spaces[6] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[7] + "|" + spaces[8] + "|" + spaces[9] + "|" + spaces[10] + "|" + spaces[11] + "|" + spaces[12] + "|" + spaces[13] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[14] + "|" + spaces[15] + "|" + spaces[16] + "|" + spaces[17] + "|" + spaces[18] + "|" + spaces[19] + "|" + spaces[20] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[21] + "|" + spaces[22] + "|" + spaces[23] + "|" + spaces[24] + "|" + spaces[25] + "|" + spaces[26] + "|" + spaces[27] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[28] + "|" + spaces[29] + "|" + spaces[30] + "|" + spaces[31] + "|" + spaces[32] + "|" + spaces[33] + "|" + spaces[34] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[35] + "|" + spaces[36] + "|" + spaces[37] + "|" + spaces[38] + "|" + spaces[39] + "|" + spaces[40] + "|" + spaces[41] + "|\n|-----|-----|-----|-----|-----|-----|-----|";
					    event.getChannel().sendMessage(board);
						row = 5;
					}
				} else {
					event.getChannel().sendMessage("It is not your turn yet " + event.getMessageAuthor().getDisplayName() + "!");
					turn--;
				}
			}
		}
	}
	
	void rowCheck() {
		if(!spaces[row * 7 + column - 1].equals("        ")){
			row--;
			rowCheck();
		} 
	}
	
	boolean winCheck() {
		return win;
	}
}
