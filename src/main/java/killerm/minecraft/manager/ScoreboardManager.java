package killerm.minecraft.manager;

import killerm.minecraft.communication.Message;
import killerm.minecraft.game.DiamondIndicator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collection;

public class ScoreboardManager {
    private static volatile Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    static {
        Objective objective1 = scoreboard.registerNewObjective("diamonds1", "dummy", Message.AQUA + Message.SYMBOL_DIAMOND);
        objective1.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        objective1.setDisplayName(Message.AQUA + Message.SYMBOL_DIAMOND);

        Objective objective2 = scoreboard.registerNewObjective("diamonds2", "dummy", Message.AQUA + Message.SYMBOL_DIAMOND);
        objective2.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective2.setDisplayName(Message.AQUA + Message.SYMBOL_DIAMOND);
    }

    public static void refresh(Collection<Player> players) {
        for (Player player : players) {
            refresh(player);
        }
    }

    public static void refresh(Player player) {
        setDiamonds(player, new DiamondIndicator().amount(player));
    }

    private static void setDiamonds(Player player, int amountOfDiamonds) {
        Score score1 = scoreboard.getObjective("diamonds1").getScore(player);
        score1.setScore(amountOfDiamonds);

        Score score2 = scoreboard.getObjective("diamonds2").getScore(player);
        score2.setScore(amountOfDiamonds);

        player.setScoreboard(scoreboard);
    }

    public static void clear(Collection<Player> players) {
        for (Player player : players) {
            clear(player);
        }
    }

    public static void clear(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(board);
    }
}