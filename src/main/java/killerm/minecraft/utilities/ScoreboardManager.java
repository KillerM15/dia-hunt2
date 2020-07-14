package killerm.minecraft.utilities;

import killerm.minecraft.communication.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

// Ugly
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

    public static void setDiamonds(Player player, int amountOfDiamonds) {
        Score score1 = scoreboard.getObjective("diamonds1").getScore(player);
        score1.setScore(amountOfDiamonds);

        Score score2 = scoreboard.getObjective("diamonds2").getScore(player);
        score2.setScore(amountOfDiamonds);

        player.setScoreboard(scoreboard);
    }

    public static void clear(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(board);
    }
}
