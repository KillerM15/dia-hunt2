package killerm.minecraft.helper;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerNameFixer {
    Printer printer = new Printer();

    // _babohaft_ -> _Babohaft_
    public String[] fixCase(String[] playerNames) {
        String[] fixedPlayerNames = new String[playerNames.length];

        for (int i = 0; i < playerNames.length; i++) {
            Player player = Bukkit.getPlayer(playerNames[i]);

            if (player == null) {
                printer.broadcastError(Message.PLAYER_NOT_FOUND);
                fixedPlayerNames[i] = null;
            } else {
                fixedPlayerNames[i] = player.getDisplayName();
            }
        }

        return fixedPlayerNames;
    }
}
