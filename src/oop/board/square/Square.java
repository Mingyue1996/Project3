package oop.board.square;

public class Square {
	private int squareID;
	private String marker;
	private boolean isMarked = false;
	
	public Square (int id, String marker) {
		squareID = id;
		this.marker = marker;
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
}