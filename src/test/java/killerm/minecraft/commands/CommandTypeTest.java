package killerm.minecraft.commands;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.ParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTypeTest {
    @Test
    public void GIVEN_HELP_WHEN_getCommandInChat_THEN_return_correct_string() {
        assertEquals("help", CommandType.HELP.toString());
    }

    @Test
    public void GIVEN_HELP_WHEN_getDescription_THEN_return_correct_description() {
        assertEquals(Message.DESCRIPTION_EMPTY, CommandType.HELP.getDescription());
    }

    @Test
    public void GIVEN_valid_commandTypeString_WHEN_getInstance_THEN_return_correct_CommandType() {
        // GIVEN
        String commandTypeString = "help";

        // WHEN
        CommandType commandType = CommandType.getInstance(commandTypeString);

        // THEN
        assertEquals(CommandType.HELP, commandType);
    }

    @Test
    public void GIVEN_invalid_commandTypeString_WHEN_getInstance_THEN_Exception() {
        // GIVEN
        String commandTypeString = "helpp";

        // WHEN
        ParameterException thrown = assertThrows(
                ParameterException.class,
                () -> CommandType.getInstance(commandTypeString),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.COMMANDTYPE_NOT_VALID));
    }

    @Test
    public void GIVEN_HELP_WHEN_isEmptyCommandEqualsHelp_THEN_return_true() {
        assertEquals(true, CommandType.HELP.isEmptyCommandEqualsHelp());
    }

    @Test
    public void GIVEN_STOP_WHEN_isEmptyCommandEqualsHelp_THEN_return_false() {
        assertEquals(false, CommandType.STOP.isEmptyCommandEqualsHelp());
    }
}