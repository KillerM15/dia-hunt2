package killerm.minecraft.communication;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.*;

class SoundsTest {
    Sounds sounds = new Sounds();

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
}