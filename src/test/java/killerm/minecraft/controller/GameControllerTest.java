package killerm.minecraft.controller;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntLogicException;
import killerm.minecraft.game.DiaHuntGameState;
import killerm.minecraft.game.Game;
import killerm.minecraft.game.GameStatus;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

class GameControllerTest {
    private Game game = mock(Game.class);
    private DiaHuntGameState diaHuntGameState = mock(DiaHuntGameState.class);
    private GameController gameController = new GameController(diaHuntGameState, game);

    @Test
    public void GIVEN_GameStatus_OFF_and_player_and_invitedPlayerNames_WHEN_play_THEN_game_startInitialize_player_invitedPlayerNames_called() {
        // GIVEN
        Player player = mock(Player.class);
        String[] invitedPlayerNames = new String[]{"player1", "player2"};
        doReturn(GameStatus.OFF).when(diaHuntGameState).getGameStatus();

        // WHEN
        gameController.play(player, invitedPlayerNames);

        // THEN
        Mockito.verify(game, times(1)).startInitialize(player, invitedPlayerNames);
    }

    @Test
    public void GIVEN_GameStatus_STARTING_WHEN_play_THEN_Exception() {
        // GIVEN
        doReturn(GameStatus.STARTING).when(diaHuntGameState).getGameStatus();

        // WHEN
        DiaHuntLogicException thrown = assertThrows(
                DiaHuntLogicException.class,
                () -> gameController.play(null, new String[]{}),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.GAME_ALREADY_STARTING));
    }

    @Test
    public void GIVEN_GameStatus_RUNNING_WHEN_play_THEN_Exception() {
        // GIVEN
        doReturn(GameStatus.RUNNING).when(diaHuntGameState).getGameStatus();

        // WHEN
        DiaHuntLogicException thrown = assertThrows(
                DiaHuntLogicException.class,
                () -> gameController.play(null, new String[]{}),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.GAME_ALREADY_RUNNING));
    }

    @Test
    public void GIVEN_GameStatus_OFF_WHEN_stop_THEN_Exception() {
        // GIVEN
        doReturn(GameStatus.OFF).when(diaHuntGameState).getGameStatus();

        // WHEN
        DiaHuntLogicException thrown = assertThrows(
                DiaHuntLogicException.class,
                () -> gameController.stop(),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.NO_GAME_IN_PROGRESS));
    }

    @Test
    public void GIVEN_GameStatus_STARTING_WHEN_stop_THEN_game_stop_called() {
        // GIVEN
        doReturn(GameStatus.STARTING).when(diaHuntGameState).getGameStatus();

        // WHEN
        gameController.stop();

        // THEN
        Mockito.verify(game, times(1)).stop();
    }

    @Test
    public void GIVEN_GameStatus_RUNNING_WHEN_stop_THEN_game_stop_called() {
        // GIVEN
        doReturn(GameStatus.RUNNING).when(diaHuntGameState).getGameStatus();

        // WHEN
        gameController.stop();

        // THEN
        Mockito.verify(game, times(1)).stop();
    }

    @Test
    public void GIVEN_GameStatus_OFF_WHEN_join_THEN_Exception() {
        // GIVEN
        doReturn(GameStatus.OFF).when(diaHuntGameState).getGameStatus();

        // WHEN
        DiaHuntLogicException thrown = assertThrows(
                DiaHuntLogicException.class,
                () -> gameController.join(null, null),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.NO_GAME_IN_PROGRESS));
    }

    @Test
    public void GIVEN_GameStatus_STARTING_and_player_and_teamString_WHEN_join_THEN_game_join_with_player_and_teamString_called() {
        // GIVEN
        doReturn(GameStatus.STARTING).when(diaHuntGameState).getGameStatus();
        Player player = mock(Player.class);
        String[] teamString = new String[]{};

        // WHEN
        gameController.join(player, teamString);

        // THEN
        Mockito.verify(game, times(1)).join(player, teamString);
    }

    @Test
    public void GIVEN_GameStatus_RUNNING_WHEN_join_THEN_Exception() {
        doReturn(GameStatus.RUNNING).when(diaHuntGameState).getGameStatus();

        // WHEN
        DiaHuntLogicException thrown = assertThrows(
                DiaHuntLogicException.class,
                () -> gameController.join(null, new String[]{}),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.GAME_ALREADY_RUNNING));
    }

    @Test
    public void GIVEN_GameStatus_OFF_WHEN_leave_THEN_Exception() {
        // GIVEN
        doReturn(GameStatus.OFF).when(diaHuntGameState).getGameStatus();

        // WHEN
        DiaHuntLogicException thrown = assertThrows(
                DiaHuntLogicException.class,
                () -> gameController.leave(null),
                "Expected to throw, but didn't"
        );

        // THEN
        assertTrue(thrown.getMessage().equals(Message.NO_GAME_IN_PROGRESS));
    }

    @Test
    public void GIVEN_GameStatus_STARTING_and_player_WHEN_leave_THEN_game_leave_player_called() {
        // GIVEN
        doReturn(GameStatus.STARTING).when(diaHuntGameState).getGameStatus();
        Player player = mock(Player.class);

        // WHEN
        gameController.leave(player);

        // THEN
        Mockito.verify(game, times(1)).leave(player);
    }

    @Test
    public void GIVEN_GameStatus_RUNNING_WHEN_leave_THEN_game_leave_player_called() {
        // GIVEN
        doReturn(GameStatus.RUNNING).when(diaHuntGameState).getGameStatus();
        Player player = mock(Player.class);

        // WHEN
        gameController.leave(player);

        // THEN
        Mockito.verify(game, times(1)).leave(player);
    }
}