package killerm.minecraft.game;


import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.utilities.MinecraftConstants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameInitPrinter {
    private Printer printer = new Printer();
    private GameStatus gameStatus;
    private PlayerGameData playerGameData;
    private List<BukkitTask> tasks = new ArrayList<>();

    public GameInitPrinter(GameStatus gameStatus, PlayerGameData playerGameData) {
        this.gameStatus = gameStatus;
        this.playerGameData = playerGameData; //gameinit printer und diaincreaser testen
    }

    public void printGameInit(String gameStarterName, String[] invitedPlayerNames) {
        printDashes();
        printStarted(gameStarterName);
        printInvitedPlayerOrPlayers(invitedPlayerNames);
        printYouInvited(invitedPlayerNames);
        printAccept();
        printCountdownAndStartTitle(60, 30, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
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
        printCountDown(seconds);

        int maxSecond = Arrays.stream(seconds).max().getAsInt();
        printStart(maxSecond);
    }

    private void printCountDown(int... seconds) {
        int maxS = Arrays.stream(seconds).max().getAsInt();

        for (int s : seconds) {
            int secondsToWait = maxS - s;

            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    printer.broadcast(Message.GAME_START_IN + String.valueOf(s) + Message.SECONDS);
                }
            }.runTaskLater(DiaHuntPlugin.getInstance(), MinecraftConstants.ticksPerSecond * secondsToWait);

            tasks.add(task);
        }
    }

    private void printStart(int seconds) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                printer.broadcast(Message.GAME_STARTED);
                for (Player player : playerGameData.players()) {
                    printer.tellTitle(player, Message.AQUA + Message.START_DIA, Message.DARK_AQUA + Message.GET_DIAS);
                }
            }
        }.runTaskLater(DiaHuntPlugin.getInstance(), MinecraftConstants.ticksPerSecond * seconds);

        tasks.add(task);
    }

    public void stop() {
        for (BukkitTask task : tasks) {
            task.cancel();
        }

        tasks.clear();
    }
}
