package code.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import code.interfaces.IBoard;

/**
 * This class is for the Board object which represents the actual board on which
 * the "Scrabble" game is played. It implements the IGame interface, holds
 * references to many variables and contains many methods used for various
 * purposes. Largely anything to do with tile interaction is written in this
 * class
 * 
 * @author Mark, Kevin, and Alex
 * 
 */
public class Board implements IBoard {

	/**
	 * _board - the 2D array of square objects which is meant to represent the
	 * board on which the game is played
	 * 
	 * _placedTiles - Array List which holds tiles that were placed in the
	 * current turn
	 * 
	 * HSrow - the integer representing the row on which the home square was
	 * made 
	 * 
	 * HScol - the integer representing the column on which the home square
	 * was made
	 * 
	 * _homeSquare - the square object which was chosen to become the homesquare
	 * for the start of the game
	 * 
	 * _game - the game object in which the board is interacting with
	 * 
	 * randomizer - the random object which is used to pick a homesquare
	 * 
	 * _lastPlacedTileRow - an integer for the row in which the last tile was
	 * placed
	 * 
	 * _lastPalcedTileCol - an integer for the column in which the last tile was
	 * placed
	 * 
	 * _BR - is the Buffered Reader used to read the dictionary text file
	 * 
	 * _formedWords - Array List of all the words formed during a turn
	 * 
	 * _lastTurnWordPoints - Array List which holds the SimpleImutableEntry of
	 * both the words and points associated with them calculated in the last
	 * turn
	 * 
	 * _dictionary - the arraylist that will contain all of the words in the given dictionary text file
	 * so it can be easily checked to see if a word is valid 
	 */
	private Square[][] _board;
	private ArrayList<Tile> _placedTiles;
	public int HSrow;
	public int HScol;
	private Square _homeSquare;
	private Random randomizer;
	private int _lastPlacedTileRow;
	private int _lastPlacedTileCol;
	private BufferedReader _BR;
	private ArrayList<ArrayList <Tile>> _formedWords;
	private ArrayList<SimpleImmutableEntry<String, Integer>> _lastTurnWordPoints;
	public ArrayList<String> _dictionary;

	


	
	/**
	 * The Board's real constructor which creates a association relationship
	 * with the game object which created the board and instantiates the new
	 * random, 2D array of squares, and arraylist of tiles placed during a turn.
	 * It also assigns all of the squares of the 2D array to square objects with
	 * a null reference to a tile, sets the upper right and lower left corners
	 * of the board to null according to the random sizes determined in the body,
	 * creates an arraylist to hold all of the words in the given dictionary and
	 * calls the create homesquare method.
	 * 
	 * @param g
	 *            - the game object used in conjunction with the board during gameplay 
	 */
	public Board(String fileName) {
		_board = new Square[20][20];
		randomizer = new Random();
		_placedTiles = new ArrayList<Tile>();
		_dictionary = new ArrayList<String>();

		//all of these try and catch statements are for the formation of the dictionary. They are needed for a buffered reader
		try {
			String word = "";
			_BR = new BufferedReader(new FileReader(fileName));
			while ((word = _BR.readLine()) != null) {
				word = word.toUpperCase();
				_dictionary.add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (_BR != null)_BR.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}


		for(int row = 0; row < _board.length; row++){		//puts squares into the board array
			for(int col = 0; col < _board[row].length; col++){
				_board[row][col] = new Square(null);
			}
		}
		//all of this just establishes random numbers between and including 3 and 7
		//to create random dimensions for the blank corners
		ArrayList<Integer> blankCornerIntegers = new ArrayList<Integer>(); 
		blankCornerIntegers.add(3);						
		blankCornerIntegers.add(4);
		blankCornerIntegers.add(5);
		blankCornerIntegers.add(6);
		blankCornerIntegers.add(7);
		Collections.shuffle(blankCornerIntegers);
		Integer blankRow = (Integer) blankCornerIntegers.get(0);
		Collections.shuffle(blankCornerIntegers);
		Integer blankCol = (Integer) blankCornerIntegers.get(0);

		for(int row = 0; row < blankRow; row++){				//this blanks out the top right corner
			for(int col = (20-blankCol); col < _board[row].length; col++){
				_board[row][col] = null;
			}
		}

		for(int row = (20-blankRow); row < _board.length; row++){  //this blanks out the bottom left corner
			for(int col = 0; col < blankCol; col++){
				_board[row][col] = null;
			}
		}
		makeHomeSquare();

	}

	
		/**
		 * Accessor for the integer variable representing the number of tile
		 * placements thus far during a turn.
		 * 
		 * @return - the integer representing the size of the array list that holds
		 *         tiles placed during the turn
		 */
		public int numberOfTilesPlaced() {
			return _placedTiles.size();
		}

		
		/**
		 * Method that selects a square on the newly created board to be the
		 * homesquare for the first turn of the game. It uses a random object to
		 * select random indexes of the double array and sets the _homesquare
		 * variable to that row and column. If it is found that the selected
		 * position is in one of the out of bounds corners then it runs the method
		 * again until it is a valid square and then returns true.
		 * 
		 * @return - true when the selected homesquare is a valid square on the
		 *         board
		 */
		public boolean makeHomeSquare() {
			do {
				HSrow = randomizer.nextInt(_board[0].length);
				HScol = randomizer.nextInt(_board.length);
				_homeSquare = _board[HSrow][HScol];
			} while (_homeSquare == null);
			return true;
		}

		
		/**
		 * Accessor method to retrieve the homesquare's row. Starting with the top
		 * most row being row 1 and the lowest row being 20.
		 * 
		 * @return - the row of the homesquare in the 2D array plus one since the
		 *         rows are labeled on a 1-20 index instead of 0-19
		 */
		public int homeSquareRow() {
			return HSrow + 1;
		}

		
		/**
		 * Accessor method to retrieve the homesquare's column. Starting with the
		 * left most column being row 1 and the far right column being 20.
		 * 
		 * @return - the column of the homesquare in the 2D array plus one since the
		 *         rows are labeled on a 1-20 index instead of 0-19
		 */
		public int homeSquareCol() {
			return HScol + 1;
		}

		
		/**
		 * Acessor method to get the tile object at the given row and column.
		 * 
		 * @param row
		 *            - the row of the tile that needs to be returned, represented
		 *            by an integer
		 * @param col
		 *            - the column of the tile that needs to be returned,
		 *            represented by an integer
		 * 
		 * @return null if the queried position is in one of the out of bounds
		 *         corners or off the board completely. True otherwise, including if
		 *         the tile is null. It would just return null in that case.
		 */
		public Tile get(int row, int col) {
			if (row > 19 || col > 19) {
				return null;
			}
			if (_board[row][col] == null) {
				return null;
			}
			return _board[row][col].getTile();
		}

		
		/**
		 * Method to set the newly placed tile's new row and column positions, which
		 * are represented by integers, to the appropriate instance variables
		 * 
		 * @param row
		 *            - the new row that the tile was placed on, represented by an
		 *            integer
		 * @param col
		 *            - the new column that the tile was placed on, represented by
		 *            an integer
		 * @return true when the assignments are successful
		 */
		public boolean setLastPlacedTilePosition(int row, int col) {
			_lastPlacedTileRow = row;
			_lastPlacedTileCol = col;
			return true;
		}

		
		/**
		 * Accessor method to retrieve the row of the last placed tile.
		 * 
		 * @return the integer instance variable representing the last placed tile's
		 *         row
		 */
		public int lastPlacedTileRow() {
			return _lastPlacedTileRow;
		}

		
		/**
		 * Accessor method to retrieve the column of the last placed tile.
		 * 
		 * @return the integer instance variable representing the last placed tile's
		 *         column
		 */
		public int lastPlacedTileCol() {
			return _lastPlacedTileCol;
		}

		
		/**
		 * Method to place the given tile onto the given position on the board.
		 * Checks to see if the position wanted is on the board, and not occupied
		 * before placing the tile
		 * 
		 * @param tile
		 *            - the tile to be placed
		 * @param row
		 *            - the row of the position that the tile needs to be placed on,
		 *            represented by an integer
		 * @param col
		 *            - the column of the position that the tile needs to be placed
		 *            on, represented by an integer
		 * @return - false if the position is off the board or if the spot is
		 *         occupied. True if the position was successful.
		 */
		public boolean place(Tile tile, int row, int col) {
			if(tile == null){
				return false;
			}
			if (row > 19 || col > 19) {
				return false;
			}
			if (isEmpty(row, col)) {
				tile.setRow(row);
				tile.setCol(col);
				_placedTiles.add(tile);
				setLastPlacedTilePosition(row, col);
				_board[row][col].setTile(tile);
				return true;
			}
			return false;
		}

		
		/**
		 * Method to remove a given tile from it's spot on the board if the tile is
		 * valid.
		 * 
		 * @param tile
		 *            - the tile that needs to be removed from the board
		 * @return false if the tile is null, true if the tile was valid and was
		 *         successfully removed from the board.
		 * 
		 */
		public boolean retract(Tile tile) {
			if (tile == null) {
				return false;
			} 
			if(_placedTiles.contains(tile)){
				_placedTiles.remove(_board[tile.getRow()][tile.getCol()].getTile());
				_board[tile.getRow()][tile.getCol()].setTile(null);
				return true;
			}
			return false;
		}

		
		/**
		 * Method to check if a given position on the board is empty, meaning that
		 * it does not contain a tile.
		 * 
		 * @param row
		 *            - the row of the position being checked, represented by an
		 *            integer
		 * @param col
		 *            - the column of the position being checked, represented by an
		 *            integer
		 * @return false if the position is occupied or off the board. True if the
		 *         position is a square containing no tile
		 */
		public boolean isEmpty(int row, int col) {
			if (_board[row][col] == null) {
				return false;
			}
			if (_board[row][col].getTile() == null) {
				return true;
			}
			return false;
		}

		
		/**
		 * Method to create a of the board at any given time into a string object.
		 * It will be in same shape and size as the board, have '_' for empty spots
		 * and the appropriate letter in the correct positions.
		 * 
		 * 
		 * @author Mark Starczewski Jr.
		 * @return A string representation of the board
		 */
		public String boardConfiguration() {
			String S = "";
			for (int row = 0; row < _board.length; row = row + 1) {
				for (int col = 0; col < _board[row].length; col = col + 1) {
					if (_board[row][col] == null) {
						S = S + " ";
					} else if (_board[row][col].getTile() == null) {
						S = S + "_";
					} else {
						S = S + _board[row][col].getTile().getLetter();
					}
				}
				S = S + System.getProperty("line.separator");
			}
			return S;
		}

		
		/**
		 * This method first checks to make sure that the homesquare actually has a
		 * value in it if it does,then it checks to make sure that each of the
		 * placed tiles has at least one other tile next to it
		 * 
		 * @author Kevin Kijanka
		 * 
		 * @return false if the homesquare is empty or if one of the placed tiles
		 *         does not have a tile somewhere next to it or if the tiles placed
		 *         are not in the same row or the same column
		 */
		public boolean legality(int turn) {

			// check to make sure the home square has a tile placed on it
			if (_homeSquare.getTile() == null) {
				return false;
			}

			// check to make sure there was at least one tile placed next to all of
			// tiles placed
			if(turn != 0){
				if(!connected()){
					System.out.println("All Tiles must be connected");
					return false;
				}
			}
			if(!placedTilesInLine()){
				return false;
			}
			if(placedTilesInCol()){
				System.out.println("Tiles Placed in same Col");
				int highRow = -1;
				int lowRow = 100;
				int col = _placedTiles.get(0).getCol();
				for(int i=0; i<_placedTiles.size(); i++){
					if(_placedTiles.get(i).getRow()<lowRow){
						lowRow = _placedTiles.get(i).getRow();
					}
					if(_placedTiles.get(i).getRow()>highRow){
						highRow = _placedTiles.get(i).getRow();
					}
				}
				
				for(; lowRow<=highRow; lowRow++){
					if(_board[lowRow][col].getTile()==null){
						System.out.println("There is a space in col of Placed Tiles");
						return false;
					}
				}
			}
			else{
				System.out.println("Tiles Placed in same Row");
				int highCol = -1;
				int lowCol = 100;
				int row = _placedTiles.get(0).getRow();
				for(int i=0; i<_placedTiles.size(); i++){
					System.out.println(_placedTiles.get(i).getLetter());
					if(_placedTiles.get(i).getCol()<lowCol){
						lowCol = _placedTiles.get(i).getCol();
					}
					if(_placedTiles.get(i).getCol()>highCol){
						highCol = _placedTiles.get(i).getCol();
					}
				}
				System.out.println("Low Col " + lowCol);
				System.out.println("High Col " + highCol);
				for(; lowCol<=highCol; lowCol++){
					if(_board[row][lowCol].getTile()==null){
						System.out.println("There is a space in row of Placed Tiles");
						return false;
					}
				}
			}
			return true;
			
		}
		
		/**
		 * Checks to determine if the tiles placed are all in the same column
		 * 
		 * @author Kevin Kijanka
		 * 
		 * @return true if they are in the same column and false if they are not
		 */
		public boolean placedTilesInCol(){
			for(int i=0; i<_placedTiles.size()-1; i++){
				Tile tempTile = _placedTiles.get(i);
				Tile nextTempTile = _placedTiles.get(i+1);
				
				if(tempTile.getCol() != nextTempTile.getCol()){
					return false;
				}
			}
			return true;
		}
		
		/**
		 * Checks to make sure that at least one of the placed tiles is next
		 * to a tile that was not placed during the turn
		 * 
		 * @author Kevin Kijanka
		 * 
		 * @return false if none of the placed tiles are connected to tiles 
		 * that were not placed during the turn	
		 * true if at least one of the placed tiles is connected to one that
		 * was not placed during the turn
		 */
		public boolean connected(){
			boolean connected = false;
			for (int i = 0; i < _placedTiles.size(); i++) {
				
				Tile tempTile = _placedTiles.get(i);
				int tempTileRow = tempTile.getRow();
				int tempTileCol = tempTile.getCol();
				
				if(above(tempTile)){
					if(_placedTiles.contains(_board[tempTileRow-1][tempTileCol].getTile())==false){
						connected = true;
					}
				}
				
				if(below(tempTile)){
					if(_placedTiles.contains(_board[tempTileRow+1][tempTileCol].getTile())==false){
						connected = true;
					}
				}
				
				if(right(tempTile)){
					if(_placedTiles.contains(_board[tempTileRow][tempTileCol+1].getTile())==false){
						connected = true;
					}
				}
				
				if(left(tempTile)){
					if(_placedTiles.contains(_board[tempTileRow][tempTileCol-1].getTile())==false){
						connected = true;
					}
				}
			}
			return connected;
		}

		
		/**
		 * This method checks to make sure that the tiles placed during the turn
		 * are in the same row or the same column
		 * 
		 * @author Kevin Kijanka
		 * 
		 * @return false if the placed tiles are not in the same row or column and
		 * true if they are in the same row or column
		 */
		public boolean placedTilesInLine(){
			boolean tilesInaLine = true;
			
			for(int i=0; i<_placedTiles.size()-1; i++){
				Tile tempTile = _placedTiles.get(i);
				Tile nextTempTile = _placedTiles.get(i+1);
				
				if(tempTile.getCol() != nextTempTile.getCol()){
					tilesInaLine = false;
				}
			}
			if(tilesInaLine == true){
				return true;
			}
			
			tilesInaLine = true;
			for(int i=0; i<_placedTiles.size()-1; i++){
				Tile tempTile = _placedTiles.get(i);
				Tile nextTempTile = _placedTiles.get(i+1);
				
				if(tempTile.getRow() != nextTempTile.getRow()){
					tilesInaLine = false;
				}
			}
			if(tilesInaLine == true){
				return true;
			}
			return false;
		}

		
		/**
		 * This method looks at each tile that were placed and creates a list of the
		 * words that were formed from them
		 * 
		 * @author Kevin Kijanka
		 */
		public void findWordsFormed() {

			_formedWords = new ArrayList<ArrayList <Tile>>();
			ArrayList<Tile> tileForWords = new ArrayList<Tile>();
			
			// go through all placed tiles and get words that surround them
			for (int i = 0; i < _placedTiles.size(); i++) {
				Tile tempTile = _placedTiles.get(i);
				System.out.println("Looking at " + tempTile.getLetter());
				if (above(tempTile) == true) {
					System.out.println("tile above " + tempTile.getLetter());
					// if there is a tile above the temp tile, we must go to the
					// highest tile
					// to start to form the word
					while (above(tempTile) == true) {
						tempTile = _board[tempTile.getRow() - 1][tempTile.getCol()]
								.getTile();
					}
					// once at the top we have to go down and add the letters along
					// the way
					// until we reach the bottom tile
					
					while (below(tempTile) == true) {
						tileForWords.add(tempTile);
						tempTile = _board[tempTile.getRow() + 1][tempTile.getCol()]
								.getTile();
					}
					tileForWords.add(tempTile);
					// add the word to the array list if not already in it
					// and reset temp variables
					if (_formedWords.contains(tileForWords) == false) {
						_formedWords.add(tileForWords);
					}
					tempTile = _placedTiles.get(i);
					tileForWords = new ArrayList<Tile>();
				}
				// if there is not a tile above and there is one below, we have to
				// go down
				// and form the word along the way until we reach the bottom tile
				else{
					if (below(tempTile) == true) {
						System.out.println("tile below " + tempTile.getLetter());
						while (below(tempTile) == true) {
							tileForWords.add(tempTile);
							tempTile = _board[tempTile.getRow() + 1][tempTile.getCol()]
									.getTile();
						}
						tileForWords.add(tempTile);
						// add the word to the array list if not already in it
						// and reset temp variables
						if (_formedWords.contains(tileForWords) == false) {
							_formedWords.add(tileForWords);
						}
						tempTile = _placedTiles.get(i);
						tileForWords = new ArrayList<Tile>();
					}
				}

				if (left(tempTile) == true) {
					System.out.println("tile left " + tempTile.getLetter());
					// if there is a tile to left of the temp tile, we must go to
					// the
					// far left tile to start to form the word
					while (left(tempTile) == true) {
						tempTile = _board[tempTile.getRow()][tempTile.getCol() - 1]
								.getTile();
					}
					// once we are at the farthest left, we must begin to form the
					// word by
					// adding the letters until we are at the far right
					while (right(tempTile) == true) {
						tileForWords.add(tempTile);
						tempTile = _board[tempTile.getRow()][tempTile.getCol() + 1]
								.getTile();
					}
					tileForWords.add(tempTile);
					// add the word to the array list if not already in it
					// and reset temp variables
					if (_formedWords.contains(tileForWords) == false) {
						_formedWords.add(tileForWords);
					}
					tempTile = _placedTiles.get(i);
					tileForWords = new ArrayList<Tile>();
				}
				// if there is not a tile to the left and there is one to the right,
				// we have to go to the far right and form the word along the way
				// until we
				// reach the far right tile
				else{
					if (right(tempTile) == true) {
						System.out.println("tile right " + tempTile.getLetter());
						while (right(tempTile) == true) {
							tileForWords.add(tempTile);
							tempTile = _board[tempTile.getRow()][tempTile.getCol() + 1]
									.getTile();
						}
						tileForWords.add(tempTile);
						// add the word to the array list and reset temp variables
						if (_formedWords.contains(tileForWords) == false) {
							_formedWords.add(tileForWords);
						}
						tempTile = _placedTiles.get(i);
						tileForWords = new ArrayList<Tile>();
					}
				}

				// wash and repeat until all placed tiles have been run through

			}
		}

		
		/**
		 * This method checks if there is a tile above the tile sent in the first
		 * statement checks if the tile is along the top border so we don't get an
		 * out of bounds error, the second if statement checks to make sure that the
		 * square above is not one that doesn't allow any tiles, and finally the
		 * third if statement makes sure that there is a tile in the square above
		 * 
		 * @param tile
		 *            - the tile object that the method will check to see if there
		 *            is anything above it
		 * 
		 * @return false if there is not a tile above and true if there is
		 */
		public boolean above(Tile tile) {
			int row = tile.getRow();
			int col = tile.getCol();
			if ((row - 1) >= 0) {
				if ((isEmpty(row - 1, col) == false)&& (_board[row-1][col] != null)) {
					return true;
				}
			}
			return false;
		}

		
		/**
		 * This method checks if there is a tile below the tile sent in the first
		 * statement checks if the tile is along the bottom border so we don't get
		 * an out of bounds error, the second if statement checks to make sure that
		 * the square below is not one that doesn't allow any tiles, and finally the
		 * third if statement makes sure that there is a tile in the square below
		 * 
		 * @param tile
		 *            - the tile object that the method will check to see if there
		 *            is anything below it
		 * 
		 * @return false if there is not a tile below and true if there is
		 */
		public boolean below(Tile tile) {

			int row = tile.getRow();
			int col = tile.getCol();
			if ((row + 1) < _board.length) {
				if ((isEmpty(row + 1, col) == false)&& (_board[row+1][col] != null)) {
					return true;
				}
			}
			return false;
		}

		
		/**
		 * This method checks if there is a tile left of the tile sent in the first
		 * statement checks if the tile is along the left border so we don't get an
		 * out of bounds error, the second if statement checks to make sure that the
		 * square to the left is not one that doesn't allow any tiles, and finally
		 * the third if statement makes sure that there is a tile in the square to
		 * the left
		 * 
		 * @param tile
		 *            - the tile object that the method will check to see if there ]
		 *            is anything to the left of it
		 * 
		 * @return false if there is not a tile to the left and true if there is
		 */
		public boolean left(Tile tile) {

			int row = tile.getRow();
			int col = tile.getCol();
			if ((col - 1) >=0) {
				if ( (isEmpty(row, col - 1) == false) && (_board[row][col-1] != null)) {
					return true;
				}
			}
			return false;
		}

		
		/**
		 * This method checks if there is a tile right of the tile sent in the first
		 * statement checks if the tile is along the right border so we don't get an
		 * out of bounds error, the second if statement checks to make sure that the
		 * square to the right is not one that doesn't allow any tiles, and finally
		 * the third if statement makes sure that there is a tile in the square to
		 * the right
		 * 
		 * @param tile
		 *            - the tile object that the method will check to see if there
		 *            is anything to the right of it
		 * 
		 * @return false if there is not a tile to the right and true if there is
		 */
		public boolean right(Tile tile) {

			int row = tile.getRow();
			int col = tile.getCol();
			if ((col + 1) < _board[0].length) {
				if ((isEmpty(row, col + 1) == false)&& (_board[row][col+1] != null)) {
					return true;
				}
			}
			return false;
		}

		
		/**
		 * Method to create the total number of points of a given word, represented
		 * by an integer. All vowels except for 'Y' get 1 point, 'Y' gets 2 points,
		 * and all other letters get 5 points, adds the words and points to a
		 * SimpleImmutableEntry and then adds the event to the array list
		 * 
		 * @author Kevin
		 */
		public void getWordPoints() {

			_lastTurnWordPoints = new ArrayList<SimpleImmutableEntry<String, Integer>>();

			// goes through each word in the list
			for (int i = 0; i < _formedWords.size(); i++) {
				int wordPoints = 0;
				ArrayList<Tile> tiles = _formedWords.get(i);
				String word = "";
				for(int tile=0; tile<tiles.size(); tile++){
					word = word + tiles.get(tile).getLetter(); 
				}
				// goes through the letters of each word and adds points
				for (int tile = 0; tile < tiles.size(); tile++) {
					Tile tempTile = tiles.get(tile);
					int row = tempTile.getRow();
					int col = tempTile.getCol();
					
					System.out.println(tempTile.getLetter() + " has points of " + tempTile.getValue() 
							+ " times multiplier " + _board[row][col].getLetterMultiplier());
					wordPoints = wordPoints + (tempTile.getValue() * _board[row][col].getLetterMultiplier());
					
				}
				
				for (int tile = 0; tile < tiles.size(); tile++) {
					Tile tempTile = tiles.get(tile);
					int row = tempTile.getRow();
					int col = tempTile.getCol();
					
					System.out.println(word + " is multiplied by "  + _board[row][col].getWordMultiplier());
					wordPoints = wordPoints  * _board[row][col].getWordMultiplier();
					
				}
				
				System.out.print(word + " received " + wordPoints);
				SimpleImmutableEntry<String, Integer> entry = new SimpleImmutableEntry<String, Integer>(
						word, wordPoints);
				_lastTurnWordPoints.add(entry);
			}
		}

		
		/**
		 * This Method goes through the list of words made in the last turn and then
		 * adds the points for them together so that it returns the total points for
		 * the turn
		 * 
		 * @author Kevin
		 * 
		 * @return total - of points from words made
		 */
		public int getTotalPoints() {
			int total = 0;
			for (int i = 0; i < _lastTurnWordPoints.size(); i++) {
				total = total + _lastTurnWordPoints.get(i).getValue();
			}
			return total;
		}

		
		/**
		 * Method to check if the word in a given string is valid word according to
		 * the rules of the project. Meaning it is at least two letters long,
		 * contains at least one vowel and 1 non-vowel, and is not a non-letter
		 * 
		 * @author Kevin and Mark
		 * 
		 * @return true if all words made are valid, false otherwise
		 */
		public boolean checkWords() {
			for (int i = 0; i < _formedWords.size(); i++) {
				ArrayList<Tile> tiles = _formedWords.get(i);
				String word = "";
				for(int tile=0; tile<tiles.size(); tile++){
					word = word + tiles.get(tile).getLetter(); 
				}
				if(!realWord(word)){
					System.out.println(word + " is not a real word");
					return false;
				}
				System.out.println(word + " is a real word");
			}
			return true;
		}

		
		/**
		 * Method to check if the word in a given string is valid word according to
		 * the rules of the project. For stage two this means that the method is checking
		 * to see if the word is in the given dictionary
		 * 
		 * @author Kevin and Mark
		 * 
		 * @return true if the word is valid and false otherwise
		 */
		public boolean realWord(String word) {

			if(_dictionary.contains(word)){
				return true;
			}
			else{
				return false;
			}
		}


		
		/**
		 * This method returns the array list of words and points made during the
		 * turn
		 * 
		 * @return _lastTurnWordPoints which contains the words and points collected
		 *         for those words during the turn
		 */
		public ArrayList<SimpleImmutableEntry<String, Integer>> getWordsandPoints() {
			return _lastTurnWordPoints;
		}

		
		/**
		 * Access method to return the board objects' double array of squares
		 * 
		 * @return the double array instantiated in the main constructor.
		 */
		public Square[][] getGameBoard() {
			return _board;

		}
		
		/**
		 * This assigns a new array list to the _placedTiles variable
		 * This way there are no placed tiles at the begining of the next turn
		 * 
		 * @author Kevin Kijanka
		 * 
		 */
		public void blankPlacedTilesList(){
			_placedTiles = new  ArrayList<Tile>();
		}
		
		
		/**
		 * Method to create a string representation of the double Array of squares that represents the 
		 * board. Each row is contained within brackets, out of bounds squares are show by a #, and 
		 * blank squares are shown as a _ with the letter and word multipliers following the character.
		 * 
		 * @return the string representation of the board given the requirements given in the stage 2 description 
		 */
		public String toString(){
			String s = "";
			for(int row = 0; row<20; row ++){
				s = s + "[";
				for(int col = 0; col<20; col++){
					if(_board[row][col] == null){
						s = s + "#";
					} 
					else 
						if(_board[row][col] != null){
							if(_board[row][col].getTile() == null){
								int lm = _board[row][col].getLetterMultiplier();
								int wm = _board[row][col].getWordMultiplier();
								s = s + "_"+lm+wm;
							}
							else{
								Tile tile = _board[row][col].getTile();
								String letter = tile.getLetter();
								s = s + letter;
							}
						}
				}
				s = s +"]";				
			}
			return s;
		}
		
		/**
		 * A simple method made to print the contents of the arraylist holding the dictionary to make 
		 * sure that it is indeed containing the appropriate entries 
		 * 
		 */
		public void printDictionary(){
			for(int i = 0; i < _dictionary.size(); i++){
					System.out.println(_dictionary.get(i));
			}
		}
		
		/**
		 * An accessor method for the arraylist holding the given dictionary
		 * 
		 * @return the instance variable holding the reference to the arraylist containing the dictionary
		 */
		public ArrayList<String> getDictionary(){
			return _dictionary;
			
		}
		
		
		
		/**
		 * This method sets all the multiplier of the squares of the placed tiles 
		 * to 1 so that they are not used in future turns
		 * 
		 */
		public void blankMultipliers(){
			for(int i=0; i<_placedTiles.size(); i++){
				int row = _placedTiles.get(i).getRow();
				int col = _placedTiles.get(i).getCol();
				
				_board[row][col].setLetterMultiplier(1);
				_board[row][col].setWordMultiplier(1);
			}
		}

		/**
		 * This method returns the arraylist to the placed tiles
		 * 
		 * @return _placedTiles - reference to the arrayList of placed tiles
		 */
		
		public ArrayList<Tile> getPlacedTiles(){
			return _placedTiles;
		}

	}

	
