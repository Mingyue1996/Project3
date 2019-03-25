package oop.board.square;

import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import oop.board.BasicGameBoard;
import oop.view.*;

public class Square extends BorderPane{
	private int squareID;
	private String marker;
	private boolean isMarked = false;
	// create a HBox
	public static HBox hBox = new HBox(20);

	
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
		if (MainView.ticTacToe.getGameState() == 0) {
			int currentPlayerID = MainView.ticTacToe.getPlayerID();
			String currentMarker = MainView.ticTacToe.player.get(currentPlayerID -1).getMarker();
			String computerMarker = "X";
			if (MainView.ticTacToe.getNumberPlayers() == 1) {
				computerMarker = (currentMarker.equals("X")) ? "O" : "X";
			}
			
			// mark the square if the square is available 
			if (!this.getIsMarked()) {
				setMarker(currentMarker, false);
				int gameState = MainView.ticTacToe.determineWinner();
	
				// check game status
				// check game status
				if (gameState != 0) {
					checkGameIsOver(gameState);
				}
				// game is going on
				else {
					// change turn
					currentPlayerID = MainView.ticTacToe.setCurrentPlayer(currentPlayerID);
					MainView.turnLabel.setText(MainView.ticTacToe.player.get(currentPlayerID -1).getUsername() + "'s turn to play.");
					
					// check if it is computer's turn. If it is, generate 
					if (MainView.ticTacToe.getNumberPlayers() == 1 && currentPlayerID == 3-MainView.getHumanPlyaerID()) {
						// generate row & column, call updatePlayerMove
						Random rand = new Random(); 
						
						int computerRow = rand.nextInt(3); 
						int computerCol = rand.nextInt(3);
						while (!MainView.ticTacToe.updatePlayerMove(computerRow, computerCol, 3-MainView.getHumanPlyaerID())) {
							computerRow = rand.nextInt(3); 
							computerCol = rand.nextInt(3);
						}
					
						BasicGameBoard.basicTwoD[computerRow][computerCol].setMarker(computerMarker, false);
						int gameState1 = MainView.ticTacToe.getGameState();
						// check game status
						if (gameState1 != 0) {
							checkGameIsOver(gameState1);
						}
		
						else {
							MainView.turnLabel.setText(MainView.ticTacToe.player.get(MainView.ticTacToe.setCurrentPlayer(currentPlayerID)-1).getUsername() + "'s turn to play.");
						}
						
						
					}// end of computer plays
				}// end of game is going on
				
				
			}// end of getIsMarked()
			
		} // end of gameState == 0
	} // end of handleMouseClick
	
	public void checkGameIsOver(int gameState) {
		// someone won the game
		if ( gameState == 1 || gameState == 2) {
			MainView.turnLabel.setText(MainView.ticTacToe.player.get(MainView.ticTacToe.getPlayerID()-1).getUsername() + " won the game.");
			
	
		}
		// there is a tie
		else if (gameState == 3) {
			MainView.turnLabel.setText("There is a tie.");
		}
		hBox.setAlignment(Pos.CENTER);
		MainView.vBoxForGame.getChildren().add(hBox);
	} // end of checkGameIsOver
} // end of Square class