package killerm.minecraft.utilities;

import killerm.minecraft.communication.Message;
import killerm.minecraft.game.Team;
import org.bukkit.entity.Player;

public class NameChanger {
    public void setPlayerColor(Player player, Team team) {
        StringBuilder sb = new StringBuilder();

        if (team == Team.AQUA) {
            sb.append(Message.TEAM_AQUA);
            sb.append(Message.SYMBOL_WATER);
            sb.append(player.getName());
            sb.append(Message.SYMBOL_WATER);
            sb.append(Message.RESET);
        } else if (team == Team.LAVA) {
            sb.append(Message.TEAM_LAVA);
            sb.append(Message.SYMBOL_FIRE);
            sb.append(player.getName());
            sb.append(Message.SYMBOL_FIRE);
            sb.append(Message.RESET);
        }

        player.setDisplayName(sb.toString());
        player.setPlayerListName(sb.toString());
    }

    public void reset(Player player) {
        player.setDisplayName(player.getName());
        player.setPlayerListName(player.getName());
    }
}
