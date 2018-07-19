
/**
 * Non playable characters that the player can interact with.
 * They can move from room to room and accept items from the player.  
 * @author weisheng3725
 */
public class Character {
    private String name, wantedItem;    
    private Item item;   
    
    
    
    /**
     * A character can be assigned a name, item, and a wanted item
     * @param name Character's name
     * @param item The item the character is currently holding
     * @param wantedItem The item that the character is looking for
     */    
    public Character(String name, Item item, String wantedItem) {
        this.name = name;     
        this.item = item;
        this.wantedItem = wantedItem;
    }
    
    /**
     * 
     * @return Name of the character
     */
    public String getName() {
        return name;
    }
    /**
     * 
     * @return Wanted item that the character is looking for
     */
    public String getWantedItem() {
        return wantedItem;
    }
    
    /**      
     * @return Item that the character is holding
     */
    public Item getItem() {
        return item;
    }
    
    /**
     * 
     * @param itemName The name of the item given to the character
     * @return True if the character wants the item, else return false
     */
    public boolean wantsItem(String itemName) {
        if (wantedItem.equals(itemName)) {
            return true;
        } else {
            System.out.println("Character does not want this item");
            return false;
        }
    }
    
    
    

    
    
    
    
    
    
    
    
    
}
