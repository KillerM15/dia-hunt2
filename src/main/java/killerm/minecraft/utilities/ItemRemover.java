package killerm.minecraft.utilities;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import java.util.List;

public class ItemRemover {
    public void remove(World world) {
        List<Entity> entities = world.getEntities();

        for (Entity entity : entities) {
            if (entity instanceof Item) {
                entity.remove();
            }
        }
    }
}
