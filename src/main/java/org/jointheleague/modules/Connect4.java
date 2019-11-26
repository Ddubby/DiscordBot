package org.jointheleague.modules;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.event.message.MessageCreateEvent;



public class Connect4 extends CustomMessageCreateListener{
	
	private String rules = "Rules:\n1. Each player (two players) has respective colors Red and Blue\n2. When placed, circles will obey laws of gravity and fall down\n3. Place 4 circles in a row to win the game\n4. 4 in a row can be achieved horizontally, vertically, and diagonally";
	private String commands = "Commands:\n!join connect4: Joins the active Connect4 game\n!place<column>: Places circle on given column\n!end connect4: Ends the current running connect4 game\n!help connect4: Displays this help message";
	private String player1 = "";
	private String player2 = "";
	private String circle = "";
	private String board = "";
	private String[] spaces = new String[42];
	private boolean load = false;
	private boolean start = false;
	private boolean win = false;
	private boolean waiting;
	private int row;
	private int column;
	private int turn;
	private Message botEditMessage;
	private Message botDeleteMessage;
	private long current;
	public Connect4(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		waiting = true;
		if (!load) {
			for (int i = 0; i < spaces.length; i++) {
				spaces[i] = "        ";
			}	
		}
		String message = event.getMessageContent().trim();
		if (message.equals("!connect4")) {
			if (!load) {
				board = "|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|";
				player1 = "";
				player2 = "";
				turn = 0;
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
				//EDIT MESSAGE CODE:
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
				row = 5;
				start = true;
			}
		} else if (load && message.startsWith("!end connect4")) {
			board = "|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|\n|        |        |        |        |        |        |        |\n|-----|-----|-----|-----|-----|-----|-----|";
			load = false;
			start = false;
			turn = 0;
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
					if (move.equals("")) {
						event.getChannel().sendMessage("No column index entered! Please try your move again");
						CompletableFuture<MessageSet> set = event.getChannel().getMessages(1);
						try {
							botDeleteMessage = set.get().first();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						event.deleteMessage();
						current = System.currentTimeMillis();
						while (waiting) {
							if ((System.currentTimeMillis() - current)/1000 == 3) {
								botDeleteMessage.delete();
								waiting = false;
							}
						}
						turn--;
					} else {
						column = Integer.parseInt(move);
						if (column < 1 || column > 8) {
							event.getChannel().sendMessage("Column index invalid! Please try your move again");
							CompletableFuture<MessageSet> set = event.getChannel().getMessages(1);
							try {
								botDeleteMessage = set.get().first();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							event.deleteMessage();
							current = System.currentTimeMillis();
							while (waiting) {
								if ((System.currentTimeMillis() - current)/1000 == 3) {
									botDeleteMessage.delete();
									waiting = false;
								}
							}
							turn--;
						} else {
							rowCheck();
							if (row == -1) {
								event.getChannel().sendMessage("There are no more spaces left in this column! Please try your move again");
								CompletableFuture<MessageSet> set = event.getChannel().getMessages(1);
								try {
									botDeleteMessage = set.get().first();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								event.deleteMessage();
								current = System.currentTimeMillis();
								while (waiting) {
									if ((System.currentTimeMillis() - current)/1000 == 3) {
										botDeleteMessage.delete();
										waiting = false;
									}
								}
								turn--;
								row = 5;
							} else {
								circle = " :red_circle: ";	
								spaces[row * 7 + column - 1] = circle;
							    board = "|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[0] + "|" + spaces[1] + "|" + spaces[2] + "|" + spaces[3] + "|" + spaces[4] + "|" + spaces[5] + "|" + spaces[6] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[7] + "|" + spaces[8] + "|" + spaces[9] + "|" + spaces[10] + "|" + spaces[11] + "|" + spaces[12] + "|" + spaces[13] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[14] + "|" + spaces[15] + "|" + spaces[16] + "|" + spaces[17] + "|" + spaces[18] + "|" + spaces[19] + "|" + spaces[20] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[21] + "|" + spaces[22] + "|" + spaces[23] + "|" + spaces[24] + "|" + spaces[25] + "|" + spaces[26] + "|" + spaces[27] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[28] + "|" + spaces[29] + "|" + spaces[30] + "|" + spaces[31] + "|" + spaces[32] + "|" + spaces[33] + "|" + spaces[34] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[35] + "|" + spaces[36] + "|" + spaces[37] + "|" + spaces[38] + "|" + spaces[39] + "|" + spaces[40] + "|" + spaces[41] + "|\n|-----|-----|-----|-----|-----|-----|-----|";
							    botEditMessage.edit(board);
							    event.deleteMessage();
							    if (winCheck(column, row, circle)) {
									event.getChannel().sendMessage(event.getMessageAuthor().getDisplayName() + " has won the game!\nStart a new game by typing !connect4 again!");
									load = false;
									start = false;
								}
							    for (int i = 0; i < spaces.length; i++) {
							    	if (spaces[i].equals("        ")) {
										break;
									}
							    	if (i == spaces.length - 1) {
										event.getChannel().sendMessage("The game has ended in a draw!\nStart a new game by typing !connect4 again!");
										load = false;
										start = false;
									}
								}
								row = 5;
							}
						}
					}
				} else {
					event.getChannel().sendMessage("It is not your turn yet " + event.getMessageAuthor().getDisplayName() + "!");
					turn--;
				}
			} else {
				if (event.getMessageAuthor().getDisplayName().equals(player2)) {
					String move = message.substring(6);
					if (move.equals("")) {
						event.getChannel().sendMessage("No column index entered! Please try your move again");
						CompletableFuture<MessageSet> set = event.getChannel().getMessages(1);
						try {
							botDeleteMessage = set.get().first();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						event.deleteMessage();
						current = System.currentTimeMillis();
						while (waiting) {
							if ((System.currentTimeMillis() - current)/1000 == 3) {
								System.out.println(botDeleteMessage);
								botDeleteMessage.delete();
								waiting = false;
							}
						}
						turn--;
					} else {
						column = Integer.parseInt(move);
						if (column < 1 || column > 8) {
							event.getChannel().sendMessage("Column index invalid! Please try your move again");
							CompletableFuture<MessageSet> set = event.getChannel().getMessages(1);
							try {
								botDeleteMessage = set.get().first();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							event.deleteMessage();
							current = System.currentTimeMillis();
							while (waiting) {
								if ((System.currentTimeMillis() - current)/1000 == 3) {
									botDeleteMessage.delete();
									waiting = false;
								}
							}
							turn--;
						} else {
							rowCheck();
							if (row == -1) {
								event.getChannel().sendMessage("There are no more spaces left in this column! Please try your move again");
								CompletableFuture<MessageSet> set = event.getChannel().getMessages(1);
								try {
									botDeleteMessage = set.get().first();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								event.deleteMessage();
								current = System.currentTimeMillis();
								while (waiting) {
									if ((System.currentTimeMillis() - current)/1000 == 3) {
										botDeleteMessage.delete();
										waiting = false;
									}
								}
								turn--;
								row = 5;
							} else {
								circle = " :blue_circle: ";	
								spaces[row * 7 + column - 1] = circle;
							    board = "|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[0] + "|" + spaces[1] + "|" + spaces[2] + "|" + spaces[3] + "|" + spaces[4] + "|" + spaces[5] + "|" + spaces[6] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[7] + "|" + spaces[8] + "|" + spaces[9] + "|" + spaces[10] + "|" + spaces[11] + "|" + spaces[12] + "|" + spaces[13] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[14] + "|" + spaces[15] + "|" + spaces[16] + "|" + spaces[17] + "|" + spaces[18] + "|" + spaces[19] + "|" + spaces[20] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[21] + "|" + spaces[22] + "|" + spaces[23] + "|" + spaces[24] + "|" + spaces[25] + "|" + spaces[26] + "|" + spaces[27] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[28] + "|" + spaces[29] + "|" + spaces[30] + "|" + spaces[31] + "|" + spaces[32] + "|" + spaces[33] + "|" + spaces[34] + "|\n|-----|-----|-----|-----|-----|-----|-----|\n|" + spaces[35] + "|" + spaces[36] + "|" + spaces[37] + "|" + spaces[38] + "|" + spaces[39] + "|" + spaces[40] + "|" + spaces[41] + "|\n|-----|-----|-----|-----|-----|-----|-----|";
							    botEditMessage.edit(board);
							    event.deleteMessage();
							    if (winCheck(column, row, circle)) {
									event.getChannel().sendMessage(event.getMessageAuthor().getDisplayName() + " has won the game!\nStart a new game by typing !connect4 again!");
									load = false;
									start = false;
								} 
							    for (int i = 0; i < spaces.length; i++) {
							    	if (spaces[i].equals("        ")) {
										break;
									}
							    	if (i == spaces.length - 1) {
										event.getChannel().sendMessage("The game has ended in a draw!\nStart a new game by typing !connect4 again!");
										load = false;
										start = false;
									}
								}
								row = 5;
							}
						}
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
			if (row != 0) {
				row--;
				rowCheck();	
			} else {
				row = -1;
			}
		} 
	}
	
	boolean winCheck(int column, int row, String circle) {
		for (int i = 0; i < 13; i++) {
			//Horizontal check, -___
			if (i == 0) {
				for (int j = 0; j < 4; j++) {
					if (row * 7 + column - 1 + j < 42 && row * 7 + column - 1 + j > 0 && column < 5) {
						if (!spaces[row * 7 + column - 1 + j].equals(circle)) {
							j = 5;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = 5;
						win = false;
					}
				}
			//Horizontal check, ___-
			} else if (i == 1 && !win) {
				for (int j = 0; j < 4; j++) {
					if (row * 7 + column - 1 - j < 42 && row * 7 + column - 1 - j > 0 && column > 3) {
						if (!spaces[row * 7 + column - 1 - j].equals(circle)) {
							j = 5;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = 5;
						win = false;
					}
				}
			//Horizontal check, _-__
			} else if (i == 2 && !win) {
				for (int j = 2; j > -2; j--) {
					if (row * 7 + column - 1 + j < 42 && row * 7 + column - 1 + j > 0 && column > 1 && column < 6) {
						if (!spaces[row * 7 + column - 1 + j].equals(circle)) {
							j = -3;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = -3;
						win = false;
					}
				}
			//Horizontal check __-_
			} else if (i == 3 && !win) {
				for (int j = 1; j > -3; j--) {
					if (row * 7 + column - 1 + j < 42 && row * 7 + column - 1 + j > 0 && column > 2 && column < 7) {
						if (!spaces[row * 7 + column - 1 + j].equals(circle)) {
							j = -4;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = -4;
						win = false;
					}
				}
			//Vertical check
			} else if (i == 4 && !win) {
				for (int j = 0; j < 4; j++) {
					if ((row + j) * 7 + column - 1 < 42 && (row + j) * 7 + column - 1 > 0) {
						if (!spaces[(row + j) * 7 + column - 1].equals(circle)) {
							j = 5;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = 5;
						win = false;
					}
				}
			//Diagonal check, top right
			} else if (i == 5 && !win) {
				for (int j = 0; j < 4; j++) {
					if ((row + j) * 7 + column - 1 - j < 42 && (row + j) * 7 + column - 1 - j > 0 && column > 3) {
						if (!spaces[(row + j) * 7 + column - 1 - j].equals(circle)) {
							j = 5;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = 5;
						win = false;
					}
				}
			//Diagonal check, top left
			} else if (i == 6 && !win) {
				for (int j = 0; j < 4; j++) {
					if ((row + j) * 7 + column - 1 + j < 42 && (row + j) * 7 + column - 1 + j > 0 && column < 5) {
						if (!spaces[(row + j) * 7 + column - 1 + j].equals(circle)) {
							j = 5;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = 5;
						win = false;
					}
				}
			//Diagonal check, bottom right
			} else if (i == 7 && !win) {
				for (int j = 0; j < 4; j++) {
					if ((row - j) * 7 + column - 1 - j < 42 && (row - j) * 7 + column - 1 - j > 0 && column > 3) {
						if (!spaces[(row - j) * 7 + column - 1 - j].equals(circle)) {
							j = 5;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = 5;
						win = false;
					}
				}
			//Diagonal check, bottom left
			} else if (i == 8 && !win) {
				for (int j = 0; j < 4; j++) {
					if ((row - j) * 7 + column - 1 + j < 42 && (row - j) * 7 + column - 1 + j > 0 && column < 5) {
						if (!spaces[(row - j) * 7 + column - 1 + j].equals(circle)) {
							j = 5;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = 5;
						win = false;
					}
				}
			//Diagonal check, upper mid right
			} else if (i == 9 && !win) {
				for (int j = 0; j < 4; j++) {
					if ((row + j - 1) * 7 + column - 1 - j + 1 < 42 && (row + j - 1) * 7 + column - 1 - j + 1 > 0 && column > 2 && column < 7) {
						if (!spaces[(row + j - 1) * 7 + column - 1 - j + 1].equals(circle)) {
							j = 5;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = 5;
						win = false;
					}
				}
			//Diagonal check, lower mid right
			} else if (i == 10 && !win) {
				for (int j = 0; j < 4; j++) {
					if ((row + j - 2) * 7 + column - 1 - j + 2 < 42 && (row + j - 2) * 7 + column - 1 - j + 2 > 0 && column > 1 && column < 6) {
						if (!spaces[(row + j - 2) * 7 + column - 1 - j + 2].equals(circle)) {
							j = 5;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = 5;
						win = false;
					}
				}
			//Diagonal check, upper mid left
			} else if (i == 11 && !win) {
				for (int j = 0; j < 4; j++) {
					if ((row + j - 1) * 7 + column - 1 + j - 1 < 42 && (row + j - 1) * 7 + column - 1 + j - 1 > 0 && column > 1 && column < 6) {
						if (!spaces[(row + j - 1) * 7 + column - 1 + j - 1].equals(circle)) {
							j = 5;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = 5;
						win = false;
					}
				}
			//Diagonal check, lower mid left
			} else if (i == 12 && !win) {
				for (int j = 0; j < 4; j++) {
					if ((row + j - 2) * 7 + column - 1 + j - 2 < 42 && (row + j - 2) * 7 + column - 1 + j - 2 > 0 && column > 2 && column < 7) {
						if (!spaces[(row + j - 2) * 7 + column - 1 + j - 2].equals(circle)) {
							j = 5;
							win = false;
						} else {
							win = true;
						}
					} else {
						j = 5;
						win = false;
					}
				}
			}
		}

		return win;
	}
}
