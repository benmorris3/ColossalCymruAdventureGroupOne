import java.util.Random;

/**
 * This class describes a collectible item rewarded after every battle.
 * @author matthew milne
 *
 */
public class Item {

	//enumerated type to hold the type of the item
	public enum ItemType{
		Sword,
		Potion,
		Bara_Birth,
		Textbook,
		Spear,
		Phone;
	}
	
	//private variable to hold the item name
	public ItemType name;
	protected int attackDamage;
	private int MAX_ATTACK_DAMAGE = 20;

	/**
	 * Constructor that sets the type of item based on the enum values.
	 * @param	name the type of item.
	 */
	public Item(ItemType name) {
    	setName(name);
    	setAttackDamage(name);
    } 

	/**
	 * Method to retrieve the name of the item.
	 */
    public Item.ItemType getName() {
        return name;
    }

    /**
	 * Private method to set the name of the item.
	 */
    private void setName(ItemType name) {
        this.name = name;
    }
    
    private void setAttackDamage(ItemType name) {
    	if(name.equals(ItemType.Spear)||(name.equals(ItemType.Sword))){
    		Random random = new Random(); 
    		this.attackDamage = random.nextInt(MAX_ATTACK_DAMAGE);
    	}
    }
   
    /**
	 * Method to use the item.  The item will effect different attributes of the player depending on the item used.
	 * @param gold  integer value for the amount of gold a player has.
	 * @param health  integer value between 0-100 for the player's current health value.
	 * @param exp  integer value to represent the current experience the player has.
	 * returns integer array with three values for gold, health and experience. 
     * @return 
	 */
    public int[] UseItem(int gold, int health, int exp) {
    	int[] stats = {gold, health, exp};
    	
    	// if the item is the sword, then display a message only and return original attributes.
    	if (name == ItemType.Sword) {
    		System.out.println("Can't Use that here!");
    		return stats;
    	}
    	// if the item is the potion, then display a message and increase the health attribute.
    	else if((name == ItemType.Potion) && (health < 80)) {
    		System.out.println("You take a swig of little potion.");
    		stats[1] += 20;
    		return stats;
    	}
    	//if the item is a potion then ensure the health does not exceed 100.
    	else if((name == ItemType.Potion)&&(health >= 80)) {
    		System.out.println("You take a swig of big potion.");
    		stats[1] = 100;
    		return stats;
    	}
    	// if the item is the bara birth, then display a message and increase the health attribute.
    	else if((name == ItemType.Bara_Birth)&&(health < 50)) {
    		System.out.println("The taste of childhood rejuvenates you...");
    		stats[1] += 50;
    		return stats;
    	}
    	//if the item is a bara birth then ensure the health does not exceed 100.
    	else if((name == ItemType.Bara_Birth)&&(health >= 50)) {
    		System.out.println("The taste of childhood rejuvenates you.");
    		stats[1] = 100;
    		return stats;
    	}
    	//if the item is a textbook then display a message and increase the experience attribute.
    	else if(name == ItemType.Textbook){
    		System.out.println("Your newfound knowledge of Discrete Maths emboldens you.");
    		stats[2] += 75;
    		return stats;
    	}
    	// if the item is the spear, then display a message only and return original attributes.
    	else if(name == ItemType.Spear){
    		System.out.println("Can't Use that here!");
    		return stats;
    	}
    	// if the item is the phone and the player has enough gold then increase the experience.
    	else if((name == ItemType.Phone)&&(gold >= 10)){
    		System.out.println("You call for some help.");
    		stats[0] -= 10;
    		stats[2] += 100;
    		return stats;
    	}
    	// if the item is the phone but the player does not have enough gold then display an error.
    	else if((name == ItemType.Phone)&&(gold < 10)){
    		System.out.println("Not enough gold to call for help.");
    		return stats;
    	}
    	else {
    		return stats;
    	}

    }

}
