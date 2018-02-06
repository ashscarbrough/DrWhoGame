import java.util.Random;
import java.util.Scanner;

/*
 *  Author: 	Ash Scarbrough
 *  Date:		8/4/2016
 *  Assignment:	Programming Assignment #3
 *  Class:		CSCI-C490 (Advanced Java)
 *  Function: 	The Game class is a Driver class for Board, Player, and Robot classes:
 *  			 - Has a static Random number generator, which will be used by other
 *  				classes to determine player (via teleport) and robot placement
 *  			 - Has constants XSIZE and YSIZE to determine game board size
 *  			 - Has a constant MAXIUMROBOTS to indicate the largest number of robots
 *  				permitted in the game.
 *  			This class includes one method: 
 *  				- menuOptions - which gives the player all options available
 *  			Function: This class will take user input to determine number of robots to use
 *  				via command line argument.  It will create a new board, a new player,
 *  				and an array of new robots based on user input.  It will reset the board
 *  				to empty, place the player, robots, and barricades on the board; display the 
 *  				board to the user.  Ask the user for the next player move (cardinal directions,
 *  				pass, teleport, zapp, or quit).  The player character will take the action 
 *  				requested by user.  Robots will hunt player character, if a robot and a player
 *  				occupy the same spot, the player will die.  If two robots occupy the same spot,
 *  				the robots will turn into a barricade.  After this, the board will be displayed
 *  				again.  If the player is dead, the player has lost and the game is over.  If the 
 *  				player is still alive and the robots are all dead, the player has won.  If neither
 *  				condition is achieved the user is asked for the next move until the game is won, 
 *  				lost, or quit.
 */

public class Game {
	
	static Random randomGen = new Random ();
	final static int XSIZE = 20;  // XSIZE determines number of rows for game board
	final static int YSIZE = 75;  // YSIZE determines number of columns for game board
	final static int MAXIMUMROBOTS = 100;  // Largest number of robots allowed in game
	
	public static void main(String [] args){
		
		// If command line argument for robot number is not given give user
		// message to include robot number in command line and exit program.
		if (args.length < 1) {
		    System.out.println("Error: Program invocation should include "
		    		+ "number of robots, with the form:");
		    System.out.println("java Game 10");
		    System.exit(0);
		}
		
		// first command line argument will be parsed as an integer and will be 
		// used to determine number of robots included in game
		int robotNumber = Integer.parseInt(args[0]);
		Robot [] robot = new Robot [robotNumber];  // Each robot is held in an array
			
		// If robotNumber requested is greater than MAXIMUMROBOTS allowed, user will be 
		// notified that the max robots is 100, and robot number will be altered to 100.
		if (robotNumber > MAXIMUMROBOTS){
		    System.out.println("Requested number of robots must not exceed: " + MAXIMUMROBOTS);
		    System.out.println("100 robots included in game");
		    robotNumber = 100;
		}
			
		Board gameBoard = new Board(XSIZE, YSIZE); // New board is created to hold game objects
			
		// Create Player character
		Player human = new Player();
		
		// A new robot is created for every robot in created array
		for (int i = 0; i < robotNumber; i++){
			robot[i] = new Robot();
				
			// If the robot is created with the same location as the initial position for the
			// player character (causing an immediate lose/game-over), the robot location is 
			// re-placed at random.
			while ((robot[i].xLocation == human.xLocation) && (robot[i].yLocation == human.yLocation))
				robot[i].changeLocation();
		}	    
		
		Scanner input = new Scanner(System.in);  // Scanner created for user input
		boolean successfulMove;  // helps determine if player needs to be asked for input again
		String userInput;
		char userMove;  // used as a parameter passed to move method for player class
		boolean playerWin = false;  // 
		boolean exit = false;  // Allows program to keep running until player quits, wins or loses
		
		// while loop will continue to run until user quites, wins or loses
		while (exit == false){
			
			// Reset board to all spaces
			gameBoard.resetBoard();
			
			// Place each robot on gameBoard
			for (int i = 0; i < robotNumber; i++){
				if (robot[i].isAlive)
					gameBoard.board[robot[i].xLocation][robot[i].yLocation] = gameBoard.ROBOTCHAR;
				else
					gameBoard.board[robot[i].xLocation][robot[i].yLocation] = gameBoard.BARRICADE;
			}
			
			// Place Player on gameBoard 
			if (human.isAlive){		// If player is alive, a P is placed at character location
				gameBoard.board[human.xLocation][human.yLocation]= gameBoard.PLAYERCHAR;				
			}
			else {  // If player character is dead, a X is placed at character location
				System.out.println("\n\n");
				gameBoard.board[human.xLocation][human.yLocation]= gameBoard.DEADPLAYER;
			}
			
			gameBoard.display(); // Board is displayed
			
			// if human is dead "Game Over" Player loses
			if (!human.isAlive){
				System.out.println("\n*******************************************");
				System.out.println("*******  EXTERMINATE!!!  You Lose!  *******");
				System.out.println("***************  GAME OVER  ***************");
				System.out.println("\n*******************************************");
				exit = true;  // Exit program is set to true
				continue;  // Control returned to while loop to quit program
			}
			else{  // If human is alive
				for (int i = 0; i < robotNumber; i++){  // look at each robot
					if (robot[i].isAlive){  // if any robot is alive, player has not won yet
						playerWin = false;  
						break;				// program breaks out of loop to look at robots
					}
					else { // if all robots are dead, player has won the game
						playerWin = true;
					}
				}
			}
			
			// if player has won game, output win message and exit program
			if (playerWin == true){
				System.out.println("\n**************************************************************************");
				System.out.println("* Congratulations!!! You have won!  You showed those robots who is boss! *");
				System.out.println("**************************************************************************");
				exit = true;
				continue;
			}
			
			menuOptions();  // If player has not won or lost, display player options
			
			userInput = input.nextLine();  // Except user input for next move
			
			// if user mistakenly inputs nothing, ask for other input
			while (userInput.equals("")){
				System.out.println("Invalid input: please input valid input");
				userInput = input.nextLine();
			}
			
			// change user input to uppercase
			userInput = userInput.toUpperCase();
			userMove = userInput.charAt(0);  // First char input is used to determine next move
			
			// Player.move() method returns true if move was one of the acceptable options
			// false if it is invalid
			successfulMove = human.move(userMove);
			
			// if the move was unsuccessful give the user options and allow them to move again
			while (!successfulMove){
				
				gameBoard.display();
				menuOptions();
				userInput = input.nextLine();
				
				while (userInput.equals("")){
					System.out.println("Invalid input: please input valid input");
					userInput = input.nextLine();
				}
				userInput = userInput.toUpperCase();
				userMove = userInput.charAt(0);
				
				successfulMove = human.move(userMove);
			}
				
			// If the user requested to quit, exit is changed to true and control
			// is given to the while loop, which will end the program
			if (userMove == 'Q'){
				exit = true;
				continue;
			}
			
			// if the user requested to zapp
			if (userMove == 'Z'){
				for (int i = 0; i < robotNumber; i++){  // look at each robot
					if (human.zapp(robot[i]))  // if zapp returns true (ie robot is next to player)
						robot[i].die();  // robot is killed
				}
			}
			
			// for each robot
			for (int i = 0; i < robotNumber; i++){
				if (robot[i].isAlive)  // if robot is alive, it should hunt the player character
					robot[i].hunt(human.xLocation, human.yLocation);  //based on player's location
				
				// if robot and human occupy the same place, human is killed
				if ((robot[i].xLocation == human.xLocation) && (robot[i].yLocation == human.yLocation))
					human.die(); 
			}
			
			// look at each robot one at a time, comparing it to the location of other robots
			for (int i = 0; i < robotNumber; i++){  // compare one robot
				for (int j = i + 1; j < robotNumber; j++){   // to another
				
					if ((robot[i].xLocation == robot[j].xLocation) && (robot[i].yLocation == robot[j].yLocation)){
						robot[i].die();  // if two robots share the same space, both are killed
						robot[j].die();
					}
				}	
			}
		}
	}			
			
	// Method displays the menu options to the user
	public static void menuOptions(){
		System.out.print("\nSelect player action: (N)orth, (E)ast, (S)outh, (W)est, (P)ass, (T)eleport, (Z)app, (Q)uit: ");
	}
}
