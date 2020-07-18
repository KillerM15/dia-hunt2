package killerm.minecraft.utilities;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.powermock.api.mockito.PowerMockito.mock;

class KillCounterTest {
    private KillCounter killCounter = new KillCounter();

    @Test
    public void GIVEN_player1_with_3_kills_added_and_player2_with_2_kills_added_WHEN_getPlayerWithMostKills_THEN_player1() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        killCounter.addKill(player1);
        killCounter.addKill(player2);
        killCounter.addKill(player1);
        killCounter.addKill(player2);
        killCounter.addKill(player1);

        // WHEN
        Collection<Player> playersWithMostKills = killCounter.retrievePlayersWithMostKills();

        // THEN
        assert (playersWithMostKills.contains(player1));
        assert (!playersWithMostKills.contains(player2));
    }

    @Test
    public void GIVEN_player1_with_3_kills_added_and_player2_with_3_kills_added_WHEN_getPlayerWithMostKills_THEN_player1_player2() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        killCounter.addKill(player1);
        killCounter.addKill(player2);
        killCounter.addKill(player1);
        killCounter.addKill(player2);
        killCounter.addKill(player1);
        killCounter.addKill(player2);

        // WHEN
        Collection<Player> playersWithMostKills = killCounter.retrievePlayersWithMostKills();

        // THEN
        assert (playersWithMostKills.contains(player1));
        assert (playersWithMostKills.contains(player2));
    }

    @Test
    public void GIVEN_player1_with_3_kills_added_and_player2_with_3_kills_added_and_player3_with_3_kills_added_WHEN_getPlayerWithMostKills_THEN_player1_player2_player3() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        killCounter.addKill(player1);
        killCounter.addKill(player2);
        killCounter.addKill(player3);
        killCounter.addKill(player1);
        killCounter.addKill(player2);
        killCounter.addKill(player3);
        killCounter.addKill(player1);
        killCounter.addKill(player2);
        killCounter.addKill(player3);

        // WHEN
        Collection<Player> playersWithMostKills = killCounter.retrievePlayersWithMostKills();

        // THEN
        assert (playersWithMostKills.contains(player1));
        assert (playersWithMostKills.contains(player2));
        assert (playersWithMostKills.contains(player3));
    }

    @Test
    public void GIVEN_no_kills_added_WHEN_getPlayerWithMostKills_THEN_isEmpty() {
        assert(killCounter.retrievePlayersWithMostKills().isEmpty());
    }

    @Test
    public void GIVEN_kills_added_and_clear_WHEN_getPlayerWithMostKills_THEN_isEmpty() {
        // GIVEN
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        killCounter.addKill(player1);
        killCounter.addKill(player2);
        killCounter.addKill(player3);
        killCounter.addKill(player1);
        killCounter.addKill(player2);
        killCounter.addKill(player3);
        killCounter.addKill(player1);
        killCounter.addKill(player2);
        killCounter.addKill(player3);

        killCounter.clear();

        // WHEN
        Collection<Player> playersWithMostKills = killCounter.retrievePlayersWithMostKills();

        // THEN
        assert (playersWithMostKills.isEmpty());
    }
}