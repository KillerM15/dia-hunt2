package killerm.minecraft.game.data;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.ParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
    @Test
    public void GIVEN_Teams_WHEN_toString_THEN_toLower() {
        assertEquals("lava", Team.LAVA.toString());
        assertEquals("aqua", Team.AQUA.toString());
    }

    @Test
    public void GIVEN_valid_teamString_WHEN_getTeam_THEN_return_correct_team() {
        assertEquals(Team.LAVA, Team.getTeam("lava"));
    }

    @Test
    public void GIVEN_invalid_teamString_WHEN_getTeam_THEN_Exception() {
        // GIVEN
        String teamString = "invalid";

        // WHEN
        ParameterException thrown = assertThrows(
                ParameterException.class,
                () -> Team.getTeam(teamString),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.TEAM_NOT_VALID));
    }

    @Test
    public void GIVEN_team_WHEN_getEnemy_THEN_correct_enemy_team_returned() {
        assertEquals(Team.LAVA, Team.AQUA.getEnemy());
        assertEquals(Team.AQUA, Team.LAVA.getEnemy());
    }
}
