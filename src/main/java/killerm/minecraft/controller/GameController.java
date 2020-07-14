package killerm.minecraft.controller;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntLogicException;
import killerm.minecraft.game.DiaChestGameData;
import killerm.minecraft.game.DiaHuntGameState;
import killerm.minecraft.game.Game;
import killerm.minecraft.game.PlayerGameData;
import org.bukkit.entity.Player;

public class GameController {
    private DiaHuntGameState diaHuntGameState;
    private Game game;

    public GameController(DiaHuntGameState diaHuntGameState, PlayerGameData playerGameData, DiaChestGameData diaChestGameData) {
        this.diaHuntGameState = diaHuntGameState;
        this.game = new Game(diaHuntGameState, playerGameData, diaChestGameData);
    }

    // This one is for tests
    public GameController(DiaHuntGameState diaHuntGameState, Game game) {
        this.diaHuntGameState = diaHuntGameState;
        this.game = game;
    }

    public void play(Player player, String[] invitedPlayerNames) {
        switch (diaHuntGameState.getGameStatus()) {
            case OFF:
                game.startInitialize(player, invitedPlayerNames);
                break;
            case STARTING:
                throw new DiaHuntLogicException(Message.GAME_ALREADY_STARTING);
            case RUNNING:
                throw new DiaHuntLogicException(Message.GAME_ALREADY_RUNNING);
            default:
                throw new DiaHuntLogicException(Message.INVALID_GAME_STATUS);
        }
    }

    public void stop() {
        switch (diaHuntGameState.getGameStatus()) {
            case OFF:
                throw new DiaHuntLogicException(Message.NO_GAME_IN_PROGRESS);
            case STARTING:
            case RUNNING:
                game.stop();
                break;
            default:
                throw new DiaHuntLogicException(Message.INVALID_GAME_STATUS);
        }
    }

    public void join(Player player, String[] teamString) {
        switch (diaHuntGameState.getGameStatus()) {
            case OFF:
                throw new DiaHuntLogicException(Message.NO_GAME_IN_PROGRESS);
            case STARTING:
                game.join(player, teamString);
                break;
            case RUNNING:
                throw new DiaHuntLogicException(Message.GAME_ALREADY_RUNNING);
            default:
                throw new DiaHuntLogicException(Message.INVALID_GAME_STATUS);
        }
    }

    public void leave(Player player) {
        switch (diaHuntGameState.getGameStatus()) {
            case OFF:
                throw new DiaHuntLogicException(Message.NO_GAME_IN_PROGRESS);
            case STARTING:
            case RUNNING:
                game.leave(player);
                break;
            default:
                throw new DiaHuntLogicException(Message.INVALID_GAME_STATUS);
        }
    }
}
