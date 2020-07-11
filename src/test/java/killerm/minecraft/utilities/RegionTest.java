package killerm.minecraft.utilities;

import org.bukkit.Location;
import org.bukkit.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

class RegionTest {

    @Test
    public void GIVEN_2_coordinates_WHEN_min_max_XYZ_THEN_return_correct_values() {
        // GIVEN
        Location location1 = new Location(null, 2.3, 30.4, 1889.2);
        Location location2 = new Location(null, 4123.5, 1.4, 21.7);
        Region region = new Region(location1, location2);

        //WHEN / THEN
        assertEquals(2, region.minX());
        assertEquals(4124, region.maxX());

        assertEquals(1, region.minY());
        assertEquals(30, region.maxY());

        assertEquals(22, region.minZ());
        assertEquals(1889, region.maxZ());
    }

    @Test
    public void GIVEN_2_equal_regions_WHEN_isEqual_THEN_true() {
        // GIVEN
        Location location1 = new Location(null, 1.5, 29.5, 1889.1);
        Location location2 = new Location(null, 4124, 1.4, 22.4);
        Region region1 = new Region(location1, location2);

        Location location3 = new Location(null, 2.2, 30, 1889);
        Location location4 = new Location(null, 4124, 1, 22);
        Region region2 = new Region(location1, location2);

        // WHEN
        boolean isEqual = region1.equals(region2);

        assertEquals(true, isEqual);
    }

    @Test
    public void GIVEN_2_unequal_regions_WHEN_isEqual_THEN_false() {
        // GIVEN
        Location location1 = new Location(null, 3, 30, 1889);
        Location location2 = new Location(null, 4124, 1, 22);
        Region region1 = new Region(location1, location2);

        Location location3 = new Location(null, 2, 30, 1889);
        Location location4 = new Location(null, 4124, 1, 22);
        Region region2 = new Region(location3, location4);

        // WHEN
        boolean isEqual = region1.equals(region2);

        assertEquals(false, isEqual);
    }

    @Test
    public void GIVEN_region_WHEN_range_XYZ_THEN_return_correct_ranges() {
        // GIVEN
        Location location1 = new Location(null, 3, 30, 1889);
        Location location2 = new Location(null, 290, 1, 22);
        Region region = new Region(location1, location2);

        // WHEN
        int rangeX = region.rangeX();
        int rangeY = region.rangeY();
        int rangeZ = region.rangeZ();

        // THEN
        assertEquals(287, rangeX);
        assertEquals(29, rangeY);
        assertEquals(1867, rangeZ);
    }

    @Test
    public void func() {
        // GIVEN
        World world = mock(World.class);
        Location location1 = new Location(world, 3, 30, 1889);
        Location location2 = new Location(world, 290, 1, 22);
        Region region = new Region(location1, location2);

        // WHEN / THEN
        assertEquals(world, region.getWorld());
    }
}
