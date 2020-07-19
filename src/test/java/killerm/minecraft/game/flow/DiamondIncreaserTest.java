package killerm.minecraft.game.flow;

import killerm.minecraft.game.data.ChestGameData;
import killerm.minecraft.game.data.PlayerGameData;
import killerm.minecraft.game.interaction.ItemGiver;
import killerm.minecraft.manager.ScoreboardManager;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.HashSet;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;

class DiamondIncreaserTest {
    private PlayerGameData playerGameData = mock(PlayerGameData.class);
    private ChestGameData chestGameData = mock(ChestGameData.class);
    private DiamondIndicator diamondIndicator = mock(DiamondIndicator.class);
    private ItemGiver itemGiver = mock(ItemGiver.class);
    private ScoreboardManager scoreboardManager = mock(ScoreboardManager.class);
    private DiamondIncreaser diamondIncreaser = new DiamondIncreaser(playerGameData, chestGameData, diamondIndicator, itemGiver, scoreboardManager);


    @Test
    public void GIVEN_0_players_WHEN_startMocked_THEN_no_items_given_and_no_scoreboard_refresh() {
        // WHEN
        diamondIncreaser.startMocked();

        // THEN
        Mockito.verify(itemGiver, times(0)).giveDia(Matchers.any(Player.class));
        Mockito.verify(itemGiver, times(0)).giveDia(Matchers.any(ShulkerBox.class));
        Mockito.verify(scoreboardManager, times(0)).refresh(Matchers.any(Player.class));
    }

    @Test
    public void GIVEN_3_players_with_diamonds_WHEN_startMocked_THEN_players_got_diamonds() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        doReturn(true).when(diamondIndicator).hasDiamonds(player1);
        doReturn(true).when(diamondIndicator).hasDiamonds(player2);
        doReturn(true).when(diamondIndicator).hasDiamonds(player3);
        Collection<Player> players = new HashSet<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        doReturn(players).when(playerGameData).players();

        // WHEN
        diamondIncreaser.startMocked();

        // THEN
        Mockito.verify(itemGiver, times(1)).giveDia(player1);
        Mockito.verify(itemGiver, times(1)).giveDia(player2);
        Mockito.verify(itemGiver, times(1)).giveDia(player3);
    }

    @Test
    public void GIVEN_2_players_with_diamonds_1_player_without_diamonds_WHEN_startMocked_THEN_2_players_got_diamonds() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        doReturn(true).when(diamondIndicator).hasDiamonds(player1);
        doReturn(true).when(diamondIndicator).hasDiamonds(player2);
        doReturn(false).when(diamondIndicator).hasDiamonds(player3);
        Collection<Player> players = new HashSet<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        doReturn(players).when(playerGameData).players();

        // WHEN
        diamondIncreaser.startMocked();

        // THEN
        Mockito.verify(itemGiver, times(1)).giveDia(player1);
        Mockito.verify(itemGiver, times(1)).giveDia(player2);
        Mockito.verify(itemGiver, times(0)).giveDia(player3);
    }


    @Test
    public void GIVEN_3_shulkerBoxes_with_diamonds_WHEN_startMocked_THEN_shulkerBoxes_got_diamonds() {
        // GIVEN
        ShulkerBox shulkerBox1 = mock(ShulkerBox.class);
        ShulkerBox shulkerBox2 = mock(ShulkerBox.class);
        ShulkerBox shulkerBox3 = mock(ShulkerBox.class);
        doReturn(true).when(diamondIndicator).hasDiamonds(shulkerBox1);
        doReturn(true).when(diamondIndicator).hasDiamonds(shulkerBox2);
        doReturn(true).when(diamondIndicator).hasDiamonds(shulkerBox3);
        Collection<ShulkerBox> shulkerBoxes = new HashSet<>();
        shulkerBoxes.add(shulkerBox1);
        shulkerBoxes.add(shulkerBox2);
        shulkerBoxes.add(shulkerBox3);
        doReturn(shulkerBoxes).when(chestGameData).getShulkerBoxes();

        doReturn(50).when(playerGameData).amountOfPlayers();

        // WHEN
        diamondIncreaser.startMocked();

        // THEN
        Mockito.verify(itemGiver, times(50)).giveDia(shulkerBox1);
        Mockito.verify(itemGiver, times(50)).giveDia(shulkerBox2);
        Mockito.verify(itemGiver, times(50)).giveDia(shulkerBox3);
    }

    @Test
    public void GIVEN_2_shulkerBoxes_with_diamonds_1_without_WHEN_startMocked_THEN_2_shulkerBoxes_got_diamonds() {
        // GIVEN
        ShulkerBox shulkerBox1 = mock(ShulkerBox.class);
        ShulkerBox shulkerBox2 = mock(ShulkerBox.class);
        ShulkerBox shulkerBox3 = mock(ShulkerBox.class);
        doReturn(true).when(diamondIndicator).hasDiamonds(shulkerBox1);
        doReturn(true).when(diamondIndicator).hasDiamonds(shulkerBox2);
        doReturn(false).when(diamondIndicator).hasDiamonds(shulkerBox3);
        Collection<ShulkerBox> shulkerBoxes = new HashSet<>();
        shulkerBoxes.add(shulkerBox1);
        shulkerBoxes.add(shulkerBox2);
        shulkerBoxes.add(shulkerBox3);
        doReturn(shulkerBoxes).when(chestGameData).getShulkerBoxes();

        doReturn(50).when(playerGameData).amountOfPlayers();

        // WHEN
        diamondIncreaser.startMocked();

        // THEN
        Mockito.verify(itemGiver, times(50)).giveDia(shulkerBox1);
        Mockito.verify(itemGiver, times(50)).giveDia(shulkerBox2);
        Mockito.verify(itemGiver, times(0)).giveDia(shulkerBox3);
    }
}