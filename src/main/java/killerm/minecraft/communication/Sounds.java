package killerm.minecraft.communication;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.utilities.PlayerRetriever;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class Sounds {
    private PlayerRetriever playerRetriever;

    public Sounds() {
        this.playerRetriever = new PlayerRetriever();
    }

    public Sounds(PlayerRetriever playerRetriever) {
        this.playerRetriever = playerRetriever;
    }

    public void play(Sound sound) {
        play(sound, (float) 1.0);
    }

    public void play(Sound sound, float pitch) {
        play(playerRetriever.retrieveOnlinePlayers(), sound, pitch);
    }

    public void play(Collection<Player> players, Sound sound, float pitch) {
        for (Player player : players) {
            play(player, sound, pitch);
        }
    }

    public void play(Collection<Player> players, Sound sound) {
        for (Player player : players) {
            play(player, sound);
        }
    }

    public void play(Player player, Sound sound) {
        play(player, sound, 1);
    }

    public void play(Player player, Sound sound, float pitch) {
        World world = player.getWorld();
        world.playSound(player.getLocation(), sound, 1, pitch);
    }

    public void playDelayedSound(Player player, Sound sound, float pitch, int afterTicks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                play(player, sound, pitch);
            }
        }.runTaskLater(DiaHuntPlugin.getInstance(), afterTicks);
    }
}
