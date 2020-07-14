package killerm.minecraft.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiaHuntGameStateTest {
    private DiaHuntGameState diaHuntGameState = new DiaHuntGameState();

    @Test
    public void WHEN_getGameStatus_THEN_return_OFF() {
        assertEquals(GameStatus.OFF, diaHuntGameState.getGameStatus());
    }

    @Test
    public void GIVEN_GameStatus_WHEN_getGameStatus_THEN_return_gameStatus() {
        // GIVEN
        GameStatus gameStatus = GameStatus.STARTING;

        // WHEN
        diaHuntGameState.setGameStatus(gameStatus);

        // THEN
        assertEquals(gameStatus, diaHuntGameState.getGameStatus());
    }
}