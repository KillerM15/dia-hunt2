package killerm.minecraft.controller;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.LogicException;
import killerm.minecraft.game.ChestGameData;
import killerm.minecraft.game.GameState;
import killerm.minecraft.game.Game;
import killerm.minecraft.game.PlayerGameData;
import org.bukkit.entity.Player;

public class GameController {
    private GameState gameState;
    private Game game;

    public GameController(GameState gameState, PlayerGameData playerGameData, ChestGameData chestGameData) {
        this.gameState = gameState;
        this.game = new Game(gameState, playerGameData, chestGameData);
    }

    // This one is for tests
    public GameController(GameState gameState, Game game) {
        this.gameState = gameState;
        this.game = game;
    }

    public void play(Player player, String[] invitedPlayerNames) {
        switch (gameState.getGameStatus()) {
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
        switch (gameState.getGameStatus()) {
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
        switch (gameState.getGameStatus()) {
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
        switch (gameState.getGameStatus()) {
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
