package killerm.minecraft.communication;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static killerm.minecraft.utilities.MinecraftConstants.ticksPerSecond;

public class Printer {
    Sounds sounds = new Sounds();

    public void broadcast(String message) {
        Bukkit.broadcastMessage(Message.PREFIX_BROADCAST + message);
        sounds.play(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float) 1.8);
        sounds.play(Sound.BLOCK_STONE_BUTTON_CLICK_OFF, (float) 1.8);
    }

    public void tell(Player player, String message) {
        player.sendMessage(Message.PREFIX_TELL + message);
        sounds.play(player, Sound.BLOCK_STONE_BUTTON_CLICK_OFF, (float) 1.8);
    }

    public void broadcastError(String message) {
        Bukkit.broadcastMessage(Message.PREFIX_BROADCAST_ERROR + message);
        sounds.play(Sound.BLOCK_SLIME_BLOCK_FALL, (float) 0.5);
    }

    public void tellError(Player player, String message) {
        player.sendMessage(Message.PREFIX_TELL_ERROR + message);
        sounds.play(Sound.BLOCK_SLIME_BLOCK_FALL, (float) 0.5);
    }

    public void tellClickable(Player player, String messageToDisplay, String commandToExecute) {
        ComponentBuilder cb = new ComponentBuilder(messageToDisplay);
        BaseComponent[] msg = cb.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandToExecute)).create();
        player.spigot().sendMessage(msg);

        sounds.play(player, Sound.BLOCK_STONE_BUTTON_CLICK_OFF, (float) 1.8);
        sounds.play(player, Sound.BLOCK_STONE_BUTTON_CLICK_OFF);
    }

    public void printDashes() {
        Bukkit.broadcastMessage(Message.AQUA + Message.DIA_LINE);
    }

    public void broadcastTitle(String title, String subtitle) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            tellTitle(player, title, subtitle);
        }
    }

    public void tellTitle(Player player, String title, String subtitle) {
        player.sendTitle(title, subtitle, 0, 3 * ticksPerSecond, 1 * ticksPerSecond);

        // Notes: F A C E F# -> 0.95 1.2 1.4 1.8 2
        sounds.playDelayedSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, 0, (float) 0.95);
        sounds.playDelayedSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, 3, (float) 1.2);
        sounds.playDelayedSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, 6, (float) 1.4);
        sounds.playDelayedSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, 9, (float) 1.8);
    }
}

