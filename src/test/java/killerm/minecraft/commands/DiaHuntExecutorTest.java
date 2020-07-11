package killerm.minecraft.commands;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.*;

class DiaHuntExecutorTest {
    private Printer printer = mock(Printer.class);
    private DiaHuntExecutor diaHuntExecutor = new DiaHuntExecutor(printer);

    @Test
    void GIVEN_command_help_WHEN_onCommand_THEN_tell_player_valid_commands() {
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