package killerm.minecraft.commands;

import java.util.Arrays;

public class Command {
    private CommandType commandType;
    private String[] parameter;

    Command(String[] args) {
        if (args.length == 0) {
            constructHelpCommand();
        } else {
            commandType = CommandType.getCommandType(args[0]);
            parameter = Arrays.copyOfRange(args, 1, args.length);
        }
    }

    private void constructHelpCommand() {
        commandType = CommandType.HELP;
        parameter = new String[]{};
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String[] getParameter() {
        return parameter;
    }
}
