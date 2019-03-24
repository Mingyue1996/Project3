package oop.board.square;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import oop.view.*;

public class Square extends BorderPane{
	private int squareID;
	private String marker;
	private boolean isMarked = false;
	
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
			String currentMarker = MainView.ticTacToe.player.get(MainView.ticTacToe.getPlayerID()-1).getMarker();
			
			// mark the square if the square is available 
			if (!this.getIsMarked()) {
				setMarker(currentMarker, false);
				int gameState = MainView.ticTacToe.determineWinner();
				// check game status
				// someone won the game
				if ( gameState == 1 || gameState == 2) {
					MainView.turnLabel.setText(MainView.ticTacToe.player.get(MainView.ticTacToe.getPlayerID()-1).getUsername() + " won the game.");
				}
				// there is a tie
				if (gameState == 3) {
					MainView.turnLabel.setText("There is a tie.");
				}
				
				// game is going on
				else {
					// change turn
					MainView.ticTacToe.setCurrentPlayer(MainView.ticTacToe.getPlayerID());
					MainView.turnLabel.setText(MainView.ticTacToe.player.get(MainView.ticTacToe.getPlayerID()-1).getUsername() + "'s turn to play.");
				}
				
				
			}// end of getIsMarked()
			
		} // end of gameState == 0
	} // end of handleMouseClick
}