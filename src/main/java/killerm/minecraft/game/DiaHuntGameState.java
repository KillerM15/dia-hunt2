package killerm.minecraft.game;

public class DiaHuntGameState {
    private volatile GameStatus gameStatus = GameStatus.OFF;

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
