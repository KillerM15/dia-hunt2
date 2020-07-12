package killerm.minecraft.game;

import killerm.minecraft.communication.NameChanger;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.utilities.Team;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.mock;

class GameDataTest {
    private Printer printer = mock(Printer.class);
    private NameChanger nameChanger = mock(NameChanger.class);
    private GameData gameData = new GameData(printer, nameChanger);

    @Test
    public void WHEN_getGameStatus_THEN_return_OFF() {
        assertEquals(GameStatus.OFF, gameData.getGameStatus());
    }

    @Test
    public void GIVEN_GameStatus_WHEN_getGameStatus_THEN_return_gameStatus() {
        // GIVEN
        GameStatus gameStatus = GameStatus.STARTING;

        // WHEN
        gameData.setGameStatus(gameStatus);

        // THEN
        assertEquals(gameStatus, gameData.getGameStatus());
    }

    @Test
    public void GIVEN_player_WHEN_add_THEN_contains_player() {
        // GIVEN
        Player player = mock(Player.class);

        // WHEN
        gameData.add(player);

        // THEN
        assert (gameData.contains(player));
    }

    @Test
    public void GIVEN_player_lava_added_WHEN_team_THEN_team_equals_lava() {
        // GIVEN
        Team team = Team.LAVA;
        Player player = mock(Player.class);
        gameData.add(player, team);

        // WHEN
        Team actualTeam = gameData.team(player);

        //THEN
        assertEquals(team, actualTeam);
    }

    @Test
    public void GIVEN_added_player_WHEN_remove_THEN_does_not_contain() {
        // GIVEN
        Player player = mock(Player.class);
        gameData.add(player);

        // WHEN
        gameData.remove(player);

        // THEN
        assert (!gameData.contains(player));
    }

    @Test
    public void GIVEN_2_players_added_WHEN_players_THEN_players_returned() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        gameData.add(player1);
        gameData.add(player2);

        // WHEN
        Collection actualPlayers = gameData.players();

        // THEN
        assert (actualPlayers.contains(player1));
        assert (actualPlayers.contains(player2));
    }

    @Test
    public void GIVEN_2_players_lava_1_player_aqua_added_WHEN_players_with_lava_THEN_2_players_returned() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        gameData.add(player1, Team.LAVA);
        gameData.add(player2, Team.LAVA);
        gameData.add(player3, Team.AQUA);

        // WHEN
        Collection players = gameData.players(Team.LAVA);

        // THEN
        assert (players.contains(player1));
        assert (players.contains(player2));
        assert (!players.contains(player3));
    }

    @Test
    public void GIVEN_player_added_WHEN_hasPlayers_THEN_true() {
        // GIVEN
        Player player = mock(Player.class);
        gameData.add(player, Team.LAVA);

        // WHEN / THEN
        assert (gameData.hasPlayers());
    }

    @Test
    public void GIVEN_player_removed_WHEN_hasPlayers_THEN_false() {
        // GIVEN
        Player player = mock(Player.class);
        gameData.add(player, Team.LAVA);
        gameData.remove(player);

        // WHEN / THEN
        assert (!gameData.hasPlayers());
    }

    @Test
    public void GIVEN_3_players_added_WHEN_amountOfPlayers_THEN_3() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        gameData.add(player1, Team.LAVA);
        gameData.add(player2, Team.LAVA);
        gameData.add(player3, Team.AQUA);

        // WHEN
        int amount = gameData.amountOfPlayers();

        // THEN
        assertEquals(3, amount);
    }

    @Test
    public void GIVEN_2_players_lava_1_player_aqua_WHEN_amountOfPlayers_lava_THEN_2() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        gameData.add(player1, Team.LAVA);
        gameData.add(player2, Team.LAVA);
        gameData.add(player3, Team.AQUA);

        // WHEN
        int amount = gameData.amountOfPlayers(Team.LAVA);

        // THEN
        assertEquals(2, amount);
    }

    @Test
    public void GIVEN_2_players_lava_1_player_aqua_WHEN_randomPlayer_lava_THEN_return_one_lava_player() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        gameData.add(player1, Team.LAVA);
        gameData.add(player2, Team.LAVA);
        gameData.add(player3, Team.AQUA);

        // WHEN
        Player randomPlayer = gameData.randomPlayer(Team.LAVA);

        // THEN
        assert (randomPlayer.equals(player1) || randomPlayer.equals(player2));
    }

    @Test
    public void WHEN_randomPlayer_THEN_null() {
        // WHEN
        Player randomPlayer = gameData.randomPlayer(Team.LAVA);

        // THEN
        assert (randomPlayer == null);
    }
}
