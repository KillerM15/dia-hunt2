package killerm.minecraft.validator;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameValidatorTest {
    GameValidator gameValidator = new GameValidator();

    @Test
    public void GIVEN_0_param_WHEN_validateStart_THEN_nothing() {
        // GIVEN
        String[] params = {};

        // WHEN
        gameValidator.validateStart(params);
    }

    @Test
    public void GIVEN_1_param_WHEN_validateStop_THEN_Exception() {
        // GIVEN
        String[] params = {"1"};

        // WHEN
        DiaHuntParameterException thrown = assertThrows(
                DiaHuntParameterException.class,
                () -> gameValidator.validateStop(params),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.TOO_MANY_INPUTS));
    }

    @Test
    public void GIVEN_0_param_WHEN_validateStop_THEN_nothing() {
        // GIVEN
        String[] params = {};

        // WHEN
        gameValidator.validateStop(params);
    }

    @Test
    public void GIVEN_2_param_WHEN_validateJoin_THEN_Exception() {
        // GIVEN
        String[] param = {"1", "2"};

        // WHEN
        DiaHuntParameterException thrown = assertThrows(
                DiaHuntParameterException.class,
                () -> gameValidator.validateJoin(param),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.TOO_MANY_INPUTS));
    }

    @Test
    public void GIVEN_0_param_WHEN_validateJoin_THEN_nothing() {
        // GIVEN
        String[] params = {};

        // WHEN
        gameValidator.validateJoin(params);
    }

    @Test
    public void GIVEN_1_valid_team_param_WHEN_validateJoin_THEN_nothing() {
        // GIVEN
        String[] params = {"aqua"};

        // WHEN
        gameValidator.validateJoin(params);
    }

    @Test
    public void GIVEN_1_invalid_team_param_WHEN_validateJoin_THEN_Exception() {
        // GIVEN
        String[] param = {"invalid team!"};

        // WHEN
        DiaHuntParameterException thrown = assertThrows(
                DiaHuntParameterException.class,
                () -> gameValidator.validateJoin(param),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.TEAM_NOT_VALID));
    }

    @Test
    public void GIVEN_1_param_WHEN_validateLeave_THEN_Exception() {
        // GIVEN
        String[] param = {"ok"};

        // WHEN
        DiaHuntParameterException thrown = assertThrows(
                DiaHuntParameterException.class,
                () -> gameValidator.validateLeave(param),
                "Expected getCommandType to throw, but it didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.TOO_MANY_INPUTS));
    }

    @Test
    public void GIVEN_0_param_WHEN_validateLeave_THEN_nothing() {
        // GIVEN
        String[] params = {};

        // WHEN
        gameValidator.validateJoin(params);
    }
}