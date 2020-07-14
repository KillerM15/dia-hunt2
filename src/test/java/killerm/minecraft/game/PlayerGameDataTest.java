package killerm.minecraft.game;

import killerm.minecraft.utilities.NameChanger;
import killerm.minecraft.communication.Printer;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.mock;

class PlayerGameDataTest {
    private Printer printer = mock(Printer.class);
    private NameChanger nameChanger = mock(NameChanger.class);
    private PlayerGameData playerGameData = new PlayerGameData(printer, nameChanger);

    @Test
    public void GIVEN_player_WHEN_add_THEN_contains_player() {
        // GIVEN
        Player player = mock(Player.class);

        // WHEN
        playerGameData.add(player);

        // THEN
        assert (playerGameData.contains(player));
    }

    @Test
    public void GIVEN_player_lava_added_WHEN_team_THEN_team_equals_lava() {
        // GIVEN
        Team team = Team.LAVA;
        Player player = mock(Player.class);
        playerGameData.add(player, team);

        // WHEN
        Team actualTeam = playerGameData.team(player);

        //THEN
        assertEquals(team, actualTeam);
    }

    @Test
    public void GIVEN_added_player_WHEN_remove_THEN_does_not_contain() {
        // GIVEN
        Player player = mock(Player.class);
        playerGameData.add(player);

        // WHEN
        playerGameData.remove(player);

        // THEN
        assert (!playerGameData.contains(player));
    }

    @Test
    public void GIVEN_2_players_added_WHEN_players_THEN_players_returned() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        playerGameData.add(player1);
        playerGameData.add(player2);

        // WHEN
        Collection actualPlayers = playerGameData.players();

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
        playerGameData.add(player1, Team.LAVA);
        playerGameData.add(player2, Team.LAVA);
        playerGameData.add(player3, Team.AQUA);

        // WHEN
        Collection players = playerGameData.players(Team.LAVA);

        // THEN
        assert (players.contains(player1));
        assert (players.contains(player2));
        assert (!players.contains(player3));
    }

    @Test
    public void GIVEN_player_added_WHEN_hasPlayers_THEN_true() {
        // GIVEN
        Player player = mock(Player.class);
        playerGameData.add(player, Team.LAVA);

        // WHEN / THEN
        assert (playerGameData.hasPlayers());
    }

    @Test
    public void GIVEN_player_removed_WHEN_hasPlayers_THEN_false() {
        // GIVEN
        Player player = mock(Player.class);
        playerGameData.add(player, Team.LAVA);
        playerGameData.remove(player);

        // WHEN / THEN
        assert (!playerGameData.hasPlayers());
    }

    @Test
    public void GIVEN_3_players_added_WHEN_amountOfPlayers_THEN_3() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        playerGameData.add(player1, Team.LAVA);
        playerGameData.add(player2, Team.LAVA);
        playerGameData.add(player3, Team.AQUA);

        // WHEN
        int amount = playerGameData.amountOfPlayers();

        // THEN
        assertEquals(3, amount);
    }

    @Test
    public void GIVEN_2_players_lava_1_player_aqua_WHEN_amountOfPlayers_lava_THEN_2() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        playerGameData.add(player1, Team.LAVA);
        playerGameData.add(player2, Team.LAVA);
        playerGameData.add(player3, Team.AQUA);

        // WHEN
        int amount = playerGameData.amountOfPlayers(Team.LAVA);

        // THEN
        assertEquals(2, amount);
    }

    @Test
    public void GIVEN_2_players_lava_1_player_aqua_WHEN_randomPlayer_lava_THEN_return_one_lava_player() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        playerGameData.add(player1, Team.LAVA);
        playerGameData.add(player2, Team.LAVA);
        playerGameData.add(player3, Team.AQUA);

        // WHEN
        Player randomPlayer = playerGameData.randomPlayer(Team.LAVA);

        // THEN
        assert (randomPlayer.equals(player1) || randomPlayer.equals(player2));
    }

    @Test
    public void WHEN_randomPlayer_THEN_null() {
        // WHEN
        Player randomPlayer = playerGameData.randomPlayer(Team.LAVA);

        // THEN
        assert (randomPlayer == null);
    }
}
