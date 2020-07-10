package killerm.minecraft.commands;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.error.DiaHuntLogicException;
import killerm.minecraft.error.DiaHuntParameterException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiaHuntExecutor implements CommandExecutor {
    Printer printer;

    public DiaHuntExecutor() {
        this.printer = new Printer();
    }

    public DiaHuntExecutor(Printer printer) {
        this.printer = printer;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {
                Command command = new Command(args);
                sendToController(player, command);
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
            case TEST1:
                new Tester().test1(player);
                break;
            case TEST2:
                new Tester().test2(player);
                break;
            default:
                throw new DiaHuntParameterException(Message.COMMAND_NOT_IMPLEMENTED);
        }
    }
}
