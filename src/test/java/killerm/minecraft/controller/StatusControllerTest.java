package killerm.minecraft.controller;

import killerm.minecraft.communication.Printer;
import killerm.minecraft.game.data.GameStatus;
import  killerm.minecraft.game.data.PlayerGameData;
import killerm.minecraft.game.data.Team;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.HashSet;

import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class StatusControllerTest {
    private Printer printer = mock(Printer.class);
    private GameStatus diaHuntGameState = mock(GameStatus.class);
    private PlayerGameData playerGameData = mock(PlayerGameData.class);

    private StatusController statusController = new StatusController(printer, diaHuntGameState, playerGameData);

    @Test
    public void GIVEN_players_with_teams_WHEN_printStatus_THEN_playerGameData_team_called() {
        // GIVEN
        Player player = mock(Player.class);
        Player playerInGame1 = mock(Player.class);
        Player playerInGame2 = mock(Player.class);
        Collection<Player> playersInGame = new HashSet<>();
        playersInGame.add(playerInGame1);
        playersInGame.add(playerInGame2);
        doReturn(playersInGame).when(playerGameData).players();
        doReturn(Team.AQUA).when(playerGameData).team(playerInGame1);
        doReturn(Team.LAVA).when(playerGameData).team(playerInGame2);

        // WHEN
        statusController.printStatus(player);

        // THEN
        Mockito.verify(playerGameData, Mockito.times(1)).team(playerInGame1);
        Mockito.verify(playerGameData, Mockito.times(1)).team(playerInGame2);
    }
}