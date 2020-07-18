package killerm.minecraft.game;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.manager.ScoreboardManager;
import killerm.minecraft.utilities.DamageRecorder;
import killerm.minecraft.utilities.InventoryCopy;
import org.bukkit.entity.Player;

public class DeathProcessor {
    private Printer printer;
    private DiaPlayerData diaPlayerData;
    private DiaChestGameData diaChestGameData;
    private DiaRespawner diaRespawner;
    private DamageRecorder damageRecorder;
    private Winner winner;
    private StatsGiver statsGiver;
    private InventoryCopy inventoryCopy;
    private ScoreboardManager scoreboardManager;

    public DeathProcessor(DiaHuntGameState diaHuntGameState, DiaPlayerData diaPlayerData, DiaChestGameData diaChestGameData, DamageRecorder damageRecorder) {
        this.printer = new Printer();
        this.diaPlayerData = diaPlayerData;
        this.diaChestGameData = diaChestGameData;
        this.diaRespawner = new DiaRespawner(diaHuntGameState, diaPlayerData, diaChestGameData);
        this.damageRecorder = damageRecorder;
        this.winner = new Winner();
        this.statsGiver = new StatsGiver();
        this.inventoryCopy = new InventoryCopy();
        this.scoreboardManager = new ScoreboardManager();
    }

    public DeathProcessor(Printer printer, DiaPlayerData diaPlayerData, DiaChestGameData diaChestGameData, DiaRespawner diaRespawner, DamageRecorder damageRecorder, Winner winner, StatsGiver statsGiver, InventoryCopy inventoryCopy, ScoreboardManager scoreboardManager) {
        this.printer = printer;
        this.diaPlayerData = diaPlayerData;
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

        if (hasNoDias(diaPlayerData.team(player)))
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
        return !diaPlayerData.carriesDias(team) && !diaChestGameData.containsDias(team);
    }

    private void respawn(Player player) {
        diaPlayerData.setCondition(player, Condition.RESPAWNING);
        diaRespawner.startRespawning(player);
    }

    private void setDead(Player player) {
        diaPlayerData.setCondition(player, Condition.DEAD);
        diaRespawner.startRespawningWhenTeamHasDias(player);
    }

    private void endGameIfLost(Player player) {
        Team team = diaPlayerData.team(player);

        if (diaPlayerData.allPlayersDead(team) && hasNoDias(team)) {
            diaRespawner.cancelAllRespawns();
            winner.win(team.getEnemy());
        }
    }
}
