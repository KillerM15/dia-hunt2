package killerm.minecraft.validator;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.ParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigValidatorTest {
    ConfigValidator configValidator = new ConfigValidator();

    @Test
    public void GIVEN_3_params_WHEN_validateExecute_THEN_Exception() {
        // GIVEN
        String[] params = new String[]{"1", "2", "3"};

        // WHEN
        ParameterException thrown = assertThrows(
                ParameterException.class,
                () -> configValidator.validateExecute(params),
                "Expected to throw, but didn't"
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