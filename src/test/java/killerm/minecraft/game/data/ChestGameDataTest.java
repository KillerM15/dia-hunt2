package killerm.minecraft.game.data;

import killerm.minecraft.game.flow.DiamondIndicator;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class ChestGameDataTest {
    private DiamondIndicator diamondIndicator = mock(DiamondIndicator.class);
    private ChestGameData chestGameData = new ChestGameData(diamondIndicator);

    @Test
    public void GIVEN_1_location_lava_2_locations_aqua_added_WHEN_getLocations_LAVA_THEN_return_only_lava_location() {
        // GIVEN
        Location loc1 = mock(Location.class);
        Location loc2 = mock(Location.class);
        Location loc3 = mock(Location.class);

        chestGameData.addLocation(loc1, Team.AQUA);
        chestGameData.addLocation(loc2, Team.AQUA);
        chestGameData.addLocation(loc3, Team.LAVA);

        // WHEN
        Collection<Location> locations = chestGameData.getLocations(Team.LAVA);

        // THEN
        assertEquals(1, locations.size());
        assert (!locations.contains(loc1));
        assert (!locations.contains(loc2));
        assert (locations.contains(loc3));
    }

    @Test
    public void GIVEN_1_shulkerbox_lava_2_shulkerboxes_aqua_added_WHEN_getShulkerBoxes_LAVA_THEN_return_only_lava_shulkerBoxes() {
        // GIVEN
        ShulkerBox shulkerBox1 = mock(ShulkerBox.class);
        ShulkerBox shulkerBox2 = mock(ShulkerBox.class);
        ShulkerBox shulkerBox3 = mock(ShulkerBox.class);

        Block block1 = mock(Block.class);
        Block block2 = mock(Block.class);
        Block block3 = mock(Block.class);

        Location loc1 = mock(Location.class);
        Location loc2 = mock(Location.class);
        Location loc3 = mock(Location.class);

        doReturn(shulkerBox1).when(block1).getState();
        doReturn(shulkerBox2).when(block2).getState();
        doReturn(shulkerBox3).when(block3).getState();

        doReturn(block1).when(loc1).getBlock();
        doReturn(block2).when(loc2).getBlock();
        doReturn(block3).when(loc3).getBlock();

        chestGameData.addLocation(loc1, Team.AQUA);
        chestGameData.addLocation(loc2, Team.AQUA);
        chestGameData.addLocation(loc3, Team.LAVA);

        // WHEN
        Collection<ShulkerBox> shulkerBoxes = chestGameData.getShulkerBoxes(Team.LAVA);

        // THEN
        assertEquals(1, shulkerBoxes.size());
        assert (!shulkerBoxes.contains(shulkerBox1));
        assert (!shulkerBoxes.contains(shulkerBox2));
        assert (shulkerBoxes.contains(shulkerBox3));
    }

    @Test
    public void GIVEN_1_shulkerbox_lava_2_shulkerboxes_aqua_added_and_removed_WHEN_getShulkerBoxes_LAVA_THEN_size_0() {
        // GIVEN

        Location loc1 = mock(Location.class);
        Location loc2 = mock(Location.class);
        Location loc3 = mock(Location.class);

        chestGameData.addLocation(loc1, Team.AQUA);
        chestGameData.addLocation(loc2, Team.AQUA);
        chestGameData.addLocation(loc3, Team.LAVA);

        chestGameData.removeLocation(loc1);
        chestGameData.removeLocation(loc2);
        chestGameData.removeLocation(loc3);

        // WHEN
        Collection<ShulkerBox> shulkerBoxes = chestGameData.getShulkerBoxes(Team.LAVA);

        // THEN
        assertEquals(0, shulkerBoxes.size());
    }

    @Test
    public void GIVEN_1_shulkerbox_lava_2_shulkerboxes_aqua_added_WHEN_getShulkerBoxes_THEN_return_all_shulkerBoxes() {
        // GIVEN
        ShulkerBox shulkerBox1 = mock(ShulkerBox.class);
        ShulkerBox shulkerBox2 = mock(ShulkerBox.class);
        ShulkerBox shulkerBox3 = mock(ShulkerBox.class);

        Block block1 = mock(Block.class);
        Block block2 = mock(Block.class);
        Block block3 = mock(Block.class);

        Location loc1 = mock(Location.class);
        Location loc2 = mock(Location.class);
        Location loc3 = mock(Location.class);

        doReturn(shulkerBox1).when(block1).getState();
        doReturn(shulkerBox2).when(block2).getState();
        doReturn(shulkerBox3).when(block3).getState();

        doReturn(block1).when(loc1).getBlock();
        doReturn(block2).when(loc2).getBlock();
        doReturn(block3).when(loc3).getBlock();

        chestGameData.addLocation(loc1, Team.AQUA);
        chestGameData.addLocation(loc2, Team.AQUA);
        chestGameData.addLocation(loc3, Team.LAVA);

        // WHEN
        Collection<ShulkerBox> shulkerBoxes = chestGameData.getShulkerBoxes();

        // THEN
        assertEquals(3, shulkerBoxes.size());
        assert (shulkerBoxes.contains(shulkerBox1));
        assert (shulkerBoxes.contains(shulkerBox2));
        assert (shulkerBoxes.contains(shulkerBox3));
    }

    @Test
    public void GIVEN_1_shulkerbox_aqua_added_WHEN_containsDias_AQUA_THEN_true() {
        // GIVEN
        ShulkerBox shulkerBox1 = mock(ShulkerBox.class);
        Block block1 = mock(Block.class);
        Location loc1 = mock(Location.class);
        doReturn(shulkerBox1).when(block1).getState();
        doReturn(block1).when(loc1).getBlock();
        chestGameData.addLocation(loc1, Team.AQUA);
        doReturn(true).when(diamondIndicator).hasDiamonds(shulkerBox1);

        // WHEN / THEN
        assert (chestGameData.containsDias(Team.AQUA));
    }

    @Test
    public void GIVEN_1_shulkerbox_aqua_added_WHEN_containsDias_LAVA_THEN_true() {
        // GIVEN
        ShulkerBox shulkerBox1 = mock(ShulkerBox.class);
        Block block1 = mock(Block.class);
        Location loc1 = mock(Location.class);
        doReturn(shulkerBox1).when(block1).getState();
        doReturn(block1).when(loc1).getBlock();
        chestGameData.addLocation(loc1, Team.AQUA);
        doReturn(true).when(diamondIndicator).hasDiamonds(shulkerBox1);

        // WHEN / THEN
        assert (!chestGameData.containsDias(Team.LAVA));
    }

    @Test
    public void GIVEN_1_shulkerbox_aqua_added_and_cleared_WHEN_containsDias_AQUA_THEN_false() {
        // GIVEN
        ShulkerBox shulkerBox1 = mock(ShulkerBox.class);
        Block block1 = mock(Block.class);
        Location loc1 = mock(Location.class);
        doReturn(shulkerBox1).when(block1).getState();
        doReturn(block1).when(loc1).getBlock();
        chestGameData.addLocation(loc1, Team.AQUA);
        doReturn(true).when(diamondIndicator).hasDiamonds(shulkerBox1);
        chestGameData.clear();

        // WHEN / THEN
        assert (!chestGameData.containsDias(Team.AQUA));
    }
}