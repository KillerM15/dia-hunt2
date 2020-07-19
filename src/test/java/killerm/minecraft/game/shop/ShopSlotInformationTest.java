package killerm.minecraft.game.shop;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class ShopSlotInformationTest {
    private ShopSlotInformation shopSlotInformation = new ShopSlotInformation(ItemCategory.values());

    @Test
    public void WHEN_6_times_getNextAvailibleSlot_of_index_0_THEN_correct_Slot_returned() {
        assertEquals(2, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[0]));
        assertEquals(3, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[0]));
        assertEquals(4, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[0]));
        assertEquals(5, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[0]));
        assertEquals(6, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[0]));
        assertEquals(7, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[0]));
    }

    @Test
    public void WHEN_3_times_getNextAvailibleSlot_of_index_4_and_5_times_getNextAvailibleSlot_of_index_5_THEN_correct_Slot_returned() {
        assertEquals(20, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[2]));
        assertEquals(21, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[2]));
        assertEquals(29, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[3]));
        assertEquals(22, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[2]));
        assertEquals(30, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[3]));
        assertEquals(23, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[2]));
        assertEquals(31, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[3]));
        assertEquals(32, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[3]));
        assertEquals(33, shopSlotInformation.getNextAvailibleSlot(ItemCategory.values()[3]));
    }
}