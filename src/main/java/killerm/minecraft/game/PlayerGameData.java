package killerm.minecraft.game;


import com.sun.istack.internal.Nullable;
import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.NameChanger;
import killerm.minecraft.communication.Printer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerGameData {
    private Map<Player, Team> playerTeams = new ConcurrentHashMap<>();
    private Printer printer;
    private NameChanger nameChanger;

    public PlayerGameData() {
        this.printer = new Printer();
        this.nameChanger = new NameChanger();
    }

    public PlayerGameData(Printer printer, NameChanger nameChanger) {
        this.printer = printer;
        this.nameChanger = nameChanger;
    }

    public void add(Player player) {
        if (amountOfPlayers(Team.AQUA) < amountOfPlayers(Team.LAVA)) {
            add(player, Team.AQUA);
        } else {
            add(player, Team.LAVA);
        }
    }

    public void add(Player player, Team team) {
        playerTeams.put(player, team);

        nameChanger.setPlayerColor(player, team);

        String joinMessage = generateJoinMessage(player, team);
        printer.broadcast(joinMessage);
    }

    private String generateJoinMessage(Player player, Team team) {
        StringBuilder sb = new StringBuilder();

        sb.append(Message.GREEN);
        sb.append(Message.BOLD);
        sb.append(Message.RIGHT_ARROW);
        sb.append(player.getDisplayName());
        sb.append(Message.GREEN);
        sb.append(Message.BOLD);
        sb.append(Message.LEFT_ARROW);
        sb.append(Message.DARK_AQUA);
        sb.append(Message.JOINED_TEAM);
        sb.append(teamColor(team));
        sb.append(StringUtils.capitalize(team.toString()));

        return sb.toString();
    }

    private String teamColor(Team team) {
        if (team == Team.LAVA) {
            return Message.TEAM_LAVA;
        }

        return Message.TEAM_AQUA;
    }

    public void remove(Player player) {
        String leaveMessage = generateLeaveMessage(player);
        printer.broadcast(leaveMessage);

        playerTeams.remove(player);

        nameChanger.reset(player);
    }

    private String generateLeaveMessage(Player player) {
        StringBuilder sb = new StringBuilder();

        sb.append(Message.RED);
        sb.append(Message.BOLD);
        sb.append(Message.RIGHT_ARROW);
        sb.append(player.getDisplayName());
        sb.append(Message.RED);
        sb.append(Message.BOLD);
        sb.append(Message.LEFT_ARROW);
        sb.append(Message.DARK_AQUA);
        sb.append(Message.LEFT);

        return sb.toString();
    }

    public Collection<Player> players() {
        return playerTeams.keySet();
    }

    public Collection<Player> players(Team team) {
        Collection<Player> players = new HashSet<>();

        for (Player player : players()) {
            if (team(player) == team) {
                players.add(player);
            }
        }

        return players;
    }

    public Team team(Player player) {
        return playerTeams.get(player);
    }

    public boolean contains(Player player) {
        return players().contains(player);
    }

    public boolean hasPlayers() {
        return !playerTeams.isEmpty();
    }

    public int amountOfPlayers() {
        return players().size();
    }

    public int amountOfPlayers(Team team) {
        return players(team).size();
    }

    @Nullable
    public Player randomPlayer(Team team) {
        Collection<Player> playersInTeam = players(team);
        Player player = randomPlayer(playersInTeam);

        if (player == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(Message.COULD_NOT_RANDOM_PLAYER);
            sb.append(teamColor(team));
            sb.append(StringUtils.capitalize(team.toString()));
            sb.append(Message.DARK_AQUA);
            sb.append(Message.IS_EMPTY);

            printer.broadcastError(sb.toString());
        }

        return player;
    }

    @Nullable
    private Player randomPlayer(Collection<Player> players) {
        if (players.size() == 0) {
            return null;
        }

        Random rnd = new Random();
        int randomIndex = rnd.nextInt(players.size());

        List<Player> playersList = new ArrayList<>();
        playersList.addAll(players);

        return playersList.get(randomIndex);
    }
}
