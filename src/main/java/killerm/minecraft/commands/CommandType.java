package killerm.minecraft.commands;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntParameterException;

public enum CommandType {
    HELP(Message.HELP_DESCRIPTION),
    TEST1(""),
    TEST2("");

    private String description;

    private CommandType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public String getDescription() {
        return description;
    }

    public static CommandType getCommandType(String commandTypeString) {
        for (CommandType d : CommandType.values()) {
            if (commandTypeString.equals(d.toString())) {
                return d;
            }
        }

        throw new DiaHuntParameterException(Message.COMMANDTYPE_NOT_VALID);
    }
}
