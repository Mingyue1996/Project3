package oop.view;

import java.util.ArrayList;

import oop.controller.*;

import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.MotionBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
//import main.course.oop.tictactoe.util.TwoDArray;

public class MainView {
	private BorderPane root;
	private Scene scene; 
	private StackPane pane = new StackPane();
	
	public static TTTControllerImpl ticTacToe = new TTTControllerImpl();
	
	private int numPlayer;
	private int timeout = 0;
	public static Label turnLabel = new Label();
	
	ArrayList<String> username = new ArrayList<>();
	ArrayList<String> marker = new ArrayList<>();
	
   // private TwoDArray twoDArr;
	private Text statusNode;
    private final int windowWidth = 900;
    private final int windowHeight = 900;
	
	public MainView() {
		this.root = new BorderPane();
		this.scene = new Scene(root, windowWidth, windowHeight);

		scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
		
		this.statusNode = new Text("no status");
		
		// original professor
		//this.root.setTop(this.buildSetupPane());
		
		// add a  game title
		this.root.setTop(new CustomPane("Welcome to Tic Tac Toe!"));
		
		// add radio buttons
		this.root.setCenter(getNumPlayers());
		System.out.println(numPlayer);
		
	} //end of MainView
	
	
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
		
		//quit the game if "quit" is cliked
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
		
		// when there is one player
		if (numPlayer == 1) {
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
		
		
		//quit the game if "quit" is cliked
		quitButton.setOnAction(e -> System.exit(0));
		
		// get timeout, username, marker when game starts
		startButton.setOnAction(e -> {			
		try {
			timeout =Integer.parseInt(fieldTimeOut.getText());		
			
			username.add(fieldUsername1.getText());
			marker.add(fieldMarker1.getText());
			
			if (numPlayer == 2) {
				username.add(fieldUsername2.getText());
				marker.add(fieldMarker2.getText());
			}
			else {
				username.add("Computer");
				marker.add("X");
			}
			
			// create a game
			ticTacToe.startNewGame(numPlayer, timeout);
			
			// create players
			ticTacToe.createPlayer(username.get(0), marker.get(0), 1);
			
			if (numPlayer == 1) {
				ticTacToe.setIsHumanPlayer(true);
				ticTacToe.createPlayer(username.get(1), marker.get(1), 2);
			}else {
				ticTacToe.setIsHumanPlayer(false);
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
		root.setCenter(ticTacToe.getGameDisplay());
		
		// show whose turn now
		turnLabel = new Label (username.get(0) + "'s turn to play.");
		Label timeLeft = new Label ("Time: ");
		VBox vBoxForGame = new VBox(20);
		vBoxForGame.getChildren().addAll(turnLabel, timeLeft);
		vBoxForGame.setAlignment(Pos.CENTER);
		root.setBottom(vBoxForGame);
		root.setPadding(new Insets(50));
		
		
	}
	
	
	
	
	
	
	
	
	
	
	public Scene getMainScene() {
		return this.scene;
	}
	
	/**
	 * The setup pane is where a user can give input
	 * for the initialization of the 2D array.
	 * 
	 * @return
	 */
	public GridPane buildSetupPane() {
	    Text sizeLabel = new Text("Number rows & columns:");  
	    Text defaultValLabel = new Text("Default value:");       
        TextField sizeTextField = new TextField();
        //TODO #1: Add a text field for a user to input a default value to init array       
        TextField defaultValueTextField = new TextField();
        
        Button button1 = new Button("Submit"); 
        button1.getStyleClass().add("submitButton");
        Line line = new Line();
        
        
        line.setStartX(0.0f); 
        line.setStartY(0.0f);         
        line.setEndX((float) windowWidth); 
        line.setEndY(0.0f);
        
        //Creating the mouse event handler 
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
           @Override 
           public void handle(MouseEvent e) { 
               String size = sizeTextField.getText();
               
               //TODO #2: Read the default input from the text field you created above
               String defaultVal = "-1";
               defaultVal = defaultValueTextField.getText();

               build2DArrayPane(size, defaultVal);
           } 
        };  
        //Registering the event filter 
        button1.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);   

        //Creating a Grid Pane 
        GridPane gridPane = new GridPane();    
        
        //Setting size for the pane 
        gridPane.setMinSize(windowWidth, (int) windowHeight/4); 
        
        //Setting the padding  
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        
        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);       
        
        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER); 
        
        gridPane.add(sizeLabel, 0, 0); 
        //TODO #3: Remove comment so that the label will show
        gridPane.add(defaultValLabel, 1, 0); 
        
        gridPane.add(sizeTextField, 0, 1); 
        
        //TODO #4: Add the text field for the default value
        gridPane.add(defaultValueTextField, 1, 1); 
        

        gridPane.add(button1, 2, 1); 
        gridPane.add(line, 0, 2, 3, 1); 
              
        return gridPane;
	} // end of buildSetupPane
	
	public void build2DArrayPane(String size, String defaultVal) {
		String text = "";
		//Clear other panes
		root.setLeft(new Text());
		root.setRight(new Text());
		
		try {
			int intSize =Integer.parseInt(size);
			int intDefaultVal = Integer.parseInt(defaultVal);
	       // twoDArr = new TwoDArray(intSize,intSize, intDefaultVal);
	       // text = twoDArr.getArrayDisplay();
	        System.out.println("Hello World " + intSize +", "+intDefaultVal); //this will print out on the command line, not the GUI
			root.setLeft(build2DArrayInputPane());
		}catch(NumberFormatException nfe) {
			text = "Please enter integer values!";
			
		}
		Text arrDisplay = new Text(text);
		arrDisplay.setId("twoDArray");
		
		MotionBlur mb= new MotionBlur();
		mb.setRadius(5.0f);
		mb.setAngle(15.0f);
		arrDisplay.setEffect(mb);
		
		//creating the rotation transformation 
		Rotate rotate= new Rotate(); 
		
		//Setting the angle for the rotation 
		rotate.setAngle(20); 
		arrDisplay.getTransforms().addAll(rotate);
		
		
		root.setCenter(arrDisplay);
	} // end of build2DArrayPane
	
	public GridPane build2DArrayInputPane() {
        Button button1 = new Button("Random Insert"); 
        
        //TODO #5: Create a mouse event handler to call updateArrayValue()
        //The following, indented code goes into the event handler
	        //At some point, you could use text fields to input the row, col and value
	        //updateArrayValue(rowTextField.getText(), colTextField.getText(), valTextField.getText());
	
	        //For now, we'll randomly generate values and pass them to the 
	        //updateArrayValue() function as Strings
	        /**
			 * The method floor() gives the largest integer 
			 * that is less than or equal to the argument.
			 */
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        	@Override
        	public void handle (MouseEvent e) {
        		int col = (int)Math.floor((Math.random() * 10));		
    			int row = (int)Math.floor((Math.random() * 10));
    			int val = (int)(Math.random() * 99);
    	        updateArrayValue(Integer.toString(row), Integer.toString(col), Integer.toString(val));
        	}
        };
			
        
        //Registering the event filter 
        //TODO #6: Remove comment
        button1.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);   

        //Creating a Grid Pane 
        GridPane gridPane = new GridPane();    
        
        //Setting size for the pane 
        gridPane.setMinSize(400, 200); 
        
        //Setting the padding  
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        
        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);       
        
        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER); 
               
        gridPane.add(button1, 0, 6); 
        gridPane.add(statusNode, 0, 8); 
        statusNode.setStyle("-fx-fill: grey;");
        
        return gridPane;

	} // end of build2DArrayInputPane
	
	public void updateArrayValue(String row, String col, String val) {
			String text = "";
			String status = "";
			try {
				int intRow =Integer.parseInt(row);
				int intCol = Integer.parseInt(col);
				int intVal = Integer.parseInt(val);
		        //status = twoDArr.insertInt(intRow, intCol, intVal);
		       // text = twoDArr.getArrayDisplay();
			}catch(NumberFormatException nfe) {
				text = "Please enter integer values!";
				
			}
								
			
			Text textNode = new Text(text);
			textNode.setId("twoDArray");
			textNode.setTextAlignment(TextAlignment.CENTER);
			
			RotateTransition rotateTransition= new RotateTransition(); 
			
			//Setting the duration for the transition 
			rotateTransition.setDuration(Duration.millis(1000)); 
			
			//Setting the node for the transition
			rotateTransition.setNode(textNode); 
			
			//Setting the angle of the rotation 
			rotateTransition.setByAngle(360); 
			
			//Setting the cycle count for the transition 
			rotateTransition.setCycleCount(5); 
			
			//Playing the animation 
			rotateTransition.play(); 
			
			root.setCenter(textNode);
			statusNode.setText(status);

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

