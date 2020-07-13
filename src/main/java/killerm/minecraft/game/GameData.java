package killerm.minecraft.game;


import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.NameChanger;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.utilities.Team;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameData {
    private Map<Player, Team> playerTeams = new ConcurrentHashMap<>();
    private volatile GameStatus gameStatus = GameStatus.OFF;
    private Printer printer;
    private NameChanger nameChanger;

    public GameData() {
        this.printer = new Printer();
        this.nameChanger = new NameChanger();
    }

    public GameData(Printer printer, NameChanger nameChanger) {
        this.printer = printer;
        this.nameChanger = nameChanger;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
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

        if (team == Team.LAVA) {
            sb.append(Message.TEAM_LAVA);
        } else {
            sb.append(Message.TEAM_AQUA);
        }

        sb.append(StringUtils.capitalize(team.toString()));

        return sb.toString();
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

    public Player randomPlayer(Team team) {
        Collection<Player> playersInTeam = players(team);
        return randomPlayer(playersInTeam);
    }

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
