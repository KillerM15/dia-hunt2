package killerm.minecraft.commands;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.controller.ConfigController;
import killerm.minecraft.error.DiaHuntLogicException;
import killerm.minecraft.error.DiaHuntParameterException;
import killerm.minecraft.validator.ConfigValidator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiaHuntExecutor implements CommandExecutor {
    private Tester tester; //TODO: Remove tester when Plugin is finished
    private Printer printer;

    private ConfigController configController;

    private ConfigValidator configValidator;

    public DiaHuntExecutor() {
        this.tester = new Tester();
        this.printer = new Printer();
        this.configController = new ConfigController();
        this.configValidator = new ConfigValidator();
    }

    public DiaHuntExecutor(Tester tester, Printer printer, ConfigController configController, ConfigValidator configValidator) {
        this.tester = tester;
        this.printer = printer;
        this.configController = configController;
        this.configValidator = configValidator;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {
                Command command = new Command(args);

                if (command.needsHelp()) {
                    printHelpCommand(player, command);
                } else {
                    sendToController(player, command);
                }
            } catch (DiaHuntParameterException | DiaHuntLogicException e) {
                printer.tellError(player, e.getMessage());
            }
        }

        return true;
    }

    private void sendToController(Player player, Command command) {
        String[] params = command.getParameter();

        switch (command.getCommandType()) {
            case HELP:
                printer.tell(player, Message.VALID_COMMANDTYPES);
                break;
            case CONFIG:
                configValidator.validateExecute(params);
                configController.execute(player, params);
                break;
            case TEST1:
                tester.test1(player);
                break;
            case TEST2:
                tester.test2(player);
                break;
            default:
                throw new DiaHuntParameterException(Message.COMMAND_NOT_IMPLEMENTED);
        }
    }

    private void printHelpCommand(Player player, Command command) {
        String helpMessage = command.getCommandType().getDescription();
        printer.tell(player, helpMessage);
    }
}
