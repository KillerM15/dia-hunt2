package killerm.minecraft.manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;

class LocationManagerTest {
    private LocationManager locationManager = new LocationManager();

    @Test
    public void GIVEN_two_players_and_location_WHEN_teleport_THEN_players_teleported() {
        // GIVEN
        Location location = mock(Location.class);
        Set<Player> players = new HashSet<>();
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        players.add(player1);
        players.add(player2);

        // WHEN
        locationManager.teleport(players, location);

        // THEN
        Mockito.verify(player1, times(1)).teleport(location);
        Mockito.verify(player2, times(1)).teleport(location);
    }

}