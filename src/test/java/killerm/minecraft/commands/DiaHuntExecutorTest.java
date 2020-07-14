package killerm.minecraft.commands;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.controller.ConfigController;
import killerm.minecraft.controller.GameController;
import killerm.minecraft.validator.ConfigValidator;
import killerm.minecraft.validator.GameValidator;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;

class DiaHuntExecutorTest {
    private Printer printer = mock(Printer.class);
    private Tester tester = mock(Tester.class);
    private ConfigController configController = mock(ConfigController.class);
    private ConfigValidator configValidator = mock(ConfigValidator.class);
    private GameController gameController = mock(GameController.class);
    private GameValidator gameValidator = mock(GameValidator.class);
    private DiaHuntExecutor diaHuntExecutor = new DiaHuntExecutor(tester, printer, configValidator, gameValidator, configController, gameController);

    @Test
    public void GIVEN_command_help_WHEN_onCommand_THEN_tell_player_valid_commands() {
        // GIVEN
        String[] commandEntered = new String[]{"help", "1", "2", "3"};
        Player player = mock(Player.class);
        org.bukkit.command.Command command = mock(org.bukkit.command.Command.class);

        // WHEN
        diaHuntExecutor.onCommand(player, command, "", commandEntered);

        // THEN
        Mockito.verify(printer, times(1)).tell(player, Message.VALID_COMMANDTYPES);
    }
}