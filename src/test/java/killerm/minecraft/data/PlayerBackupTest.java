package killerm.minecraft.data;

import killerm.minecraft.utilities.ItemStackCopy;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class PlayerBackupTest {
    private ItemStackCopy itemStackCopy = mock(ItemStackCopy.class);
    private PlayerBackup playerBackup = new PlayerBackup(itemStackCopy);

    @Test
    public void GIVEN_player_set_backup_WHEN_restore_THEN_data_set_to_player() {
        // GIVEN
        Player player = mock(Player.class);
        GameMode gamemode = GameMode.SPECTATOR;
        PlayerInventory inventory = mock(PlayerInventory.class);
        ItemStack[] itemStacks = new ItemStack[]{};
        ItemStack[] itemStacksArmor = new ItemStack[]{};
        Location location = mock(Location.class);
        Collection<PotionEffect> potionEffects = new HashSet<>();
        double health = 20;
        int foodLevel = 5;


        doReturn(itemStacks).when(itemStackCopy).getCopy(itemStacks);
        doReturn(itemStacksArmor).when(itemStackCopy).getCopy(itemStacksArmor);

        doReturn(gamemode).when(player).getGameMode();
        doReturn(inventory).when(player).getInventory();
        doReturn(itemStacks).when(inventory).getContents();
        doReturn(itemStacksArmor).when(inventory).getArmorContents();
        doReturn(location).when(player).getLocation();
        doReturn(potionEffects).when(player).getActivePotionEffects();
        doReturn(health).when(player).getHealth();
        doReturn(foodLevel).when(player).getFoodLevel();

        Set<Player> players = new HashSet<>();
        players.add(player);

        playerBackup.backup(players);

        // WHEN
        playerBackup.restore(player);

        // THEN
        Mockito.verify(player, times(1)).setGameMode(gamemode);
        Mockito.verify(player.getInventory(), times(1)).setContents(itemStacks);
        Mockito.verify(player.getInventory(), times(1)).setContents(itemStacksArmor);
        Mockito.verify(player, times(1)).teleport(location);
        Mockito.verify(player, times(1)).addPotionEffects(potionEffects);
        Mockito.verify(player, times(1)).setHealth(health);
        Mockito.verify(player, times(1)).setFoodLevel(foodLevel);
    }

    @Test
    public void GIVEN_player_backup_WHEN_restore_twice_THEN_Exception() {
        // GIVEN
        Player player = mock(Player.class);
        GameMode gamemode = GameMode.SPECTATOR;
        PlayerInventory inventory = mock(PlayerInventory.class);
        ItemStack[] itemStacks = new ItemStack[]{};
        ItemStack[] itemStacksArmor = new ItemStack[]{};
        Location location = mock(Location.class);
        Collection<PotionEffect> potionEffects = new HashSet<>();
        double health = 20;
        int foodLevel = 5;

        doReturn(itemStacks).when(itemStackCopy).getCopy(itemStacks);
        doReturn(itemStacksArmor).when(itemStackCopy).getCopy(itemStacksArmor);

        doReturn(gamemode).when(player).getGameMode();
        doReturn(inventory).when(player).getInventory();
        doReturn(itemStacks).when(inventory).getContents();
        doReturn(itemStacksArmor).when(inventory).getArmorContents();
        doReturn(location).when(player).getLocation();
        doReturn(potionEffects).when(player).getActivePotionEffects();
        doReturn(health).when(player).getHealth();
        doReturn(foodLevel).when(player).getFoodLevel();

        playerBackup.backup(player);
        playerBackup.restore(player);

        // WHEN
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> playerBackup.restore(player),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals("Player not found!"));
    }
}