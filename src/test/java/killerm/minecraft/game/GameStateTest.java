package killerm.minecraft.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    private GameState gameState = new GameState();

    @Test
    public void WHEN_getGameStatus_THEN_return_OFF() {
        assertEquals(GameStatus.OFF, gameState.getGameStatus());
    }

    @Test
    public void GIVEN_GameStatus_WHEN_getGameStatus_THEN_return_gameStatus() {
        // GIVEN
        GameStatus gameStatus = GameStatus.STARTING;

        // WHEN
        gameState.setGameStatus(gameStatus);

        // THEN
        assertEquals(gameStatus, gameState.getGameStatus());
    }
}