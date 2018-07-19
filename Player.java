
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The player is what the user controls. The player's inventory is stored and
 * managed here.
 *
 * @author weisheng3725
 */
public class Player {

    private double weightLimit, currentWeight;
    private int health;
    private ArrayList<Item> playerItems; //a collection of items
    private Room currentRoom; //The room where the player is in currently

    /**
     * Constructs a player object, where the current room of the player must be given.     
     * The player starts with 100 health, 0 current weight and 15 as their weight limit.
     * The player also has no items in their inventory at the at the beginning.      
     *
     * @param currentRoom Sets the room the player starts in      
     */
    public Player(Room currentRoom) {
        this.health = 100;
        this.weightLimit = 15;
        this.currentWeight = 0;
        this.playerItems = new ArrayList<Item>();
        this.currentRoom = currentRoom;
    }
    
    /**
     * Takes an item from a room
     * The item is added to the player's inventory and is removed from the room
     * @param itemName item in the room
     */
    public void takeItem(String itemName) {
        Item item = getCurrentRoom().getItem(itemName);

        if (item == null) {
            System.out.println("Item is not in room");
        } else {
            if (inventoryHasSpace(item)) {
                addItem(item);
                currentRoom.removeItem(item);
            }
        }

    }
    
    /**
     * Receives an item from a character
     * The item is added to the player's inventory
     * @param receivedItem Item given by a character
     */
    public void receiveItem(Item receivedItem) {

        if (inventoryHasSpace(receivedItem)) {
            addItem(receivedItem);
        }

    }

    /**
     * Adds an item to the inventory, increasing the current weight
     */
    private void addItem(Item item) {
        currentWeight += item.getWeight();
        playerItems.add(item);
        System.out.println("YOU HAVE OBTAINED " + item.getName().toUpperCase());
    }

    /**
     * Checks if the player's inventory has enough space
     *
     * @param item The item that will be added to the inventory
     * @return true if there's space, false if there's not enough
     */
    private boolean inventoryHasSpace(Item item) {
        //if adding an item to the inventory causes it to be over the weight limit return false
        if (item.getWeight() + currentWeight > weightLimit) {
            System.out.println("You are unable to carry any more items");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Removes an item from the player's inventory, decreasing the current weight
     * @param itemName The name of the item to be removed from the inventory
     */
    public void dropItem(String itemName) {
        boolean found = false;
        Iterator<Item> iterator = playerItems.iterator();

        //searches through the inventory and removes the item when found
        while (iterator.hasNext()) {
            Item i = iterator.next();
            if (i.getName().equals(itemName)) {
                found = true;
                System.out.println(i.getName().toUpperCase() + " WAS REMOVED FROM INVENTORY");
                currentWeight -= i.getWeight();
                iterator.remove();
            }
        }

        if (found == false) {
            System.out.println("No such item exists in the inventory");
        }

    }

    /**
     * Displays all the items held by the player
     */
    public void displayInventory() {
        for (Item i : playerItems) {
            System.out.println(i.getName() + ", " + i.getDescription());
        }
        System.out.println("Total weight: " + currentWeight + "/" + weightLimit);
    }

    /**
     *
     * @return Player's current weight
     */
    public double getCurrentWeight() {
        return currentWeight;
    }

    /**
     *
     * @return Player's weight limit
     */
    public double getWeightLimit() {
        return weightLimit;
    }
    
    /**
     * 
     * @return Player's current health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Decreases the player's health
     * @param damage amount of damage received
     */
    public void decreaseHealth(int damage) {
        health -= damage;
    }
    

    /**
     *
     * @return Player's current location
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Set the player's current room to a new room     
     *
     * @param newRoom The new room that the player will be in
     */
    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }

    /**
     *
     * @param itemName The name of the item
     * @return Item from the player's inventory. If the item does not exist in
     * the inventory, return null
     */
    public Item getItem(String itemName) {

        for (Item i : playerItems) {
            if (i.getName().equals(itemName)) {
                return i;
            }
        }
        return null;

    }
    
    /**
     * Consumes an item from the inventory.
     * The player's health will increase or decrease depending on the item's effect
     * @param itemName Name of the item that is going to be consumed
     */
    public void consumeItem(String itemName) {
        
        Item item = getItem(itemName);
        
        //if the player does not have the item, print a message
        if (item == null) {
            System.out.println("You do not have this item");                            
        } else {            
            System.out.println("YOU CONSUMED " + itemName.toUpperCase());
            health += item.getEffect();
            dropItem(itemName);            
        }
        
        
    }

}
