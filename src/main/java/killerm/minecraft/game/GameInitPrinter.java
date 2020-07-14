package killerm.minecraft.game;


import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.utilities.MinecraftConstants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class GameInitPrinter {
    private Printer printer = new Printer();
    private GameData gameData;

    public GameInitPrinter(GameData gameData, String gameStarterName, String[] invitedPlayers) {
        this.gameData = gameData;
        printGameInit(gameStarterName, invitedPlayers);
    }

    private void printGameInit(String gameStarterName, String[] invitedPlayerNames) {
        printDashes();
        printStarted(gameStarterName);
        printInvitedPlayerOrPlayers(invitedPlayerNames);
        printYouInvited(invitedPlayerNames);
        printAccept();
        printCountdownAndStartTitle(60, 30, 10, 5, 4, 3, 2, 1);
    }

    private void printDashes() {
        printer.printDashes();
    }

    private void printStarted(String gameStarterName) {
        StringBuilder sb = new StringBuilder();

        sb.append(Message.GAME_STARTED_BY);
        sb.append(Message.GOLD);
        sb.append(gameStarterName);
        sb.append(Message.DARK_AQUA);
        sb.append(Message.EXMARK);

        printer.broadcast(sb.toString());
    }

    private void printInvitedPlayerOrPlayers(String[] invitedPlayerNames) {
        if (invitedPlayerNames.length > 1) {
            printInvitedPlayers(invitedPlayerNames);
        } else if (invitedPlayerNames.length == 1) {
            printInvitedPlayer(invitedPlayerNames[0]);
        }
    }

    private void printInvitedPlayers(String[] invitedPlayerNames) {
        StringBuilder sb = new StringBuilder();

        sb.append(Message.GOLD);
        sb.append(String.join(", ", invitedPlayerNames));
        sb.append(Message.DARK_AQUA);
        sb.append(Message.HAVE_BEEN_INVITED);

        printer.broadcast(sb.toString());
    }

    private void printInvitedPlayer(String invitedPlayer) {
        StringBuilder sb = new StringBuilder();

        sb.append(Message.GOLD);
        sb.append(invitedPlayer);
        sb.append(Message.DARK_AQUA);
        sb.append(Message.HAS_BEEN_INVITED);

        printer.broadcast(sb.toString());
    }

    private void printYouInvited(String[] invitedPlayers) {
        for (String player : invitedPlayers) {
            printer.tell(Bukkit.getPlayer(player), Message.YOU_ARE_INVITED);
        }
    }

    private void printAccept() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            printer.tellClickable(player, Message.ACCEPT, "/diahunt join");
        }
    }

    private void printCountdownAndStartTitle(int... seconds) {
        int maxS = Arrays.stream(seconds).max().getAsInt();

        for (int s : seconds) {
            int secondsToWait = maxS - s;

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (gameData.getGameStatus() == GameStatus.STARTING)
                        printer.broadcast(Message.GAME_START_IN + String.valueOf(s) + Message.SECONDS);
                }
            }.runTaskLater(DiaHuntPlugin.getInstance(), MinecraftConstants.ticksPerSecond * secondsToWait);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : gameData.players()) {
                    printer.broadcast(Message.GAME_STARTED);
                    printer.tellTitle(player, Message.AQUA + Message.START_DIA, Message.DARK_AQUA + Message.GET_DIAS);
                }
            }
        }.runTaskLater(DiaHuntPlugin.getInstance(), MinecraftConstants.ticksPerSecond * maxS);
    }

}