package killerm.minecraft.commands;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.controller.ConfigController;
import killerm.minecraft.controller.GameController;
import killerm.minecraft.error.DiaHuntLogicException;
import killerm.minecraft.error.DiaHuntParameterException;
import killerm.minecraft.game.DiaChestGameData;
import killerm.minecraft.game.DiaHuntGameState;
import killerm.minecraft.game.PlayerGameData;
import killerm.minecraft.validator.ConfigValidator;
import killerm.minecraft.validator.GameValidator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiaHuntExecutor implements CommandExecutor {
    private Tester tester; //TODO: Remove tester when Plugin is finished
    private Printer printer;

    private ConfigValidator configValidator;
    private GameValidator gameValidator;

    private ConfigController configController;
    private GameController gameController;

    public DiaHuntExecutor(DiaHuntGameState diaHuntGameState, PlayerGameData playerGameData, DiaChestGameData diaChestGameData) {
        this.tester = new Tester();
        this.printer = new Printer();
        this.configValidator = new ConfigValidator();
        this.gameValidator = new GameValidator();
        this.configController = new ConfigController();
        this.gameController = new GameController(diaHuntGameState, playerGameData, diaChestGameData);
    }

    // For tests
    public DiaHuntExecutor(Tester tester, Printer printer, ConfigValidator configValidator, GameValidator gameValidator, ConfigController configController, GameController gameController) {
        this.tester = tester;
        this.printer = printer;
        this.configValidator = configValidator;
        this.gameValidator = gameValidator;
        this.configController = configController;
        this.gameController = gameController;
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
            case TEST3:
                tester.test3(player);
                break;
            case TEST4:
                tester.test4(player);
                break;
            case PLAY:
                gameValidator.validateStart(params);
                gameController.play(player, params);
                break;
            case JOIN:
                gameValidator.validateJoin(params);
                gameController.join(player, params);
                break;
            case LEAVE:
                gameValidator.validateLeave(params);
                gameController.leave(player);
                break;
            case STOP:
                gameValidator.validateStop(params);
                gameController.stop();
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
