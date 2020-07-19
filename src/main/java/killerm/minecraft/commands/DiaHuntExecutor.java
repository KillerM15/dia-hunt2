package killerm.minecraft.commands;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.controller.ConfigController;
import killerm.minecraft.controller.GameController;
import killerm.minecraft.controller.StatusController;
import killerm.minecraft.error.LogicException;
import killerm.minecraft.error.ParameterException;
import killerm.minecraft.game.data.ChestGameData;
import killerm.minecraft.game.data.GameStatus;
import killerm.minecraft.game.data.PlayerGameData;
import killerm.minecraft.validator.ConfigValidator;
import killerm.minecraft.validator.GameValidator;
import killerm.minecraft.validator.StatusValidator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiaHuntExecutor implements CommandExecutor {
    private Printer printer;

    private ConfigValidator configValidator;
    private GameValidator gameValidator;
    private StatusValidator statusValidator;

    private ConfigController configController;
    private GameController gameController;
    private StatusController statusController;

    private Tester tester; //TODO: Remove tester when Plugin is finished

    public DiaHuntExecutor(GameStatus gameStatus, PlayerGameData playerGameData, ChestGameData chestGameData) {
        this.tester = new Tester();
        this.printer = new Printer();
        this.configValidator = new ConfigValidator();
        this.gameValidator = new GameValidator();
        this.statusValidator = new StatusValidator();
        this.configController = new ConfigController();
        this.gameController = new GameController(gameStatus, playerGameData, chestGameData);
        this.statusController = new StatusController(gameStatus, playerGameData);
    }

    public DiaHuntExecutor(Tester tester, Printer printer, ConfigValidator configValidator, GameValidator gameValidator, StatusValidator statusValidator, ConfigController configController, GameController gameController, StatusController statusController) {
        this.tester = tester;
        this.printer = printer;
        this.configValidator = configValidator;
        this.gameValidator = gameValidator;
        this.statusValidator = statusValidator;
        this.configController = configController;
        this.gameController = gameController;
        this.statusController = statusController;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            executeCommandAsPlayer(player, args);
        } else {
            executeCommandAsServer(args);
        }

        return true;
    }

    private void executeCommandAsPlayer(Player player, String[] args) {
        try {
            Command command = new Command(args);
            if (command.needsHelp()) {
                printHelpCommand(player, command);
            } else {
                sendToController(player, command);
            }
        } catch (ParameterException | LogicException e) {
            printer.tellError(player, e.getMessage());
        }
    }

    private void executeCommandAsServer(String[] args) {
        try {
            Command command = new Command(args);
            sendToController(null, command);
        } catch (ParameterException | LogicException e) {
            printer.broadcastError(e.getMessage());
        }
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
            case STATUS:
                statusValidator.validateStatus(params);
                statusController.printStatus(player);
                break;
            default:
                throw new ParameterException(Message.COMMAND_NOT_IMPLEMENTED);
        }
    }

    private void printHelpCommand(Player player, Command command) {
        String helpMessage = command.getCommandType().getDescription();
        printer.tell(player, helpMessage);
    }
}
