/*
 *  Author: 	Ash Scarbrough
 *  Date:		8/4/2016
 *  Assignment:	Programming Assignment #3
 *  Class:		CSCI-C490 (Advanced Java)
 *  Function: 	The Player class has:
 *  				- An xLocation and a yLocation on the board
 *  				- an isAlive value to determine player being alive or dead
 *  				- a zappCount, to determine how many times the player can zapp
 *  				- the constructor places the player at the center of the board
 *  					sets the player to alive, and gives it one opportunity to use
 *  					zapp move.
 *  			it has four methods:
 *  				- move(char) which moves the player based on user input from driver
 *  					program
 *  				- teleport which moves the player to a random location on the board
 *  				- zapp(Robot) which returns true if a robot is in the immediate vicinity
 *  					of the player character (N, S, E, W, or diagonal)
 *  				- die() which changes the isAlive value to false, killing the player
 */

public class Player {
		
	int xLocation;
	int yLocation;
	boolean isAlive;
	int zappCount;
	
	// Constructor that places player in the center of the board
	// it sets isAlive value to true(alive) and gives player one Zapp
	Player(){
		xLocation = (int) Game.XSIZE/2;
		yLocation = (int) Game.YSIZE/2;
		isAlive = true;
		zappCount = 1;
	}
		
	// Move player based on char input from driver program
	public boolean move (char playerMove){
			
		// Switch statement to deal with input from user from Game Class
		switch (playerMove){
			case('N'):  // move player north
				if (xLocation > 0){
					--xLocation;
					return true;
				}
				else { 
					System.out.println("Player cannot exceed game board boundaries, choose another option.");
					return false;
				}
			case('E'): // move player east
				if (yLocation < (Game.YSIZE - 1)){
					++yLocation;
					return true;
				}
				else {
					System.out.println("Player cannot exceed game board boundaries, choose another option.");
					return false;
				}
			case('S'):  // move player south
				if (xLocation < (Game.XSIZE -1)){
					++xLocation;
					return true;
				}
				else {
					System.out.println("Player cannot exceed game board boundaries, choose another option.");
					return false;
				}
			case('W'):  // move player west
				if (yLocation > 0){
					--yLocation;
					return true;
				}
				else {
					System.out.println("Player cannot exceed game board boundaries, choose another option.");
					return false;
				}
			case('P'):  // player passes without movement
				return true;
			case('T'):  // player is teleported to random location on the board
				teleport();
				return true;
			case('Z'):  // if the player has zapps left, permit move
				if (zappCount > 0){
					zappCount--;
					return true;						
				}
				else{
					System.out.println("You are permitted one Zapp per game. You have no more zapps.");
					return false;
				}
			case ('Q'):  //quit is valid move
				return true;
			default:  // if other option is selected notify user of problem
				System.out.println("Invalid user selection.  Select valid option.");
				return false;		
		}
	}
	
	// Method teleports Player to random location on the board
	// player has the potential of landing on a robot, where he will die
	public void teleport() {
		xLocation = Game.randomGen.nextInt(Game.XSIZE);
		yLocation = Game.randomGen.nextInt(Game.YSIZE);
	}
	
	// method looks in at the location of the player character and the robot passed to this method
	// and determines if the robot is within one space of the player character: N, S, E, W, or
	// diagonal
	public boolean zapp(Robot robot){  
		if (((this.xLocation == (robot.xLocation - 1)) || (this.xLocation == robot.xLocation) || 
				(this.xLocation == (robot.xLocation + 1))) && 
			((this.yLocation == (robot.yLocation - 1)) || (this.yLocation == robot.yLocation) || 
				this.yLocation == (robot.yLocation + 1))){
			return true;  // if robot is close enough return true
		}
		else   // if robot is not close enough return false
			return false;
	}
	
	// Mutator method that changes the boolean isAlive to false 
	public void die (){
		isAlive = false;
	}
}
