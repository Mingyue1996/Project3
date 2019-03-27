package oop.view;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import oop.board.BasicGameBoard;
import oop.board.square.Square;
import oop.controller.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;

//import main.course.oop.tictactoe.util.TwoDArray;

public class MainView {
	public static BorderPane root;
	private Scene scene; 
	private StackPane pane = new StackPane();
	
	public static VBox vBoxForGame = new VBox(20);
	public static TTTControllerImpl ticTacToe = new TTTControllerImpl();
	
	private int numPlayer;
	private static int timeout = 0;
	public static Label turnLabel = new Label();
	
	ArrayList<String> username = new ArrayList<>();
	ArrayList<String> marker = new ArrayList<>();
	private static int humanPlayerID = 1;
	public static Timeline timer;
	private static boolean isAIMove = false;
	
	private boolean emptyErrors = false;
	private boolean duplicateErrors = false;
	
    private final int windowWidth = 1000;
    private final int windowHeight = 900;
    private Text emptyInputsText = new Text ("User name(s) and marker(s) cannot be empty.");	
    private Text timeStringErrorText = new Text ("Time out must be an integer");
    private Text duplicateInputsText = new Text ("Two players cannot use the same username or marker.");
    private static boolean isImageMarker1 = false;
    private static boolean isImageMarker2 = false;
    
    public static final String MEDIA_URL_WIN = "src/winSound.mp3";
    //public static Timeline timerSquare;
    
	public MainView() {
		
		MainView.root = new BorderPane();
		this.scene = new Scene(root, windowWidth, windowHeight);

		scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());

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
		isImageMarker1 = false;
		isImageMarker2 = false;
	}
	
	
	public StackPane getNumPlayers() {
		this.pane = new StackPane();
		
		// create a pane for buttons
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
		fieldTimeOut.setPrefColumnCount(4);
		
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
			//Label wantGoFirstLabel = new Label ("Do you want to play first?");
			
				
			// group the buttons together
			//ToggleGroup radioButtonsGroup = new ToggleGroup();
			//playFirst.setToggleGroup(radioButtonsGroup);
		//	playSecond.setToggleGroup(radioButtonsGroup);
			
			// add buttons to the pane
			//gridPaneForInfo.add(wantGoFirstLabel, 0, 4);
			//gridPaneForInfo.add(playFirst, 1, 4);
			//gridPaneForInfo.add(playSecond, 2, 4);
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
			gridPaneForInfo.add(player2UsernameLabel, 0, 4);
			
			// add player 2's user name
			Label player2MarkerLabel = new Label("Enter palyer 2's marker");
		    gridPaneForInfo.add(player2MarkerLabel, 0, 5);
			
			// add them to field
			gridPaneForInfo.add(fieldUsername2, 1, 4);
		}
		
		// add a textField for player1 user name
		fieldUsername1 = new TextField();
		
		// add username1 to field
		gridPaneForInfo.add(fieldUsername1, 1, 1);
		
		// add a textField for player1 marker
		fieldMarker1 = new TextField();
		
		
		
		
		// add radio buttons
		RadioButton enterMarkerButton = new RadioButton("Enter a string");
		RadioButton chooseImageButton = new RadioButton("Choose an image");
		
		// group the buttons together
		ToggleGroup radioButtonsGroup = new ToggleGroup();
		enterMarkerButton.setToggleGroup(radioButtonsGroup);
		chooseImageButton.setToggleGroup(radioButtonsGroup);
					
		
		gridPaneForInfo.add(enterMarkerButton, 1, 2);
		gridPaneForInfo.add(chooseImageButton, 2, 2);
		
		enterMarkerButton.setSelected(true);
		gridPaneForInfo.add(fieldMarker1, 1, 3);
		
		
		// create rbUS
		RadioButton rbUS = new RadioButton("US");
		rbUS.setGraphic(new ImageView(Paths.get("src/us.png").toUri().toString()));
		rbUS.setContentDisplay(ContentDisplay.LEFT);
		rbUS.setSelected(true);
		
		// create rbCN
		RadioButton rbCN = new RadioButton("China");
		rbCN.setGraphic(new ImageView(Paths.get("src/cn.png").toUri().toString()));
		rbCN.setContentDisplay(ContentDisplay.LEFT);
		
		// create rbGB
		RadioButton rbGB = new RadioButton("United Kingdom");
		rbGB.setGraphic(new ImageView(Paths.get("src/gb.png").toUri().toString()));
		rbGB.setContentDisplay(ContentDisplay.LEFT);
	
		// group the buttons together
		ToggleGroup radioButtonsImageGroup = new ToggleGroup();
		rbUS.setToggleGroup(radioButtonsImageGroup);
		rbCN.setToggleGroup(radioButtonsImageGroup);
		rbGB.setToggleGroup(radioButtonsImageGroup);
		
		// add radio buttons

		RadioButton enterMarkerButton2 = new RadioButton("Enter a string");
		RadioButton chooseImageButton2 = new RadioButton("Choose an image");
		
		
		RadioButton rbUS2 = new RadioButton("US");
		RadioButton rbCN2 = new RadioButton("China");
		RadioButton rbGB2 = new RadioButton("United Kingdom");
		
		
if (numPlayer == 2) {
			
			// create image selection for player2
			
			// group the buttons together
			ToggleGroup radioButtonsGroup2 = new ToggleGroup();
			enterMarkerButton2.setToggleGroup(radioButtonsGroup2);
			chooseImageButton2.setToggleGroup(radioButtonsGroup2);
						
			
			gridPaneForInfo.add(enterMarkerButton2, 1, 5);
			gridPaneForInfo.add(chooseImageButton2, 2, 5);
			
			enterMarkerButton2.setSelected(true);
			gridPaneForInfo.add(fieldMarker2, 1, 6);
			
			
			// create rbUS
			rbUS2.setGraphic(new ImageView(Paths.get("src/us.png").toUri().toString()));
			rbUS2.setContentDisplay(ContentDisplay.LEFT);
			rbUS2.setSelected(true);
			
			// create rbCN
			rbCN2.setGraphic(new ImageView(Paths.get("src/cn.png").toUri().toString()));
			rbCN2.setContentDisplay(ContentDisplay.LEFT);
			
			// create rbGB
			rbGB2.setGraphic(new ImageView(Paths.get("src/gb.png").toUri().toString()));
			rbGB2.setContentDisplay(ContentDisplay.LEFT);
		
			// group the buttons together
			ToggleGroup radioButtonsImageGroup2 = new ToggleGroup();
			rbUS2.setToggleGroup(radioButtonsImageGroup2);
			rbCN2.setToggleGroup(radioButtonsImageGroup2);
			rbGB2.setToggleGroup(radioButtonsImageGroup2);
			
			
			
		} // end of numPlayer == 2
		
		
		
		
		enterMarkerButton.setOnAction(e -> {
			if (enterMarkerButton.isSelected()) {
				// add text field
				if (!gridPaneForInfo.getChildren().contains(fieldMarker1)) {
					gridPaneForInfo.add(fieldMarker1, 1, 3);
				}
				
				// remove image buttons
				if (gridPaneForInfo.getChildren().contains(rbUS)) {
					gridPaneForInfo.getChildren().remove(rbUS);
				}
				if (gridPaneForInfo.getChildren().contains(rbCN)) {
					gridPaneForInfo.getChildren().remove(rbCN);
				}
				if (gridPaneForInfo.getChildren().contains(rbGB)) {
					gridPaneForInfo.getChildren().remove(rbGB);
				}
			} // end of enterMarkerButton is selected
		});
		
		
		chooseImageButton.setOnAction(e -> {
			if (chooseImageButton.isSelected()) {
				// remove text field
				if (gridPaneForInfo.getChildren().contains(fieldMarker1)) {
					gridPaneForInfo.getChildren().remove(fieldMarker1);
				}			

				
				// add image radio buttons
				if (!gridPaneForInfo.getChildren().contains(rbUS)) {
					gridPaneForInfo.add(rbUS, 1, 3);
				}
				if (!gridPaneForInfo.getChildren().contains(rbCN)) {
					gridPaneForInfo.add(rbCN, 2, 3);
				}
				if (!gridPaneForInfo.getChildren().contains(rbGB)) {
					gridPaneForInfo.add(rbGB, 3, 3);
				}
				
			} // end of if ImageButton is selected
		});
		
		// select enter a marker2
		enterMarkerButton2.setOnAction(e -> {
			if (enterMarkerButton2.isSelected()) {
				// add text field
				if (!gridPaneForInfo.getChildren().contains(fieldMarker2)) {
					gridPaneForInfo.add(fieldMarker2, 1, 6);
				}
				
				// remove image buttons
				if (gridPaneForInfo.getChildren().contains(rbUS2)) {
					gridPaneForInfo.getChildren().remove(rbUS2);
				}
				if (gridPaneForInfo.getChildren().contains(rbCN2)) {
					gridPaneForInfo.getChildren().remove(rbCN2);
				}
				if (gridPaneForInfo.getChildren().contains(rbGB2)) {
					gridPaneForInfo.getChildren().remove(rbGB2);
				}
			} // end of enterMarkerButton is selected
		});
		
		// imageButton2
		chooseImageButton2.setOnAction(e -> {
			if (chooseImageButton2.isSelected()) {
				// remove text field
				if (gridPaneForInfo.getChildren().contains(fieldMarker2)) {
					gridPaneForInfo.getChildren().remove(fieldMarker2);
				}			

				
				// add image radio buttons
				if (!gridPaneForInfo.getChildren().contains(rbUS2)) {
					gridPaneForInfo.add(rbUS2, 1, 6);
				}
				if (!gridPaneForInfo.getChildren().contains(rbCN2)) {
					gridPaneForInfo.add(rbCN2, 2, 6);
				}
				if (!gridPaneForInfo.getChildren().contains(rbGB2)) {
					gridPaneForInfo.add(rbGB2, 3, 6);
				}
				
			} // end of if ImageButton is selected
		});
		
		
		//quit the game if "quit" is clicked
		quitButton.setOnAction(e -> System.exit(0));
		
		
		
		// get timeout, user name, marker when game starts
		startButton.setOnAction(e -> {			
		try {
			String username1 = fieldUsername1.getText(), marker1 = fieldMarker1.getText(), username2 = fieldUsername2.getText(), marker2 = fieldMarker2.getText();
			
			// if an image is selected for player1
			if (chooseImageButton.isSelected()) {
				isImageMarker1 = true;
				// check which image is selected and store its directory
				if (rbUS.isSelected()) {
					marker1 = "us.png";
				}
				else if (rbCN.isSelected()) {
					marker1 = "cn.png";
				}
				else if (rbGB.isSelected()) {
					marker1 = "gb.png";
				}
			} // end of if an image is selected
			
			
			if (numPlayer == 2 && chooseImageButton2.isSelected()) {
				isImageMarker2 = true;
				// check which image is selected and store its directory
				if (rbUS2.isSelected()) {
					marker2 = "us.png";
				}
				else if (rbCN2.isSelected()) {
					marker2 = "cn.png";
				}
				else if (rbGB2.isSelected()) {
					marker2 = "gb.png";
				}
			} // end of numPlayer == 2
			
			
			if (gridPaneForInfo.getChildren().contains(emptyInputsText)) {
				gridPaneForInfo.getChildren().remove(emptyInputsText);
				
			} // end of remove errors
			if (gridPaneForInfo.getChildren().contains(duplicateInputsText)) {
				gridPaneForInfo.getChildren().remove(duplicateInputsText);
			}
			emptyErrors = false;
			duplicateErrors = false;
			
			if (!chooseImageButton.isSelected()) {
				isImageMarker1 = false;
			}
			if (!chooseImageButton2.isSelected()) {
				isImageMarker2 = false;
			}
			
			// user names and markers cannot be empty
			if  (username1.trim().length() == 0 || (marker1.trim().length() == 0 && !isImageMarker1) || ( numPlayer == 2 && (username2.trim().length() == 0 ||( marker2.trim().length() == 0) && !isImageMarker2))) {
				emptyErrors = true;
				gridPaneForInfo.add(emptyInputsText, 0, 7);
			}
			
			// user names and markers cannot be the same
			if (numPlayer == 2) {
				if ((username1.trim().length() != 0 && username1.equals(username2)) || ( marker1.trim().length() != 0 && marker1.equals(marker2) && !isImageMarker1 && !isImageMarker2) || (isImageMarker1 && isImageMarker2 && marker1.equals(marker2))) {
					duplicateErrors = true;
					if (emptyErrors) {
						gridPaneForInfo.add(duplicateInputsText, 0, 8);
					}
					else {
						gridPaneForInfo.add(duplicateInputsText, 0, 7);
					}
					
				}
			} // end of numPlayer == 2
			
			System.out.println("marker1: "+ marker1);
			//System.out.println("empty errors: " + emptyErrors + " duplicateErrors: " + duplicateErrors);
			
			timeout =Integer.parseInt(fieldTimeOut.getText());		
			if (!duplicateErrors && !emptyErrors) {
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
					marker.add(marker1);
					username.add(username2);
					marker.add(marker2);
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
			} // end of error exists
		} catch (NumberFormatException error){

			
			if (gridPaneForInfo.getChildren().contains(timeStringErrorText)) {
				gridPaneForInfo.getChildren().remove(timeStringErrorText);
			}
							
			gridPaneForInfo.add(timeStringErrorText, 2, 0);
			
		} // end of catch
		
			
			
		});
	} // end of getPlayerInfo
	
	
	// playGame
	public void playGame() {
		// add a  game title
		root.setTop(new CustomPane("Welcome to Tic Tac Toe!"));
		
		// show the game board
		root.setCenter(ticTacToe.getGameDisplay());
		
		
		// computer makes first moves if necessary
		if (humanPlayerID == 2 && numPlayer == 1) {
			isAIMove = true;
			// generate row & column, call updatePlayerMove
			AIMove();
			
		} // end of  computer first move
		
					
		// show whose turn now
		turnLabel = new Label (username.get(ticTacToe.getPlayerID()-1) + "'s turn to play.");
		Label timeLeft = new Label ("Time: ");
		
		vBoxForGame.getChildren().addAll(turnLabel, timeLeft);
		vBoxForGame.setAlignment(Pos.CENTER);
		
		
		
		
		
		
		// add timeout
		if (timeout > 0) {
			// start the timer, when time is up, change playerID and restart animation
			// when a valid move is made, change playerID and restart animation
			timer = new Timeline(new KeyFrame(Duration.millis(timeout*1000), e-> {
				// when the timer is called, the previous player does not make a move, so we need to change the player ID here
				int curPlayerID = ticTacToe.setCurrentPlayer(ticTacToe.getPlayerID());
				// show whose turn now
				turnLabel.setText(username.get(curPlayerID-1) + "'s turn to play.");
				// computer makes a move (If a human moves, then this timer would not be called)
				// generate row & column, call updatePlayerMove
				if (numPlayer == 1) {
					AIMove();
					isAIMove = true;
					// when the game is over
					if (ticTacToe.getGameState() != 0) {
						// stop the timer when game is over
						timer.stop();
						// show the human lost the game

						Square.playSound("src/loseSound.mp3");
						
						turnLabel.setText(username.get(humanPlayerID-1) + " lost the game.");
						// show the two buttons (play again and quit)
						Square.hBox.setAlignment(Pos.CENTER);
						MainView.vBoxForGame.getChildren().add(Square.hBox);
					} // end of game over
					
					// get Current Player (must be a human since computer already changes player ID)
					curPlayerID = ticTacToe.getPlayerID();
				} // end of numPlayer == 1
				
				// show whose turn now
				if (ticTacToe.getGameState() == 0) {
					turnLabel.setText(username.get(curPlayerID-1) + "'s turn to play.");
				}
			
			}));
			
			timer.setCycleCount(9);
			timer.play();
		} // end of timeout
		
			
			
			
			
			
		
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
	
	public void AIMove() {
		Random rand = new Random(); 
		int computerRow = rand.nextInt(3); 
		int computerCol = rand.nextInt(3);
		while (!MainView.ticTacToe.updatePlayerMove(computerRow, computerCol, 3-humanPlayerID)) {
			computerRow = rand.nextInt(3); 
			computerCol = rand.nextInt(3);
		}
	
		BasicGameBoard.basicTwoD[computerRow][computerCol].setMarker(marker.get(2-humanPlayerID), false);
	}
	
	public static int getTimeout() {
		return timeout;
	}

	public static boolean getIsAIMove() {
		return isAIMove;
	}
	
	public static void setIsAIMove(boolean isMove) {
		isAIMove = isMove;
	}
	
	public static boolean getIsImageMarker1 () {
		return isImageMarker1;
	}
	
	public static boolean getIsImageMarker2 () {
		return isImageMarker2;
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

