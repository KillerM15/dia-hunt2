package killerm.minecraft.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegionTest {

    @Test
    void GIVEN_2_coordinates_WHEN_min_max_XYZ_THEN_return_correct_values() {
        // GIVEN
        Coordinates coordinates1 = new Coordinates(2, 30, 1889);
        Coordinates coordinates2 = new Coordinates(4124, 1, 22);
        Region region = new Region(coordinates1, coordinates2);

        //WHEN / THEN
        assertEquals(2, region.minX());
        assertEquals(4124, region.maxX());

        assertEquals(1, region.minY());
        assertEquals(30, region.maxY());

        assertEquals(22, region.minZ());
        assertEquals(1889, region.maxZ());
    }

    @Test
    void GIVEN_2_equal_regions_WHEN_isEqual_THEN_true() {
        // GIVEN
        Coordinates coordinates1 = new Coordinates(2, 30, 1889);
        Coordinates coordinates2 = new Coordinates(4124, 1, 22);
        Region region1 = new Region(coordinates1, coordinates2);

        Coordinates coordinates3 = new Coordinates(2, 30, 1889);
        Coordinates coordinates4 = new Coordinates(4124, 1, 22);
        Region region2 = new Region(coordinates3, coordinates4);

        // WHEN
        boolean isEqual = region1.equals(region2);

        assertEquals(true, isEqual);
    }

    @Test
    void GIVEN_2_unequal_regions_WHEN_isEqual_THEN_false() {
        // GIVEN
        Coordinates coordinates1 = new Coordinates(3, 30, 1889);
        Coordinates coordinates2 = new Coordinates(4124, 1, 22);
        Region region1 = new Region(coordinates1, coordinates2);

        Coordinates coordinates3 = new Coordinates(2, 30, 1889);
        Coordinates coordinates4 = new Coordinates(4124, 1, 22);
        Region region2 = new Region(coordinates3, coordinates4);

        // WHEN
        boolean isEqual = region1.equals(region2);

        assertEquals(false, isEqual);
    }

    @Test
    void GIVEN_region_WHEN_range_XYZ_THEN_return_correct_ranges() {
        // GIVEN
        Coordinates coordinates1 = new Coordinates(3, 30, 1889);
        Coordinates coordinates2 = new Coordinates(290, 1, 22);
        Region region = new Region(coordinates1, coordinates2);

        // WHEN
        int rangeX = region.rangeX();
        int rangeY = region.rangeY();
        int rangeZ = region.rangeZ();

        // THEN
        assertEquals(287, rangeX);
        assertEquals(29, rangeY);
        assertEquals(1867, rangeZ);
    }
}
