package killerm.minecraft.utilities;

import org.junit.jupiter.api.Test;

class ConfigValueTypeTest {
    ConfigDeducer configDeducer = new ConfigDeducer("location");

    @Test
    public void GIVEN_location_WHEN_isLocation_THEN_true() {
        assert(configDeducer.isLocation("location"));
    }

    @Test
    public void GIVEN_double_WHEN_isNumber_THEN_true() {
        assert(configDeducer.isNumber("2229.9930"));
    }

    @Test
    public void GIVEN_random_string_WHEN_isNumber_THEN_false() {
        assert(!configDeducer.isNumber("2229.9930e"));
    }

    @Test
    public void GIVEN_Integer_WHEN_isNumber_THEN_true() {
        assert(configDeducer.isNumber("2229"));
    }
}
