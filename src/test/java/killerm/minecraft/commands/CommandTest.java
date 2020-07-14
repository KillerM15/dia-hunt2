package killerm.minecraft.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {
    @Test
    public void GIVEN_command_args_WHEN_construct_Command_with_args_THEN_correct_CommandType_saved() {
        // GIVEN
        String args[] = {"help", "parameter1", "parameter2"};

        // WHEN
        Command command = new Command(args);

        // THEN
        assertEquals(CommandType.HELP, command.getCommandType());
    }

    @Test
    public void GIVEN_command_args_WHEN_construct_Command_with_args_THEN_correct_parameters_saved() {
        // GIVENp
        String args[] = {"config", "parameter0", "parameter1", "parameter2"};

        // WHEN
        Command command = new Command(args);

        // THEN
        assertEquals("parameter0", command.getParameter()[0]);
        assertEquals("parameter1", command.getParameter()[1]);
        assertEquals("parameter2", command.getParameter()[2]);
    }

    @Test
    public void GIVEN_empty_command_args_WHEN_construct_Command_with_args_THEN_CommandType_HELP_saved() {
        // GIVEN
        String args[] = {};

        // WHEN
        Command command = new Command(args);

        // THEN
        assertEquals(CommandType.HELP, command.getCommandType());
    }

    @Test
    public void GIVEN_command_help_args_WHEN_isHelpCommand_THEN_true() {
        // GIVEN
        String args[] = {"config", "help", "123"};

        // WHEN
        Command command = new Command(args);
        boolean isHelpCommand = command.needsHelp();

        // THEN
        assert (isHelpCommand);
    }

    @Test
    public void GIVEN_command_with_empty_param_with_isEmptyCommandEqualsHelp_true_WHEN_isHelpCommand_THEN_true() {
        // GIVEN
        String args[] = {"config"};

        // WHEN
        Command command = new Command(args);
        boolean isHelpCommand = command.needsHelp();

        // THEN
        assert (isHelpCommand);
    }

    @Test
    public void GIVEN_command_with_empty_param_with_isEmptyCommandEqualsHelp_false_WHEN_isHelpCommand_THEN_false() {
        // GIVEN
        String args[] = {"stop"};

        // WHEN
        Command command = new Command(args);
        boolean isHelpCommand = command.needsHelp();

        // THEN
        assert (!isHelpCommand);
    }
}