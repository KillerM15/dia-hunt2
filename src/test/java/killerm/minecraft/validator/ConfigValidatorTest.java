package killerm.minecraft.validator;

import killerm.minecraft.commands.CommandType;
import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigValidatorTest {
    ConfigValidator configValidator = new ConfigValidator();

    @Test
    public void GIVEN_3_params_WHEN_validateExecute_THEN_Exception() {
        // GIVEN
        String[] params = new String[]{"1", "2", "3"};

        // WHEN
        DiaHuntParameterException thrown = assertThrows(
                DiaHuntParameterException.class,
                () -> configValidator.validateExecute(params),
                "Expected getCommandType to throw, but it didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.TOO_MANY_ARGS));
    }

    @Test
    public void GIVEN_2_params_WHEN_validateExecute_THEN_Nothing() {
        // GIVEN
        String[] params = new String[]{"1", "2"};

        // WHEN / THEN
        configValidator.validateExecute(params);
    }
}