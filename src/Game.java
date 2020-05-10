import java.sql.ClientInfoStatus;
import java.util.ArrayList;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private ArrayList<Item> inventory = new ArrayList<>();

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }

    private void createRooms() {
        Room patio, garage, basement, livingRoom, secondFloor;

        // create the rooms
        patio = new Room("standing in the front yard");
        garage = new Room("inside the garage");
        basement = new Room("in the basement");
        livingRoom = new Room("in the living room");
        secondFloor = new Room("in the second floor");

        patio.setExits("north", livingRoom);
        patio.setExits("east", garage);
        garage.setExits("west", patio);
        garage.setExits("down", basement);
        basement.setExits("up", garage);
        livingRoom.setExits("south", patio);
        livingRoom.setExits("up", secondFloor);
        secondFloor.setExits("down", livingRoom);

        patio.getThings().add(new Item("bush", 14));
        patio.getThings().add(new Item("cat", 12));
        garage.getThings().add(new Item("car", 3500));
        garage.getThings().add(new Item("box", 20));
        livingRoom.getThings().add(new Item("lamp", 23));
        secondFloor.getThings().add(new Item("cobweb", 0.5));
        secondFloor.getThings().add(new Item("vase", 13));

        currentRoom = patio;  // start game outside
    }

    /**
     * Main play routine.  Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the Wumpus World");
        System.out.println("Wumpus World is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        currentRoom.printKeys();
        System.out.println();
    }

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
        } else if (commandWord.equals("look")) {
            look(command);
        } else if (commandWord.equals("grab")) {
            grab(command);
        } else if (commandWord.equals("drop")) {
                drop(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        for (int i = 0; i < CommandWords.getValidCommands().length; i++)
            System.out.print(CommandWords.getValidCommands()[i] + " ");
        System.out.println();
        //CHANGE THIS TO PRINT ARRAY ATTRIBUTE FROM COMMANDWORDS
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;

        nextRoom = currentRoom.getExit(direction);


        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println("You are " + currentRoom.getDescription());
            System.out.print("Exits: ");
            currentRoom.printKeys();
            System.out.println();
        }
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }

    private void look(Command command) {

        if (currentRoom.getThings().size() == 0)
            System.out.println("This room is empty");
        else

            for (int i = 0; i < currentRoom.getThings().size(); i++) {//traverse through array
                Item item = currentRoom.getThings().get(i);
                System.out.println("There is a(n) " + item.getName() + " in the room that weighs " + item.getWeight() + " pounds");
            }
    }


    private void grab(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Grab what?");
            return;
        }

        String thing = command.getSecondWord();

        int foundIn = 0;
        boolean found = false;

        for (int i = 0; i < currentRoom.getThings().size(); i++) {//traverse through array
            if (currentRoom.getThings().get(i).getName().equals(thing)) {
                foundIn = i;
                found = true;
            }
        }
        if (!found) System.out.println("That item is not in the room!");// if thing is not in array
        if (found) {
            inventory.add(currentRoom.getThings().get(foundIn));
            System.out.println("You've added a(n) " + currentRoom.getThings().get(foundIn).getName() + " to your inventory");
            currentRoom.getThings().remove(foundIn);

        }
    }

    private void drop(Command command) {
        if(!command.hasSecondWord()) {
        System.out.println("Drop what?");
        System.out.println("These are the items in your inventory:");
        for (int i = 0; i < inventory.size(); i++)
            System.out.print(inventory.get(i).getName() + " ");
            System.out.println();
        return;
        }

        String thing = command.getSecondWord();

        int foundIn = 0;
        boolean found = false;

        for(int i = 0; i<inventory.size(); i++) {//traverse through array
            if (inventory.get(i).getName().equals(thing)) {
                foundIn = i;
                found = true;
            }
        }
        if(!found)
            System.out.println("That item is not in your inventory!");// if thing is not in array
        if(found) {
            currentRoom.getThings().add(inventory.get(foundIn));
            System.out.println("You've removed a(n) " + inventory.get(foundIn).getName() + " from your inventory");
            inventory.remove(foundIn);
        }
    }
}

