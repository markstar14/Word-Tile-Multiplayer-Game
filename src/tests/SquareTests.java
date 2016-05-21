package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.model.Square;

public class SquareTests {

	
	@Test
	public void letterMultiplierTest(){
		Square s = new Square(null);
		Integer actual = s.getLetterMultiplier();
		assertTrue("The number retrieved was "+actual, (actual == 1)||(actual == 0)||(actual == 2)||(actual == -1) );
	}
	
	@Test
	public void LetterMultiplierTest2(){
		Square s1 = new Square(null);
		Square s2 = new Square(null);
		do {
			s1 = new Square(null);
			s2 = new Square(null);
		}while(s1.getLetterMultiplier() == s2.getLetterMultiplier());
		assertTrue("", s1.getLetterMultiplier() != s2.getLetterMultiplier());
	}
	
	@Test
	public void wordMultiplierTest(){
		Square s = new Square(null);
		Integer actual = s.getWordMultiplier();
		assertTrue("The number retrieved was "+actual, (actual == 1)||(actual == 0)||(actual == 2)||(actual == 3) );
	}
	
	@Test
	public void wordMultiplierTest2(){
		Square s1 = new Square(null);
		Square s2 = new Square(null);
		do {
			s1 = new Square(null);
			s2 = new Square(null);
		}while(s1.getWordMultiplier() == s2.getWordMultiplier());
		assertTrue("", s1.getWordMultiplier() != s2.getWordMultiplier());
	}
}
