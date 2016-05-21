package code.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This class handles the event when a square button of the board is pressed.
 * The class will either place a selected tile onto the square or remove a tile from the square
 * 
 * _board - holds reference to instance of Board
 * _gui - holds reference to instance of GUI
 * _row - holds reference to the row in which the square is located in the board
 * _col - holds reference to the column in which the square is located in the board
 * 
 * @author Kevin
 *
 */
public class SquareEventHandler  implements ActionListener {

	private Board _board;
	private GUI _gui;
	private int _row;
	private int _col;

	/**
	 * Constructor assigns references to the instances of the GUI and Board, to the _gui and _board variables. 
	 * It also assigns the parameterized integers to the _row and _col integer variables
	 * 
	 * @param board - reference to the instance of the Board
	 * @param gui - reference to the instance of the GUI
	 * @param row - reference to the row in which the Square is located within the Board
	 * @param col - reference to the column in which the Square is located within the Board
	 */
	SquareEventHandler(Board board, GUI gui, int row, int col) {
		_board = board;
		_gui = gui;
		_row = row;
		_col = col;
	}

	/**
	 * Defined actionPerormed method from the ActionListener interface.
	 * It retrieves the currently selected tile from the GUI object and attempts to place it onto the Square
	 * If the placement in successful then the selected tile is removed from the player's tile rack, 
	 * the GUI's reference to a current tile is set to null, and the user interface is updated to reflect the changes.
	 * If placement failed then the Board will attempt to retract the Square's current tile. 
	 * If retraction is successful than the tile will be returned to the Player's tile rack, 
	 * the GUI's reference to a currently selected tile will be set to null, and the user interface will be updated to show any changes
	 * 
	 *  @param e - ActionEvent for the action when the button is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		Tile tile = _gui.getPickedTile();
		if (_board.place(tile, _row, _col)) {
			_gui.removeFromRack(tile);
			_gui.setPickedTile(null);
			_gui.updateRack();
			_gui.updateBoard();
			_gui.updateScoreText();
		}
		else{ 
			Tile temp = _board.get(_row, _col);
			if(_board.retract(_board.get(_row, _col))){ 
				_gui.addToRack(temp); 
				_gui.setPickedTile(null);
				_gui.updateRack();
				_gui.updateBoard();
				_gui.updateScoreText();
			}
		 }
	}
}