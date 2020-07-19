package killerm.minecraft.communication;

import killerm.minecraft.manager.SoundManager;
import killerm.minecraft.utilities.PlayerRetriever;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.HashSet;

import static killerm.minecraft.utilities.MinecraftConstants.ticksPerSecond;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class PrinterTest {
    private SoundManager soundManager = mock(SoundManager.class);
    private PlayerRetriever playerRetriever = mock(PlayerRetriever.class);
    private Printer printer = new Printer(soundManager, playerRetriever);

    @Test
    public void GIVEN_2_online_players_and_message_WHEN_broadcast_THEN_each_player_got_message_and_2_sounds() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Collection<Player> players = new HashSet<>();
        players.add(player1);
        players.add(player2);
        doReturn(players).when(playerRetriever).retrieveOnlinePlayers();

        String message = "hello";

        // WHEN
        printer.broadcast(message);

        // THEN
        Mockito.verify(soundManager, times(2)).play(any(Sound.class), Matchers.anyFloat());
        Mockito.verify(player1, times(1)).sendMessage(anyString());
        Mockito.verify(player2, times(1)).sendMessage(anyString());
    }

    @Test
    public void GIVEN_player_and_message_WHEN_tell_THEN_player_got_message_and_sound() {
        // GIVEN
        Player player = mock(Player.class);
        String message = "hello";

        // WHEN
        printer.tell(player, message);

        // THEN
        Mockito.verify(soundManager, times(1)).play(any(Player.class), any(Sound.class), Matchers.anyFloat());
        Mockito.verify(player, times(1)).sendMessage(anyString());
    }

    @Test
    public void GIVEN_2_online_players_and_title_and_subtitle_WHEN_broadcastTitle_THEN_player_got_two_sounds_and_title() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Collection<Player> players = new HashSet<>();
        players.add(player1);
        players.add(player2);
        doReturn(players).when(playerRetriever).retrieveOnlinePlayers();

        String title = "hello";
        String subtitle = "helloo";

        // WHEN
        printer.broadcastTitle(title, subtitle);

        // THEN
        Mockito.verify(soundManager, times(4)).playDelayedSound(any(Player.class), any(Sound.class), anyFloat(), anyInt());
        Mockito.verify(player1, times(1)).sendTitle(title, subtitle, 0, 3 * ticksPerSecond, 1 * ticksPerSecond);
        Mockito.verify(player2, times(1)).sendTitle(title, subtitle, 0, 3 * ticksPerSecond, 1 * ticksPerSecond);
    }

    @Test
    public void GIVEN_2_online_players_and_message_WHEN_broadcastError_THEN_each_player_got_message_and_1_sound() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Collection<Player> players = new HashSet<>();
        players.add(player1);
        players.add(player2);
        doReturn(players).when(playerRetriever).retrieveOnlinePlayers();

        String message = "hello";

        // WHEN
        printer.broadcastError(message);

        // THEN
        Mockito.verify(soundManager, times(1)).play(any(Sound.class), Matchers.anyFloat());
        Mockito.verify(player1, times(1)).sendMessage(anyString());
        Mockito.verify(player2, times(1)).sendMessage(anyString());
    }

    @Test
    public void GIVEN_player_and_message_WHEN_tellError_THEN_player_got_message_and_sound() {
        // GIVEN
        Player player = mock(Player.class);
        String message = "hello";

        // WHEN
        printer.tellError(player, message);

        // THEN
        Mockito.verify(soundManager, times(1)).play(any(Player.class), any(Sound.class), Matchers.anyFloat());
        Mockito.verify(player, times(1)).sendMessage(anyString());
    }

    @Test
    public void GIVEN_2_online_players_WHEN_broadcastClickable_THEN_each_player_got_2_sounds() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player.Spigot spigot1 = mock(Player.Spigot.class);
        Player.Spigot spigot2 = mock(Player.Spigot.class);
        doReturn(spigot1).when(player1).spigot();
        doReturn(spigot2).when(player2).spigot();
        Collection<Player> players = new HashSet<>();
        players.add(player1);
        players.add(player2);
        doReturn(players).when(playerRetriever).retrieveOnlinePlayers();

        // WHEN
        printer.broadcastClickable("message", "command");

        // THEN
        Mockito.verify(soundManager, times(2)).play(any(Player.class), any(Sound.class), Matchers.anyFloat());
        Mockito.verify(soundManager, times(2)).play(any(Player.class), any(Sound.class));
    }

}