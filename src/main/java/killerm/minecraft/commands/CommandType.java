package killerm.minecraft.commands;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntParameterException;

public enum CommandType {
    HELP(Message.DESCRIPTION_EMPTY, true),
    TEST1(Message.DESCRIPTION_EMPTY, false),
    TEST2(Message.DESCRIPTION_EMPTY, false),
    TEST3(Message.DESCRIPTION_EMPTY, false),
    TEST4(Message.DESCRIPTION_EMPTY, false),
    CONFIG(Message.DESCRIPTION_CONFIG, true),
    PLAY(Message.DESCRIPTION_START, false),
    JOIN(Message.DESCRIPTION_JOIN, false),
    LEAVE(Message.DESCRIPTION_LEAVE, false),
    STOP(Message.DESCRIPTION_STOP, false);

    private String description;
    private boolean emptyCommandEqualsHelp; // "/diahunt command" -> results in help, if true

    private CommandType(String description, boolean emptyCommandEqualsHelp) {
        this.description = description;
        this.emptyCommandEqualsHelp = emptyCommandEqualsHelp;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public String getDescription() {
        return description;
    }

    public static CommandType getInstance(String commandTypeString) {
        for (CommandType d : CommandType.values()) {
            if (commandTypeString.equals(d.toString())) {
                return d;
            }
        }

        throw new DiaHuntParameterException(Message.COMMANDTYPE_NOT_VALID);
    }

    public boolean isEmptyCommandEqualsHelp() {
        return emptyCommandEqualsHelp;
    }
}
