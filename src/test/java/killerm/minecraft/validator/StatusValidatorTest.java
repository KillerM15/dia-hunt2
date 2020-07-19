package killerm.minecraft.validator;

import killerm.minecraft.commands.CommandType;
import killerm.minecraft.communication.Message;
import killerm.minecraft.error.ParameterException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class StatusValidatorTest {

    private StatusValidator statusValidator = new StatusValidator();

    @Test
    public void GIVEN_0_param_WHEN_validateStatus_THEN_no_Exception() {
        // GIVEN
        String commandTypeString[] = {};

        // WHEN
        statusValidator.validateStatus(commandTypeString);
    }

    @Test
    public void  GIVEN_1_param_WHEN_validateStatus_THEN_Exception() {
        // GIVEN
        String commandTypeString[] = {"param1"};

        // WHEN
        ParameterException thrown = assertThrows(
                ParameterException.class,
                () -> statusValidator.validateStatus(commandTypeString),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.TOO_MANY_INPUTS));
    }
}