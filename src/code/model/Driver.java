package code.model;


/** 
 * TheMain Class creates a new game and sets up two players
 * 
 * @author Kevin and Mark
 *
 */
public class Driver {

	
	/**
	 *The Main method which creates the instance of the Game along with the desired number of players. It is required that a person write out
	 *the Player objects within the Main method in order to change the number of players and their names. The method aslo registers the players with
	 *the Game and calls start
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String dictionaryPath = "";
		for(int i=0; i<args.length; i++){
			if(args[i].equals("-d")){
				dictionaryPath = args[i+1];
			}
		}
		Game game = new Game(dictionaryPath);
		
		String name = "";
	
		for(int i=0; i<args.length-1; i++){
			if(args[i].equals("-p")){
				name = args[i+1];
				Player temp = new Player();
				temp.setName(name);
				game.register(temp);
				name = "";
			}
		}
		System.out.println("Hello");
		game.start();
	}
}
