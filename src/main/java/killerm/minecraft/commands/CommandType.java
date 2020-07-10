package killerm.minecraft.commands;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntParameterException;

public enum CommandType {
    HELP(Message.HELP, Message.HELP_HELP),
    TEST1("TEST1", ""),
    TEST2("TEST2", "");

    private String commandInChat;
    private String help;

    private CommandType(String commandInChat, String help) {
        this.commandInChat = commandInChat;
        this.help = help;
    }

    public String getCommandInChat() {
        return commandInChat;
    }

    public String getHelp() {
        return help;
    }

    public static CommandType getCommandType(String commandTypeString) {
        for (CommandType d : CommandType.values()) {
            if (commandTypeString.equals(d.toString().toLowerCase())) {
                return d;
            }
        }

        throw new DiaHuntParameterException(Message.COMMANDTYPE_NOT_VALID);
    }
}
