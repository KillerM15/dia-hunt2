package killerm.minecraft.game.data;

import killerm.minecraft.game.flow.GameStatusType;

public class GameStatus {
    private volatile GameStatusType gameStatusType = GameStatusType.OFF;

    public void setGameStatusType(GameStatusType gameStatusType) {
        this.gameStatusType = gameStatusType;
    }

    public GameStatusType getGameStatusType() {
        return gameStatusType;
    }
}
