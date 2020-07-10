package killerm.minecraft.communication;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Sounds {
    public void play(Sound sound) {
        play((Collection<Player>) Bukkit.getOnlinePlayers(), sound);
    }

    public void play(Sound sound, float pitch) {
        play((Collection<Player>) Bukkit.getOnlinePlayers(), sound, pitch);
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
}
