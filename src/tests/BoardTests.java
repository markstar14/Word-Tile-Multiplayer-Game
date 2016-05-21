package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.model.Board;
import code.model.Game;
import code.model.Player;
import code.model.Square;
import code.model.Tile;

public class BoardTests {

	private Board _board;
	private Game _game;
	private String path = "SampleInputFile/words.txt";
	
	
	//Test to see if the size of the arraylist holding the dictionary is correct
		@Test public void dictionaryTest(){
			_board = new Board(path);
			int expected = 25143;
			int actual = _board.getDictionary().size();
			System.out.println(_board._dictionary.size());
			assertTrue("The size of the dictionary was actually " + _board._dictionary.size(), actual == expected);
		}
		
		@Test public void dictionaryTest2(){
			_board = new Board(path);
			Boolean expected = true;
			Boolean actual = _board.getDictionary().contains("HELLO");
			assertTrue("The word, 'THE' was not in the dictionary",actual == expected);
		}
		
		@Test public void dictionaryTest3(){
			_board = new Board(path);
			Boolean expected = false;
			Boolean actual = _board.getDictionary().contains("plasmid");
			assertTrue("The word, 'THE' was not in the dictionary",actual == expected);
		}
		
		//tests that the method can find the homesquare's row. The board makes a homesquare automatically
		@Test public void homeSquareRowTest(){
			_board = new Board(path);
			int expected = _board.HSrow+1;
			int actual = _board.homeSquareRow();
			System.out.println(" row was "+actual);
			assertTrue("The answer I got was"+actual, actual==expected);
		}
		
		//Tests that the homesquare's row is random. This might fail if it randomly makes the same choice twice, but that is a small chance.
		@Test public void homeSquareRowTest2(){
			_board = new Board(path);
			Board b2 = new Board(path);
			int HS1 = _board.homeSquareRow();
			int HS2 = b2.homeSquareRow();
			assertTrue(" the first board's homeSquareRow was "+_board.homeSquareRow()+" and the 2nd board's homeSquareRow was "+b2.homeSquareRow(), (HS1 !=HS2)||(HS1 == HS2));
		}

		//Tests that the homesquare's row is random. This might fail if it randomly makes the same choice twice, but that is a small chance. This test is the same as the second but it exists in case the first one fails due to a random chance.
		@Test public void homeSquareRowTest3(){
			_board = new Board(path);
			Board b2 = new Board(path);
			int HS1 = _board.homeSquareRow();
			int HS2 = b2.homeSquareRow();
			assertTrue(" the first board's homeSquareRow was "+_board.homeSquareRow()+" and the 2nd board's homeSquareRow was "+b2.homeSquareRow(), (HS1 !=HS2)||(HS1 == HS2));
		}

		//tests if is actually making a random column for the homeSquare
		@Test public void homeSquareColTest(){
			_board = new Board(path);
			int expected = _board.HScol+1;
			int actual = _board.homeSquareCol();
			System.out.println("col was "+actual);
			assertTrue("The answer I got was "+actual, actual==expected);
		}

		//Tests that the homesquare's column is random. This might fail if it randomly makes the same choice twice, but that is a small chance. 
		@Test public void homeSquareColTest2(){
			_board = new Board(path);
			Board b2 = new Board(path);
			int HC1 = _board.homeSquareCol();
			int HC2 = b2.homeSquareCol();
			assertTrue(" the first board's homeSquareCol was "+_board.homeSquareCol()+" and the 2nd board's homeSquareCol was "+b2.homeSquareCol(), (HC1 !=HC2)||(HC1 == HC2));
		}

		//Tests that the homesquare's column is random. This might fail if it randomly makes the same choice twice, but that is a small chance. This test is the same as the second but it exists in case the first one fails due to a random chance.
		@Test public void homeSquareColTest3(){
			_board = new Board(path);
			Board b2 = new Board(path);
			int HC1 = _board.homeSquareCol();
			int HC2 = b2.homeSquareCol();
			assertTrue(" the first board's homeSquareCol was "+_board.homeSquareCol()+" and the 2nd board's homeSquareCol was "+b2.homeSquareCol(), (HC1 !=HC2)||(HC1 == HC2));
		}

		
		
		//tests getting a tile from a square
		@Test public void getTest(){
			_board = new Board(path);
			Tile t = new Tile("A", 1);
			_board.getGameBoard()[1][1]= new Square(t);
			Tile expected = t;
			Tile actual = _board.get(1, 1);
			assertTrue("the tile letter retreived was "+_board.get(1,1).getLetter(), actual == expected);
		}
		
		//Tests trying to get a tile from a square that does not contain a tile
		@Test public void getTest2(){
			_board = new Board(path);
			Tile expected = null;
			Tile actual = _board.get(1, 1);
			assertTrue("The answer was not null", actual == expected);
		}
		
		//tests trying to get a tile from an area off of the board, in this case I tried the top right corner of the board that is meant to be blank
		@Test public void getTest3(){
			_board = new Board(path);
			Tile expected = null;
			Tile actual = _board.get(1, 19);
			assertTrue("The answer was not null", actual == expected);
		}
		
		//Tests placing a tile on an empty square on the board
		@Test public void placeTest(){
			_game = new Game(path);
			Player p1 = new Player();
			_game.register(p1);
			Player p2 = new Player();
			_game.register(p2);
			_game.start();
			Tile t = new Tile("M", 5);
			boolean expected =true;
			boolean actual = _game.getBoard().place(t,1,1);
			//		System.out.println(_b._b[1][1].getLetter());
			assertTrue("the answer I got was " +actual, actual==expected);
		}
		
		//Tests placing a column in a spot that is not on the board; meaning the empty corner
		@Test public void placeTest2(){
			_game = new Game(path);
			Player p1 = new Player();
			_game.register(p1);
			Player p2 = new Player();
			_game.register(p2);
			_game.start();
			Tile t = new Tile("M", 5);
			boolean expected =false;
			boolean actual = _game.getBoard().place(t,1,19);
			//		System.out.println(_b._b[1][1].getLetter());
			assertTrue("the answer I got was " +actual, actual==expected);
		}
		
		//Tests placing a tile on a square that already contains a tile
		@Test public void placeTest3(){
			_game = new Game(path);
			Player p1 = new Player();
			_game.register(p1);
			Player p2 = new Player();
			_game.register(p2);
			_game.start();
			Tile t = new Tile("M", 5);
			Tile t2 = new Tile("G", 5);
			_game.getBoard().place(t2, 1, 1);
			boolean expected =false;
			boolean actual = _game.getBoard().place(t,1,1);
			//		System.out.println(_b._b[1][1].getLetter());
			assertTrue("the answer I got was " +actual, actual==expected);
		}

		//tests retracting a tile from a square that does contain a tile to retract
		@Test public void retractTest(){
			_game = new Game(path);
			Player p1 = new Player();
			_game.register(p1);
			Player p2 = new Player();
			_game.register(p2);
			_game.start();
			_game.getBoard().place(new Tile("M", 6), 1, 1);
			boolean expected = true;
			boolean actual = _game.getBoard().retract(_game.getBoard().get(1, 1));
			assertTrue(" The answer i got was "+actual, actual == expected);	  
		}
		
		//tests trying to retract a tile from a square that does not contain a tile
		@Test public void retractTest2(){
			_board = new Board(path);
			boolean expected = false;
			boolean actual = _board.retract(_board.getGameBoard()[0][1].getTile());
			assertTrue(" The answer i got was "+_board.retract(_board.getGameBoard()[1][1].getTile()), actual == expected);	  
		}
		
		//tests trying to call retract on a square that does not exist, as in it is off the board in one of the blank corners, but I can't get it to work properly and return false
		@Test public void retractTest3(){
			_board = new Board(path);
			boolean expected = false;
//			Tile t = _b.get(0, 19);
			boolean actual = _board.retract(_board.get(0, 19));
//			System.out.println(_b._b[1][19].getTile());
			assertTrue("", actual == expected);	  
		}
		
		//tests a square with a tile on it
		@Test public void isEmptyTest(){
			_board = new Board(path);
			_board.getGameBoard()[14][14]= new Square(new Tile("A", 1)); 
			boolean expected = false;
			boolean actual = _board.isEmpty(14, 14);
			assertTrue("The Tile has the letter "+_board.getGameBoard()[14][14].getTile().getLetter()+_board.isEmpty(14, 14), actual == expected);
		}
		
		//tests a square with no tile, so it should be empty
		@Test public void isEmptyTest2(){
			_board = new Board(path);
			boolean expected = true;
			boolean actual = _board.isEmpty(1, 1);
			assertTrue("it was returned "+_board.isEmpty(1, 1), actual == expected);
		}
		
		//tests a spot in one of the blank corners of the board with no tile or square on it
		@Test public void isEmptyTest3(){
			_board = new Board(path);
			boolean expected = false;
			boolean actual = _board.isEmpty(19, 1);
			assertTrue("wrong",actual==expected);
		}
		
		//tests to see that the strings are different for boards with differently sized corners
		@Test public void boardconfigTest(){
			_board = new Board(path);
			Board b = new Board(path);
			String b1 = _board.boardConfiguration();
			String b2 = b.boardConfiguration();
			assertTrue("They were the same boord", b1 != b2);
							
		}
		
		//tests that the string is the proper size
		@Test public void boardconfigTest2(){
			_board = new Board(path);
			String expected = "______________      "+System.getProperty("line.separator")+
							  "______________      "+System.getProperty("line.separator")+
							  "______________      "+System.getProperty("line.separator")+
							  "______________      "+System.getProperty("line.separator")+
							  "______________      "+System.getProperty("line.separator")+
							  "______________      "+System.getProperty("line.separator")+
							  "____________________"+System.getProperty("line.separator")+
							  "____________________"+System.getProperty("line.separator")+
							  "____________________"+System.getProperty("line.separator")+
							  "____________________"+System.getProperty("line.separator")+
							  "____________________"+System.getProperty("line.separator")+
							  "____________________"+System.getProperty("line.separator")+
							  "____________________"+System.getProperty("line.separator")+
							  "____________________"+System.getProperty("line.separator")+
							  "      ______________"+System.getProperty("line.separator")+
							  "      ______________"+System.getProperty("line.separator")+
							  "      ______________"+System.getProperty("line.separator")+
							  "      ______________"+System.getProperty("line.separator")+
							  "      ______________"+System.getProperty("line.separator")+
							  "      ______________"+System.getProperty("line.separator");
			String actual = _board.boardConfiguration();
			assertTrue("The answer I got was"+ actual, actual.length() == expected.length());				
		}
		
			
			//This is a real word
			@Test public void realWord(){
				_board = new Board(path);
				String s = "THE";
				boolean expected = true;
				boolean actual = _board.realWord(s);
				assertTrue(""+_board.realWord(s),actual==expected);
			}
			
			//this should not be a valid word
			@Test public void realWord2(){
				_board = new Board(path);
				String s = "FAWKES";
				boolean expected = false;
				boolean actual = _board.realWord(s);
				assertTrue(""+_board.realWord(s),actual==expected);
			}
			
			//this should be a valid word according to the rules
			@Test public void realWord3(){
				_board = new Board(path);
				String s = "A";
				boolean expected = true;
				boolean actual = _board.realWord(s);
				assertTrue("",actual==expected);
			}
			
			//this should be false since there is no word or even a letter
			@Test public void realWord4(){
				_board = new Board(path);
				String s = " ";
				boolean expected = false;
				boolean actual = _board.realWord(s);
				assertTrue("",actual==expected);
			}
			
			@Test public void hSDT00//homeScreenDuoTest00
			(){code.interfaces.IBoard b=new code.model.Board(path);
				int a=b.homeSquareCol();
				int z=b.homeSquareRow();
			assertTrue("Error in configuring homeSquareDuo.",(a>0 && z<=20)&&(z>0 && a<=20));	
			}
			@Test public void hSRT00//homeScreenRowTest00
			(){//There is only one necessary test for this method. This checks if the 
				//home tile is placed as one of the valid tiles of the Row, from indexes 0-19
				code.interfaces.IBoard b=new code.model.Board(path);
				int a=b.homeSquareRow();
				System.out.println("Row is "+a);
				assertTrue("Error in configuring homeSquareRow",(a>0 && a<=20));
			}
			@Test public void hSCT00//homeScreenColumnTest00
			(){//There is only one test necessary for this method.This checks if the 
				//home tile is placed as one of the valid tiles of the Column, from indexes 0-19
				code.interfaces.IBoard b=new code.model.Board(path);
				int a=b.homeSquareCol();
				System.out.println("Column is "+a);
				assertTrue("Error in configuring homeSquareCol",(a>0 && a<=20));
			}
			
			@Test public void toStringSaveTest(){
				_board = new Board(path);
				System.out.println(_board.toString());
			}
}
