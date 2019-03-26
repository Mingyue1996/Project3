package oop.board.square;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import oop.board.BasicGameBoard;
import oop.player.Player;
import oop.view.*;

public class Square extends BorderPane{
	private int squareID;
	private String marker;
	private boolean isMarked = false;
	// create a HBox
	public static HBox hBox = new HBox(20);
	private Timeline timerSquare;
	private String computerMarker;
	private int currentPlayerID;
	
	private boolean firstTime = true;
	
	private ArrayList<Timeline> timerLists = new ArrayList<>();
	
	public Square (int id, String marker) {
		squareID = id;
		this.marker = marker;
		setStyle("-fx-border-color: black");
		this.setPrefSize(180,180);
		this.setOnMouseClicked(e->handleMouseClick());
		
	}
	
	// return squareID
	public int getSquareID() {
		return squareID;
	}
	
	
	//display each marker
	public String display() {
		return marker;
	}
	
	// update marker
	public void setMarker(String marker, boolean isReset) {
		this.marker = marker;

		this.setCenter(new Text(marker));
		if (isReset) {
			isMarked = false;
		}
		else {
			isMarked = true;
		}
		
	}
	
	
	// return square state
	public boolean getIsMarked() {
		return isMarked;
	}
	
	// handle a mouse click event
	private void handleMouseClick() {
		MainView.setIsAIMove(false);

		if (MainView.ticTacToe.getGameState() == 0) {
			currentPlayerID = MainView.ticTacToe.getPlayerID();
			String currentMarker = MainView.ticTacToe.player.get(currentPlayerID -1).getMarker();
			computerMarker = "X";
			if (MainView.ticTacToe.getNumberPlayers() == 1) {
				computerMarker = (currentMarker.equals("X")) ? "O" : "X";
			}
			
			
			// mark the square if the square is available 
			if (!this.getIsMarked()) {
				
				// change player if a human makes a move
				if (!MainView.getIsAIMove()) {
					// newly add
					currentPlayerID = MainView.ticTacToe.setCurrentPlayer(currentPlayerID);

				}
				
				//if (MainView.getTimeout() > 0) {
				if (MainView.getTimeout() > 0) {
					MainView.timer.stop();
				}		
					// add a timer for humans					
					// it is human's turn
					//System.out.println( "human id: " + MainView.getHumanPlyaerID());
					//System.out.println( "current id: " + currentPlayerID);
					//if (MainView.ticTacToe.getNumberPlayers() == 2 || (MainView.ticTacToe.getNumberPlayers() == 1 && currentPlayerID == MainView.getHumanPlyaerID())) {
						//timerSquare  = new Timeline(new KeyFrame(Duration.millis(MainView.getTimeout()*1000), e-> {
							//timerSquare.stop();

							//System.out.println( "current id: " + currentPlayerID);
							// if computer is the next player, generate moves
							//if (MainView.ticTacToe.getNumberPlayers() == 1 && currentPlayerID == 3-MainView.getHumanPlyaerID()) {
								// newly add
								//timerSquare.stop();
								// computer makes move and check game results
							//	AI_Move_CheckWin();
							//	MainView.setIsAIMove(true);
								
								
								
							//}
							
							
							// determine winner no matter who is playing
							// if game state is 0, restart the timer
							//if (MainView.ticTacToe.getGameState() == 0) {
								//timerSquare.play();
								// add a timer here
								
								
								
							//}
						//	else {
								// otherwise, stop the timer
								//timerSquare.stop();
								
							//}
							
						//}));
						
						// start the animation
						//timerSquare.play();
				//} // end of test if it is human's turn
			//} // end of timeout > 0
				
				
				
				setMarker(currentMarker, false);
				int gameState = MainView.ticTacToe.determineWinner();
				System.out.println(timerSquare + "   human player");
				// check game status
				if (gameState != 0) {
					
					if (timerSquare != null ) {
						timerSquare.stop();
						System.out.println(timerSquare +" stops after human plays");
					}
						
					checkGameIsOver(gameState);
				}
				// game is going on
				else {
					// change turn

					// newly remove
					//currentPlayerID = MainView.ticTacToe.setCurrentPlayer(currentPlayerID);
					MainView.turnLabel.setText(MainView.ticTacToe.player.get(currentPlayerID -1).getUsername() + "'s turn to play.");

					// check if it is computer's turn. If it is, generate moves 
					//System.out.println("currentID when going on: " + currentPlayerID);
					if (MainView.ticTacToe.getNumberPlayers() == 1 && currentPlayerID == 3-MainView.getHumanPlyaerID()) {
						// computer makes move and check game results
						AI_Move_CheckWin();		
						MainView.setIsAIMove(true);
						
						//add a timer
//						// add timeout
//						if (MainView.getTimeout() > 0) {
//							if (MainView.ticTacToe.getGameState() == 0) {
//
//							// start the timer, when time is up, change playerID and restart animation
//							// when a valid move is made, change playerID and restart animation
//							
//								if (timerSquare != null) {
//									timerSquare.stop();
//									System.out.println(timerSquare +" stops after computer plays");
//								}
//								
//								
//								firstTime = false;
//								timerSquare = new Timeline(new KeyFrame(Duration.millis(MainView.getTimeout()*1000), e-> {
//								timerLists.add(timerSquare);
//								System.out.println(timerSquare +" starts");
//									// when the timer is called, the previous player does not make a move, so we need to change the player ID here
//									currentPlayerID = MainView.ticTacToe.setCurrentPlayer(currentPlayerID);
//									// show whose turn now
//									MainView.turnLabel.setText(MainView.ticTacToe.player.get(currentPlayerID-1).getUsername() + "'s turn to play.");
//									// computer makes a move (If a human moves, then this timer would not be called)
//									// generate row & column, call updatePlayerMove
//									if (MainView.ticTacToe.getNumberPlayers() == 1) {
//										if (MainView.ticTacToe.getGameState() == 0) {
//											AI_Move_CheckWin();
//										}
//										MainView.setIsAIMove(true);
//										// when the game is over
//										if (MainView.ticTacToe.getGameState() != 0) {
//											// stop the timer when game is over
//											if (timerSquare != null) {
//												timerSquare.stop();
//	
//											}
//											// show the human lost the game
//											MainView.turnLabel.setText(MainView.ticTacToe.player.get(MainView.getHumanPlyaerID()-1).getUsername() + " lost the game.");
//											// show the two buttons (play again and quit)
//											Square.hBox.setAlignment(Pos.CENTER);
//											if (!MainView.vBoxForGame.getChildren().contains(hBox)) {
//												MainView.vBoxForGame.getChildren().add(Square.hBox);
//											}
//										} // end of game over
//										
//		
//									} // end of numPlayer == 1
//									
//									// show whose turn now
//									if (MainView.ticTacToe.getGameState() == 0) {
//										timerSquare.stop();
//										timerSquare.play();
//										MainView.turnLabel.setText(MainView.ticTacToe.player.get(currentPlayerID-1).getUsername() + "'s turn to play.");
//									}
//								
//								}));
//						
//								timerSquare.play();
//							
//							if (MainView.ticTacToe.getGameState() == 0) {
//								timerSquare.setCycleCount(9);
//								timerSquare.play();
//							}
//								
//							}
//						} // end of timeout
//						
						
						
						
						
					}// end of computer plays
				
					
				}// end of game is going on
				
				
			}// end of getIsMarked()
			
		} // end of gameState == 0
	} // end of handleMouseClick
	
	public void AI_Move_CheckWin() {
		// generate row & column, call updatePlayerMove
		Random rand = new Random(); 
		
		int computerRow = rand.nextInt(3); 
		int computerCol = rand.nextInt(3);
		while (!MainView.ticTacToe.updatePlayerMove(computerRow, computerCol, 3-MainView.getHumanPlyaerID())) {
			computerRow = rand.nextInt(3); 
			computerCol = rand.nextInt(3);
		}
	
			BasicGameBoard.basicTwoD[computerRow][computerCol].setMarker(computerMarker, false);

		// newly add
		currentPlayerID = MainView.ticTacToe.getPlayerID();
		// get game state
		int gameState1 = MainView.ticTacToe.getGameState();
		// check game status
		if (gameState1 != 0) {

			if (MainView.getTimeout() > 0) {
				if (timerSquare != null) {
					System.out.println(timerSquare + "stops");					
					timerSquare.stop();

					//clearTimer();
				}
					
			}
			checkGameIsOver(gameState1);
		}

		else {
			// change turn newly changes  MainView.turnLabel.setText(MainView.ticTacToe.player.get(MainView.ticTacToe.setCurrentPlayer(currentPlayerID)-1).getUsername() + "'s turn to play.");
			MainView.turnLabel.setText(MainView.ticTacToe.player.get(currentPlayerID-1).getUsername() + "'s turn to play.");
		}
	}
	
	
	public void checkGameIsOver(int gameState) {
		// someone won the game
		if ( gameState == 1 || gameState == 2) {
			MainView.turnLabel.setText(MainView.ticTacToe.player.get(currentPlayerID-1).getUsername() + " won the game.");
			
	
		}
		// there is a tie
		else if (gameState == 3) {
			MainView.turnLabel.setText("There is a tie.");
		}
		hBox.setAlignment(Pos.CENTER);
		if (!MainView.vBoxForGame.getChildren().contains(hBox)) {
			MainView.vBoxForGame.getChildren().add(hBox);
		}
		
	} // end of checkGameIsOver
	
	public void clearTimer () {
			for (int i = 0; i < timerLists.size(); i++) {
				timerLists.get(i).stop();
			}
			timerLists.clear();
		
	}
} // end of Square class