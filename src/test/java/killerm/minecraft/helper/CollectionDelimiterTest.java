package killerm.minecraft.helper;

import killerm.minecraft.communication.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollectionDelimiterTest {
    @Test
    public void GIVEN_enum_WHEN_delimit_THEN_values_correctly_delimited() {
        // WHEN
        String output = CollectionDelimiter.delimit(Letter.values(), Letter::toLower);

        // THEN
        String expectedOutput = "a" + Message.COLLECTION_DELIMITER + "b"+ Message.COLLECTION_DELIMITER + "c";
        assertEquals(expectedOutput, output);
    }

    // GIVEN
    private enum Letter {
        A, B, C;

        public String toLower() {
            return this.toString().toLowerCase();
        }
    }
}