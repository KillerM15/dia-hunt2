package killerm.minecraft.utilities;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class ItemRemoverTest {
    private ItemRemover itemRemover = new ItemRemover();

    @Test
    public void GIVEN_world_with_item_entity_WHEN_remove_THEN_entity_removed() {
        // GIVEN
        World world = mock(World.class);
        Item item = mock(Item.class);
        List<Entity> entities = new ArrayList<>();
        entities.add(item);
        doReturn(entities).when(world).getEntities();

        // WHEN
        itemRemover.remove(world);

        // THEN
        Mockito.verify(item, times(1)).remove();
    }
}