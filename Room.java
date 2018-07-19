import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "MAKAN" application. 
 * "MAKAN" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String name;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> roomItems;
    private ArrayList<Character> roomCharacters;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param name The name of the room
     */
    public Room(String name) 
    {
        this.name = name;
        this.roomItems = new ArrayList<Item>();
        this.roomCharacters = new ArrayList<Character>();
        exits = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Places an item into the room
     * @param item The item to be placed into the room
     */
    public void setItem(Item item) {
        roomItems.add(item);
    }
    
    /**
     * Removes an item from the room
     * @param item The item to be removed from the room
     */
    public void removeItem(Item item) {
        roomItems.remove(item);
    }
    
     /**
     * Places a character into the room
     * @param character  The character to be placed into the room
     */
    public void setCharacter(Character character) {                
        
        roomCharacters.add(character);
    }
    
    /**
     * Removes a character from the room
     * @param character  The character to be removed from the room
     */
    public void removeCharacter(Character character) {
        roomCharacters.remove(character);
    }    
   
    /**
     * @return The name of the room     
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return a description of the room in the form:
     *     Area: Forest
     *     Exits: north
     *     Items in this area: necronomicon
     *     Characters in this area: Shaman 
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "Area: " + name + "\n" + getExitString() + "\n" + 
                "Items in this area: " + displayAllItems() + "\n" + "Characters in this area: " + displayAllCharacters();
    }
    
    /**
     * 
     * @return An item from the room
     */
    public ArrayList<Item> getRoomItems() {
        return roomItems;
    }    
    
    
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**     
     * @param itemName The name of the item in the room
     * @return Returns an item in the room
     */
    public Item getItem(String itemName) {        
        
        for (Item i : roomItems) {
            if (i.getName().equals(itemName)) {
                return i;                
            }
        }        
        return null;
    }
    
    /** 
     * @return all items in the room
     */
    private String displayAllItems() {
        String allItems = "";
        for (Item i : roomItems) {
            allItems += i.getName() + " ";
        }
        return allItems;
    }
    
    /**
     * 
     * @return all characters in the room
     */
    private String displayAllCharacters() {
        String allCharacters = "";
        for (Character i : roomCharacters) {
            allCharacters += i.getName() + " ";
        }
        return allCharacters;
    }
    
    /**
     * Returns a character if a character is in the room,
     * else return null
     * @param characterName The name of the character in the room
     * @return Returns the character in the room if true, else return null
     */
    public Character getCharacter(String characterName) {
        
        for (Character i : roomCharacters) {
            if (i.getName().equals(characterName)) {
                return i;
            } 
        }
        return null;
    }
}

