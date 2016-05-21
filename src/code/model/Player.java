package code.model;

import java.util.ArrayList;

/**
 * A class representing the individual players of the game. Each new paerson
 * must create a new Player object and register to play the game. Player objects
 * are used to store the unigue information for each player and to place and
 * retract tiles when it is their turn.
 * 
 * @author Mark
 * 
 */

public class Player {

	/** 
	 * _score - the integer score of the player
	 * 
	 * _name - the String name of the player 
	 * 
	 * _rack - the tile rack of the player representing an arraylist 
	 * 
	 * _gameWindow - the user interface that belongs to the player object which shows the board, the player's tiles, and scores
	 */
	private int _score;
	private String _name;
	private ArrayList<Tile> _rack;
	private GUI _gameWindow;
	
	/**
	 * A constructor for the Player class that sets the integer representing the
	 * player's score to zero and creates a new arraylist for the tile rack to
	 * be populated later.
	 */
	public Player() {
		_score = 0;
		_rack = new ArrayList<Tile>();
	}
	
	/**
	 * Mutator method to assign a GUI object to this player object
	 * 
	 * @param _gameWindow - the GUI that is to be assigned to this player
	 */
	public void setGameWindow(GUI gameWindow){
		_gameWindow = gameWindow;
	}
	
	/**
	 * Accessor method to retrieve the reference to the GUI object belonging to this player
	 * 
	 * @return _gameWindow - the GraphicalUserInterface that belongs to this player object
	 */
	public GUI getGameWindow(){
		return _gameWindow;
	}

	/**
	 * Accessor method to return the tile rack of this player object
	 * 
	 * @return returns the tiles the player has
	 */
	public ArrayList<Tile> getRack() {
		return _rack;
	}

	/**
	 * Void method to assign an arraylist that represents the tile rack to this
	 * player object
	 * 
	 * @param racks
	 *            sets the players tiles to the rack parameter
	 */
	public void setRack(ArrayList<Tile> rack) {
		_rack = rack;
	}

	/**
	 * Method to return the value of the player object's current score
	 * 
	 * @return _score returns the score of the player
	 */
	public int getScore() {
		return _score;
	}

	/**
	 * Method adds i to the player's current score
	 * 
	 * @param i  - an integer value to be added to the current score
	 * 
	 * @return the instance variable representing the score of the player
	 */
	public int setScore(int i) {
		_score = _score + i;
		return _score;
	}

	/**
	 * Accesses the String object that represents the player object's name.
	 * 
	 * @return _name returns the players name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Mutator method to assign the player object a name using the _name
	 * instance variable.
	 * 
	 * @param - name sets the players name to the value sent in
	 */
	public void setName(String name) {
		_name = name;
	}
	
	/**
	 * This method adds a tile that is passed in to the player's rack
	 * 
	 * @param tile - tile that is going to be added to the rack
	 */
	public void addTile(Tile tile){
		_rack.add(tile);
	}
}
