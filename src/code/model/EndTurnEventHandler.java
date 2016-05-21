package code.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This class handles the event when the end turn button is pressed.
 * It does so by calling the endTurn() method of the game class.
 * 
 * _game - holds reference to instance of game
 * @author Kevin
 * 
 */
public class EndTurnEventHandler implements ActionListener{

	private Game _game;
	
	/**
	 * Constructor assigns the reference to the instance of the game to the
	 * _game variable
	 * 
	 * @author Kevin
	 * @param game - reference to the instance of the game
	 * 
	 */
	EndTurnEventHandler(Game game){
		_game = game;
	}
	
	
	/**
	 * Defined actionPerformed() method of ActionListener interface.
	 * Calls endTurn method of game class when called
	 * 
	 * @author Kevin
	 * @param e - ActionEvent for the action when the button is pressed
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		_game.endTurn();
	}

}
