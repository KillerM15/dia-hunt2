package killerm.minecraft.utilities;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

// Very bad code, but done on purpose to simplify testing
public class DiaHuntScoreboardProvider {
    private static volatile Scoreboard diaHuntScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    public Scoreboard getScoreboard() {
        return diaHuntScoreboard;
    }
}
