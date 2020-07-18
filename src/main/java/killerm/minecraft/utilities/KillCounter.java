package killerm.minecraft.utilities;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class KillCounter {
    private Map<Player, Integer> kills = new ConcurrentHashMap<>();

    public void addKill(Player player) {
        if (kills.get(player) == null) {
            kills.put(player, 1);
        } else {
            int oldKills = kills.get(player);
            kills.put(player, oldKills + 1);
        }
    }

    public Collection<Player> retrievePlayersWithMostKills() {
        Collection<Player> playersWithMostKills = new HashSet<>();

        if (noPlayersRecorded()) {
            return playersWithMostKills;
        }

        int maxKills = Collections.max(kills.values());

        for (Player player : kills.keySet()) {
            if (kills.get(player) == maxKills) {
                playersWithMostKills.add(player);
            }
        }

        return playersWithMostKills;
    }

    private boolean noPlayersRecorded() {
        return kills.isEmpty();
    }

    public void clear() {
        kills.clear();
    }
}