package killerm.minecraft.communication;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

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
}
