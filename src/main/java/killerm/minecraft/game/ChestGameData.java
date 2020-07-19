package killerm.minecraft.game;

import org.bukkit.Location;
import org.bukkit.block.ShulkerBox;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChestGameData {
    private Map<Location, Team> chestLocations = new ConcurrentHashMap();
    private DiamondIndicator diamondIndicator;

    public ChestGameData() {
        this.diamondIndicator = new DiamondIndicator();
    }

    public ChestGameData(DiamondIndicator diamondIndicator) {
        this.diamondIndicator = diamondIndicator;
    }

    public void addLocation(Location location, Team team) {
        chestLocations.put(location, team);
    }

    public void removeLocation(Location location) {
        chestLocations.remove(location);
    }

    public Collection<Location> getLocations() {
        return chestLocations.keySet();
    }

    public Collection<Location> getLocations(Team team) {
        Set<Location> chestLocationsTeam = new HashSet<>();

        for (Location location : getLocations()) {
            if (chestLocations.get(location) == team) {
                chestLocationsTeam.add(location);
            }
        }

        return chestLocationsTeam;
    }

    public Set<ShulkerBox> getShulkerBoxes() {
        Set<ShulkerBox> shulkerBoxes = new HashSet<>();

        for (Location location : getLocations()) {
            shulkerBoxes.add((ShulkerBox) location.getBlock().getState());
        }

        return shulkerBoxes;
    }

    public Set<ShulkerBox> getShulkerBoxes(Team team) {
        Set<ShulkerBox> shulkerBoxes = new HashSet<>();

        for (Location location : getLocations(team)) {
            shulkerBoxes.add((ShulkerBox) location.getBlock().getState());
        }

        return shulkerBoxes;
    }

    public boolean containsDias(Team team) {
        for (ShulkerBox shulkerBox : getShulkerBoxes(team)) {
            if (diamondIndicator.hasDiamonds(shulkerBox)) {
                return true;
            }
        }

        return false;
    }

    public void clear() {
        chestLocations.clear();
    }
}
