package killerm.minecraft.commands;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTypeTest {
    @Test
    public void GIVEN_HELP_WHEN_getCommandInChat_THEN_return_correct_string() {
        assertEquals(Message.HELP, CommandType.HELP.getCommandInChat());
    }

    @Test
    public void GIVEN_HELP_WHEN_getHelp_THEN_return_correct_string() {
        // Makes no sense because help doesn't have a help message
        assertEquals(Message.HELP_HELP, CommandType.HELP.getHelp());
    }

    @Test
    public void GIVEN_valid_commandTypeString_WHEN_getCommandType_THEN_return_correct_CommandType() {
        // GIVEN
        String commandTypeString = "help";

        // WHEN
        CommandType commandType = CommandType.getCommandType(commandTypeString);

        // THEN
        assertEquals(CommandType.HELP, commandType);
    }

    @Test
    public void GIVEN_invalid_commandTypeString_WHEN_getCommandType_THEN_Exception() {
        // GIVEN
        String commandString = "helpp";

        // WHEN
        DiaHuntParameterException thrown = assertThrows(
                DiaHuntParameterException.class,
                () -> CommandType.getCommandType(commandString),
                "Expected getCommandType to throw, but it didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.COMMANDTYPE_NOT_VALID));
    }
}