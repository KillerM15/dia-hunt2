package killerm.minecraft.commands;

import java.util.Arrays;

public class Command {
    private CommandType commandType;
    private String[] parameter;

    Command(String[] args) {
        if (isGeneralHelpCommand(args)) {
            constructCommandTypeHelp();
        } else {
            constructCommandFromArgs(args);
        }
    }

    private boolean isGeneralHelpCommand(String[] args) {
        return args.length == 0 || args[0].equals(CommandType.HELP.toString());
    }

    private void constructCommandTypeHelp() {
        commandType = CommandType.HELP;
        parameter = new String[]{""};
    }

    private void constructCommandFromArgs(String[] args) {
        commandType = CommandType.getInstance(args[0]);
        parameter = Arrays.copyOfRange(args, 1, args.length);
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String[] getParameter() {
        return parameter;
    }

    public boolean needsHelp() {
        if (parameter.length == 0 && commandType.isEmptyCommandEqualsHelp()) {
            return true;
        }

        if(parameter.length > 0 && parameter[0].equals("help")){
            return true;
        }

        return false;
    }
}
