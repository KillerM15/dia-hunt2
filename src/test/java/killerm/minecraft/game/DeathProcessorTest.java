package killerm.minecraft.game;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.manager.ScoreboardManager;
import killerm.minecraft.utilities.DamageRecorder;
import killerm.minecraft.utilities.InventoryCopy;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class DeathProcessorTest {
    private Printer printer = mock(Printer.class);
    private PlayerGameData playerGameData = mock(PlayerGameData.class);
    private ChestData chestData = mock(ChestData.class);
    private Respawner respawner = mock(Respawner.class);
    private DamageRecorder damageRecorder = mock(DamageRecorder.class);
    private Winner winner = mock(Winner.class);
    private StatsGiver statsGiver = mock(StatsGiver.class);
    private InventoryCopy inventoryCopy = mock(InventoryCopy.class);
    private ScoreboardManager scoreboardManager = mock(ScoreboardManager.class);
    private LocationSetter locationSetter = mock(LocationSetter.class);
    private DeathProcessor deathProcessor = new DeathProcessor(printer, playerGameData, chestData, respawner, damageRecorder, winner, statsGiver, inventoryCopy, scoreboardManager, locationSetter);

    @Test
    public void GIVEN_player_and_killer_WHEN_processDeath_THEN_give_killer_diamonds_and_print_correct_message_and_refresh_killer_scoreboard() {
        // GIVEN
        Player player = mock(Player.class);
        Player killer = mock(Player.class);
        doReturn(killer).when(damageRecorder).getDamager(player);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(printer).broadcast(killer.getDisplayName() + Message.DARK_AQUA + Message.KILLED + player.getDisplayName());
        Mockito.verify(inventoryCopy).copyItemstacksWithName(player, killer, Message.ITEM_DIAMOND);
        Mockito.verify(scoreboardManager).refresh(killer);
    }

    @Test
    public void GIVEN_player_and_no_killer_WHEN_processDeath_THEN_print_correct_message() {
        // GIVEN
        Player player = mock(Player.class);
        doReturn(null).when(damageRecorder).getDamager(player);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(printer).broadcast(player.getDisplayName() + Message.DARK_AQUA + Message.HAS_DIED);
    }

    @Test
    public void GIVEN_player_WHEN_processDeath_THEN_stats_cleared_and_spectatorStats_given_and_Scoreboard_refreshed() {
        // GIVEN
        Player player = mock(Player.class);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(statsGiver).clear(player);
        Mockito.verify(statsGiver).giveSpectatorStats(player);
        Mockito.verify(scoreboardManager).refresh(player);
    }

    @Test
    public void GIVEN_player_and_team_and_allPlayersDead_false_and_carriesDias_false_and_containsDias_false_WHEN_processDeath_THEN_team_not_lost() {
        // GIVEN
        Player player = mock(Player.class);
        Team team = Team.AQUA;
        doReturn(team).when(playerGameData).team(player);

        doReturn(false).when(playerGameData).allPlayersDead(team);
        doReturn(false).when(playerGameData).carriesDias(team);
        doReturn(false).when(chestData).containsDias(team);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(respawner, times(0)).cancelAllRespawns();
        Mockito.verify(winner, times(0)).win(team.getEnemy());
    }

    @Test
    public void GIVEN_player_and_team_and_allPlayersDead_true_and_carriesDias_false_and_containsDias_false_WHEN_processDeath_THEN_team_lost() {
        // GIVEN
        Player player = mock(Player.class);
        Team team = Team.AQUA;
        doReturn(team).when(playerGameData).team(player);

        doReturn(true).when(playerGameData).allPlayersDead(team);
        doReturn(false).when(playerGameData).carriesDias(team);
        doReturn(false).when(chestData).containsDias(team);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(respawner, times(1)).cancelAllRespawns();
        Mockito.verify(winner, times(1)).win(team.getEnemy());
    }

    @Test
    public void GIVEN_player_and_team_and_allPlayersDead_false_and_carriesDias_true_and_containsDias_false_WHEN_processDeath_THEN_team_not_lost() {
        // GIVEN
        Player player = mock(Player.class);
        Team team = Team.AQUA;
        doReturn(team).when(playerGameData).team(player);

        doReturn(false).when(playerGameData).allPlayersDead(team);
        doReturn(true).when(playerGameData).carriesDias(team);
        doReturn(false).when(chestData).containsDias(team);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(respawner, times(0)).cancelAllRespawns();
        Mockito.verify(winner, times(0)).win(team.getEnemy());
    }

    @Test
    public void GIVEN_player_and_team_and_allPlayersDead_true_and_carriesDias_true_and_containsDias_false_WHEN_processDeath_THEN_team_not_lost() {
        // GIVEN
        Player player = mock(Player.class);
        Team team = Team.AQUA;
        doReturn(team).when(playerGameData).team(player);

        doReturn(true).when(playerGameData).allPlayersDead(team);
        doReturn(true).when(playerGameData).carriesDias(team);
        doReturn(false).when(chestData).containsDias(team);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(respawner, times(0)).cancelAllRespawns();
        Mockito.verify(winner, times(0)).win(team.getEnemy());
    }

    @Test
    public void GIVEN_player_and_team_and_allPlayersDead_false_and_carriesDias_false_and_containsDias_true_WHEN_processDeath_THEN_team_not_lost() {
        // GIVEN
        Player player = mock(Player.class);
        Team team = Team.AQUA;
        doReturn(team).when(playerGameData).team(player);

        doReturn(false).when(playerGameData).allPlayersDead(team);
        doReturn(false).when(playerGameData).carriesDias(team);
        doReturn(true).when(chestData).containsDias(team);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(respawner, times(0)).cancelAllRespawns();
        Mockito.verify(winner, times(0)).win(team.getEnemy());
    }

    @Test
    public void GIVEN_player_and_team_and_allPlayersDead_true_and_carriesDias_false_and_containsDias_true_WHEN_processDeath_THEN_team_not_lost() {
        // GIVEN
        Player player = mock(Player.class);
        Team team = Team.AQUA;
        doReturn(team).when(playerGameData).team(player);

        doReturn(true).when(playerGameData).allPlayersDead(team);
        doReturn(false).when(playerGameData).carriesDias(team);
        doReturn(true).when(chestData).containsDias(team);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(respawner, times(0)).cancelAllRespawns();
        Mockito.verify(winner, times(0)).win(team.getEnemy());
    }


    @Test
    public void GIVEN_player_and_team_and_allPlayersDead_false_and_carriesDias_true_and_containsDias_true_WHEN_processDeath_THEN_team_not_lost() {
        // GIVEN
        Player player = mock(Player.class);
        Team team = Team.AQUA;
        doReturn(team).when(playerGameData).team(player);

        doReturn(false).when(playerGameData).allPlayersDead(team);
        doReturn(true).when(playerGameData).carriesDias(team);
        doReturn(true).when(chestData).containsDias(team);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(respawner, times(0)).cancelAllRespawns();
        Mockito.verify(winner, times(0)).win(team.getEnemy());
    }

    @Test
    public void GIVEN_player_and_team_and_allPlayersDead_true_and_carriesDias_true_and_containsDias_true_WHEN_processDeath_THEN_team_not_lost() {
        // GIVEN
        Player player = mock(Player.class);
        Team team = Team.AQUA;
        doReturn(team).when(playerGameData).team(player);

        doReturn(true).when(playerGameData).allPlayersDead(team);
        doReturn(true).when(playerGameData).carriesDias(team);
        doReturn(true).when(chestData).containsDias(team);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(respawner, times(0)).cancelAllRespawns();
        Mockito.verify(winner, times(0)).win(team.getEnemy());
    }

    @Test
    public void GIVEN_player_and_team_and_carriesDias_false_and_containsDias_false_WHEN_processDeath_THEN_Condition_DEAD_and_no_respawn() {
        // GIVEN
        Player player = mock(Player.class);
        Team team = Team.AQUA;
        doReturn(team).when(playerGameData).team(player);

        doReturn(false).when(playerGameData).carriesDias(team);
        doReturn(false).when(chestData).containsDias(team);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(playerGameData).setCondition(player, Condition.DEAD);
        Mockito.verify(respawner).startRespawningWhenTeamHasDias(player);
    }

    @Test
    public void GIVEN_player_and_team_and_carriesDias_true_and_containsDias_false_WHEN_processDeath_THEN_Condition_RESPAWNING_and_respawn() {
        // GIVEN
        Player player = mock(Player.class);
        Team team = Team.AQUA;
        doReturn(team).when(playerGameData).team(player);

        doReturn(true).when(playerGameData).carriesDias(team);
        doReturn(false).when(chestData).containsDias(team);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(playerGameData).setCondition(player, Condition.RESPAWNING);
        Mockito.verify(respawner).startRespawning(player);
    }

    @Test
    public void GIVEN_player_and_team_and_carriesDias_false_and_containsDias_true_WHEN_processDeath_THEN_Condition_RESPAWNING_and_respawn() {
        // GIVEN
        Player player = mock(Player.class);
        Team team = Team.AQUA;
        doReturn(team).when(playerGameData).team(player);

        doReturn(false).when(playerGameData).carriesDias(team);
        doReturn(true).when(chestData).containsDias(team);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(playerGameData).setCondition(player, Condition.RESPAWNING);
        Mockito.verify(respawner).startRespawning(player);
    }

    @Test
    public void GIVEN_player_and_team_and_carriesDias_true_and_containsDias_true_WHEN_processDeath_THEN_Condition_RESPAWNING_and_respawn() {
        // GIVEN
        Player player = mock(Player.class);
        Team team = Team.AQUA;
        doReturn(team).when(playerGameData).team(player);

        doReturn(true).when(playerGameData).carriesDias(team);
        doReturn(true).when(chestData).containsDias(team);

        // WHEN
        deathProcessor.processDeath(player);

        // THEN
        Mockito.verify(playerGameData).setCondition(player, Condition.RESPAWNING);
        Mockito.verify(respawner).startRespawning(player);
    }
}