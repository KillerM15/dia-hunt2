package killerm.minecraft.listener;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.data.DiaConfig;
import killerm.minecraft.game.data.ChestGameData;
import killerm.minecraft.game.data.GameStatus;
import killerm.minecraft.game.data.PlayerGameData;
import killerm.minecraft.game.data.Team;
import killerm.minecraft.game.flow.Condition;
import killerm.minecraft.game.flow.DeathProcessor;
import killerm.minecraft.game.flow.GameStatusType;
import killerm.minecraft.game.item.GameItem;
import killerm.minecraft.game.shop.Shop;
import killerm.minecraft.manager.ScoreboardManager;
import killerm.minecraft.utilities.DamageRecorder;
import killerm.minecraft.utilities.ItemEquals;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class DiaHuntListener implements Listener {
    private Printer printer;
    private GameStatus gameStatus;
    private PlayerGameData playerGameData;
    private ChestGameData chestGameData;
    private DamageRecorder damageRecorder;
    private DeathProcessor deathProcessor;
    private ScoreboardManager scoreboardManager;
    private Shop shop;
    private GameItem gameItem;

    public DiaHuntListener(GameStatus gameStatus, PlayerGameData playerGameData, ChestGameData chestGameData) {
        this.printer = new Printer();
        this.gameStatus = gameStatus;
        this.playerGameData = playerGameData;
        this.chestGameData = chestGameData;
        this.damageRecorder = new DamageRecorder(gameStatus, (int) (double) DiaConfig.SECONDS_COUNTS_AS_KILL.get());
        this.deathProcessor = new DeathProcessor(gameStatus, playerGameData, chestGameData, damageRecorder);
        this.scoreboardManager = new ScoreboardManager();
        this.shop = new Shop();
        this.gameItem = new GameItem();
    }

    public DiaHuntListener(Printer printer, GameStatus gameStatus, PlayerGameData playerGameData, ChestGameData chestGameData, DamageRecorder damageRecorder, DeathProcessor deathProcessor, ScoreboardManager scoreboardManager, Shop shop) {
        this.printer = printer;
        this.gameStatus = gameStatus;
        this.playerGameData = playerGameData;
        this.chestGameData = chestGameData;
        this.damageRecorder = damageRecorder;
        this.deathProcessor = deathProcessor;
        this.scoreboardManager = scoreboardManager;
        this.shop = shop;
        this.gameItem = null; // TODO
    }

    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent e) {
        // Disable health regen
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && e.getEntity() instanceof Player
                && playerGameData.inGame((Player) e.getEntity())
                && (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || e.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN)) {

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        // Disable hunger
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && e.getEntity() instanceof Player
                && playerGameData.inGame((Player) e.getEntity())) {

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        // Add shulkerBoxes to dia chest locations
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && playerGameData.inGame(e.getPlayer())
                && (e.getBlock().getType() == Material.BLUE_SHULKER_BOX || e.getBlock().getType() == Material.RED_SHULKER_BOX)) {

            Location boxLocation = e.getBlock().getLocation();

            if (e.getBlock().getType() == Material.BLUE_SHULKER_BOX) {
                chestGameData.addLocation(boxLocation, Team.AQUA);
            } else {
                chestGameData.addLocation(boxLocation, Team.LAVA);
            }
        }

        // Disable placing shop item
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && playerGameData.inGame(e.getPlayer())
                && shop.isShopItem(e.getItemInHand())) {

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        // Remove shulkerBoxes from chest locations
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && playerGameData.inGame(e.getPlayer())
                && (e.getBlock().getType() == Material.BLUE_SHULKER_BOX || e.getBlock().getType() == Material.RED_SHULKER_BOX)) {

            Location boxLocation = e.getBlock().getLocation();
            chestGameData.removeLocation(boxLocation);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        // Add damage taken to damage recorder
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && e.getEntity() instanceof Player
                && e.getDamager() instanceof Player
                && playerGameData.inGame((Player) e.getEntity())
                && playerGameData.inGame((Player) e.getDamager())) {

            Player receiver = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();

            damageRecorder.put(receiver, damager);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        // Print error if respawn by title screen
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && playerGameData.inGame(e.getPlayer())) {

            Player player = e.getPlayer();

            if (playerGameData.team(player) == Team.AQUA) {
                e.setRespawnLocation(DiaConfig.SPAWN_AQUA.get());
            } else if (playerGameData.team(player) == Team.LAVA) {
                e.setRespawnLocation(DiaConfig.SPAWN_LAVA.get());
            }

            printer.broadcastError("This shouldn't have happened! Player respawned not by DiaHunt!");
        }
    }

    @EventHandler
    public void onDamageEvent(EntityDamageEvent e) {
        // Process death if entity has <=0 hp
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && e.getEntity() instanceof Player
                && playerGameData.inGame((Player) e.getEntity())
                && playerGameData.getCondition((Player) e.getEntity()) == Condition.ALIVE) {

            Player player = (Player) e.getEntity();
            boolean gotDamageInVoid = e.getCause().equals(EntityDamageEvent.DamageCause.VOID);

            if (gotDamageInVoid) {
                deathProcessor.processDeathInVoid(player);
                e.setCancelled(true);
            } else if (player.getHealth() - e.getDamage() <= 0) {
                deathProcessor.processDeath(player);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        // Force player to leave game
        Player player = e.getPlayer();

        if (playerGameData.inGame(player)) {
            player.chat("/diahunt leave");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        // Buy item in shop
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && playerGameData.inGame((Player) e.getWhoClicked())
                && shop.isShop(e.getClickedInventory())) {

            Player player = (Player) e.getWhoClicked();
            int slot = e.getSlot();
            ItemStack itemStack = e.getClickedInventory().getItem(slot);

            if (e.getAction() == InventoryAction.PICKUP_ALL) {
                shop.buyItem(player, itemStack);
            } else if (e.getAction() == InventoryAction.PICKUP_HALF) {
                shop.buyItemUntilOneDiaLeft(player, itemStack);
            }

            e.setCancelled(true);
        }

        // Disable moving shop item
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && playerGameData.inGame((Player) e.getWhoClicked())
                && shop.isShopItem(e.getCurrentItem())) {

            e.setCancelled(true);
        }

        // Disable shift-clicking in shop
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && playerGameData.inGame((Player) e.getWhoClicked())
                && shop.isShop(e.getView().getTopInventory())
                && e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {

            e.setCancelled(true);
        }

        // Update diamond scoreboard, to keep it simple on every click
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && e.getWhoClicked() instanceof Player
                && playerGameData.inGame((Player) e.getWhoClicked())) {

            Player player = (Player) e.getWhoClicked();

            new BukkitRunnable() {
                @Override
                public void run() {
                    scoreboardManager.refresh(player);
                }
            }.runTaskLater(DiaHuntPlugin.getInstance(), 1);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        // Disable dragging items in shop
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && playerGameData.inGame((Player) e.getWhoClicked())
                && shop.isShop(e.getInventory())) {

            e.setCancelled(true); // TODO: Drag is disabled also in player inventory. Enable it
        }

        // Update diamond scoreboard, to keep it simple on every drag
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && e.getWhoClicked() instanceof Player
                && playerGameData.inGame((Player) e.getWhoClicked())) {

            Player player = (Player) e.getWhoClicked();

            new BukkitRunnable() {
                @Override
                public void run() {
                    scoreboardManager.refresh(player);
                }
            }.runTaskLater(DiaHuntPlugin.getInstance(), 1);
        }
    }

    @EventHandler
    public void onInventoryItemDrop(PlayerDropItemEvent e) {
        // Update diamond scoreboard
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && playerGameData.inGame(e.getPlayer())
                && ItemEquals.equals(e.getItemDrop().getItemStack(), gameItem.diamond())) {
            Player player = e.getPlayer();

            new BukkitRunnable() {
                @Override
                public void run() {
                    scoreboardManager.refresh(player);
                }
            }.runTaskLater(DiaHuntPlugin.getInstance(), 1);
        }

        // Cancel shop item drop
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && playerGameData.inGame(e.getPlayer())
                && shop.isShopItem(e.getItemDrop().getItemStack())) {

            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        // Update diamond scoreboard
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && e.getEntity() instanceof Player
                && playerGameData.inGame((Player) e.getEntity())
                && ItemEquals.equals(e.getItem().getItemStack(), gameItem.diamond())) {

            new BukkitRunnable() {
                @Override
                public void run() {
                    scoreboardManager.refresh((Player) e.getEntity());
                }
            }.runTaskLater(DiaHuntPlugin.getInstance(), 1);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        // Open shop if player presses right click with shop item
        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING
                && playerGameData.inGame(e.getPlayer())
                && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
                && shop.isShopItem(e.getPlayer().getInventory().getItemInMainHand())) {

            Team team = playerGameData.team(e.getPlayer());
            e.getPlayer().openInventory(shop.getInventory(team));
        }
    }
}
