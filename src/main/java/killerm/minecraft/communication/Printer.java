package killerm.minecraft.communication;

import killerm.minecraft.manager.SoundManager;
import killerm.minecraft.utilities.PlayerRetriever;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static killerm.minecraft.utilities.MinecraftConstants.ticksPerSecond;

public class Printer {
    private SoundManager soundManager;
    private PlayerRetriever playerRetriever;

    public Printer() {
        soundManager = new SoundManager();
        playerRetriever = new PlayerRetriever();
    }

    public Printer(SoundManager soundManager, PlayerRetriever playerRetriever) {
        this.soundManager = soundManager;
        this.playerRetriever = playerRetriever;
    }

    public void broadcast(String message) {
        tellAllPlayers(Message.PREFIX_BROADCAST + message);
        soundManager.play(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float) 1.8);
        soundManager.play(Sound.BLOCK_STONE_BUTTON_CLICK_OFF, (float) 1.8);
    }

    private void tellAllPlayers(String message) {
        for (Player player : playerRetriever.retrieveOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public void tell(Player player, String message) {
        player.sendMessage(Message.PREFIX_TELL + message);
        soundManager.play(player, Sound.BLOCK_STONE_BUTTON_CLICK_OFF, (float) 1.8);
    }

    public void broadcastError(String message) {
        tellAllPlayers(Message.PREFIX_BROADCAST_ERROR + message);
        soundManager.play(Sound.BLOCK_SLIME_BLOCK_FALL, (float) 0.5);
    }

    public void tellError(Player player, String message) {
        player.sendMessage(Message.PREFIX_TELL_ERROR + message);
        soundManager.play(player, Sound.BLOCK_SLIME_BLOCK_FALL, (float) 0.5);
    }

    public void broadcastClickable(String messageToDisplay, String commandToExecute) {
        for (Player player : playerRetriever.retrieveOnlinePlayers()) {
            tellClickable(player, messageToDisplay, commandToExecute);
        }
    }

    public void tellClickable(Player player, String messageToDisplay, String commandToExecute) {
        ComponentBuilder cb = new ComponentBuilder(messageToDisplay);
        BaseComponent[] msg = cb.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandToExecute)).create();
        player.spigot().sendMessage(msg);

        soundManager.play(player, Sound.BLOCK_STONE_BUTTON_CLICK_OFF, (float) 1.8);
        soundManager.play(player, Sound.BLOCK_STONE_BUTTON_CLICK_OFF);
    }

    public void printDashes() {
        tellAllPlayers(Message.AQUA + Message.DIA_LINE);
    }

    public void broadcastTitle(String title, String subtitle) {
        for (Player player : playerRetriever.retrieveOnlinePlayers()) {
            tellTitle(player, title, subtitle);
        }
    }

    public void tellTitle(Player player, String title, String subtitle) {
        player.sendTitle(title, subtitle, 0, 3 * ticksPerSecond, 1 * ticksPerSecond);

        soundManager.playDelayedSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, (float) 0.95, 0);
        soundManager.playDelayedSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, (float) 1.8, 3);
    }
}

