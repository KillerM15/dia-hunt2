package killerm.minecraft.game;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.data.DiaConfig;
import killerm.minecraft.error.DiaHuntParameterException;
import killerm.minecraft.helper.PlayerNameFixer;
import killerm.minecraft.utilities.MinecraftConstants;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;

public class Game {
    private Printer printer;
    private DiaHuntGameState diaHuntGameState;
    private PlayerGameData playerGameData;
    private GameBackup gameBackup;
    private GameInitPrinter gameInitPrinter;
    private DiamondIncreaser diamondIncreaser;
    private LocationSetter locationSetter;
    private StatsGiver statsGiver;
    private ItemGiver itemGiver;
    private PlayerNameFixer playerNameFixer;
    private BukkitTask startingTask;

    public Game(DiaHuntGameState diaHuntGameState, PlayerGameData playerGameData, DiaChestGameData diaChestGameData) {
        this.printer = new Printer();
        this.diaHuntGameState = diaHuntGameState;
        this.playerGameData = playerGameData;
        this.gameBackup = new GameBackup();
        this.gameInitPrinter = new GameInitPrinter(diaHuntGameState, playerGameData);
        this.diamondIncreaser = new DiamondIncreaser(playerGameData, diaChestGameData);
        this.locationSetter = new LocationSetter();
        this.statsGiver = new StatsGiver();
        this.itemGiver = new ItemGiver();
        this.playerNameFixer = new PlayerNameFixer();
    }

    // This is why you should use dependency injection frameworks
    public Game(Printer printer, DiaHuntGameState diaHuntGameState, PlayerGameData playerGameData, GameBackup gameBackup, GameInitPrinter gameInitPrinter, DiamondIncreaser diamondIncreaser, LocationSetter locationSetter, StatsGiver statsGiver, ItemGiver itemGiver, PlayerNameFixer playerNameFixer) {
        this.printer = printer;
        this.diaHuntGameState = diaHuntGameState;
        this.playerGameData = playerGameData;
        this.gameBackup = gameBackup;
        this.gameInitPrinter = gameInitPrinter;
        this.diamondIncreaser = diamondIncreaser;
        this.locationSetter = locationSetter;
        this.statsGiver = statsGiver;
        this.itemGiver = itemGiver;
        this.playerNameFixer = playerNameFixer;
    }

    public void startInitialize(Player gameStarter, String[] invitedPlayerNames) {
        diaHuntGameState.setGameStatus(GameStatus.STARTING);
        printCountDown(gameStarter, invitedPlayerNames);
        startGameAfterDelay();
        join(gameStarter, null);
    }

    private void printCountDown(Player gameStarter, String[] invitedPlayerNames) {
        invitedPlayerNames = playerNameFixer.fixCase(invitedPlayerNames);

        gameInitPrinter.printGameInit(gameStarter.getDisplayName(), invitedPlayerNames);
    }

    private void startGameAfterDelay() {
        // Added 1 tick extra because GameInitPrinter needs unstarted game after 60 seconds
        int delay = (int) (double) DiaConfig.SECONDS_UNTIL_START.get() * MinecraftConstants.ticksPerSecond + 1;

        this.startingTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (diaHuntGameState.getGameStatus() != GameStatus.STARTING) {
                    return;
                }

                startGame();
            }
        }.runTaskLater(DiaHuntPlugin.getInstance(), delay);
    }

    private boolean oneTeamNoPlayers() {
        return playerGameData.amountOfPlayers(Team.AQUA) == 0 || playerGameData.amountOfPlayers(Team.LAVA) == 0;
    }

    private void startGame() {
        diaHuntGameState.setGameStatus(GameStatus.RUNNING);

        gameBackup.getMapBackup().backup();
        gameBackup.getPlayerBackup().backup(playerGameData.players());

        initializePlayers();
        diamondIncreaser.start();
    }

    private void initializePlayers() {
        locationSetter.teleport(playerGameData.players(Team.AQUA), DiaConfig.SPAWN_AQUA);
        locationSetter.teleport(playerGameData.players(Team.LAVA), DiaConfig.SPAWN_LAVA);

        statsGiver.clear(playerGameData.players());
        statsGiver.giveBaseStats(playerGameData.players());
        itemGiver.giveBaseAquaItems(playerGameData.players(Team.AQUA));
        itemGiver.giveBaseLavaItems(playerGameData.players(Team.LAVA));
        itemGiver.giveDia(playerGameData.players());

        for (int i = 0; i < 2; i++) {
            Player aquaPlayer = playerGameData.randomPlayer(Team.AQUA);
            if (aquaPlayer != null) {
                itemGiver.giveAquaDiaChest(aquaPlayer);
            }

            Player lavaPlayer = playerGameData.randomPlayer(Team.LAVA);
            if (lavaPlayer != null) {
                itemGiver.giveLavaDiaChest(lavaPlayer);
            }
        }
    }

    public void join(Player player, String[] teamString) {
        if (teamString == null || teamString.length == 0) {
            playerGameData.add(player);
        } else {
            Team team = Team.getTeam(teamString[0]);
            playerGameData.add(player, team);
        }
    }

    public void leave(Player player) {
        throwIfNotIngame(player);

        playerGameData.remove(player);
        statsGiver.clear(player);

        if (diaHuntGameState.getGameStatus() == GameStatus.RUNNING) {
            gameBackup.getPlayerBackup().restore(player);
        }

        if (!playerGameData.hasPlayers()) {
            printer.broadcast(Message.NO_PLAYERS_LEFT);
            endGame();
        }
    }

    private void endGame() {
        if (diaHuntGameState.getGameStatus() == GameStatus.STARTING) {
            gameInitPrinter.stop();
            startingTask.cancel();
        } else if (diaHuntGameState.getGameStatus() == GameStatus.RUNNING) {
            gameBackup.getMapBackup().restore();
            diamondIncreaser.stop();
        }

        printer.broadcast(Message.STOPPED);
        diaHuntGameState.setGameStatus(GameStatus.OFF);
    }

    private void throwIfNotIngame(Player player) {
        if (!playerGameData.contains(player)) {
            throw new DiaHuntParameterException(Message.NOT_INGAME);
        }
    }

    public void stop() {
        leave(playerGameData.players());
    }

    private void leave(Collection<Player> players) {
        for (Player player : players) {
            leave(player);
        }
    }
}