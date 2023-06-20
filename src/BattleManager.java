import java.util.Random;
/**
 * A class describing BattleManager - a class to handle Battle gameplay.
 * @author jackroberts
 *
 */
public class BattleManager {
	private Player player; // The player involved in the battle
	private Monster monster; // The monster involved in the battle
	private boolean playerTurn; // True if it's the players turn, false if not
	private final int MAX_ATTACK_VALUE = 20; // Maximum value of a monster attack
	
	/**
	 * Constructor for the BattleManager class 
	 * @param player is the player involved in the battle
	 * @param monster is the monster involved in the battle
	 */
	public BattleManager(Player player, Monster monster) {
		this.player = player;
		this.monster = monster;
		this.playerTurn = true; // Always starts on players turn
		manageBattle(); // Call manage battle to carry out the battle
	}
	/**
	 * Manage the battle between player and monster
	 */
	public void manageBattle() {
		// Instantiate Random class
		Random rand = new Random();
		// While loop will continue while both player and monster health is more than 0
		while((player.getHealth() > 0) && (monster.getHealth() > 0)) {
			//Output the health of both actors
			System.out.println(player.getName() + " health is " + player.getHealth());
			System.out.println(monster.getName() + " health is " + monster.getHealth());
			System.out.println();
			// If it is the player's turn then call attack method against the monster
			if(playerTurn == true) {
				int randomAttack = rand.nextInt(MAX_ATTACK_VALUE + 1);
				System.out.println("Need to implement weapon attack damage into Item class!!");
				attack(randomAttack,player.getArmourClass());
			// If it is the monster's turn then call attack method against the player
			} else {
				int randomAttack = rand.nextInt(MAX_ATTACK_VALUE + 1);
				attack(randomAttack,monster.getArmourClass());
			}
			// Reset to the other actor
			playerTurn = !playerTurn;
		}
		// End the game if it would now be the player's turn and the loop has ended
		if (playerTurn == true) {
			gameOver();
		// Otherwise state that the monster has been killed
		} else {
			System.out.println(monster.getName() + " has been killed!");
			System.out.println(player.getName() + " has won the battle!");
		}
	}
	/**
	 * Executes the attacks from either player or monster
	 * @param attackDamage is the damage of the attack
	 * @param armourClass is the value of the armour equipped
	 */
	public void attack(int attackDamage, int armourClass) {
		String name;
		// Sets name to whichever Actor's turn it is
		if(playerTurn == true) {
			name = player.getName();
		} else {
			name = monster.getName();
		}
		// Outputs a message depending on the attack damage
		if(attackDamage == 0) {
			System.out.println(name + " missed!");
		} else if (attackDamage <= armourClass ) {
			System.out.println(name + " struck a glancing blow!");
		} else {
			// reduces the health of the actor if damage caused
			int damageDone = attackDamage - armourClass;
			if (playerTurn == true) {
				monster.setHealth(monster.getHealth() - damageDone);
			} else {
				player.setHealth(player.getHealth() - damageDone);
			}
			System.out.println(name + " landed a hit for " + damageDone + " hp!");
		}
	}
	/**
	 * A method to output a message and end the game if the player is dead
	 */
	public void gameOver() {
		System.out.println(player.getName() + " is dead!!!");
		System.out.println("Game Over!!!!");
		System.exit(0);
	}

}
