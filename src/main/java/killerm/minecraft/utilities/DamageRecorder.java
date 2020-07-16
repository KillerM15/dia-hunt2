package killerm.minecraft.utilities;


import com.sun.istack.internal.Nullable;
import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.game.DiaHuntGameState;
import killerm.minecraft.game.GameStatus;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DamageRecorder {
    private Map<Player, Player> lastDamagers = new ConcurrentHashMap<>();
    private Map<Player, BukkitRunnable> lastDamagerRemoverTasks = new ConcurrentHashMap<>();
    private DiaHuntGameState diaHuntGameState;
    private int secondsUntilDelete;

    public DamageRecorder(DiaHuntGameState diaHuntGameState, int secondsUntilDelete) {
        this.diaHuntGameState = diaHuntGameState;
        this.secondsUntilDelete = secondsUntilDelete;
    }

    public void put(Player receiver, Player damager) {
        lastDamagers.put(receiver, damager);
        deleteRemoverTaskIfExists(receiver);
        initializeRemoverTask(receiver, damager);
    }

    private void deleteRemoverTaskIfExists(Player receiver) {
        if (lastDamagerRemoverTasks.get(receiver) != null) {
            lastDamagerRemoverTasks.get(receiver).cancel();
        }
    }

    private void initializeRemoverTask(Player receiver, Player damager) {
        BukkitRunnable removerTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (diaHuntGameState.getGameStatus() == GameStatus.RUNNING)
                    lastDamagers.remove(receiver, damager);
            }
        };

        removerTask.runTaskLater(DiaHuntPlugin.getInstance(), secondsUntilDelete * MinecraftConstants.ticksPerSecond);
        lastDamagerRemoverTasks.put(receiver, removerTask);
    }

    @Nullable
    public Player getDamager(Player receiver) {
        return lastDamagers.get(receiver);
    }
}
