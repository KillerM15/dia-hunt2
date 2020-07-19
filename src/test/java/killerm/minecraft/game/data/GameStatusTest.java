package killerm.minecraft.game.data;

import killerm.minecraft.game.flow.GameStatusType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStatusTest {
    private GameStatus gameStatus = new GameStatus();

    @Test
    public void WHEN_getGameStatus_THEN_return_OFF() {
        assertEquals(GameStatusType.OFF, gameStatus.getGameStatusType());
    }

    @Test
    public void GIVEN_GameStatus_WHEN_getGameStatus_THEN_return_gameStatus() {
        // GIVEN
        GameStatusType gameStatusType = GameStatusType.STARTING;

        // WHEN
        gameStatus.setGameStatusType(gameStatusType);

        // THEN
        assertEquals(gameStatusType, gameStatus.getGameStatusType());
    }
}