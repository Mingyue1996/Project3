package oop.board;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import oop.board.square.Square;

public class BasicGameBoard {
	private boolean hasWon = true;
	private Square[][] basicTwoD = new Square[3][3];
	
	// create a pane to hold squares
	GridPane gridPane = new GridPane();
	
	private String marker1, marker2;
	private int boardFormat = 0;
	// constructor
	public BasicGameBoard() {
		int id = 0;
		// initialize basicTwoD
		for (int i = 0; i < 3; i ++) {
			for (int j = 0; j < 3; j++, id++) {
				gridPane.add(basicTwoD[i][j] = new Square(id, "   "),j,i);
			}
		}
		 //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER); 
	} // end of constructor
	
	
	// reset the board
	public void reset() {
		int id = 0;
		gridPane.getChildren().clear();
		for (int i = 0; i < 3; i ++) {
			for (int j = 0; j < 3; j++) {
				gridPane.add(basicTwoD[i][j] = new Square(id, "   "),j,i);
			}
		}
		 //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER); 
	}
	
	// display
	public Pane display() {
		return gridPane;
//		String boardDisplay = "";
//		for (int num = 0; num <boardFormat * 3 + 4; num++) {
//			if (num % (boardFormat + 1) == 0) {
//				boardDisplay += String.format("+");
//			}
//			else {
//				boardDisplay += String.format("=");
//			}
//			
//		}
//		boardDisplay += String.format("\n");	
//		boardDisplay += "|";
//			for (int i = 0; i < 3; i ++) {
//				for (int j = 0; j < 3; j++) {
//					
//					boardDisplay += String.format("%-" + boardFormat + "s", basicTwoD[i][j].display());	
//					boardDisplay += "|";
//				}
//				boardDisplay += String.format("\n");
//				for (int num = 0; num < boardFormat * 3 + 4; num++) {
//					if (num % (boardFormat + 1) == 0) {
//						boardDisplay += String.format("+");
//					}
//					else {
//						boardDisplay += String.format("=");
//					}
//					
//				}
//				//boardDisplay += String.format("\n");
//				
//				if (i+1 <3)
//					boardDisplay += "\n|";
//				else
//					boardDisplay += "\n";
//			}
//			return boardDisplay;
	}
	
	// mark the board
	public boolean markBoard(int row, int col, String marker) {
		if((!basicTwoD[row][col].getIsMarked())) {
			basicTwoD[row][col].setMarker(marker, false);
			return true;
		}
		return false;
		
	}
	
	// check if there is at least an empty space in the board
	public boolean isEmptySpaceAvailable() {
		
		for (int i = 0; i < 3; i ++) {
			for (int j = 0; j < 3; j++) {
				if (!basicTwoD[i][j].getIsMarked()) {
					return true;
				}
			}
		}
		return false;
	}
	
	// check if there is a winner
	public boolean hasWon(int row, int col, String marker) {
//			hasWon = true;
//			// check row
//	        for(int i=0; i<3; i++){
//	            if(!(basicTwoD[row][i].display()).equals(marker)){
//	            	hasWon = false;
//	            }
//	        }
//	        
//	        if (hasWon) {
//	        	return hasWon;
//	        }
//	        
//	        hasWon = true;
//        	// check column
//	        for(int i=0; i<3; i++){
//	            if(!(basicTwoD[i][col].display()).equals(marker)){
//	            	hasWon = false;
//	            }
//	        }
//		    
//		    if (hasWon) return hasWon;
//		    
//		    hasWon = true;
//            // check back diagonal
//	        for(int i=0; i<3; i++){
//	        	if(!(basicTwoD[i][i].display()).equals(marker)){		        		
//	        		hasWon = false;
//	        	}
//	        }
//
//	        if (hasWon) return hasWon;
//	        
//	        hasWon = true;
//            // check forward diagonal
//	        for(int i=0, j=2; i<3; i++, j--){
//	        	if(!(basicTwoD[i][j].display()).equals(marker)){
//	        		hasWon = false;	
//	        	}
//	        }
	    
			// check if three same markers are in one row
			for (int i = 0; i < 3; i++) {
				if(basicTwoD[i][0].display().equals(marker)
				   && basicTwoD[i][1].display().equals(marker)
				   && basicTwoD[i][2].display().equals(marker)) {
					return true;
				}
			}
			
			// check if three same markers are in one column
			for (int j = 0; j < 3; j++) {
				if(basicTwoD[0][j].display().equals(marker)
				   && basicTwoD[1][j].display().equals(marker)
				   && basicTwoD[2][j].display().equals(marker)) {
					return true;
				}
			}
			
			//check if forward diagonal has three same markers
				if(basicTwoD[0][2].display().equals(marker)
				   && basicTwoD[1][1].display().equals(marker)
				   && basicTwoD[2][0].display().equals(marker)) {
					return true;
			}
		
				
			//check if forward diagonal has three same markers
			if(basicTwoD[0][0].display().equals(marker)
			   && basicTwoD[1][1].display().equals(marker)
			   && basicTwoD[2][2].display().equals(marker)) {
				return true;
			}
		
	        return false;
	} // end of hasWon
	
	// set marker
	public void setMarker (String marker, int id) {
		if (id == 1) {
			marker1 = marker;
		}
		else {
			marker2 = marker;
			
			// compare which marker contains more characters
			if (marker1.length() >= marker2.length()) {
				boardFormat = 2 + marker1.length();
			}
			else {
				boardFormat = 2 + marker2.length();
			}
		}
	} // end of setMarker
	

}