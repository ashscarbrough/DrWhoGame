
/*
 *  Author: 	Ash Scarbrough
 *  Date:		8/4/2016
 *  Assignment:	Programming Assignment #3
 *  Class:		CSCI-C490 (Advanced Java)
 *  Function: 	Board Class has:
 *  				- a two dimensional array that will act as a board
 *  					comprised of spaces, a player, and robots
 *  				- four static characters that represent spaces, the player,
 *  					robots, and barricades
 *  				- a single constructor taking two parameters that will
 *  					provide the dimensions for the board
 *  			Class has two methods:
 *  				- display each object in the character array board.
 *  				- resetBoard resets all elements of array to spaces '.'
 */

public class Board {
	
	public char [][] board;
	final public static char EMPTYSPACE = '.';
	final public static char PLAYERCHAR = 'P';
	final public static char DEADPLAYER = 'X';
	final public static char ROBOTCHAR = 'R';
	final public static char BARRICADE = '|';
	
	// Constructor creates a board made of a two dimensional array
	// from sizes provided in driver program
	Board (int xSize, int ySize){
		board = new char [xSize] [ySize];
	}
	
	// resetBoard method resets all places on board to empty spaces prior
	// to the placing of player and robots
	public void resetBoard(){

		// Resets contents of two dimensional array, row (i) by row first
		for (int i = 0; i < Game.XSIZE; i++){ 
			// Resets contents each column (j) item of two dimensional array
			for (int j = 0; j < Game.YSIZE; j++){
				board[i][j] = EMPTYSPACE; // Set each array element to Empty
			}
		}
	}

	// Display Method displays the game board to the screen
	public void display() {
		
		System.out.println("\n\n");
		
		// Method prints two dimensional array, row (i) by row first
		for (int i = 0; i < Game.XSIZE; i++){ 
			// Method prints each column (j) item of two dimensional array
			for (int j = 0; j < Game.YSIZE; j++){
				
				// If first column, do not add space before item
				if (j == 0)
					System.out.print("\n" + board[i][j]);
				else
					System.out.print(" " + board [i][j]);
			}
		}	
	}
}
