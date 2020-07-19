package killerm.minecraft.game.flow;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.game.data.Team;
import killerm.minecraft.utilities.MinecraftConstants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Winner {
    Printer printer = new Printer();

    public void win(Team team) { // TODO: Everything
        for (Player player : Bukkit.getOnlinePlayers()) {
            printer.tellTitle(player, Message.AQUA + "Team " + team.toString() + " has won!!!!!", "This titles are just placeholders");
        }

        printer.broadcast("Game finished!");
        printer.broadcast("TODO: Game finsh");

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "diahunt stop");
            }
        }.runTaskLater(DiaHuntPlugin.getInstance(), 3 * MinecraftConstants.ticksPerSecond);
    }
}
