package killerm.minecraft.communication;

import killerm.minecraft.utilities.PlayerRetriever;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class SoundsTest {
    private PlayerRetriever playerRetriever = mock(PlayerRetriever.class);
    private Sounds sounds = new Sounds(playerRetriever);

    @Test
    public void GIVEN_player_location_world_sound_pitch_WHEN_play_THEN_verify_correct_method_called() {
        // GIVEN
        World world = mock(World.class);
        Player player = mock(Player.class);
        Location playerLocation = new Location(world, 1, 2, 3);
        doReturn(world).when(player).getWorld();
        doReturn(playerLocation).when(player).getLocation();

        Sound sound = Sound.ENTITY_HORSE_DEATH;
        float pitch = (float) 0.5;

        // WHEN
        sounds.play(player, Sound.ENTITY_HORSE_DEATH, pitch);

        // THEN
        Mockito.verify(world, times(1)).playSound(playerLocation, sound, 1, pitch);
    }

    @Test
    public void GIVEN_player_location_world_sound_WHEN_play_THEN_verify_correct_method_called() {
        // GIVEN
        World world = mock(World.class);
        Player player = mock(Player.class);
        Location playerLocation = new Location(world, 1, 2, 3);
        doReturn(world).when(player).getWorld();
        doReturn(playerLocation).when(player).getLocation();

        Sound sound = Sound.ENTITY_HORSE_DEATH;

        // WHEN
        sounds.play(player, sound);

        // THEN
        Mockito.verify(world, times(1)).playSound(playerLocation, sound, 1, 1);
    }

    @Test
    public void GIVEN_set_players_WHEN_play_THEN_verify_correct_method_called() {
        // GIVEN
        World world1 = mock(World.class);
        Player player1 = mock(Player.class);
        Location playerLocation1 = new Location(world1, 1, 2, 3);
        doReturn(world1).when(player1).getWorld();
        doReturn(playerLocation1).when(player1).getLocation();

        World world2 = mock(World.class);
        Player player2 = mock(Player.class);
        Location playerLocation2 = new Location(world2, 1, 2, 3);
        doReturn(world2).when(player2).getWorld();
        doReturn(playerLocation2).when(player2).getLocation();

        Set<Player> players = new HashSet<Player>();
        players.add(player1);
        players.add(player2);

        Sound sound = Sound.ENTITY_HORSE_DEATH;

        // WHEN
        sounds.play(players, sound);

        // THEN
        Mockito.verify(world1, times(1)).playSound(playerLocation1, sound, 1, 1);
        Mockito.verify(world2, times(1)).playSound(playerLocation2, sound, 1, 1);
    }

    @Test
    public void GIVEN_set_players_WHEN_play_with_pitch_THEN_verify_correct_method_called() {
        // GIVEN
        World world1 = mock(World.class);
        Player player1 = mock(Player.class);
        Location playerLocation1 = new Location(world1, 1, 2, 3);
        doReturn(world1).when(player1).getWorld();
        doReturn(playerLocation1).when(player1).getLocation();

        World world2 = mock(World.class);
        Player player2 = mock(Player.class);
        Location playerLocation2 = new Location(world2, 1, 2, 3);
        doReturn(world2).when(player2).getWorld();
        doReturn(playerLocation2).when(player2).getLocation();

        Set<Player> players = new HashSet<Player>();
        players.add(player1);
        players.add(player2);

        Sound sound = Sound.ENTITY_HORSE_DEATH;

        // WHEN
        sounds.play(players, sound, (float) 1.5);

        // THEN
        Mockito.verify(world1, times(1)).playSound(playerLocation1, sound, 1, (float) 1.5);
        Mockito.verify(world2, times(1)).playSound(playerLocation2, sound, 1, (float) 1.5);
    }


    @Test
    public void GIVEN_online_players_WHEN_play_with_sound_THEN_verify_correct_method_called() {
        // GIVEN
        World world1 = mock(World.class);
        Player player1 = mock(Player.class);
        Location playerLocation1 = new Location(world1, 1, 2, 3);
        doReturn(world1).when(player1).getWorld();
        doReturn(playerLocation1).when(player1).getLocation();

        World world2 = mock(World.class);
        Player player2 = mock(Player.class);
        Location playerLocation2 = new Location(world2, 1, 2, 3);
        doReturn(world2).when(player2).getWorld();
        doReturn(playerLocation2).when(player2).getLocation();

        Set<Player> players = new HashSet<Player>();
        players.add(player1);
        players.add(player2);

        doReturn(players).when(playerRetriever).retrieveOnlinePlayers();

        Sound sound = Sound.ENTITY_HORSE_DEATH;

        // WHEN
        sounds.play(sound);

        // THEN
        Mockito.verify(world1, times(1)).playSound(playerLocation1, sound, 1,1);
        Mockito.verify(world2, times(1)).playSound(playerLocation2, sound, 1, 1);
    }
}