package killerm.minecraft.controller;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.LogicException;
import killerm.minecraft.game.ChestGameData;
import killerm.minecraft.game.GameStatus;
import killerm.minecraft.game.Game;
import killerm.minecraft.game.PlayerGameData;
import org.bukkit.entity.Player;

public class GameController {
    private GameStatus gameStatus;
    private Game game;

    public GameController(GameStatus gameStatus, PlayerGameData playerGameData, ChestGameData chestGameData) {
        this.gameStatus = gameStatus;
        this.game = new Game(gameStatus, playerGameData, chestGameData);
    }

    // This one is for tests
    public GameController(GameStatus gameStatus, Game game) {
        this.gameStatus = gameStatus;
        this.game = game;
    }

    public void play(Player player, String[] invitedPlayerNames) {
        switch (gameStatus.getGameStatusType()) {
            case OFF:
                game.startInitialize(player, invitedPlayerNames);
                break;
            case STARTING:
                throw new LogicException(Message.GAME_ALREADY_STARTING);
            case RUNNING:
                throw new LogicException(Message.GAME_ALREADY_RUNNING);
            default:
                throw new LogicException(Message.INVALID_GAME_STATUS);
        }
    }

    public void stop() {
        switch (gameStatus.getGameStatusType()) {
            case OFF:
                throw new LogicException(Message.NO_GAME_IN_PROGRESS);
            case STARTING:
            case RUNNING:
                game.stop();
                break;
            default:
                throw new LogicException(Message.INVALID_GAME_STATUS);
        }
    }

    public void join(Player player, String[] teamString) {
        switch (gameStatus.getGameStatusType()) {
            case OFF:
                throw new LogicException(Message.NO_GAME_IN_PROGRESS);
            case STARTING:
                game.join(player, teamString);
                break;
            case RUNNING:
                throw new LogicException(Message.GAME_ALREADY_RUNNING);
            default:
                throw new LogicException(Message.INVALID_GAME_STATUS);
        }
    }

    public void leave(Player player) {
        switch (gameStatus.getGameStatusType()) {
            case OFF:
                throw new LogicException(Message.NO_GAME_IN_PROGRESS);
            case STARTING:
            case RUNNING:
                game.leave(player);
                break;
            default:
                throw new LogicException(Message.INVALID_GAME_STATUS);
        }
    }
}
