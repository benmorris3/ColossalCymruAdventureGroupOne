import java.util.ArrayList;
import java.util.Random;

/**
 * This class describes an Actor: a character in the game.
 * @author jackroberts
 *
 */
public abstract class Actor {

    protected int level;
    private String name;
    private int armourClass;
    protected int health;

    private final int MINIMUM_LEVEL = 0;
    private final int MAXIMUM_LEVEL = 100;
    private final int MINIMUM_ARMORCLASS_LEVEL = 0;
    private final int MAXIMUM_HEALTH = 100;
    public Actor(int level, String name) {

        if (level >= MINIMUM_LEVEL && level <= MAXIMUM_LEVEL) {
            this.level = level;
        } else {
            throw new IllegalArgumentException("Level must be between 0" +
                    " and 100. Level must also start at 0.");
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is invalid. Please try" +
                    "again.");
        } else {
            this.name = name;
        }

        this.armourClass = calculateArmourClass(level);
        if (armourClass < MINIMUM_ARMORCLASS_LEVEL ||
                armourClass > calculateArmourClass(level)) {
            throw new IllegalArgumentException("Armour level must be above 0" +
                    "and below the actor level divided by 2.");
        }

        this.health = MAXIMUM_HEALTH;
        // Sets health at 100 (the max) when constructing an actor
    }
        public int getLevel() {
            return level;
        }

        public String getName() {
            return name;
        }

        public int getArmourClass() {
            return armourClass;
        }

        public int getHealth() {
            return health;
        }

    public void setHealth(int health) {
        this.health = health;
    }

    private int calculateArmourClass(int level) {
        return Math.min(level / 2, 100);
        }
}

    class Player extends Actor {
    	public int experience;
        private ArrayList<Item> inventory;
        protected int gold;
        private Item currentWeapon;
        protected int gainedXP;
        private final int MINIMUM_EXP = 0;
        private final int MINIMUM_GOLD = 0;

        public Player(int level, String name) {
            super(level, name);
            this.gainedXP = MINIMUM_EXP;

            this.inventory = new ArrayList<Item>();

            this.gold = MINIMUM_GOLD;

            this.currentWeapon = null;

            calculatePlayersLevel();
        }

        // Getters and setters for player constructor
        public int getExperience() {
        	return experience;
        }

        public void setExperience(int experience) {
            this.experience = experience;
        }

        public ArrayList<Item> getInventory() {
            return inventory;
        }

        public void setInventory(ArrayList<Item> inventory) {
            this.inventory = inventory;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }
        
        public int getLevel() {
        	return level;
        }
        
        public int getGainedXP() {
        	return gainedXP;
        }
        
        public void setGainedXP(int gainedXP) {
        	this.gainedXP = gainedXP;
        }

        public Item getCurrentWeapon() {
            return currentWeapon;
        }

        public void setCurrentWeapon(Item currentWeapon) {
            this.currentWeapon = currentWeapon;
        }

        public void calculatePlayersLevel() {
            final int MAX_LEVEL = 100;
            final int MINIMUM_EXP = 0;
            boolean levelIncrease = false;
            experience = (int) (Math.pow(3, (level * 0.1)) + 10);
            if (level < MAX_LEVEL) {
            	//while the player has enough experience, keep levelling up.
                while (gainedXP > experience) {
                	int excessXP = gainedXP - experience;
                    level += 1;
                    levelIncrease = true;
                    
                    experience = (int) (Math.pow(3, (level * 0.1)) + 10);
                    gainedXP = excessXP;
                }
                if(levelIncrease) {
                	System.out.println("You have leveled up! You are now level "
                            + this.level + ".");
                }
                
            } else if (gainedXP < MINIMUM_EXP) {
            throw new IllegalArgumentException("Experience cannot be " +
                    "below 0.");
            } else{
                System.out.println("You have reached the maximum level." +
                        "You have been awarded 100 gold.");
                setGold(gold + 100);
            }
        }
    }

    class Monster extends Actor {
    private Item loot;
    protected boolean alive;

    public Monster(int level, String name) {
        super(level, name);
        this.loot = generateRandomItem();
        this.alive = true;
    }

        private Item generateRandomItem() {
            Random random = new Random();
            int itemTypeIndex = random.nextInt(6);
            Item.ItemType[] itemTypes = Item.ItemType.values(); // Get all enum values
            Item.ItemType randomItemType = itemTypes[itemTypeIndex]; // Get the random enum type

            String itemName = "Item name will go here " + random.nextInt(100);
            Item item = new Item(randomItemType); // Create an item with
            // the random enum type
            return item;
        }


    public Item getLoot() {
        return loot;
    }

    public void setLoot(Item loot) {
        this.loot = loot;
    }
    
    public void defeated() {
    	this.alive = false;
    }
    
    public boolean isAlive() {
    	return this.alive;
    }
    
    @Override
    public String toString() {
        return "Monster - Name: " + getName() + ", Level: " + getLevel() +
                ", Loot: " + loot.getName();
    }
}

