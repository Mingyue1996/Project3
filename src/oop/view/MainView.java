package oop.view;

import java.util.ArrayList;
import java.util.Random;

import oop.board.BasicGameBoard;
import oop.board.square.Square;
import oop.controller.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;

//import main.course.oop.tictactoe.util.TwoDArray;

public class MainView {
	public static BorderPane root;
	private Scene scene; 
	private StackPane pane = new StackPane();
	
	public static VBox vBoxForGame = new VBox(20);
	public static TTTControllerImpl ticTacToe = new TTTControllerImpl();
	
	private int numPlayer;
	private int timeout = 0;
	public static Label turnLabel = new Label();
	
	ArrayList<String> username = new ArrayList<>();
	ArrayList<String> marker = new ArrayList<>();
	private static int humanPlayerID = 1;
	
    private final int windowWidth = 900;
    private final int windowHeight = 900;
	
	public MainView() {
		
		MainView.root = new BorderPane();
		this.scene = new Scene(root, windowWidth, windowHeight);

		scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
		
		
		// original professor
		//this.root.setTop(this.buildSetupPane());
		getMainView();
		
		
	} //end of MainView
	
	public void getMainView() {
		// add a  game title
		root.setTop(new CustomPane("Welcome to Tic Tac Toe!"));		
		// remove play again buttons and whoseTurn label
		vBoxForGame.getChildren().clear();		
		// add radio buttons
		root.setCenter(getNumPlayers());
		//clear user name and marker lists
		username.clear();
		marker.clear();
	}
	
	
	public StackPane getNumPlayers() {
		this.pane = new StackPane();
		
		VBox vBoxForButtons = new VBox(30);
		
		
		// create new buttons
		RadioButton onePlayer = new RadioButton("Play against computer");
		RadioButton twoPlayers = new RadioButton("Play with another human");
		
			
		// group the buttons together
		ToggleGroup radioButtonsGroup = new ToggleGroup();
		onePlayer.setToggleGroup(radioButtonsGroup);
		twoPlayers.setToggleGroup(radioButtonsGroup);
		
		// set a default selection
		onePlayer.setSelected(true);
		
		// create two buttons: continue and quit
		Button continueButton = new Button("Continue");
		Button quitButton = new Button("Quit the game");
		
		
		// add buttons to the pane
		vBoxForButtons.getChildren().addAll(onePlayer, twoPlayers, continueButton, quitButton);		
		vBoxForButtons.setAlignment(Pos.CENTER);
		
		pane.getChildren().addAll(vBoxForButtons);
		
		//quit the game if "quit" is clicked
		quitButton.setOnAction((e -> System.exit(0)));
		
		//get the number of players
		continueButton.setOnAction(e -> {
			if (onePlayer.isSelected()) {
				numPlayer = 1;
			}
			else if (twoPlayers.isSelected()) {
				numPlayer = 2;
			}
			
			// remove "ask number of players" 
			vBoxForButtons.getChildren().clear();
			
			// call another function to get timeout, user names, and markers
			getPlayerInfo(vBoxForButtons);
		});
		
		return pane;
	}
	
	
	// get timeout, user names, and markers
	public void getPlayerInfo(VBox vBoxForButtons) {
		
		// create two buttons: continue and quit
		Button startButton = new Button("Start the Game");
		Button quitButton = new Button("Quit the game");
		
		// add buttons to the pane
		vBoxForButtons.getChildren().addAll(startButton, quitButton);		
		vBoxForButtons.setAlignment(Pos.CENTER);
		
		GridPane gridPaneForInfo = new GridPane();
		//gridPaneForInfo.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		gridPaneForInfo.setHgap(20);
		gridPaneForInfo.setVgap(20);
		gridPaneForInfo.setAlignment(Pos.CENTER);
		
		Label timeoutLabel = new Label("Time limit: ");
		gridPaneForInfo.add(timeoutLabel, 0, 0);
		
		// add a textField for timeoutLabel
		TextField fieldTimeOut = new TextField();
		fieldTimeOut.setPrefColumnCount(6);
		
		gridPaneForInfo.add(fieldTimeOut, 1, 0);
		vBoxForButtons.getChildren().add(0, gridPaneForInfo);
		
		TextField fieldUsername1;
		TextField fieldUsername2 = new TextField();
		TextField fieldMarker1;
		TextField fieldMarker2 = new TextField();
		
		// create new buttons
		RadioButton playFirst = new RadioButton("Yes");
		RadioButton playSecond = new RadioButton("No");
		
		// when there is one player
		if (numPlayer == 1) {
			
			// ask whether the human wants to play the first
			Label wantGoFirstLabel = new Label ("Do you want to play first?");
			
				
			// group the buttons together
			ToggleGroup radioButtonsGroup = new ToggleGroup();
			playFirst.setToggleGroup(radioButtonsGroup);
			playSecond.setToggleGroup(radioButtonsGroup);
			
			// add buttons to the pane
			gridPaneForInfo.add(wantGoFirstLabel, 0, 3);
			gridPaneForInfo.add(playFirst, 1, 3);
			gridPaneForInfo.add(playSecond, 2, 3);
			// set a default selection
			playFirst.setSelected(true);
			
			Label humanPlayerUsernameLabel = new Label ("Give yourself a user name: ");
			gridPaneForInfo.add(humanPlayerUsernameLabel, 0, 1);
			
			Label humanPlayerMarkerLabel = new Label ("Put your marker: ");
			gridPaneForInfo.add(humanPlayerMarkerLabel, 0, 2);
		} 
		// when we have two players
		else {
			// add player 1's user name
			Label player1UsernameLabel = new Label("Enter palyer 1's user name");
			gridPaneForInfo.add(player1UsernameLabel, 0, 1);
			
			// add player 2's user name
			Label player1MarkerLabel = new Label("Enter palyer 1's marker");
			gridPaneForInfo.add(player1MarkerLabel, 0, 2);
			
			// add player 2's user name
			Label player2UsernameLabel = new Label("Enter palyer 2's user name");
			gridPaneForInfo.add(player2UsernameLabel, 0, 3);
			
			// add player 2's user name
			Label player2MarkerLabel = new Label("Enter palyer 2's marker");
		    gridPaneForInfo.add(player2MarkerLabel, 0, 4);
			
			// add them to field
			gridPaneForInfo.add(fieldUsername2, 1, 3);
		}
		
		// add a textField for player1 user name
		fieldUsername1 = new TextField();
		
		// add username1 to field
		gridPaneForInfo.add(fieldUsername1, 1, 1);
		
		// add a textField for player1 marker
		fieldMarker1 = new TextField();
		
		// add them to field
		gridPaneForInfo.add(fieldMarker1, 1, 2);
		
		if (numPlayer == 2) {
			// add marker2 to field
			gridPaneForInfo.add(fieldMarker2, 1, 4); 
		}
		
		
		//quit the game if "quit" is clicked
		quitButton.setOnAction(e -> System.exit(0));
		
		
		
		// get timeout, user name, marker when game starts
		startButton.setOnAction(e -> {			
		try {
			String username1 = fieldUsername1.getText(), marker1 = fieldMarker1.getText(), username2, marker2;
			timeout =Integer.parseInt(fieldTimeOut.getText());		
			
			// one player, she/he goes first
			if (numPlayer == 1) {
				if (playFirst.isSelected()) {
					humanPlayerID = 1;
					
					username.add(username1);
					marker.add(marker1);
					username.add("Computer");
					if (!marker1.equals("X"))
						marker.add("X");
					else
						marker.add("O");
					
				} else {
					humanPlayerID = 2;
					// one player, she/he goes second
					if (!marker1.equals("X"))
						marker.add("X");
					else
						marker.add("O");
					username.add("Computer");
					username.add(username1);
					marker.add(marker1);
				}
				
			} // end of one player
			
			
			// two players		
			else {
				username.add(username1);
				marker.add(fieldMarker1.getText());
				username.add(fieldUsername2.getText());
				marker.add(fieldMarker2.getText());
			}
			
			// create a game
			ticTacToe.startNewGame(numPlayer, timeout);
			
			// create players	
			// one player
			if (numPlayer == 1) {
				// human first
				if (humanPlayerID == 1) {
					ticTacToe.setIsHumanPlayer(true);
					ticTacToe.createPlayer(username.get(humanPlayerID-1), marker.get(humanPlayerID-1), humanPlayerID);				
					ticTacToe.setIsHumanPlayer(false);
					ticTacToe.createPlayer(username.get(2-humanPlayerID), marker.get(2-humanPlayerID), 3-humanPlayerID);
				}
				// human second
				else {
					ticTacToe.setIsHumanPlayer(false);
					ticTacToe.createPlayer(username.get(2-humanPlayerID), marker.get(2-humanPlayerID), 3-humanPlayerID);
					ticTacToe.setIsHumanPlayer(true);
					ticTacToe.createPlayer(username.get(humanPlayerID-1), marker.get(humanPlayerID-1), humanPlayerID);		
				}
				
			}
			// two players
			else {
				ticTacToe.setIsHumanPlayer(true);
				ticTacToe.createPlayer(username.get(0), marker.get(0), 1);
				ticTacToe.createPlayer(username.get(1), marker.get(1), 2);
			}
			
			// clear all the elements in pane
			pane.getChildren().clear();
			gridPaneForInfo.getChildren().clear();
			root.getChildren().clear();
			
			// play game
			playGame();
			
		} catch (NumberFormatException error){
			Text errorText = new Text ("Time out must be an integer");		

			int size = gridPaneForInfo.getChildren().size();
			if (size == 7 || size == 11) {
				gridPaneForInfo.getChildren().remove(size-1);
			}
							
			gridPaneForInfo.add(errorText, 2, 0);
			
		} // end of catch
		
			
			
		});
	} // end of getPlayerInfo
	
	
	// playGame
	public void playGame() {
		// add a  game title
		root.setTop(new CustomPane("Welcome to Tic Tac Toe!"));

		// computer make first moves if necessary
		if (humanPlayerID == 2 && numPlayer == 1) {
			// generate row & column, call updatePlayerMove
			Random rand = new Random(); 
			
			int computerRow = rand.nextInt(3); 
			int computerCol = rand.nextInt(3);
			while (!MainView.ticTacToe.updatePlayerMove(computerRow, computerCol, 1)) {
				computerRow = rand.nextInt(3); 
				computerCol = rand.nextInt(3);
			}
		
			BasicGameBoard.basicTwoD[computerRow][computerCol].setMarker(marker.get(0), false);
			
		}
		
		// show whose turn now
		turnLabel = new Label (username.get(ticTacToe.getPlayerID()-1) + "'s turn to play.");
		Label timeLeft = new Label ("Time: ");

		
		vBoxForGame.getChildren().addAll(turnLabel, timeLeft);
		vBoxForGame.setAlignment(Pos.CENTER);
		root.setBottom(vBoxForGame);
		root.setPadding(new Insets(50));
		
		root.setBottom(vBoxForGame);
		root.setPadding(new Insets(50));
		
		// create two buttons
		Button playAgainButton = new Button("Play Again");
		Button quitButton = new Button("Quit the game");
		
		if (Square.hBox.getChildren().size() == 0) {
			Square.hBox.getChildren().addAll(playAgainButton, quitButton);

		}
		
		// restart the game when "Play Again" works
		((ButtonBase) Square.hBox.getChildren().get(0)).setOnAction(e -> {
			// restart a game
			ticTacToe.setIsReplay(true);
			getMainView();
		});
		
		// quit the game
		((ButtonBase) Square.hBox.getChildren().get(1)).setOnAction(e -> {
			// quit the game
			System.exit(0);
		});
	} // end of playGame
	
	

	public Scene getMainScene() {
		return this.scene;
	}
	
	public static int getHumanPlyaerID() {
		return humanPlayerID;
	}

}


// customePane
class CustomPane extends StackPane {
	public CustomPane(String title) {
		Text textTitle = new Text(title);
		getChildren().add(textTitle);
		textTitle.getStyleClass().add("textTitle");
	}
}

