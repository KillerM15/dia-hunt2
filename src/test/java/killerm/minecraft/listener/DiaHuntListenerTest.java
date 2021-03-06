package killerm.minecraft.listener;

import killerm.minecraft.communication.Printer;
import killerm.minecraft.game.data.ChestGameData;
import killerm.minecraft.game.data.GameStatus;
import killerm.minecraft.game.data.PlayerGameData;
import killerm.minecraft.game.flow.Condition;
import killerm.minecraft.game.flow.DeathProcessor;
import killerm.minecraft.game.flow.GameStatusType;
import killerm.minecraft.game.shop.Shop;
import killerm.minecraft.manager.ScoreboardManager;
import killerm.minecraft.utilities.DamageRecorder;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class DiaHuntListenerTest {
    private Printer printer = mock(Printer.class);
    private GameStatus gameStatus = mock(GameStatus.class);
    private PlayerGameData playerGameData = mock(PlayerGameData.class);
    private ChestGameData chestGameData = mock(ChestGameData.class);
    private DamageRecorder damageRecorder = mock(DamageRecorder.class);
    private DeathProcessor deathProcessor = mock(DeathProcessor.class);
    private ScoreboardManager scoreboardManager = mock(ScoreboardManager.class);
    private Shop shop = mock(Shop.class);
    private DiaHuntListener diaHuntListener = new DiaHuntListener(printer, gameStatus, playerGameData, chestGameData, damageRecorder, deathProcessor, scoreboardManager, shop);

    @Test
    public void GIVEN_EntityRegainHealthEvent_and_GameStatus_RUNNING_and_player_inGame_and_Regain_Reason_REGEN_WHEN_onPlayerRegainHealth_THEN_event_cancelled() {
        // GIVEN
        EntityRegainHealthEvent e = mock(EntityRegainHealthEvent.class);
        doReturn(GameStatusType.RUNNING).when(gameStatus).getGameStatusType();
        Player player = mock(Player.class);
        doReturn(player).when(e).getEntity();
        doReturn(true).when(playerGameData).inGame(player);
        doReturn(EntityRegainHealthEvent.RegainReason.REGEN).when(e).getRegainReason();

        // WHEN
        diaHuntListener.onPlayerRegainHealth(e);

        // THEN
        verify(e).setCancelled(true);
    }

    @Test
    public void GIVEN_FoodLevelChangeEvent_and_GameStatus_RUNNING_and_player_inGame_WHEN_onHungerDeplete_THEN_event_cancelled() {
        // GIVEN
        FoodLevelChangeEvent e = mock(FoodLevelChangeEvent.class);
        doReturn(GameStatusType.RUNNING).when(gameStatus).getGameStatusType();
        Player player = mock(Player.class);
        doReturn(player).when(e).getEntity();
        doReturn(true).when(playerGameData).inGame(player);

        // WHEN
        diaHuntListener.onHungerDeplete(e);

        // THEN
        verify(e).setCancelled(true);
    }

    @Test
    public void onPlace() {
        // Can't test because final method can't be mocked
    }

    @Test
    public void onBreak() {
        // Can't test because final method can't be mocked
    }

    @Test
    public void GIVEN_GameStatus_RUNNING_and_damager_and_reciever_inGame_WHEN_onEntityDamageByEntity_THEN_damageRecorder_put_receiver_damager() {
        // GIVEN
        EntityDamageByEntityEvent e = mock(EntityDamageByEntityEvent.class);
        doReturn(GameStatusType.RUNNING).when(gameStatus).getGameStatusType();
        Player receiver = mock(Player.class);
        Player damager = mock(Player.class);
        doReturn(receiver).when(e).getEntity();
        doReturn(damager).when(e).getDamager();
        doReturn(true).when(playerGameData).inGame(receiver);
        doReturn(true).when(playerGameData).inGame(damager);

        // WHEN
        diaHuntListener.onEntityDamageByEntity(e);

        // THEN
        Mockito.verify(damageRecorder).put(receiver, damager);
    }

    @Test
    public void onRespawn() {
        // Can't test because config uses server
    }

    @Test
    public void GIVEN_EntityDamageEvent_and_GameStatus_RUNNING_and_player_inGame_and_ALIVE_and_damageCause_VOID_WHEN_onDamageEvent_THEN_deathProcessor_processDeathInVoid() {
        // GIVEN
        EntityDamageEvent e = mock(EntityDamageEvent.class);
        doReturn(GameStatusType.RUNNING).when(gameStatus).getGameStatusType();
        Player player = mock(Player.class);
        doReturn(player).when(e).getEntity();
        doReturn(true).when(playerGameData).inGame(player);
        doReturn(Condition.ALIVE).when(playerGameData).getCondition(player);
        doReturn(EntityDamageEvent.DamageCause.VOID).when(e).getCause();

        // WHEN
        diaHuntListener.onDamageEvent(e);

        // THEN
        Mockito.verify(deathProcessor).processDeathInVoid(player);
    }

    @Test
    public void GIVEN_EntityDamageEvent_and_GameStatus_RUNNING_and_player_inGame_and_ALIVE_and_damageCause_LIGHTNING_WHEN_onDamageEvent_THEN_nothing() {
        // GIVEN
        EntityDamageEvent e = mock(EntityDamageEvent.class);
        doReturn(GameStatusType.RUNNING).when(gameStatus).getGameStatusType();
        Player player = mock(Player.class);
        doReturn(player).when(e).getEntity();
        doReturn(true).when(playerGameData).inGame(player);
        doReturn(Condition.ALIVE).when(playerGameData).getCondition(player);
        doReturn(EntityDamageEvent.DamageCause.LIGHTNING).when(e).getCause();
        doReturn(1.0).when(player).getHealth();
        doReturn(1.0).when(e).getDamage();


        // WHEN
        diaHuntListener.onDamageEvent(e);

        // THEN
        Mockito.verify(deathProcessor).processDeath(player);
    }

    @Test
    public void onPlayerQuit() {
        // Can't test because final method can't be mocked
    }

    @Test
    public void onInventoryClick() {
        // Can't test because BukkitTask
    }

    @Test
    public void onInventoryItemDrop() {
        // Can't test because BukkitTask
    }

    @Test
    public void onItemPickup() {
        // Can't test because BukkitTask
    }
}