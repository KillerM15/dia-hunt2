package killerm.minecraft.game;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.data.PlayerBackup;
import killerm.minecraft.data.RegionBackup;
import killerm.minecraft.error.DiaHuntParameterException;
import killerm.minecraft.helper.PlayerNameFixer;
import killerm.minecraft.utilities.ItemRemover;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class GameTest {
    // Most methods are tested in game

    private Printer printer = mock(Printer.class);
    private DiaHuntGameState diaHuntGameState = mock(DiaHuntGameState.class);
    private PlayerGameData playerGameData = mock(PlayerGameData.class);
    private GameBackup gameBackup = mock(GameBackup.class);
    private PlayerBackup playerBackup = mock(PlayerBackup.class);
    private RegionBackup mapBackup = mock(RegionBackup.class);
    private GameInitPrinter gameInitPrinter = mock(GameInitPrinter.class);
    private DiamondIncreaser diamondIncreaser = mock(DiamondIncreaser.class);
    private LocationSetter locationSetter = mock(LocationSetter.class);
    private StatsGiver statsGiver = mock(StatsGiver.class);
    private ItemGiver itemGiver = mock(ItemGiver.class);
    private PlayerNameFixer playerNameFixer = mock(PlayerNameFixer.class);
    private ItemRemover itemRemover = mock(ItemRemover.class);
    private Game game = new Game(printer, diaHuntGameState, playerGameData, gameBackup, gameInitPrinter, diamondIncreaser, locationSetter, statsGiver, itemGiver, playerNameFixer, itemRemover);

    @BeforeEach
    public void setup() {
        doReturn(playerBackup).when(gameBackup).getPlayerBackup();
        doReturn(mapBackup).when(gameBackup).getMapBackup();
    }

    @Test
    public void GIVEN_player_WHEN_join_team_null_THEN_player_added() {
        // GIVEN
        Player player = mock(Player.class);

        // WHEN
        game.join(player, null);

        // THEN
        Mockito.verify(playerGameData, times(1)).add(player);
    }

    @Test
    public void GIVEN_player_WHEN_join_team_empty_THEN_player_added() {
        // GIVEN
        Player player = mock(Player.class);

        // WHEN
        game.join(player, new String[]{});

        // THEN
        Mockito.verify(playerGameData, times(1)).add(player);
    }

    @Test
    public void GIVEN_player_WHEN_join_team_aqua_THEN_player_added_to_aqua() {
        // GIVEN
        Player player = mock(Player.class);

        // WHEN
        game.join(player, new String[]{"aqua"});

        // THEN
        Mockito.verify(playerGameData, times(1)).add(player, Team.AQUA);
    }

    @Test
    public void GIVEN_player_not_added_ingame_WHEN_leave_THEN_Exception() {
        // GIVEN
        Player player = mock(Player.class);

        // WHEN
        DiaHuntParameterException thrown = assertThrows(
                DiaHuntParameterException.class,
                () -> game.leave(player),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.NOT_INGAME));
    }

    @Test
    public void GIVEN_GameStatus_RUNNING_player_WHEN_leave_THEN_playerBackup_restore_playerGameData_remove_statsGiver_clear() {
        // GIVEN
        doReturn(GameStatus.RUNNING).when(diaHuntGameState).getGameStatus();
        Player player = mock(Player.class);
        doReturn(true).when(playerGameData).contains(player);

        // WHEN
        game.leave(player);

        // THEN
        Mockito.verify(playerBackup, times(1)).restore(player);
        Mockito.verify(playerGameData).remove(player);
        Mockito.verify(statsGiver).clear(player);
    }
}