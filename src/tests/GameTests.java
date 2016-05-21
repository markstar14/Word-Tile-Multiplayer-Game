package tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import code.model.Game;
import code.model.Player;
import code.model.Tile;

public class GameTests {
	
	private Game _game;
	private String path = "SampleInputFile/words.txt";
	
	//tests that there are the correct number of players in the list after the method is executed
		@Test public void orderOfPlayTest(){
			_game = new Game(path);
			Player p1 = new Player();
			Player p2 = new Player();
			Player p3 = new Player();
			_game.orderOfPlay().add(p1);
			_game.orderOfPlay().add(p2);
			_game.orderOfPlay().add(p3);
			_game.orderOfPlay();
			assertTrue(""+_game.orderOfPlay().size(), _game.orderOfPlay().size()==3);
		}
		
		//tests that it is randomized. this might fail every so often if it makes the new list in the same order as the original
		@Test public void orderOfPlayTest2(){
			_game = new Game(path);
			Player p1 = new Player();
			Player p2 = new Player();
			Player p3 = new Player();
			_game.orderOfPlay().add(p1);
			_game.orderOfPlay().add(p2);
			_game.orderOfPlay().add(p3);
			ArrayList<Player> original = _game.orderOfPlay();
			_game.randomizePlayerlist();
			ArrayList<Player> actual = _game.orderOfPlay();
			System.out.println(actual);
			System.out.println("player 1 "+p1+" player 2 "+p2+" player 3 "+p3);
			assertTrue("", !original.equals(actual));
		}
		
		
		//basic test to get the score of a player that is registered to play and has no points yet
		@Test public void scoreTest(){
			_game = new Game(path);
			Player p = new Player();
			_game.register(p);
			int expected = 0;
			int actual = _game.score(p);
			assertTrue("The score returned was "+_game.score(p),actual==expected);
		}
		
		//tests a player who is registered and has points greater then zero
		@Test public void scoreTest2(){
			_game = new Game(path);
			Player p = new Player();
			_game.register(p);
			p.setScore(14);
			int expected = 14;
			int actual = _game.score(p);
			assertTrue("The score returned was "+_game.score(p),actual==expected);
		}
		
		//tests getting the score of a player that has not yet been registered to play
		@Test public void scoreTest3(){
			_game = new Game(path);
			Player p = new Player();
			int expected = 0;
			int actual = _game.score(p);
			assertTrue("The score returned was "+_game.score(p),actual==expected);
		}
		
		//tests getting the score of a particular player among a group of players
		@Test public void scoreTest4(){
			_game = new Game(path);
			Player p = new Player();
			Player p2 = new Player();
			Player p3 = new Player();
			_game.register(p); _game.register(p2); _game.register(p3);
			p.setScore(31); p2.setScore(18); p3.setScore(2);
			int expected = 18;
			int actual = _game.score(p2);
			assertTrue("The score returned was "+_game.score(p),actual==expected);
		}
		
		//Tests that the method works and the player was registered
		@Test public void registerTest(){
			_game = new Game(path);
			Player p = new Player();
			boolean actual = _game.register(p);
			boolean expected = true;
			assertTrue("",actual==expected);
		}
		
		//tests that the registered player is added to the list of players
		@Test public void registerTest2(){
			_game = new Game(path);
			Player p = new Player();
			 _game.register(p);
			boolean actual = _game.orderOfPlay().contains(p);
			boolean expected = true;
			assertTrue(actual + " was retured when checking if the player registed. "
					+ expected + " was expected.",actual==expected);
		}
		
		//tests that the player is not in the list of registered players until they are registered
			@Test public void registerTest3(){
				_game = new Game(path);
				Player p = new Player();
				boolean actual1 = _game.orderOfPlay().contains(p);
				boolean expected1 = false;
				_game.register(p);
				boolean actual2 = _game.orderOfPlay().contains(p);
				boolean expected2 = true;
				assertTrue("A player was added to the list of players before registering them",
						(actual1==expected1)&&(actual2==expected2));
			}
			
			//tests if the method will return true if there has been no placements of tiles remaining on the board
			@Test public void endTurn(){
				_game = new Game(path);
				Player p1 = new Player();
				Player p2 = new Player();
				_game.register(p1);
				_game.register(p2);
				_game.start();
				boolean expected = false;
				boolean actual = _game.endTurn();
				assertTrue("", actual == expected);	
			}
			
			//tests if you can end the turn with a tile placed somewhere that is not the homesquare
			@Test public void endTurn2(){
				_game = new Game(path);
				Player p1 = new Player();
				Player p2 = new Player();
				_game.register(p1);
				_game.register(p2);
				_game.start();
				_game.getBoard().place(new Tile("A", 1),1,1);
				boolean expected = false;
				boolean actual = _game.endTurn();
				assertTrue("", actual == expected);	
			}
			
			//tests to see if you can end the turn with a valid word placed on the board
			@Test public void endTurn3(){
				_game = new Game(path);
				Player p1 = new Player();
				Player p2 = new Player();
				_game.register(p1);
				_game.register(p2);
				_game.start();
				int HSR = _game.getBoard().homeSquareRow() - 1;
				int HSC = _game.getBoard().homeSquareCol() - 1;
				_game.getBoard().place(new Tile("T", 2),HSR,HSC);
				_game.getBoard().place(new Tile("H", 4),HSR,HSC + 1);
				_game.getBoard().place(new Tile("E", 1),HSR,HSC + 2);
//				System.out.println(_g.getBoard().compareHorizontalConfiguration(_g.getBoard().getPriorHorizontalConfiguration(),_g.getBoard().boardConfiguration()));
//				System.out.println(_g.getBoard().verticalBoardConfiguration());
				boolean expected = true;
				boolean actual = _game.endTurn();
				assertTrue("", actual == expected);	
			}
			
			//tests for a list with the words from the first turn of a game
			@Test public void lastTurnWordScores(){
				_game = new Game(path);
				Player p1 = new Player();
				p1.setName("Mark");
				Player p2 = new Player();
				p2.setName("Steve");
				_game.register(p1);
				_game.register(p2);
				_game.start();
				_game.getBoard().place(new Tile("E",1), _game.getBoard().homeSquareRow()-1, _game.getBoard().homeSquareCol()-1);
				_game.getBoard().place(new Tile("H",4), _game.getBoard().homeSquareRow()-2, _game.getBoard().homeSquareCol()-1);
				_game.getBoard().place(new Tile("T",2), _game.getBoard().homeSquareRow()-3, _game.getBoard().homeSquareCol()-1);
				_game.endTurn();
				assertTrue("", _game.lastTurnWordScores().size()==1);
			}
			
			//tests for a list of words from the second turn of the game. this should not have the word from the first turn
			@Test public void lastTurnWordScores2(){
				_game = new Game(path);
				Player p1 = new Player();
				p1.setName("Mark");
				Player p2 = new Player();
				p2.setName("Steve");
				_game.register(p1);
				_game.register(p2);
				_game.start();
				_game.getBoard().place(new Tile("E", 1), _game.getBoard().homeSquareRow()-1, _game.getBoard().homeSquareCol()-1);
				_game.getBoard().place(new Tile("H", 4), _game.getBoard().homeSquareRow()-2, _game.getBoard().homeSquareCol()-1);
				_game.getBoard().place(new Tile("T", 2), _game.getBoard().homeSquareRow()-3, _game.getBoard().homeSquareCol()-1);
				_game.endTurn();
				_game.endTurn();
				System.out.println(_game.lastTurnWordScores());
				assertTrue("the number of words from the last turn was "+_game.lastTurnWordScores().size(), _game.lastTurnWordScores().size()==1);
			}
			
			
			
			
		/*
		 * Kevin Kijanka Tests for start() and endturn()
		 */
		
		//Test that adds two players and should start
		@Test public void startTest1(){
			_game = new Game(path);
			Player p1 = new Player();
			Player p2 = new Player();
			_game.register(p1);
			_game.register(p2);
			boolean expected = true;
			boolean actual = _game.start();
			assertTrue("When the game was started, "+actual+ " was returned "
					+ " when " + expected + " should have been", expected==actual);
			
		}
		
		//Test that adds one player and should not start
		@Test public void startTest2(){
			_game = new Game(path);
			Player p1 = new Player();
			_game.register(p1);
			boolean expected = false;
			boolean actual = _game.start();
			assertTrue("When the game was started, "+actual+ " was returned "
					+ " when " + expected + " should have been", expected==actual);
			
		}
		
		//Test that adds two players and starts, but we try to start again
		@Test public void startTest3(){
			_game = new Game(path);
			Player p1 = new Player();
			Player p2 = new Player();
			_game.register(p1);
			_game.register(p2);
			_game.start();
			boolean expected = false;
			boolean actual = _game.start();
			assertTrue("When the game was started, "+actual+ " was returned "
					+ " when " + expected + " should have been", expected==actual);
			
		}
		
		//
		//Joe's Tests
	//
		@Test public void winnerTest00(){
			code.model.Game g = new code.model.Game(path);
			Player p1 = new Player();
			Player p2 = new Player();
			p1.setName("Harry");
			p2.setName("Ginny");
			g.register(p1);g.register(p2);
			p1.setScore(70);
			p2.setScore(35);
			wTs(g,p1);
		}
		@Test public void winnerTest01(){
			code.model.Game g = new code.model.Game(path);
			Player p1 = new Player();
			p1.setName("George");
			Player p2 = new Player();
			p2.setName("Fred");
			Player p3 = new Player();
			p3.setName("Charlie");
			g.register(p1); g.register(p2);	g.register(p3);
			p1.setScore(120);
			p2.setScore(200);
			p3.setScore(14);
			wTs(g,p2);
		}
		@Test public void winnerTest02(){
			code.model.Game g = new code.model.Game(path);
			Player p1 = new Player();
			p1.setName("Lily");
			Player p2 = new Player();
			p2.setName("James");
			Player p3 = new Player();
			p3.setName("Sirius");
			g.register(p1);g.register(p2);g.register(p3);
			p1.setScore(0);
			p2.setScore(0);
			p3.setScore(5);
			wTs(g,p3);
		}
		@Test public void winnerTest03(){
			code.model.Game g = new code.model.Game(path);
			Player p1 = new Player();
			p1.setName("Hermione");
			Player p2 = new Player();
			p2.setName("Luna");
			Player p3 = new Player();
			p3.setName("Ron");
			Player p4 = new Player();
			p4.setName("Neville");
			g.register(p1);g.register(p2);g.register(p3);g.register(p4);
			p1.setScore(0);
			p2.setScore(0);
			p3.setScore(5);
			wTs(g,p3);
		}
		
		public void wTs(Game g,Player expectedWinner){
			 //Can't do much with the helper 
			Player actualWinner = g.decideWinner();
			assertTrue(expectedWinner.getName()+" should have won, but "
			+ actualWinner.getName()+" won.",actualWinner.equals(expectedWinner));
		 }
		
		@Test public void saveTest(){
			_game = new Game(path);
			Player p1 = new Player();
			p1.setName("Elizabeth");
			Player p2 = new Player();
			p2.setName("Booker");
			_game.register(p1);
			_game.register(p2);
			_game.start();
//			System.out.println(_game.createSaveString());
			_game.SaveGame(_game.createSaveString());
			assertTrue("",_game.createSaveString().length() > 10 );
		}
		
		//tests to make sure the inventory is the proper size
		@Test public void inventoryTest1(){
			_game = new Game(path);
			int actual = _game.getInventory().size();
			int expected = 496;
			assertTrue("the size of the inventory is "+_game.getInventory().size(), actual == expected);
		}
		
		//tests to see if the tiles are in a random order. Might fail if the three picked tiles are randomly the same
		@Test public void inventorytest2(){
			_game = new Game(path);
			String t1 = _game.getInventory().get(0).getLetter();
			String t2 = _game.getInventory().get(1).getLetter();
			if(t1 != t2){
				assertTrue("", t1 != t2);
			}
			String t3 = _game.getInventory().get(14).getLetter();
			assertTrue("", t1 != t3);
		}
		
		@Test public void AccessSaveTest(){
			_game = new Game(path);
			System.out.println(_game.AccessSave());
		}
		
//		@Test public void RestoreTest(){
//			_game = new Game(path);
//			_game.restoreGame();
//		}

}
