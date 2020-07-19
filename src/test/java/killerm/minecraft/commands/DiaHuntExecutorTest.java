package killerm.minecraft.commands;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.controller.ConfigController;
import killerm.minecraft.controller.GameController;
import killerm.minecraft.controller.StatusController;
import killerm.minecraft.validator.ConfigValidator;
import killerm.minecraft.validator.GameValidator;
import killerm.minecraft.validator.StatusValidator;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;

class DiaHuntExecutorTest {
    private Printer printer = mock(Printer.class);
    private Tester tester = mock(Tester.class);
    private ConfigValidator configValidator = mock(ConfigValidator.class);
    private GameValidator gameValidator = mock(GameValidator.class);
    private StatusValidator statusValidator = mock(StatusValidator.class);
    private ConfigController configController = mock(ConfigController.class);
    private GameController gameController = mock(GameController.class);
    private StatusController statusController = mock(StatusController.class);
    private DiaHuntExecutor diaHuntExecutor = new DiaHuntExecutor(tester, printer, configValidator, gameValidator, statusValidator, configController, gameController, statusController);
    
    @Test
    public void GIVEN_command_WHEN_onCommand_with_non_player_THEN_execute_command_as_server() {
        // GIVEN
        String[] commandEntered = new String[]{"help"};

        // WHEN
        diaHuntExecutor.onCommand(null, null, null, commandEntered);

        // THEN
        Mockito.verify(printer, times(1)).tell(null, Message.VALID_COMMANDTYPES);
    }

    @Test
    public void GIVEN_command_and_player_WHEN_onCommand_with_player_THEN_execute_command_as_player() {
        // GIVEN
        String[] commandEntered = new String[]{"help"};
        Player player = mock(Player.class);

        // WHEN
        diaHuntExecutor.onCommand(player, null, null, commandEntered);

        // THEN
        Mockito.verify(printer, times(1)).tell(player, Message.VALID_COMMANDTYPES);
    }

    @Test
    public void GIVEN_command_needs_help_and_player_WHEN_onCommand_with_player_THEN_execute_printHelpCommand() {
        // GIVEN
        String[] commandEntered = new String[]{"config", "help"};
        Player player = mock(Player.class);

        // WHEN
        diaHuntExecutor.onCommand(player, null, null, commandEntered);

        // THEN
        Mockito.verify(printer, times(1)).tell(player, Message.DESCRIPTION_CONFIG);
    }

    @Test
    public void GIVEN_invalid_command_and_player_WHEN_onCommand_with_player_THEN_handle_Exception() {
        // GIVEN
        String[] commandEntered = new String[]{"feafae"};
        Player player = mock(Player.class);

        // WHEN
        diaHuntExecutor.onCommand(player, null, null, commandEntered);

        // THEN
        Mockito.verify(printer, times(1)).tellError(player, Message.COMMANDTYPE_NOT_VALID);
    }


    @Test
    public void GIVEN_invalid_command_WHEN_onCommand_with_non_player_THEN_handle_Exception() {
        // GIVEN
        String[] commandEntered = new String[]{"feafae"};

        // WHEN
        diaHuntExecutor.onCommand(null, null, null, commandEntered);

        // THEN
        Mockito.verify(printer, times(1)).broadcastError(Message.COMMANDTYPE_NOT_VALID);
    }

}