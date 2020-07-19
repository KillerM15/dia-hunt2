package killerm.minecraft.game.flow;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.game.data.ChestGameData;
import killerm.minecraft.game.data.GameStatus;
import killerm.minecraft.game.data.PlayerGameData;
import killerm.minecraft.game.data.Team;
import killerm.minecraft.game.interaction.ItemGiver;
import killerm.minecraft.game.interaction.LocationSetter;
import killerm.minecraft.game.interaction.StatsGiver;
import killerm.minecraft.manager.ScoreboardManager;
import killerm.minecraft.utilities.MinecraftConstants;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Respawner {
    private Printer printer = new Printer();
    private List<BukkitTask> tasks = Collections.synchronizedList(new ArrayList<>());
    private GameStatus gameStatus;
    private PlayerGameData playerGameData;
    private ChestGameData chestGameData;
    private ItemGiver itemGiver = new ItemGiver();
    private StatsGiver statsGiver = new StatsGiver();
    private LocationSetter locationSetter = new LocationSetter();
    private DiamondIndicator diamondIndicator = new DiamondIndicator();
    private ScoreboardManager scoreboardManager = new ScoreboardManager();

    public Respawner(GameStatus gameStatus, PlayerGameData playerGameData, ChestGameData chestGameData) {
        this.gameStatus = gameStatus;
        this.playerGameData = playerGameData;
        this.chestGameData = chestGameData;
    }

    public void startRespawningWhenTeamHasDias(Player player) {
        Team team = playerGameData.team(player);

        new BukkitRunnable() {
            public void run() {
                if (gameStatus.getGameStatusType() != GameStatusType.RUNNING) {
                    cancel();
                }

                if (playerGameData.carriesDias(team) || chestGameData.containsDias(team)) {
                    startRespawning(player);
                    cancel();
                }
            }
        }.runTaskTimer(DiaHuntPlugin.getInstance(), 0, 1);
    }

    public void startRespawning(Player player) {
        printCountdown(player, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        delayedRespawn(player, 10);
    }

    private void printCountdown(Player player, int... seconds) {
        int maxS = Arrays.stream(seconds).max().getAsInt();

        for (int s : seconds) {
            int secondsToWait = maxS - s;

            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (playerGameData.inGame(player)) {
                        printer.tellTitle(player, Message.AQUA + String.valueOf(s), Message.DARK_AQUA + Message.UNTIL_RESPAWN);
                    }
                }
            }.runTaskLater(DiaHuntPlugin.getInstance(), MinecraftConstants.ticksPerSecond * secondsToWait);

            tasks.add(task);
        }
    }

    private void delayedRespawn(Player player, int maxSecond) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!playerGameData.inGame(player)) {
                    return;
                }

                statsGiver.clear(player);
                statsGiver.giveBaseStats(player);
                teleportPlayerToSpawn(player);
                playerGameData.setCondition(player, Condition.ALIVE);
                printer.tellTitle(player, Message.AQUA + Message.GO, Message.DARK_AQUA + Message.GET_DIAS);
                scoreboardManager.refresh(player);
            }
        }.runTaskLater(DiaHuntPlugin.getInstance(), MinecraftConstants.ticksPerSecond * maxSecond);

        tasks.add(task);
    }

    private void teleportPlayerToSpawn(Player player) {
        if (playerGameData.team(player) == Team.AQUA) {
            itemGiver.giveBaseAquaItems(player);
            locationSetter.teleportToAquaSpawn(player);
        } else {
            itemGiver.giveBaseLavaItems(player);
            locationSetter.teleportToLavaSpawn(player);
        }
    }

    public void cancelAllRespawns() {
        for (BukkitTask task : tasks) {
            task.cancel();
        }

        tasks.clear();
    }
}
