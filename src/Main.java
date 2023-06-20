/**
 * This file contains the main class for running ColossalCymruAdventure.
 */


import java.util.Scanner;


/**
 * @author jackroberts
 * This class describes a runnable Java terminal application.
 */
public class Main {
	
	public final static String GAME_NAME = "Colossal Cymru Adventure"; //Permanent store of game's title.

	/**
	 * Runs from the Java terminal to initiate the game.
	 * @param args Arguments with which the program can be called (null).
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to " + GAME_NAME + "!");
		
		Scanner menuScanner = new Scanner(System.in); //Provide read of menu choice.
		boolean menuDecision = true; //Establish game exit point.
		
		//Game Loop until decision is reached to quit the game.
		while (menuDecision) {
			System.out.println("Do you want to:");
			System.out.println("make a (new) game");
			System.out.println("(quit) to desktop");
			System.out.println("\nWhat will it be?\t");
		
			String menuChoice = menuScanner.nextLine();
			
			//Handles choice of menu input. Switch chosen to allow for expansion.
			switch (menuChoice) {
				case "new":
					GameManager.beginGame(); //Hand control to the GameManager.
					menuDecision = false; //Included to avoid small bug. Possible marks for spit and polish if fixed.
					break;
				case "quit":
					System.out.println("Thank you for playing " + GAME_NAME + ". Hwyl fawr!");
					menuDecision = false; //Exit game loop
					break;
				default:
					//Default to handle undefined menu options from user.
					System.out.println("I'm not sure what you're asking. Please rephrase.");
			}
		}
		menuScanner.close();
	}
}

