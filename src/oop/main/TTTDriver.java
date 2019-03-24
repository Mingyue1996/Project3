package oop.main;

import oop.controller.TTTControllerImpl;
import java.util.Scanner;

import javax.imageio.ImageIO;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random; 

public class  TTTDriver {
	private static int inputToCheck;
	private static String user_name;
	
	// create a tic tac toe game
	public static void main(String[] args) throws IOException {
			int numPlayers = 0;
			int timeout = 0;
			TTTControllerImpl ticTacToe = new TTTControllerImpl();
			ArrayList<String> username = new ArrayList<>();
			ArrayList<String> marker = new ArrayList<>();
			String boardDemo;
			int humanPlayerID = 1;
			boolean isPlaying = true;
			boolean isValid = false;
			final int ZEROVALUE = -16777216;
			final int WIDTH = 120;
			final int HEIGHT = 20;
			
			// print out Tic Tac Toe (ASCII arts)
			// create an instance of Graphics2D and set the image type to integer mode
			BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics graphics = bufferedImage.getGraphics();
			graphics.setFont(new Font("TimesRoman", Font.PLAIN, 24));
			
			// generate ASCII art
			Graphics2D graphics2D = (Graphics2D) graphics;
			graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			graphics2D.drawString("Tic Tac Toe", 2, 20);
	
			// print out ASCII art
			for (int height = 0; height < HEIGHT; height++) {
			    StringBuilder stringBuilder  = new StringBuilder();
			    for (int width = 0; width < WIDTH; width++)
			    	if (bufferedImage.getRGB(width, height) == ZEROVALUE) {
			    		stringBuilder.append(" ");
			    	}
			    	else {
			    		stringBuilder.append("*");
			    	}
			    
			    	// print out strings only
				    if (stringBuilder.toString().trim().isEmpty()) {
				    	continue;
				    }
				    System.out.println(stringBuilder);
			} // end of for loop
			
			
			// create instance of Random class 
	        Random rand = new Random(); 
	        
			System.out.println("\nWelcome to play Tic Tac Toe.");
			System.out.println("Enter Quit (case insensitive) to quit the game at any time.");
			System.out.println("\n================================================================================");
			System.out.println("\nAt the end of the game, you will see an emoji based on game results:");
			System.out.println("You will see an expressionless face when there is a tie.");
			System.out.println("You will see a smiley face when a human player won the game (no matter which human player).");
			System.out.println("You will see an unhappy face when a human player plays against a computer and the human player lost.");
			
			System.out.println("\nWhen a human player plays against a computer, the result would only show whether the human player won or not.");
			System.out.println("For example, when a human player with user name Computer won the game, the result would show "
			+ "Computer won the game. This means the human player won the game, not the aritificial intelligence.");
			
			System.out.println("\n================================================================================");
			System.out.println("\nEnter the corresponding row and column number as shown below to mark the board.");
			boardDemo = "";
			for (int num = 0; num <49; num++) {
				if (num % 16 == 0) {
					boardDemo += String.format("+");
				}
				else {
					boardDemo += String.format("=");
				}
				
			}
			boardDemo += String.format("\n");	
			boardDemo += "|";
				for (int i = 0; i < 3; i ++) {
					for (int j = 0; j < 3; j++) {
						
						boardDemo += String.format("%-5s", "  " + "row "+ i + " " + "col " + j + "  " );	
						boardDemo += "|";
					}
					boardDemo += String.format("\n");
					for (int num = 0; num <49; num++) {
						if (num % 16 == 0) {
							boardDemo += String.format("+");
						}
						else {
							boardDemo += String.format("=");
						}
						
					}
					//boardDemo += String.format("\n");
					
					if (i+1 <3)
						boardDemo += "\n|";
					else
						boardDemo += "\n";
				}
				
				System.out.println(boardDemo);
			
			Scanner inputs = new Scanner(System.in);  // Reading from System.in
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			
			// start the game
			while (isPlaying) {
				username.clear();
				marker.clear();
				boardDemo = "";
				
				// get number of players
				isValid = false;
				while (!isValid) {
					System.out.println("\nEnter the number of players (1 or 2): ");
					isValid = checkInputsNumeric(inputs);
					if (isValid && inputToCheck != 1 && inputToCheck != 2) {	
						isValid = false;
						System.out.println("Invalid Input.");
					}
					else {
						numPlayers = inputToCheck;					
					}
				} // end of while true (valid number of players)
				
				//get timeout
				isValid = false;
				while (!isValid) {	
					System.out.println("Enter the time in seconds for each turn. Enter a negative number or zero for no timer.");
					isValid = checkInputsNumeric(inputs);
					if (isValid) {
						timeout = inputToCheck;
					}
				} // end of while true time out
				
				
				if (numPlayers == 1) {
					// ask who wants to go first
					isValid = false;
					while (!isValid) {
						System.out.println("Do you want to play first? Enter 1 to play first; if you enter 2, computer will play first.");
						isValid = checkInputsNumeric(inputs);
						if (isValid && inputToCheck != 1 && inputToCheck != 2) {	
							isValid = false;
							System.out.println("Invalid Input.");
						}
						else {
							humanPlayerID = inputToCheck;					
						}
					} // end of while true (valid number of players)
					
				} // end of (ask who goes first)
				
				
				// get user name			
				String username1;
				while (true) {
					if (numPlayers == 2) {
						System.out.println("Player 1 will start first. \nEnter player 1's user name. If you enter Quit (case insensitive), "
						+ "you would exit the game.");
					}
					else {
						System.out.println("Enter your user name. If you enter Quit (case insensitive), you would exit the game.");
					}
					
					username1 = inputs.nextLine();
					if (username1.trim().isEmpty()) {
						System.out.println("You cannot enter an empty user name.");
					}
					else if (username1.equalsIgnoreCase("Quit")) {
						System.out.println("You quitted the game. Bye~");
						createAsciiPicture("Bye.jpg");
						System.exit(0);
					}
					else {
						if (numPlayers == 2 || humanPlayerID == 1) {
							username.add(username1);
						}
						// one human player and computer goes first
						else {
							username.add("Computer");
							username.add(username1);
						}
						
						break;
					}
				} // end of while true (make sure user name is not empty)
				
			    
				// get player 2's user name
				if (numPlayers == 2) {
					String username2;
					while (true) {
						System.out.println("Enter player 2's user name. You cannot have the same user name as Player 1. If you enter Quit" + 
						" (case insensitive), you would exit the game.");	
						username2 = inputs.nextLine();
						if (username2.equalsIgnoreCase("Quit")) {
							System.out.println("You quitted the game. Bye~");
							createAsciiPicture("Bye.jpg");
							System.exit(0);
						}
						if (username2.equals(username.get(0))) {
							System.out.println("Invalid. Player 2's user name is the same as Player 1.");
						}
						else if (username2.trim().isEmpty()) {
							System.out.println("You cannot enter an empty user name.");
						}
						else {
							username.add(username2);
							break;
						}				
						
					} // end of while true (check same marker)
				} // end of get player 2's user name
				
				// one human player --- store AI's user name
				else if (numPlayers == 1 && humanPlayerID == 1) {
					username.add("Computer");
				}
					
				
				// get player 1's marker
				String marker1;
				while (true) {
					if (numPlayers == 2) {
						System.out.println("Enter player 1's marker. If you enter Quit (case insensitive), you would exit the game.");
					} else {
						System.out.println("Enter your marker. If you enter Quit (case insensitive), you would exit the game.");
					}
					marker1 = inputs.nextLine();
					if (marker1.trim().isEmpty()) {
						System.out.println("You cannot enter an empty marker.");
					}
					else if (marker1.equalsIgnoreCase("Quit")) {
						System.out.println("You quitted the game. Bye~");
						createAsciiPicture("Bye.jpg");
						System.exit(0);
					}
					// valid marker, store marker
					else {
						if (numPlayers == 2 || humanPlayerID == 1) {
							marker.add(marker1);
						}
						// one human player and computer goes first
						else {
							// store marker as O if human player's marker is not O
							if (!marker1.equals("O")) {
								marker.add("O");
							}
							else {
								marker.add("X");
							}
							marker.add(marker1);
						}
						
						break;
					}
				} // end of while true (make sure user name is not empty)
					
				// get player 2's marker
				if (numPlayers == 2) {
					String marker2;
					while (true ) {
						System.out.println("Enter player 2's  marker. You cannot have the same marker as Player 1. If you enter Quit, "
						+ "you would exit the game.");	
						marker2 = inputs.nextLine();
						if (marker2.equalsIgnoreCase("Quit")) {
							System.out.println("You quitted the game. Bye~");
							createAsciiPicture("Bye.jpg");
							System.exit(0);
						}
						if (marker2.equals(marker.get(0))) {
							System.out.println("Invalid. Player 2's marker is the same as Player 1.");
						}
						else if (marker2.trim().isEmpty()) {
							System.out.println("You cannot enter an empty marker.");
						}
						else {
							marker.add(marker2);
							break;
						}				
						
					} // end of while true (check same marker)
					
				} // end of if playerID = 2
				
				// store computer's marker
				else if (numPlayers == 1 && humanPlayerID == 1) {
					// store marker as O if human player's marker is not O
					if (!marker1.equals("O")) {
						marker.add("O");
					}
					else {
						marker.add("X");
					}
				}
				
				// create a game board
				ticTacToe.startNewGame(numPlayers,timeout);
			
				// create players
				// two human players
				if (numPlayers == 2) {
					ticTacToe.setIsHumanPlayer(true);
					ticTacToe.createPlayer(username.get(0), marker.get(0), 1);
					ticTacToe.createPlayer(username.get(1), marker.get(1), 2);				
				}
				// one human player
				else {
					// human plays first
					if (humanPlayerID == 1) {
						ticTacToe.setIsHumanPlayer(true);
						ticTacToe.createPlayer(username.get(0), marker.get(0), 1);
						ticTacToe.setIsHumanPlayer(false);
						ticTacToe.createPlayer(username.get(1), marker.get(1), 2);
					}
					// computer plays first
					else {
						ticTacToe.setIsHumanPlayer(false);
						ticTacToe.createPlayer(username.get(0), marker.get(0), 1);
						ticTacToe.setIsHumanPlayer(true);
						ticTacToe.createPlayer(username.get(1), marker.get(1), 2);
					}
					
				}
				
				// start game
				System.out.println(username.get(0) + " will start first.");
				
				
				// user enters location of the marker
				while (ticTacToe.getGameState() == 0) {	
					if (ticTacToe.getIsLastMoveValid()) {
						System.out.println("\nCurrent Board:\n");
						System.out.println(ticTacToe.getGameDisplay());	
					}
					else {
						ticTacToe.setCurrentPlayer(ticTacToe.getPlayerID());
					}
					ticTacToe.setIsLastMoveValid (false);
									
					// ask human player inputs
					if (numPlayers == 2 || (numPlayers == 1 && humanPlayerID == ticTacToe.getPlayerID())) {
						
						// get current user name
						user_name = username.get(ticTacToe.getPlayerID()-1);
						
						// record current time
						long startTime = System.currentTimeMillis();
						System.out.println(user_name + ", " + "enter the row and column (separated by white space; enter the row first) of"
						+ " your next move.");
						
						// when the game has a timer
						if (timeout > 0) {
							while (!ticTacToe.getIsLastMoveValid() && (System.currentTimeMillis() - startTime) < timeout * 1000) 
			        		{
								ticTacToe.getRowCol(in);
							
			        		} // end of timer loop
							
						} // end of if timeout > 0
						
						// no timer
						else {
							while (!ticTacToe.getIsLastMoveValid()) 
			        		{
								ticTacToe.getRowCol(in);
							
			        		} // end of timer loop
						}
						
						if (!ticTacToe.getIsLastMoveValid()) {
							System.out.println("Time is up. You cannot make move for now.");
						}
						
					} // end of if human player is playing
					// computer player generates a valid random move
					else {
						user_name = username.get(ticTacToe.getPlayerID()-1);
						// generate row & column, call updatePlayerMove
						int computerRow = rand.nextInt(3); 
						int computerCol = rand.nextInt(3);
						while (!ticTacToe.updatePlayerMove(computerRow, computerCol, ticTacToe.getPlayerID())) {
							computerRow = rand.nextInt(3); 
							computerCol = rand.nextInt(3);
						}
						ticTacToe.setIsLastMoveValid(true);
						System.out.println("\nComputer generated a move with row " + computerRow + " and column " + computerCol + ".");
					}			
					
				} // end of while (game over)
				
				System.out.println("\nCurrent Board:\n");
				System.out.println(ticTacToe.getGameDisplay());
				// print out results
				if (ticTacToe.getGameState() == 3) {
					System.out.println("No winner. There is a tie.");
					createAsciiPicture("src/tie_face.jpg");
				}
				else {
											
					// print out a smiley face when two players play and one of them won
					if (numPlayers == 2 || (numPlayers == 1 && username.indexOf(user_name) == humanPlayerID - 1)) {
						System.out.println(user_name + " won the game.");
						createAsciiPicture("src/smiley_face.jpg");					
					}
					
					// print out an unhappy face when the human player lost (in human vs. computer)
					if (numPlayers == 1 && username.indexOf(user_name) != humanPlayerID - 1) {
						System.out.println(username.get(humanPlayerID - 1) + " lost the game.");
						createAsciiPicture("src/cry_face.jpg");
					}
				}
						
			isValid = false;
		    // ask user(s) to play again or quit
			while (!isValid) {			
				System.out.println("Enter 1 to Play Again. Enter Quit (case insensitive) to exit the game.");	
				isValid = checkInputsNumeric(inputs);
				if (isValid && inputToCheck != 1) {
					isValid = false;
					System.out.println("Invalid Input.");
				}
				else if (isValid && inputToCheck == 1) {
					ticTacToe.setIsReplay(true);
				}
			} // end of while true (valid options)
			
		} // while true (the whole game)
		
		inputs.close();
	} // end of main method
	
	// check the validity of inputs
	public static boolean checkInputsNumeric(Scanner userInputs) {
		try {
			String extraString;
			// get input
			inputToCheck = userInputs.nextInt();
			extraString = userInputs.nextLine();
			// input is a number
			if (extraString.trim().isEmpty()) {
				return true;
			}
			// the input might be 1 a
			else {
				System.out.println("Invalid input.");
				return false;
		}

		// string is the input (including quit)
		} catch (InputMismatchException e) {
			String input = userInputs.nextLine();
			// quit the game
			if (input.equalsIgnoreCase("Quit")) {
				System.out.println("You quitted the game. Bye~");
				createAsciiPicture("src/Bye.jpg");
				System.exit(0);
			}
			System.out.println("Invalid input.");
			return false;
		} // end of catch			
	} // end of checkValidity
	
	
	// create an ASCII picture
	public static void createAsciiPicture(final String FILE_PATH) {
		String characters = "#####";
		int pixel;
		int red, green, blue;
		float color;
		int colorIndex;
		
		try {
			// read image
			BufferedImage image = ImageIO.read(new File(FILE_PATH));
			
			for (int height = 0; height < image.getHeight(); height +=2) {
				for (int width = 0; width < image.getWidth(); width++) {
					// get pixel in the specific width and height
					pixel = image.getRGB(width, height);
					
					// get red, green, blue
					red = (pixel & 0xff0000) >> 16;
					green = (pixel & 0xff00) >> 8; 
					blue = pixel & 0xff;
					
					// get color
					color =  red * 0.2f  + green * 0.5f + blue * 0.3f;
					colorIndex = Math.round((characters.length() + 1) * color / 255);
					// print out proper characters
					if (colorIndex < characters.length()) {
						System.out.print("#");
					}
					else {
						System.out.print(" ");
					}
					
				} // end of inner for
				System.out.println();
			} // end of outer for
		} catch (IOException e) {
			// image file is not found
			System.out.println("You were supposed to see an image, but the image file is not found, so the image cannot show."
			+ "\n However, this does not affect the game. Keep going.");
		} // end of catch
	} // end of createAsciiPicture
	
	
} // end of TTTDriver class