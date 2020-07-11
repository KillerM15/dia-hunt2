package killerm.minecraft.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    @Test
    void GIVEN_equal_coordinates_WHEN_equals_THEN_true() {
        // GIVEN
        Coordinates coordinates1 = new Coordinates(2, 3, 4);
        Coordinates coordinates2 = new Coordinates(2, 3, 4);

        // WHEN
        boolean isEqual = coordinates1.equals(coordinates2);

        // THEN
        assertEquals(true, isEqual);
    }

    @Test
    void GIVEN_unequal_coordinates_WHEN_equals_THEN_false() {
        // GIVEN
        Coordinates coordinates1 = new Coordinates(2, 3, 5);
        Coordinates coordinates2 = new Coordinates(2, 3, 4);

        // WHEN
        boolean isEqual = coordinates1.equals(coordinates2);

        // THEN
        assertEquals(false, isEqual);
    }
}