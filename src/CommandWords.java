public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "quit", "help", "look", "grab", "drop"

            //return word to go back to the last room using a stack
    };

    public CommandWords()
    {
        // nothing to do at the moment...
    }

    public static String[] getValidCommands(){
        return validCommands;
    }

    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
}
