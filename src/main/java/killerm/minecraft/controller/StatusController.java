package killerm.minecraft.controller;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.game.data.GameStatus;
import  killerm.minecraft.game.data.PlayerGameData;
import org.bukkit.entity.Player;

public class StatusController {
    private GameStatus gameStatus;
    private Printer printer;
    private PlayerGameData playerGameData;

    public StatusController(GameStatus gameStatus, PlayerGameData playerGameData) {
        this.printer = new Printer();
        this.gameStatus = gameStatus;
        this.playerGameData = playerGameData;
    }

    public StatusController(Printer printer, GameStatus diaHuntGameState, PlayerGameData playerGameData) {
        this.printer = printer;
        this.gameStatus = diaHuntGameState;
        this.playerGameData = playerGameData;
    }

    public void printStatus(Player player) {
        printDashes();
        printGameStatus(player);
        printTableTopic(player);
        printPlayersInGame(player);
    }

    private void printDashes() {
        printer.printDashes();
    }

    private void printGameStatus(Player player) {
        printer.tell(player, "Gamestatus: " + gameStatus.getGameStatusType());
    }

    private void printTableTopic(Player player) {
        printer.tell(player, "Player" + Message.TABLE_DELIMITER + "Team" + Message.TABLE_DELIMITER + "Condition");
    }

    private void printPlayersInGame(Player player) {
        for (Player playerInGame : playerGameData.players()) {
            StringBuilder statusMessage = new StringBuilder();

            String playerTeam = playerGameData.team(playerInGame).toString();
            statusMessage.append(Message.ITALIC + playerInGame.getName()).append(Message.GREY + Message.TABLE_DELIMITER);
            statusMessage.append(Message.ITALIC + playerTeam).append(Message.GREY + Message.TABLE_DELIMITER);
            statusMessage.append(Message.ITALIC + playerGameData.getCondition(player));

            printer.tell(player, statusMessage.toString());
        }
    }
}
