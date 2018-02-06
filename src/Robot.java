
/*
 *  Author: 	Ash Scarbrough
 *  Date:		8/4/2016
 *  Assignment:	Programming Assignment #3
 *  Class:		CSCI-C490 (Advanced Java)
 *  Function:   Robot class creates a robot, which has:
 *  				- an int x location and a y location on a two dimensional grid
 *  				- a boolean value to represent if the robot is "alive" or not
 *  			Class Robot has one constructor, taking no parameters, that gives 
 *  				the robot a random location (x and y) and sets it as "alive"
 *  			This class includes methods for: 
 *  				- hunt (taking player location) and advances "alive" robots toward 
 *  					the player.
 *  				- die alters the robot's isAlive value to false, killing it.
 *  				- changeLocation gives the robot a new random location
 */

public class Robot {
	
	int xLocation;
	int yLocation;
	boolean isAlive;
	
	// Constructor for robot: places new robot in random location on board
	Robot(){
		xLocation = Game.randomGen.nextInt(Game.XSIZE);
		yLocation = Game.randomGen.nextInt(Game.YSIZE);
		isAlive = true;
	}
	
	// Mutator method takes input of Player's location (x and y), and 
	// robot moves closer to to player
	public void hunt (int playerXLocation, int playerYLocation){
		
		if (playerXLocation > xLocation)
			xLocation++;
		if (playerXLocation < xLocation)
			xLocation--;
		if (playerYLocation > yLocation)
			yLocation++;
		if (playerYLocation < yLocation)
			yLocation--;
	}
	
	// Mutator method that changes the boolean isAlive to false 
	public void die (){
		isAlive = false;
	}
	
	// Mutator function gives robot a new random location
	// which is used if initial starting place is the same as
	// the player's starting point, causing an immediate Game Over.
	public void changeLocation(){
		xLocation = Game.randomGen.nextInt(Game.XSIZE);
		yLocation = Game.randomGen.nextInt(Game.YSIZE);
	}
}
