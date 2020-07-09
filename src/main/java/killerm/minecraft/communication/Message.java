package killerm.minecraft.communication;

import killerm.minecraft.commands.CommandType;
import killerm.minecraft.helper.CollectionDelimiter;

// Intentional code duplication and ugly inner class to increase readability
public class Message {
    public static final String COLLECTION_DELIMITER = ", ";
    public static final String VALID_COMMANDTYPES = "Valid commands: " + CollectionDelimiter.delimit(CommandType.values(), CommandType::getCommandInChat);
    public static final String COMMANDTYPE_NOT_VALID = "This command isn't valid! " + VALID_COMMANDTYPES;
    public static final String HELP = "help";
    public static final String HELP_HELP = "";
}
