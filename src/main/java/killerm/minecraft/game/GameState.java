package killerm.minecraft.game;

public class GameState {
    private volatile GameStatusType gameStatusType = GameStatusType.OFF;

    public void setGameStatusType(GameStatusType gameStatusType) {
        this.gameStatusType = gameStatusType;
    }

    public GameStatusType getGameStatusType() {
        return gameStatusType;
    }
}
