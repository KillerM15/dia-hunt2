package killerm.minecraft.game;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.manager.ScoreboardManager;
import killerm.minecraft.utilities.DamageRecorder;
import killerm.minecraft.utilities.InventoryCopy;
import org.bukkit.entity.Player;

public class DeathProcessor {
    private Printer printer;
    private PlayerGameData playerGameData;
    private DiaChestGameData diaChestGameData;
    private DiaRespawner diaRespawner;
    private DamageRecorder damageRecorder;
    private Winner winner;
    private StatsGiver statsGiver;
    private InventoryCopy inventoryCopy;
    private ScoreboardManager scoreboardManager;

    public DeathProcessor(DiaHuntGameState diaHuntGameState, PlayerGameData playerGameData, DiaChestGameData diaChestGameData, DamageRecorder damageRecorder) {
        this.printer = new Printer();
        this.playerGameData = playerGameData;
        this.diaChestGameData = diaChestGameData;
        this.diaRespawner = new DiaRespawner(diaHuntGameState, playerGameData, diaChestGameData);
        this.damageRecorder = damageRecorder;
        this.winner = new Winner();
        this.statsGiver = new StatsGiver();
        this.inventoryCopy = new InventoryCopy();
        this.scoreboardManager = new ScoreboardManager();
    }

    public DeathProcessor(Printer printer, PlayerGameData playerGameData, DiaChestGameData diaChestGameData, DiaRespawner diaRespawner, DamageRecorder damageRecorder, Winner winner, StatsGiver statsGiver, InventoryCopy inventoryCopy, ScoreboardManager scoreboardManager) {
        this.printer = printer;
        this.playerGameData = playerGameData;
        this.diaChestGameData = diaChestGameData;
        this.diaRespawner = diaRespawner;
        this.damageRecorder = damageRecorder;
        this.winner = winner;
        this.statsGiver = statsGiver;
        this.inventoryCopy = inventoryCopy;
        this.scoreboardManager = scoreboardManager;
    }

    public void processDeath(Player player) {
        giveKillerItemsAndPrint(player);
        givePlayerDeadStats(player);

        if (hasNoDias(playerGameData.team(player)))
            setDead(player);
        else
            respawn(player);

        endGameIfLost(player);
    }

    private void giveKillerItemsAndPrint(Player player) {
        Player killer = damageRecorder.getDamager(player);

        if (killer != null) {
            printer.broadcast(killer.getDisplayName() + Message.DARK_AQUA + Message.KILLED + player.getDisplayName());
            inventoryCopy.copyItemstacksWithName(player, killer, Message.ITEM_DIAMOND);
            scoreboardManager.refresh(killer);
        } else {
            printer.broadcast(player.getDisplayName() + Message.DARK_AQUA + Message.HAS_DIED);
        }
    }

    private void givePlayerDeadStats(Player player) {
        statsGiver.clear(player);
        statsGiver.giveSpecatorStats(player);
        scoreboardManager.refresh(player);
    }

    private boolean hasNoDias(Team team) {
        return !playerGameData.carriesDias(team) && !diaChestGameData.containsDias(team);
    }

    private void respawn(Player player) {
        playerGameData.setCondition(player, Condition.RESPAWNING);
        diaRespawner.startRespawning(player);
    }

    private void setDead(Player player) {
        playerGameData.setCondition(player, Condition.DEAD);
        diaRespawner.startRespawningWhenTeamHasDias(player);
    }

    private void endGameIfLost(Player player) {
        Team team = playerGameData.team(player);

        if (playerGameData.allPlayersDead(team) && hasNoDias(team)) {
            diaRespawner.cancelAllRespawns();
            winner.win(team.getEnemy());
        }
    }
}
