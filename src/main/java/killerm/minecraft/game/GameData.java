package killerm.minecraft.game;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    volatile static GameStatus gameStatus = GameStatus.OFF;
    private volatile static List<Location> shulkerBoxLocations = new ArrayList<>(); //das in listener hinein
    /*
    private Map<Player, Team> playerTeams = new ConcurrentHashMap<>();
    private Printer printer;
    private NameChanger nameChanger;

    public GamePlayerData() {
        this.printer = new Printer();
        this.nameChanger = new NameChanger();
    }

    public GamePlayerData(Printer printer, NameChanger nameChanger) {
        this.printer = printer;
        this.nameChanger = nameChanger;
    }

    public void add(Player player, Team team) {
        playerTeams.put(player, team);

        nameChanger.setPlayerColor(player, team);

        String joinMessage = generateJoinMessage(player, team);
        printer.broadcast(joinMessage);
    }

    private String generateJoinMessage(Player player, Team team) {
        String message = Messages.GREEN_RIGHT_ERROR
                + Messages.Color.GOLD
                + player.getDisplayName()
                + Messages.GREEN_LEFT_ERROR
                + Messages.Color.DARK_AQUA
                + Messages.JOINED_TEAM;

        if (team == Team.LAVA) {
            message += Messages.Color.TEAM_LAVA;
        } else {
            message += Messages.Color.TEAM_AQUA;
        }

        message += StringUtils.capitalize(team.toString());

        return message;
    }

    public void add(Player player) {
        if (amountOfPlayers(Team.AQUA) < amountOfPlayers(Team.LAVA)) {
            add(player, Team.AQUA);
        } else {
            add(player, Team.LAVA);
        }
    }

    public static void remove(Player player) {
        playerTeams.remove(player);

        Printer.printWithSound(Messages.RED_RIGHT_ERROR
                + Messages.Color.GOLD
                + player.getDisplayName()
                + Messages.RED_LEFT_ERROR
                + Messages.Color.DARK_AQUA
                + Messages.LEFT);

        NameChanger.reset(player);
    }

    public static boolean hasPlayers() {
        return !playerTeams.isEmpty();
    }

    public static Collection<Player> players() {
        return playerTeams.keySet();
    }

    public static Collection<Player> players(Team team) {
        Collection<Player> players = new HashSet<>();

        for (Player player : players()) {
            if (team(player) == team) {
                players.add(player);
            }
        }

        return players;
    }

    public static int amountOfPlayers(Team team) {
        return players(team).size();
    }

    public static int amountOfPlayers() {
        return players().size();
    }

    public static Team team(Player player) {
        return playerTeams.get(player);
    }

    public static Player randomPlayer(Team team) {
        Collection<Player> playersInTeam = players(team);
        return randomPlayer(playersInTeam);
    }

    private static Player randomPlayer(Collection<Player> players) {
        if (players.size() == 0) {
            return null;
        }

        Random rnd = new Random();
        int randomIndex = rnd.nextInt(players.size());

        List<Player> playersList = new ArrayList<>();
        playersList.addAll(players);
        return playersList.get(randomIndex);
    }

    public static boolean contains(Player player) {
        return players().contains(player);
    }*/
}
