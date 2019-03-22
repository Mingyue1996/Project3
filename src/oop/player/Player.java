package oop.player;


public class Player {
	String username;
	String marker;
	int playerID;
	int newMoveRow = -1;
	int newMoveCol = -1;
	public Player() {
		
	}
	
	// return new row
	public int getNewMoveRow() {
		return newMoveRow;
	}
	
	//  return new column
	public int getNewMoveCol() {
		return newMoveCol;
	}
	
	public String getMarker() {
		return marker;
	}
	
	public void makeMove(int row, int col) {
		newMoveRow = row;
		newMoveCol = col;
	}
}