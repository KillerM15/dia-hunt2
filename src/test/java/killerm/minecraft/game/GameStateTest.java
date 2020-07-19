package killerm.minecraft.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    private GameState gameState = new GameState();

    @Test
    public void WHEN_getGameStatus_THEN_return_OFF() {
        assertEquals(GameStatusType.OFF, gameState.getGameStatusType());
    }

    @Test
    public void GIVEN_GameStatus_WHEN_getGameStatus_THEN_return_gameStatus() {
        // GIVEN
        GameStatusType gameStatusType = GameStatusType.STARTING;

        // WHEN
        gameState.setGameStatusType(gameStatusType);

        // THEN
        assertEquals(gameStatusType, gameState.getGameStatusType());
    }
}