/**
 * This file contains classes relevant to managing the gameplay of the program.
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class describes a GameManager, the class responsible for dictating the flow of gameplay.
 * This class allows a player to navigate the map, initiate battles, and use items.
 * @author jackroberts
 *
 */
public class GameManager {
	
	public final static Map GAME_MAP = new Map(); //Create new map for entire game.
	private static int playerX = 0; //Tracks x coord of player. player starts in top left
	private static int playerY = 0; //tracks y coord of player. player starts in top left
	public static Player player = new Player(0, "Jack"); //Creates the player character
	
	/**
	 * Initialises a game and provides main gameplay loop.
	 */
	public static void beginGame() {

		boolean returnToMainMenu = false; //Allow exit of game loop.
		
		System.out.println("Controls:");
		System.out.println("Use n, e, s & w to move North, East, South and West.");
		System.out.println("Type 'use' to use an item from inventory.");
		System.out.println("Type 'attack' to atack a monster.");
		System.out.println("Type 'stats' to view the player info.");
		System.out.println("Type 'quit' to quit the game.");
		System.out.println();
		
		Scanner actionScanner = new Scanner(System.in);

		//Game Loop until decision is reached to return to main menu.
		while (!returnToMainMenu) {

			//First step is to describe where the player is.
			System.out.println(GAME_MAP.getDescription(playerX, playerY));

			//Get whether there is a monster at current location.
			Monster monsterAtLocation = GAME_MAP.getMonsterAt(playerX, playerY);
			//If there is a monster present, inform the player as to what the monster is called.
			//Check if the monster has already been defeated.
			if (monsterAtLocation != null) {
				if(!monsterAtLocation.isAlive()) {
					System.out.println(monsterAtLocation.getName() + " has alreay been defeated.");
				}
				else {
					System.out.println("There is a monster here: "
							+ monsterAtLocation.getName());
				}
			}

			System.out.println();
			player.calculatePlayersLevel();
			System.out.println();
			
			System.out.println("What now?\n"); //Prompt for user input.

			String actionChoice = actionScanner.nextLine();

			//Switches based on user choice.
			try {
				switch (actionChoice) {
					case "n":
						if (playerY <= 0) {
							System.out.println("Can't move North!");
						} else {
							playerY--;
							System.out.println("Moved north. Current position:"
									+ playerX + ", " + playerY + ")");
						}
						break;
					case "e":
						if (playerX >= 4) {
							System.out.println("Can't move East!");
						} else {
							playerX++;
							System.out.println("Moved East. Current position:"
									+ playerX + ", " + playerY + ")");
						}
						break;
					case "s":
						if (playerY >= 4) {
							System.out.println("Can't move South!");
						} else {
							playerY++;
							System.out.println("Moved South. Current position:"
									+ playerX + ", " + playerY + ")");
						}
						break;
					case "w":
						if (playerX <= 0) {
							System.out.println("Can't move West!");
						} else {
							playerX--;
							System.out.println("Moved West. Current position:"
									+ playerX + ", " + playerY + ")");
						}
						break;
					case "use":
						//Handle player using an item.
						beginUse();
						break;
					case "attack":
						//Handle player attacking a monster.
						beginBattle();
						break;
					case "stats":
						//Bring up the player statistics -- NEW FEATURE
						displayStats();
						break;
					case "quit":
						//Allow a user to return to main menu.
						returnToMainMenu = true;
						break;
					default:
						//Handle unexpected inputs.
						System.out.println("I'm not sure what you're asking. Please rephrase.");
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid move! You can't go outside of the" +
						" map!");
			}
		}
		actionScanner.close();
	}

	/**
	 * Begins a battle if the player chooses to attack and a monster is present.
	 * Checks the player's inventory for weapons to use and gives them the option to select
	 * which weapon they want to use.
	 */

	private static void beginBattle() {
		if ((GAME_MAP.getMonsterAt(playerX, playerY) != null)&& (GAME_MAP.getMonsterAt(playerX, playerY).isAlive())) {
			Scanner weaponScanner = new Scanner(System.in);
			ArrayList<Item> weapons = new ArrayList<>();
			for (Item s : player.getInventory()) {
				if(s.name == Item.ItemType.Sword) {
					weapons.add(s);
				}
				else if(s.name == Item.ItemType.Spear) {
					weapons.add(s);
				}
			}
			if(weapons.isEmpty()) {
				System.out.println("No weapons available, fight the monster unarmed!");
			}
			else {
				System.out.println("Choose a weapon to use in battle: ");
				int i = 0;
				for (Item w : weapons) {
					if(w.name == Item.ItemType.Sword) {
						System.out.println(i + " - " + w.getName());
						i++;
					}
					else if(w.name == Item.ItemType.Spear) {
						System.out.println(i + " - " + w.getName());
						i++;
					}
				}
					int weaponChoice = weaponScanner.nextInt();	 //user input will select item
					player.setCurrentWeapon(player.getInventory().get(weaponChoice)); //get the weapon object from the inventory

			}

			BattleManager battle = new BattleManager(player, (Monster) GAME_MAP.getMonsterAt(playerX, playerY));
			
		}
		else {
			System.out.println("There's no monster to battle!");
		}
	}
	
	/**
	 * Method to handle using items in the player's inventory.
	 * Checks if there are items in the inventory and lists them to allow the player to choose.
	 * Uses the Item class to handle the behavior of the items.
	 */
	private static void beginUse() {
		if(player.getInventory().isEmpty()) {
			System.out.println("Inventory is empty, keep searching for items.");
		}
			else {
				//Display user interface with player's inventory and numbered options to choose which item to use.
				System.out.println("Choose the item you wish to use:");
				int i = 0;
				for (Item s : player.getInventory()) {
					System.out.println(i + " - " + s.getName());
					i++;
				}
					
				Scanner useScanner = new Scanner(System.in); //initiate scanner to let user pick item to use
				int itemChoice = useScanner.nextInt();		 //user input will select item
				Item chosenItem = player.getInventory().get(itemChoice); //get the item object from the inventory

				//call the use item method and pass in the player attributes
				int[] stats = chosenItem.UseItem(player.getGold(), player.getHealth()
						, player.getGainedXP());
				
				player.getInventory().remove(chosenItem);
		    	
				//update player attributes
				player.setGold(stats[0]);
				player.setHealth(stats[1]);
				player.setGainedXP(stats[2]);
				
		}
				
	}
	
	/**
	 * Method to display the player's current stats.
	 * Prints the player's gold, health, level and remaining XP before levelling up. 
	 */
	private static void displayStats() {
		System.out.print("Gold: " + player.getGold() + "\t\t");
		System.out.print("Health: " + player.getHealth() + "\t");
		System.out.println("Level: " + player.getLevel());
		int remainingExp = player.getExperience() - player.getGainedXP();
		System.out.println(remainingExp + "xp requierd to Level Up");
	}


}
