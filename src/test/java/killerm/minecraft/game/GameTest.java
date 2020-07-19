package killerm.minecraft.game;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.data.PlayerBackup;
import killerm.minecraft.data.RegionBackup;
import killerm.minecraft.error.ParameterException;
import killerm.minecraft.helper.PlayerNameFixer;
import killerm.minecraft.manager.ScoreboardManager;
import killerm.minecraft.utilities.ItemRemover;
import killerm.minecraft.utilities.WorldProvider;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class GameTest {
    // Most methods are tested in game

    private Printer printer = mock(Printer.class);
    private DiaHuntGameState diaHuntGameState = mock(DiaHuntGameState.class);
    private DiaChestGameData diaChestGameData = mock(DiaChestGameData.class);
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
    private BukkitTask startingTask = mock(BukkitTask.class);
    private ItemRemover itemRemover = mock(ItemRemover.class);
    private ScoreboardManager scoreboardManager = mock(ScoreboardManager.class);
    private WorldProvider worldProvider = mock(WorldProvider.class);
    private Game game = new Game(printer, diaHuntGameState, diaChestGameData, playerGameData, gameBackup, gameInitPrinter, diamondIncreaser, locationSetter, statsGiver, itemGiver, playerNameFixer, startingTask, itemRemover, scoreboardManager, worldProvider);

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
        ParameterException thrown = assertThrows(
                ParameterException.class,
                () -> game.leave(player),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.NOT_INGAME));
    }

    @Test
    public void GIVEN_GameStatus_RUNNING_player_WHEN_leave_THEN_playerBackup_restore_diaPlayerData_remove_statsGiver_clear() {
        // GIVEN
        doReturn(GameStatus.RUNNING).when(diaHuntGameState).getGameStatus();
        Player player = mock(Player.class);
        doReturn(true).when(playerGameData).inGame(player);

        // WHEN
        game.leave(player);

        // THEN
        Mockito.verify(playerBackup, times(1)).restore(player);
        Mockito.verify(playerGameData).remove(player);
        Mockito.verify(statsGiver).clear(player);
    }

    @Test
    public void GIVEN_gameStarter_WHEN_gameInitializeMocked_THEN_GameStatus_STARTING_and_then_GameStatus_RUNNING() {
        // GIVEN
        Player gameStarter = mock(Player.class);
        doReturn("name").when(gameStarter).getName();

        // WHEN
        game.startInitializeMocked(gameStarter, null);

        // THEN
        Mockito.verify(diaHuntGameState, times(1)).setGameStatus(GameStatus.STARTING);
        Mockito.verify(diaHuntGameState, times(1)).setGameStatus(GameStatus.RUNNING);
    }

    @Test
    public void GIVEN_gameStarter_and_invited_player_names_WHEN_gameInitializeMocked_THEN_printGameInit_with_gameStarterName_and_invitedPlayerNames_called() {
        // GIVEN
        Player gameStarter = mock(Player.class);
        String gameStarterName = "name";
        doReturn(gameStarterName).when(gameStarter).getName();
        String[] invitedPlayerNames = new String[]{"player1", "player2"};
        doReturn(invitedPlayerNames).when(playerNameFixer).fixCase(invitedPlayerNames);

        // WHEN
        game.startInitializeMocked(gameStarter, invitedPlayerNames);

        // THEN
        Mockito.verify(gameInitPrinter).printGameInit(gameStarterName, invitedPlayerNames);
    }

    @Test
    public void GIVEN_gameStarter_WHEN_gameInitializeMocked_THEN_gameStarter_join_game() {
        // GIVEN
        Player gameStarter = mock(Player.class);

        // WHEN
        game.startInitializeMocked(gameStarter, null);

        // THEN
        Mockito.verify(playerGameData).add(gameStarter);
    }

    @Test
    public void GIVEN_gameStarter_as_random_player_of_Team_AQUA_WHEN_startInitializeMocked_THEN_giveAquaDiaChest_to_gameStarter_twice() {
        // GIVEN
        Player gameStarter = mock(Player.class);
        doReturn(gameStarter).when(playerGameData).randomPlayer(Team.AQUA);

        // WHEN
        game.startInitializeMocked(gameStarter, null);

        // THEN
        Mockito.verify(itemGiver, times(2)).giveAquaDiaChest(gameStarter);
    }

    @Test
    public void GIVEN_gameStarter_as_random_player_of_Team_LAVA_WHEN_startInitializeMocked_THEN_giveLavaDiaChest_to_gameStarter_twice() {
        // GIVEN
        Player gameStarter = mock(Player.class);
        doReturn(gameStarter).when(playerGameData).randomPlayer(Team.LAVA);

        // WHEN
        game.startInitializeMocked(gameStarter, null);

        // THEN
        Mockito.verify(itemGiver, times(2)).giveLavaDiaChest(gameStarter);
    }

    @Test
    public void GIVEN_player_joined_WHEN_stop_THEN_gameInitPrinter_stop() {
        // GIVEN
        Player player = mock(Player.class);
        Collection<Player> players = new HashSet<>();
        players.add(player);

        doReturn(players).when(playerGameData).players();
        doReturn(GameStatus.STARTING).when(diaHuntGameState).getGameStatus();
        doReturn(true).when(playerGameData).inGame(player);

        // WHEN
        game.stop();

        // THEN
        Mockito.verify(gameInitPrinter).stop();
    }
}