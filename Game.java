
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is the main class of the "MAKAN" application. "MAKAN" is a very simple, text based adventure game.
 * Users can walk around some scenery, pick up, consume or give items to other characters 
 *
 * To play this game, create an instance of this class and call the "play"
 * method.
 *
 * This main class creates and initialises all the others: it creates all rooms, items and characters
 * It also creates the parser and the player before starting the game the game. It also evaluates and executes the
 * commands that the parser returns.
 *
 * @author Michael KÃ¶lling and David J. Barnes
 * @version 2016.02.29
 */
public class Game {

    private Parser parser;    
    private ArrayList<Room> previousRooms;//records the previous rooms the player has been in
    private Player player;
    private ArrayList<Room> rooms;    
    private Character shaman, merchant;
    private Room townCenter, house, beach, slums, tavern, forest, gate, cave, portal, lake;
    private Item necronomicon, indomee, elixir, bread, boulder, gold, whiskey;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createItems();
        createCharacters();
        createRooms();              
        parser = new Parser();
        this.player = new Player(lake); //player's starting location is the lake        
        
    }

    /**
     * Creates items
     */
    private void createItems() {
        necronomicon = new Item("necronomicon", 3, "the forbidden book", -100);
        indomee = new Item("indomee", 0.5, "delicious noodles, heals 100 hp", 100);
        bread = new Item("bread", 1, "plain old bread, heals 20 hp", 20);
        gold = new Item("gold", 5, "can be traded with a merchant for a special item", -50);        
        elixir = new Item("elixir", 1, "the legendary elixir (also known as teh tarikh) that extends life", 1000);        
        boulder = new Item("boulder", 99, "extremely heavy", 0);
        whiskey = new Item("whiskey", 2, " man's best friend, heals 50 hp", 50);
    }

    /**
     * 
     * Creates rooms and links their exits together,     * 
     * Initialises the rooms and previousRooms list
     * Characters and items are placed into their respective rooms
     */    
    private void createRooms() {
        // create the rooms        
        this.townCenter = new Room("town center");
        this.house = new Room("player's house");
        this.beach = new Room("beach");
        this.slums = new Room("slums");
        this.tavern = new Room("tavern");
        this.forest = new Room("forest");
        this.gate = new Room("gate");
        this.cave = new Room("cave");
        this.portal = new Room("portal");
        this.lake = new Room("lake");

        // initialise room exits
        house.setExit("west", beach);
        house.setExit("east", townCenter);
        beach.setExit("east", house);
        townCenter.setExit("north", slums);
        townCenter.setExit("east", tavern);
        townCenter.setExit("west", house);
        townCenter.setExit("south", forest);
        tavern.setExit("west", townCenter);
        slums.setExit("north", gate);
        slums.setExit("south", townCenter);
        gate.setExit("south", slums);

        forest.setExit("north", townCenter);
        forest.setExit("east", portal);
        forest.setExit("west", cave);
        forest.setExit("south", lake);
        cave.setExit("east", forest);
        lake.setExit("north", forest);

        //add rooms to rooms list
        this.rooms = new ArrayList<Room>();
        this.previousRooms = new ArrayList<Room>();
        rooms.add(gate);
        rooms.add(slums);
        rooms.add(beach);
        rooms.add(house);
        rooms.add(townCenter);
        rooms.add(tavern);
        rooms.add(cave);
        rooms.add(forest);       
        rooms.add(lake);
        
        
        //set items and characters in rooms
        beach.setItem(necronomicon);
        lake.setCharacter(shaman);
        townCenter.setCharacter(merchant);
        cave.setItem(gold);
        slums.setItem(bread);        
        townCenter.setItem(boulder);
        tavern.setItem(whiskey);
        
    }

    /**
     * Creates characters
     *
     */
    private void createCharacters() {
        this.shaman = new Character("shaman", elixir, "necronomicon");
        this.merchant = new Character("merchant", indomee, "gold");        
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        boolean finished = false;        
        
        while (!finished) {            
            //if time is 0 or lesser, the player loses and the game ends
            if (player.getHealth() <= 0) {                
                System.out.println("YOU DIED");
                break;                                                  
            //if the game has ended, play the end sequence
            } else if (player.getHealth() > 1000) {
                System.out.println("YOU WIN");
                break;
            //else continue with the game
            } else  {                
                Command command = parser.getCommand();
                finished = processCommand(command);
            }
        }
        System.out.println("Thank you for playing.  Good bye.");

    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to MAKAN");
        System.out.println("Find and consume the legendary elixir to win the game");                
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printUserInterface();
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("back")) {
            goBack();
        } else if (commandWord.equals("take")) {
            takeItem(command);
        } else if (commandWord.equals("inventory")) {
            displayInventory();
        } else if (commandWord.equals("drop")) {
            dropItem(command);
        } else if (commandWord.equals("give")) {
            giveItem(command);
        } else if (commandWord.equals("consume")) {
            consumeItem(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:
    /**
     * Print out some help information. Here we print some stupid, cryptic
     * message and a list of the command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander around town.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to move in one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     * If the next room is a portal, transport the player to a random room.
     * Time passes when the player moves into a new room
     * 
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);
        
        //If there is no room in a particular direction, print out a message
        if (nextRoom == null) {
            System.out.println("There is nothing over there");
        } else {
            //if the player enters the portal, they are teleported to a random room
            if (nextRoom.getName().equals("portal")) {
                System.out.println("YOU HAVE ENTERED A PORTAL");
                System.out.println("YOU HAVE BEEN TRANSPORTED TO A RANDOM LOCATION");
                nextRoom = getRandomRoom();
            }
            previousRooms.add(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);            
            moveCharacters();
            player.decreaseHealth(10);
            printUserInterface();
        }
        
    }

    /**
     * Goes back to the previous room. If there is no previous room, print an
     * error message.
     */
    private void goBack() {
        //tracks the last room that the player has been in
        int previousRoomIndex = previousRooms.size() - 1;
        
        //if there is a room that the player has been in, set the current room to the previous room                    
        if (previousRooms.size() > 0) {            
            player.setCurrentRoom(previousRooms.get(previousRoomIndex));            
            
            //The previous room is removed since it is no longer required
            previousRooms.remove(previousRoomIndex);
            
            printUserInterface();
            moveCharacters();
            player.decreaseHealth(10);
        } else {
            System.out.println("There is no where to go back to.");
        }

    }

    /**      
     * Executes the take item command
     * If there is no second word, print an error message
     */
    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Get what?");
            return;
        }
        
       String itemName = command.getSecondWord();        
       
       player.takeItem(itemName);                
        System.out.println("");
        printUserInterface();

    }

    /**
     * Executes the drop item command      
     * If there is no second word, print an error message
     */
    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Get what?");
            return;
        }

        String itemName = command.getSecondWord();
        player.dropItem(itemName);

    }

    /**
     * Executes the give item command, which triggers an exchange between the 
     * player and the character
     * If there is no second or third word, print an error message
     * 
     */
    private void giveItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Give what?");
            return;
        }

        if (!command.hasThirdWord()) {
            // if there is no third word, we don't know where to go...
            System.out.println("Give to who?");
            return;
        }

        String characterName = command.getSecondWord();
        String itemName = command.getThirdWord();
        
        //The character the player is giving the item to
        Character character = player.getCurrentRoom().getCharacter(characterName);
        Item givenItem = player.getItem(itemName);               
               
        //if the receiver is not in the room, print a message
        if (character == null) {
            System.out.println("This character is not in the room");                               
        //if the player does not have the item, print a message
        } else if (givenItem == null) {
            System.out.println("You do not have this item");                  
        } else {
            //if the receiver wants the item, the given item is traded for another item
            if (character.wantsItem(itemName)) {
                player.dropItem(itemName);
                player.receiveItem(character.getItem());
            }
        }
        

    }
    
    /**
     * Executes the consume item command          
     * If there is no second word, print an error message 
     */
    private void consumeItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Use what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        player.consumeItem(itemName);        
        printUserInterface();
    }        
    
        
    /**
     * Executes the inventory command      
     */
    private void displayInventory() {
        System.out.println("Player inventory: ");
        player.displayInventory();
    }

    
    /**
     * Allows for multiple characters to be moved in the game
     */
    private void moveCharacters() {
        moveCharacter(merchant, getRandomRoom());
    }
    
    /**
     * Moves a character into a different room
     *
     * @param character The character which is about to be moved
     * @param room The new room the character will be in
     */
    private void moveCharacter(Character character, Room room) {
        
        //search for the character's current location and remove them from there
        for (Room r : rooms) {            
            if (r.getCharacter(character.getName()) != null) {
                r.removeCharacter(character);
            }
        }
        
        //set the character to a new location
        room.setCharacter(character);        
        
    }

    /**
     * Returns a random room from the list of rooms
     * 
     */
    private Room getRandomRoom() {
        Random rand = new Random();
        return rooms.get(rand.nextInt(rooms.size()));
    }
    
    /**
     * Prints out the user interface
     */
    private void printUserInterface() {
        System.out.println(player.getCurrentRoom().getLongDescription());
        System.out.println("Health left: " + player.getHealth());
    }

    /**
     * "Quit" was entered. Check the rest of the command to see whether we
     * really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }
}
