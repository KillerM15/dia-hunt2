package killerm.minecraft.game.shop;

import killerm.minecraft.communication.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemCategoryTest {

    @Test
    public void GIVEN_ItemCategory_WHEN_toString_THEN_capitalized_String_returned() {
        assertEquals("Building", ItemCategory.BUILDING.toString());
    }

    @Test
    public void GIVEN_ItemCategory_WHEN_getDescription_THEN_correct_description_returned() {
        assertEquals(Message.DESCRIPTION_BUILDING, ItemCategory.BUILDING.getDescription());
    }
}