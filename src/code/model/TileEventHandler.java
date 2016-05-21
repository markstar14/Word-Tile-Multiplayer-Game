package code.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This class handles the event when a tile button is clicked on the tile rack. 
 * It makes the GUI hold a reference to the clicked tile so it can be placed on the board.
 * 
 * _tile - the reference to the tile which the button is representing 
 * _gui - the reference to the GUI instance 
 * 
 * @author Kevin
 *
 */
public class TileEventHandler implements ActionListener {

	Tile _tile;
	GUI _gui;
	
	/**
	 * Constructor assigns references to the instances of the Tile and GUI, to the _tile and _gui variables
	 * 
	 * @author Kevin
	 * @param gui - reference to the instance of the GUI
	 * @param tile - reference to the instance of the Tile
	 */
	public TileEventHandler(GUI gui, Tile tile){
		_gui = gui;
		_tile = tile;
	}
	
	/**
	 * Defined actionPerformed() method of ActionListener interface.
	 * calls setPickedTile of the GUI class on the Tile instance when called
	 * 
	 * @author Kevin
	 * @param e - ActionEvent for the action when the button is pressed
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		_gui.setPickedTile(_tile);
	}

}
