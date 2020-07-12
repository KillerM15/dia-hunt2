package killerm.minecraft.communication;

import killerm.minecraft.utilities.Team;
import org.bukkit.entity.Player;

public class NameChanger {
    public void setPlayerColor(Player player, Team team) {
        if (team == Team.AQUA) {
            String newName = Message.TEAM_AQUA
                    + Message.SYMBOL_WATER
                    + player.getName()
                    + Message.SYMBOL_WATER
                    + Message.RESET;

            player.setDisplayName(newName);
            player.setPlayerListName(newName);
        } else if (team == Team.LAVA) {
            String newName = Message.TEAM_LAVA
                    + Message.SYMBOL_FIRE
                    + player.getName()
                    + Message.SYMBOL_FIRE
                    + Message.RESET;

            player.setDisplayName(newName);
            player.setPlayerListName(newName);
        }
    }

    public void reset(Player player) {
        player.setDisplayName(player.getName());
        player.setPlayerListName(player.getName());
    }
}
