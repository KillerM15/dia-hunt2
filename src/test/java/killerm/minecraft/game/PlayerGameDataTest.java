package killerm.minecraft.game;

import killerm.minecraft.communication.Printer;
import killerm.minecraft.communication.NameChanger;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

class PlayerGameDataTest {
    private Printer printer = mock(Printer.class);
    private NameChanger nameChanger = mock(NameChanger.class);
    private DiamondIndicator diamondIndicator = mock(DiamondIndicator.class);
    private PlayerGameData playerGameData = new PlayerGameData(printer, nameChanger, diamondIndicator);

    @Test
    public void GIVEN_player_WHEN_add_THEN_player_inGame() {
        // GIVEN
        Player player = mock(Player.class);

        // WHEN
        playerGameData.add(player);

        // THEN
        assert (playerGameData.inGame(player));
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
        assert (!playerGameData.inGame(player));
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

    @Test
    public void GIVEN_player_with_Condition_ALIVE_WHEN_isAlive_THEN_true() {
        // GIVEN
        Player player = mock(Player.class);
        playerGameData.setCondition(player, Condition.ALIVE);

        // WHEN / THEN
        assert (playerGameData.isAlive(player));
    }

    @Test
    public void GIVEN_player_with_Condition_RESPAWNING_WHEN_isAlive_THEN_false() {
        // GIVEN
        Player player = mock(Player.class);
        playerGameData.setCondition(player, Condition.RESPAWNING);

        // WHEN / THEN
        assert (!playerGameData.isAlive(player));
    }

    @Test
    public void GIVEN_player_with_Condition_DEAD_WHEN_isAlive_THEN_false() {
        // GIVEN
        Player player = mock(Player.class);
        playerGameData.setCondition(player, Condition.DEAD);

        // WHEN / THEN
        assert (!playerGameData.isAlive(player));
    }

    @Test
    public void GIVEN_player_added_to_Team_AQUA_WHEN_allPlayersDead_THEN_false() {
        // GIVEN
        Player player = mock(Player.class);
        playerGameData.add(player, Team.AQUA);

        // WHEN / THEN
        assert (!playerGameData.allPlayersDead(Team.AQUA));
    }

    @Test
    public void GIVEN_player_added_to_Team_AQUA_with_Condition_ALIVE_WHEN_allPlayersDead_THEN_false() {
        // GIVEN
        Player player = mock(Player.class);
        playerGameData.add(player, Team.AQUA);
        playerGameData.setCondition(player, Condition.ALIVE);

        // WHEN / THEN
        assert (!playerGameData.allPlayersDead(Team.AQUA));
    }

    @Test
    public void GIVEN_player_added_to_Team_AQUA_with_Condition_DEAD_WHEN_allPlayersDead_THEN_true() {
        // GIVEN
        Player player = mock(Player.class);
        playerGameData.add(player, Team.AQUA);
        playerGameData.setCondition(player, Condition.DEAD);

        // WHEN / THEN
        assert (playerGameData.allPlayersDead(Team.AQUA));
    }

    @Test
    public void GIVEN_player_added_to_Team_AQUA_with_Condition_RESPAWNING_and_player_added_to_Team_AQUA_with_Condition_DEAD_WHEN_allPlayersDead_AQUA_THEN_false() {
        // GIVEN
        Player player1 = mock(Player.class);
        playerGameData.add(player1, Team.AQUA);
        playerGameData.setCondition(player1, Condition.RESPAWNING);

        Player player2 = mock(Player.class);
        playerGameData.add(player2, Team.LAVA);
        playerGameData.setCondition(player2, Condition.DEAD);

        // WHEN / THEN
        assert (!playerGameData.allPlayersDead(Team.AQUA));
    }

    @Test
    public void GIVEN_player_added_to_Team_AQUA_with_Condition_RESPAWNING_WHEN_allPlayersDead_THEN_false() {
        // GIVEN
        Player player = mock(Player.class);
        playerGameData.add(player, Team.AQUA);
        playerGameData.setCondition(player, Condition.RESPAWNING);

        // WHEN / THEN
        assert (!playerGameData.allPlayersDead(Team.AQUA));
    }

    @Test
    public void GIVEN_2_player_added_to_Team_AQUA_WHEN_allPlayersDead_THEN_false() {
        // GIVEN
        Player player1 = mock(Player.class);
        playerGameData.add(player1, Team.AQUA);

        Player player2 = mock(Player.class);
        playerGameData.add(player2, Team.AQUA);

        // WHEN / THEN
        assert (!playerGameData.allPlayersDead(Team.AQUA));
    }

    @Test
    public void GIVEN_2_player_added_to_Team_AQUA_with_Condition_RESPAWNING_WHEN_allPlayersDead_THEN_false() {
        // GIVEN
        Player player1 = mock(Player.class);
        playerGameData.add(player1, Team.AQUA);
        playerGameData.setCondition(player1, Condition.RESPAWNING);

        Player player2 = mock(Player.class);
        playerGameData.add(player2, Team.AQUA);
        playerGameData.setCondition(player2, Condition.RESPAWNING);

        // WHEN / THEN
        assert (!playerGameData.allPlayersDead(Team.AQUA));
    }

    @Test
    public void GIVEN_2_player_added_to_Team_AQUA_with_Condition_DEAD_WHEN_allPlayersDead_THEN_true() {
        // GIVEN
        Player player1 = mock(Player.class);
        playerGameData.add(player1, Team.AQUA);
        playerGameData.setCondition(player1, Condition.DEAD);

        Player player2 = mock(Player.class);
        playerGameData.add(player2, Team.AQUA);
        playerGameData.setCondition(player2, Condition.DEAD);

        // WHEN / THEN
        assert (playerGameData.allPlayersDead(Team.AQUA));
    }

    @Test
    public void GIVEN_2_player_added_to_Team_AQUA_1_with_Condition_DEAD_1_with_Condition_RESPAWNING_WHEN_allPlayersDead_THEN_false() {
        // GIVEN
        Player player1 = mock(Player.class);
        playerGameData.add(player1, Team.AQUA);
        playerGameData.setCondition(player1, Condition.DEAD);

        Player player2 = mock(Player.class);
        playerGameData.add(player2, Team.AQUA);
        playerGameData.setCondition(player2, Condition.RESPAWNING);

        // WHEN / THEN
        assert (!playerGameData.allPlayersDead(Team.AQUA));
    }

    @Test
    public void GIVEN_2_player_added_AND_1_with_Diamonds_WHEN_carriesDias_THEN_true() {
        // GIVEN
        Player player1 = mock(Player.class);
        playerGameData.add(player1, Team.AQUA);

        Player player2 = mock(Player.class);
        playerGameData.add(player2, Team.AQUA);

        Mockito.doReturn(true).when(diamondIndicator).hasDiamonds(player1);

        // WHEN / THEN
        assert (playerGameData.carriesDias(Team.AQUA));
    }

    @Test
    public void GIVEN_2_player_added_AND_2_with_Diamonds_WHEN_carriesDias_THEN_true() {
        // GIVEN
        Player player1 = mock(Player.class);
        playerGameData.add(player1, Team.AQUA);

        Player player2 = mock(Player.class);
        playerGameData.add(player2, Team.AQUA);

        Mockito.doReturn(true).when(diamondIndicator).hasDiamonds(player1);
        Mockito.doReturn(true).when(diamondIndicator).hasDiamonds(player2);

        // WHEN / THEN
        assert (playerGameData.carriesDias(Team.AQUA));
    }

    @Test
    public void GIVEN_2_player_added_AND_0_with_Diamonds_WHEN_carriesDias_THEN_false() {
        // GIVEN
        Player player1 = mock(Player.class);
        playerGameData.add(player1, Team.AQUA);

        Player player2 = mock(Player.class);
        playerGameData.add(player2, Team.AQUA);

        Mockito.doReturn(false).when(diamondIndicator).hasDiamonds(player1);
        Mockito.doReturn(false).when(diamondIndicator).hasDiamonds(player2);

        // WHEN / THEN
        assert (!playerGameData.carriesDias(Team.AQUA));
    }
}
