package code.model;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
/**
 * Each instance of this class is a separate JFrame. An instance is created for each 
 * player. A reference to the board is passed in on creation through the constructor.
 * This class handles setting up the different panels and buttons and adding them to
 * the frame.
 * 
 * @author Kevin
 * 
 */
@SuppressWarnings("serial")
public class GUI extends JFrame{
	
	/*
	 * _gameWindow - this is the JFrame for the GUI
	 * _game - contains a reference to the game
	 * _board - contains a reference to the board
	 * _boardPanel - the JPanel that holds all of the square buttons for the board
	 * _scorePanel - this is the Pane that holds the text for points and other info
	 * _tilePanel - JPanel for the players rack
	 * _endTurn - the Jbutton that is added to the JFrame to end their turn
	 * _playersRack - contains a reference to the Players tile rack
	 * _pickedTile - a reference to the tile that is currently selected
	 * _scoreArea - the JTextArea for the score area in the game window
	 */
	
    private JFrame _gameWindow;
    
    private Game _game;
	private Board _board;
	
	private JPanel _boardPanel;
	private JPanel _tilePanel;
	private JScrollPane _scorePanel;
	
	private JButton _endTurn;
//	private Font buttonFont = new Font("Arial", Font.PLAIN, 9); 
	
	private ArrayList<Tile> _playersRack;

	private Tile _pickedTile;
	private JTextArea _scoreArea;
	
	/**
	 * The constructor of the GUI class that sets up the JFrame for a single user
	 * along with all the panels and buttons
	 * 
	 * @param game - reference to the instance of the game
	 * @param playersRack - a reference to the Player's tile rack
	 * @param name - name of the player
	 */
    public GUI(Game game, ArrayList<Tile> playersRack, String name){
    	_game = game;
    	_board = _game.getBoard();
    	_playersRack = playersRack;
    	
    	//set up the JFrame
    	_gameWindow = new JFrame(name + "'s Screen");
        _gameWindow.setBounds(0, 0, 900, 640);
        _gameWindow.setVisible(true);
        _gameWindow.setLayout(null);
       
        _gameWindow.setEnabled(false);
        
        //set up the board grid
        GridLayout grid = new GridLayout(20,20);
        _boardPanel = new JPanel();
        _boardPanel.setLayout(grid);
        _boardPanel.setBounds(1, 1, 600, 500);
        _boardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        _gameWindow.add(_boardPanel);
        
        // add the end turn button
    	_endTurn = new JButton("End Turn");
		EndTurnEventHandler turnHandler = new EndTurnEventHandler(_game);
		_endTurn.addActionListener(turnHandler);
		_endTurn.setBounds(670, 510, 100, 100);
		_gameWindow.add(_endTurn);
        
		//add the area that reports the scores
		_scoreArea = new JTextArea();
		_scoreArea.setBounds(0, 0, 250, 500);
		_scoreArea.setEditable(false);
        _scoreArea.setText(_game.getScoreText());
     
        _scorePanel = new JScrollPane(_scoreArea);
        _scorePanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        _scorePanel.setBounds(620, 1, 250, 500);
        _scorePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        _scorePanel.setEnabled(true);
        _gameWindow.add(_scorePanel);
        
       //add the area that represents the player's rack
        _tilePanel = new JPanel();
        GridBagLayout tilePanelLayout = new GridBagLayout();
        _tilePanel.setLayout(tilePanelLayout);
        _tilePanel.setBounds(1, 510, 660, 100);
        _tilePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        _gameWindow.add(_tilePanel);
        
        _tilePanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        
        //add buttons to the initial setup of the board
        createBoard();
        
    	GridBagConstraints c = new GridBagConstraints();

    	//add tiles to the players rack
    	for(int i=0; i<_playersRack.size(); i++){
    		Tile tempTile = _playersRack.get(i);
    		JButton tileButton = new JButton();
    		
    		TileEventHandler tileHandler = new TileEventHandler(this, tempTile);
    		tileButton.addActionListener(tileHandler);
    		tileButton.setText(tempTile.getLetter());
    		
    	    c.ipady = 50; 
    	    c.weightx = 1;
    	    c.gridx = i;
    	    c.gridy = 0;
    		
    		_tilePanel.add(tileButton, c);
    	}
        
        _boardPanel.revalidate();
    }
    
    
	/**
	 * The createBoard method sets up the initial layout of the board. 
	 * It looks at all the current squares on the board and adds a button if there 
	 * is a square and a blank label if there is not one in the square 2-D array.
	 *
	 */
    public void createBoard(){
    	Square[][] gameBoard = _board.getGameBoard();
    	SquareEventHandler squareHandler;
    	for(int row=0; row<gameBoard.length; row++){
    		for(int col=0; col< gameBoard[row].length; col++){
    			if(gameBoard[row][col] != null){
    				JButton squareButton = new JButton();
    				squareButton.setText("");
    				if((row == (_board.homeSquareRow()-1))
    						&&(col == (_board.homeSquareCol()-1)) 
    						&& (gameBoard[row][col].getTile() == null)){
    					squareButton.setText("+");
    				}
    				squareHandler = new SquareEventHandler(_board, this, row, col);
    				squareButton.addActionListener(squareHandler);
    				squareButton.setMargin(new Insets(0, 0, 0, 0));
    				_boardPanel.add(squareButton);
    				gameBoard[row][col].setButton(squareButton);
    			}
    			else{
    				JLabel label = new JLabel("");
    				_boardPanel.add(label);
    			}
    		}
    	}
    }
    
    
	/**
	 * The removeFromRack method removes the button from the panel that is the 
	 * tile rack. It does this by removing the tile from the array list for
	 * the rack and then by calling update rack to redisplay all buttons in the rack
	 * 
	 * @parm tile - reference to the tile that is to be removed
	 */
    public void removeFromRack(Tile tile){
    	_playersRack.remove(tile);
    	updateRack();
    }
    
    
	/**
	 * The addToRack method adds the tile to the panel that is the 
	 * tile rack. It does this by adding the tile to the array list for
	 * the rack and then by calling update rack to redisplay all buttons in the rack
	 * 
	 * @parm tile - reference to the tile that is to be added
	 */
    public void addToRack(Tile tile){
    	_playersRack.add(tile);
    	updateRack();
    }
    
    
	/**
	 * The updateRack removes all buttons from the rack panel and then
	 * goes through the array list of tiles to add them back in
	 * 
	 */
    public void updateRack(){
    	_tilePanel.removeAll();    
    	
    	GridBagConstraints c = new GridBagConstraints();
    	
    	for(int i=0; i<_playersRack.size(); i++){
    		Tile tempTile = _playersRack.get(i);
    		JButton tileButton = new JButton();
    		
    		TileEventHandler tileHandler = new TileEventHandler(this, tempTile);
    		tileButton.addActionListener(tileHandler);
    		tileButton.setText(tempTile.getLetter());
    		
    	    c.ipady = 50; 
    	    c.weightx = 1;
    	    c.gridx = i;
    	    c.gridy = 0;
    		
    		_tilePanel.add(tileButton, c);
    	}
    	_tilePanel.revalidate();
    	_tilePanel.repaint();
    }
    
    
	/**
	 * The updateScoreText gets the current score text from the instance of game 
	 * and then sets that text as the current score text in the text area.
	 * 
	 */
    public void updateScoreText(){
    	_scoreArea.setText(_game.getScoreText());
    	_scoreArea.revalidate();
    	_scorePanel.revalidate();
    }

	/**
	 * The updateBoard method removes all buttons from the game board panel and then
	 * goes through the 2-D array of squares to add them back in
	 * It also sets the squares to label if the tile was placed in the last turn.
	 * However, it does a button if it was this turn.
	 * 
	 */
    public void updateBoard(){
    	_boardPanel.removeAll();
    	
    	Square[][] gameBoard = _board.getGameBoard();
       	SquareEventHandler squareHandler;
    	
       	for(int row=0; row<gameBoard.length; row++){
    		for(int col=0; col< gameBoard[row].length; col++){
    			if(gameBoard[row][col] != null){
    				if(gameBoard[row][col].hasTile()){
    					if(!_board.getPlacedTiles().contains(gameBoard[row][col].getTile())){
            				JLabel label = new JLabel(gameBoard[row][col].getTile().getLetter());
            				label.setHorizontalAlignment( SwingConstants.CENTER );
            				_boardPanel.add(label);
    					}
    					else{
	    					JButton squareButton = new JButton();
	        				squareButton.setText(gameBoard[row][col].getTile().getLetter());
	        				squareHandler = new SquareEventHandler(_board, this, row, col);
	        				squareButton.addActionListener(squareHandler);
	        				squareButton.setMargin(new Insets(0, 0, 0, 0));
	        				_boardPanel.add(squareButton);
    					}
    				}
    				else{
        				if((row == (_board.homeSquareRow()-1))
        						&&(col == (_board.homeSquareCol()-1)) 
        						&& (gameBoard[row][col].getTile() == null)){
        					JButton squareButton = new JButton();
        					squareButton.setText("+");
            				squareHandler = new SquareEventHandler(_board, this, row, col);
            				squareButton.addActionListener(squareHandler);
            				squareButton.setMargin(new Insets(0, 0, 0, 0));
            				_boardPanel.add(squareButton);
        				}
        				else{
        					JButton squareButton = new JButton();
        					squareButton.setText("");
        					squareHandler = new SquareEventHandler(_board, this, row, col);
        					squareButton.addActionListener(squareHandler);
        					_boardPanel.add(squareButton);	
        				}
    				}
    				
    			}
    			else{
    				JLabel label = new JLabel("");
    				_boardPanel.add(label);
    			}
    		}
    	}
    	_boardPanel.revalidate();
    	_boardPanel.repaint();
    }
    
    
	/**
	 * This method sets the _pickedTile variable to the reference of 
	 * the currently picked tile
	 * 
	 * @param pickedTile - reference to the currently selected tile
	 */
    public void setPickedTile(Tile pickedTile){
    	_pickedTile = pickedTile;
    }
    
    
	/**
	 * This method returns the currently selected tile
	 * 
	 * @return _pickedTile - the reference to the currently selected tile
	 * 
	 */
    public Tile getPickedTile(){
    	return _pickedTile;
    }
    
    
	/**
	 * This method returns the reference to the board
	 * 
	 * @return _board - reference to the board
	 * 
	 */
    public Board getBoard(){
    	return _board;
    }

    
	/**
	 * This method enables the JFrame so that the player can interact with
	 * it
	 * 
	 */
    public void enableWindow(){
    	_gameWindow.setEnabled(true);
    }
    
    
	/**
	 * This method disables the JFrame so that the player can not interact with
	 * it
	 * 
	 */
    public void disableWindow(){
    	_gameWindow.setEnabled(false);
    }

}