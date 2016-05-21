package code.model;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;

/**
 * A class for the Square object. Squares are what make up the board for the game and either contain the 
 * letter tiles or contain no tile to show that a new tile can be placed onto it.
 * 
 * @author Mark and Kevin
 *
 */
public class Square {
	
	/**
	 * _tile - variable assigned to the tile that is placed in the square
	 * 
	 * _letterMultiplier - integer representing the bonus multiplier for a tile placed on this square
	 * 
	 * _wordMultiplier - integer representing the bonus multiplier for the word formed with a tile placed on this square
	 * 
	 * _button - the JButton representing the Square object in the user interface
	 */
	private Tile _tile;
	private int _letterMultiplier;
	private int _wordMultiplier;
	private JButton _button;

	/**
	 * The constructor establishes an association relationship with a tile object, and selects the letter and word multipliers.
	 * This is done by creating arraylists and filling them with the appropriate number of integers to represent the weights for
	 * each option, and then shuffling the list and selecting the new first integer in the list.
	 * 
	 * @param tile - the tile object that is placed in the square upon creation of the square.
	 */
	public Square(Tile tile) {
		_tile = tile;
		
		ArrayList<Integer> letterMultiple = new ArrayList<Integer>();
		for(int i = 0; i < 80; i++){
			letterMultiple.add(1);
			if(i < 10){
				letterMultiple.add(2);
			}
			if(i < 5){
				letterMultiple.add(0);
				letterMultiple.add(-1);
			}
		}
		Collections.shuffle(letterMultiple);
		_letterMultiplier = (Integer) letterMultiple.get(0);
		
		ArrayList<Integer> wordMultiple = new ArrayList<Integer>();
		for(int i = 0; i < 75; i++){
			wordMultiple.add(1);
			if(i < 15){
				wordMultiple.add(2);
			}
			if(i < 5){
				wordMultiple.add(0);
				wordMultiple.add(3);
			}
		}
		Collections.shuffle(wordMultiple);
		_wordMultiplier = (Integer) wordMultiple.get(0);
	}

	
	/**
	 * Mutator method to assign a new tile object to the square while also setting the text of the Square's JButton
	 * to the appropriate letter from the tile
	 * 
	 * @param tile - the new tile object that is to be placed into the square
	 */
	public void setTile(Tile tile) {
		_tile = tile;
		if(tile == null){
			_button.setText("");
		}
		else{
			_button.setText(tile.getLetter());
			
		}
	}

	
	/**
	 * accesses the tile object contained within the square
	 * 
	 * @return - the tile object that the square holds a reference to
	 */
	public Tile getTile() {
		return _tile;
	}
	
	/**
	 * Accessor method to return the letter multiplier for a tile placed on this square
	 * 
	 * @return the integer representing the word multiplier of this square
	 */
	public int getLetterMultiplier(){
		return _letterMultiplier;
	}
	
	/**
	 * Accessor method to return the word multiplier for the word formed from a tile placed on this square
	 * 
	 * @return an integer representing the word multiplier for this square
	 */
	public int getWordMultiplier(){
		return _wordMultiplier;
	}
	
	
	/**
	 * This method sets _letterMultiplier to letterMultiplier
	 * 
	 *  @param letterMultiplier - what to set _letterMultiplier to
	 */
	public void setLetterMultiplier(int letterMultiplier){
		 _letterMultiplier = letterMultiplier;
	}
	
	/**
	 * This method sets _wordMultiplier to wordMultiplier
	 * 
	 *  @param wordMultiplier - what to set _wordMultiplier to
	 */
	public void setWordMultiplier(int wordMultiplier){
		 _wordMultiplier = wordMultiplier;
	}
	
	/**
	 * Mutator method to assign a JButton to this square object
	 * 
	 * @param button the button to be assigned to this square
	 */
	public void setButton(JButton button){
		_button = button;
	}
	
	/**
	 * Accessor method to retrieve the JButton assigned to this square
	 * 
	 * @return The square object's assigned JButton
	 */
	public JButton getButton(){
		return _button;
	}
	
	/**
	 * Method to check whether the square object contains a tile
	 * 
	 * @return true if it holds a tile, and false if it does not
	 */
	public boolean hasTile(){
		return (_tile!=null);
	}

}
