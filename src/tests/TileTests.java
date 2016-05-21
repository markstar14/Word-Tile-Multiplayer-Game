package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.model.Game;
import code.model.Player;
import code.model.Tile;

public class TileTests {

	private Tile _tile;
	private Game _game;
	private String path = "SampleInputFile/words.txt";
	
	@Test public void getLetterTest(){
		_tile = new Tile("A", 3);
		String expected = "A";
		String actual = _tile.getLetter();

		assertTrue( "The char was" + actual, actual == expected);
	}
	@Test public void getValueTest(){
		_tile = new Tile("A", 3);
		int expected = 3;
		int actual = _tile.getValue();
		assertTrue( "The value was" + actual, actual == expected);
	}
	
	@Test public void getValueTest3(){
		_tile = new Tile("M", 6);
		int expected = 6;
		int actual = _tile.getValue();
		assertTrue( "The value was" + actual, actual == expected);
	}
	@Test public void getValueTest4(){
		_tile = new Tile("Y", 7);
		int expected = 7;
		int actual = _tile.getValue();
		assertTrue( "The value was" + actual, actual == expected);
	}
	
	//These methods test the values of the tiles, using the logic that the first tile should not equal the second
		//unless it is the same tile, input as row1,col1,row2,col2. Since Failure is an expectation, boolean is also passed into 
		//the helper method that checks the tile values.
		@Test public void tGT00(){tGet(1,1,1,1,new Tile("A", 3),true);}
		@Test public void tGT01(){tGet(1,5,1,5,new Tile("B", 7),true);}
		@Test public void tGT02(){tGet(5,1,5,1,new Tile("C", 6),true);}
		@Test public void tGT03(){tGet(1,22,1,22,new Tile("D", 5),false);}
		@Test public void tGT04(){tGet(22,1,22,1,new Tile("E", 1),false);}
		@Test public void tGT05(){tGet(22,22,22,22,new Tile("F", 7),false);}
		@Test public void tGT06(){tGet(19,19,19,19,new Tile("G", 7),true);}
		@Test public void tGT07(){tGet(1,16,1,16,new Tile("H", 4),false);}
		@Test public void tGT08(){tGet(17,1,17,1,new Tile("I", 4),false);}
		@Test public void tGT09(){tGet(1,1,2,2,new Tile("J", 8),false);}
		@Test public void tGT10(){tGet(1,17,1,17,new Tile("K", 7),false);}
		@Test public void tGT11(){tGet(0,0,0,0,new Tile("K", 7),true);}
		@Test public void tGT12(){tGet(20,20,20,20,new Tile("L", 5),false);}
		@Test public void tGTX1(){
			int a=(int) (Math.random()*20);
			System.out.println(a);
			int b=(int) (Math.random()*20);
			int c=(int) (Math.random()*20);
			int d=(int) (Math.random()*20);
			tGet(a,b,c,d,new Tile("X", 8),(a==c)&&(b==d));
		}
		@Test public void tGTX2(){
		int b=(int) (Math.random()*20);
		int c=(int) (Math.random()*20);
		int d=(int) (Math.random()*20);
		tGet(1,b,c,d,new Tile("X", 8),(1==c)&&(b==d));
		}
		@Test public void tGTX3(){
		int b=(int) (Math.random()*20);
		int d=(int) (Math.random()*20);
		tGet(1,b,1,d,new Tile("X", 8),(b==d));
		}
		
		public void tGet(int row1, int col1,int row2, int col2,Tile t,boolean c){//Helper for tGT(TileGetTests)
			_game = new Game(path);
			Player p1 = new Player();
			_game.register(p1);
			Player p2 = new Player();
			_game.register(p2);
			_game.start();
			_game.getBoard().place(t, row1, col1);
			Tile a = _game.getBoard().get(row2, col2);
			boolean d = a==t;
			assertTrue("I expected the Tile's value to be, "+t+" but the real value was "+a,d==c);
		}
}
