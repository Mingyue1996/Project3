package oop.board;

import oop.board.square.Square;

public class BasicGameBoard {
	private boolean hasWon = true;
	private Square[][] basicTwoD = new Square[3][3];
	private String marker1, marker2;
	private int boardFormat = 0;
	// constructor
	public BasicGameBoard() {
		int id = 0;
		// initialize basicTwoD
		for (int i = 0; i < 3; i ++) {
			for (int j = 0; j < 3; j++, id++) {
				basicTwoD[i][j] = new Square(id, "   ");
			}
		}
		 
	} // end of constructor
	
	
	// reset the board
	public void reset() {
		for (int i = 0; i < 3; i ++) {
			for (int j = 0; j < 3; j++) {
				basicTwoD[i][j].setMarker("   ", true);
			}
		}
	}
	
	// display
	public String display() {
		String boardDisplay = "";
		for (int num = 0; num <boardFormat * 3 + 4; num++) {
			if (num % (boardFormat + 1) == 0) {
				boardDisplay += String.format("+");
			}
			else {
				boardDisplay += String.format("=");
			}
			
		}
		boardDisplay += String.format("\n");	
		boardDisplay += "|";
			for (int i = 0; i < 3; i ++) {
				for (int j = 0; j < 3; j++) {
					
					boardDisplay += String.format("%-" + boardFormat + "s", basicTwoD[i][j].display());	
					boardDisplay += "|";
				}
				boardDisplay += String.format("\n");
				for (int num = 0; num < boardFormat * 3 + 4; num++) {
					if (num % (boardFormat + 1) == 0) {
						boardDisplay += String.format("+");
					}
					else {
						boardDisplay += String.format("=");
					}
					
				}
				//boardDisplay += String.format("\n");
				
				if (i+1 <3)
					boardDisplay += "\n|";
				else
					boardDisplay += "\n";
			}
			return boardDisplay;
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
			hasWon = true;
			// check row
	        for(int i=0; i<3; i++){
	            if(!(basicTwoD[row][i].display()).equals(marker)){
	            	hasWon = false;
	            }
	        }
	        
	        if (hasWon) {
	        	return hasWon;
	        }
	        
	        hasWon = true;
        	// check column
	        for(int i=0; i<3; i++){
	            if(!(basicTwoD[i][col].display()).equals(marker)){
	            	hasWon = false;
	            }
	        }
		    
		    if (hasWon) return hasWon;
		    
		    hasWon = true;
            // check back diagonal
	        for(int i=0; i<3; i++){
	        	if(!(basicTwoD[i][i].display()).equals(marker)){		        		
	        		hasWon = false;
	        	}
	        }

	        if (hasWon) return hasWon;
	        
	        hasWon = true;
            // check forward diagonal
	        for(int i=0, j=2; i<3; i++, j--){
	        	if(!(basicTwoD[i][j].display()).equals(marker)){
	        		hasWon = false;	
	        	}
	        }
	    
		    
	        return hasWon;
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