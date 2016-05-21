package code.model;

/**
 * A class for the tile objects that are to be placed into the square objects of the board.
 * A tile holds references to the letter it is assigned, value of the letter, the turn it was placed in the game,
 * and its position on the board
 * 
 * @author Mark and Kevin
 */
public class Tile {
	
	/**
	 * _letter - the letter character of the tile 
	 * 
	 * _value - the value of the letter character assigned
	 * 
	 * _row - the row of the board that the tile was placed onto
	 * 
	 * _column - the column of the board that the tile was placed onto
	 */
	private String _letter;
	private int _value;
	private int _row;
	private int _col;

	
	/**
	 * The constructor for the tile class creates composition relationships with the tile's letter character and the letter's value
	 * 
	 * @param letter - The letter assigned to the tile
	 * @param value - the value of the letter assigned
	 */
	public Tile(String letter, int value) {
		_letter = letter;
		_value = value;
	}

	
	/**
	 * Accessor method to retrieve the letter character of the tile.
	 * 
	 * @return - the letter character that the tile holds
	 */
	public String getLetter() {
		return _letter;
	}


	/**
	 * Accessor method to retrieve the value that the tile's letter represents.
	 * 
	 * @return - the integer representing the value of the tile's letter
	 */
	public int getValue() {
		return _value;
	}

	
	
	/**
	 * Mutator method to set the row of the board that the tile was placed onto.
	 * 
	 * @param i - the integer representing the row of the double array in which the tile is placed
	 */
	public void setRow(int i) {
		_row = i;
	}

	
	/**
	 * Accessor to retrieve the row that the tile is placed on.
	 * 
	 * @return - the integer representing the row of the board on which the tile is placed
	 */
	public int getRow() {
		return _row;
	}

	
	/**
	 * Mutator method to set the column of the board that the tile was placed onto.
	 * 
	 * @param i - the integer representing the column of the double array in which the tile is placed
	 */
	public void setCol(int i) {
		_col = i;
	}

	
	/**
	 * Accessor to retrieve the column that the tile is placed on.
	 * 
	 * @return - the integer representing the column of the board on which the tile is placed
	 */
	public int getCol() {
		return _col;
	}

}
