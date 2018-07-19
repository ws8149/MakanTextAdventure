/** 
 *
 * This class is part of the "MAKAN" application. 
 * "MAKAN" is a very simple, text based adventure game.  
 *
 * The item class stores information of an item 
 *   
 */
public class Item {
    private String name, description;
    private double weight;
    private int effect; 
    
    
    /**
     * Construct an item object provided the name, weight and description  
     * of the item is given
     * @param name Item's name
     * @param weight Item's weight
     * @param description Item's description
     * @param effect Item's effect which heals or damages the player, could be positive or negative
     */
    public Item(String name, double weight, String description, int effect) {
        this.name = name;
        this.weight = weight;       
        this.description = description;        
        this.effect = effect;
    }
    
    /**
     * 
     * @return Name of the item
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return Weight of the item
     */
    public double getWeight() {
        return weight;
    }
    
    /**
     * 
     * @return Description of the item
     */
    public String getDescription() {
        return description;
    }   
    
    /**
     * 
     * @return item's effect 
     */
    public int getEffect() {
        return effect;
    }

    

    
    
     
    
    
    
    
    
}
