package killerm.minecraft.game;

import killerm.minecraft.utilities.Team;
import org.bukkit.Location;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.mock;

class DiaChestGameDataTest {
    private DiaChestGameData diaChestGameData = new DiaChestGameData();

    @Test
    public void GIVEN_1_location_lava_2_locations_aqua_added_WHEN_getLocations_THEN_return_only_lava_location() {
        // GIVEN
        Location loc1 = mock(Location.class);
        Location loc2 = mock(Location.class);
        Location loc3 = mock(Location.class);

        diaChestGameData.addLocation(loc1, Team.AQUA);
        diaChestGameData.addLocation(loc2, Team.AQUA);
        diaChestGameData.addLocation(loc3, Team.LAVA);

        // WHEN
        Collection<Location> locations = diaChestGameData.getLocations(Team.LAVA);

        // THEN
        assertEquals(1, locations.size());
        assert(!locations.contains(loc1));
        assert(!locations.contains(loc2));
        assert(locations.contains(loc3));
    }

}