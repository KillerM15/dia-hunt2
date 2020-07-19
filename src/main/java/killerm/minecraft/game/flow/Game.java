package killerm.minecraft.game.flow;

import killerm.minecraft.DiaHuntPlugin;
import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.data.DiaConfig;
import killerm.minecraft.error.ParameterException;
import killerm.minecraft.game.data.*;
import killerm.minecraft.game.interaction.ItemGiver;
import killerm.minecraft.game.interaction.LocationSetter;
import killerm.minecraft.game.interaction.StatsGiver;
import killerm.minecraft.manager.ScoreboardManager;
import killerm.minecraft.utilities.ItemRemover;
import killerm.minecraft.utilities.MinecraftConstants;
import killerm.minecraft.utilities.PlayerNameFixer;
import killerm.minecraft.utilities.WorldProvider;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;

public class Game {
    private Printer printer;
    private GameStatus gameStatus;
    private PlayerGameData playerGameData;
    private ChestGameData chestGameData;
    private GameBackup gameBackup;
    private GameInitPrinter gameInitPrinter;
    private DiamondIncreaser diamondIncreaser;
    private LocationSetter locationSetter;
    private StatsGiver statsGiver;
    private ItemGiver itemGiver;
    private PlayerNameFixer playerNameFixer;
    private BukkitTask startingTask;
    private ItemRemover itemRemover;
    private ScoreboardManager scoreboardManager;
    private WorldProvider worldProvider;

    public Game(GameStatus gameStatus, PlayerGameData playerGameData, ChestGameData chestGameData) {
        this.printer = new Printer();
        this.gameStatus = gameStatus;
        this.playerGameData = playerGameData;
        this.chestGameData = chestGameData;
        this.gameBackup = new GameBackup();
        this.gameInitPrinter = new GameInitPrinter(gameStatus, playerGameData);
        this.diamondIncreaser = new DiamondIncreaser(playerGameData, chestGameData);
        this.locationSetter = new LocationSetter();
        this.statsGiver = new StatsGiver();
        this.itemGiver = new ItemGiver();
        this.playerNameFixer = new PlayerNameFixer();
        this.itemRemover = new ItemRemover();
        this.scoreboardManager = new ScoreboardManager();
        this.worldProvider = new WorldProvider();
    }

    // This is why you should use dependency injection frameworks
    public Game(Printer printer, GameStatus gameStatus, ChestGameData chestGameData, PlayerGameData playerGameData, GameBackup gameBackup, GameInitPrinter gameInitPrinter, DiamondIncreaser diamondIncreaser, LocationSetter locationSetter, StatsGiver statsGiver, ItemGiver itemGiver, PlayerNameFixer playerNameFixer, BukkitTask startingTask, ItemRemover itemRemover, ScoreboardManager scoreboardManager, WorldProvider worldProvider) {
        this.printer = printer;
        this.gameStatus = gameStatus;
        this.chestGameData = chestGameData;
        this.playerGameData = playerGameData;
        this.gameBackup = gameBackup;
        this.gameInitPrinter = gameInitPrinter;
        this.diamondIncreaser = diamondIncreaser;
        this.locationSetter = locationSetter;
        this.statsGiver = statsGiver;
        this.itemGiver = itemGiver;
        this.playerNameFixer = playerNameFixer;
        this.startingTask = startingTask;
        this.itemRemover = itemRemover;
        this.scoreboardManager = scoreboardManager;
        this.worldProvider = worldProvider;
    }

    public void startInitialize(Player gameStarter, String[] invitedPlayerNames) {
        gameStatus.setGameStatusType(GameStatusType.STARTING);
        printCountDown(gameStarter, invitedPlayerNames);
        startGameAfterDelay();
        join(gameStarter, null);
    }

    // For testing, without threads
    public void startInitializeMocked(Player gameStarter, String[] invitedPlayerNames) {
        gameStatus.setGameStatusType(GameStatusType.STARTING);
        printCountDown(gameStarter, invitedPlayerNames);
        startGame();
        join(gameStarter, null);
    }

    private void printCountDown(Player gameStarter, String[] invitedPlayerNames) {
        invitedPlayerNames = playerNameFixer.fixCase(invitedPlayerNames);

        gameInitPrinter.printGameInit(gameStarter.getName(), invitedPlayerNames);
    }

    private void startGameAfterDelay() {
        // Added 1 tick extra because GameInitPrinter needs unstarted game after 60 seconds
        int delay = (int) (double) DiaConfig.SECONDS_UNTIL_START.get() * MinecraftConstants.ticksPerSecond + 1;

        this.startingTask = new BukkitRunnable() {
            @Override
            public void run() {
                startGame();
            }
        }.runTaskLater(DiaHuntPlugin.getInstance(), delay);
    }

    private void startGame() {
        gameStatus.setGameStatusType(GameStatusType.RUNNING);
        gameBackup.reloadMapRegionFromConfig();

        itemRemover.remove(worldProvider.getWorld());

        chestGameData.clear();

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
        scoreboardManager.refresh(playerGameData.players());

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

        if (gameStatus.getGameStatusType() == GameStatusType.RUNNING) {
            gameBackup.getPlayerBackup().restore(player);
            scoreboardManager.clear(player);
        }

        if (!playerGameData.hasPlayers()) {
            printer.broadcast(Message.NO_PLAYERS_LEFT);
            endGame();
        }
    }

    private void endGame() {
        if (gameStatus.getGameStatusType() == GameStatusType.STARTING) {
            gameInitPrinter.stop();
            startingTask.cancel();
        } else if (gameStatus.getGameStatusType() == GameStatusType.RUNNING) {
            itemRemover.remove(worldProvider.getWorld());
            gameBackup.getMapBackup().restore();
            diamondIncreaser.stop();
        }

        printer.broadcast(Message.STOPPED);
        gameStatus.setGameStatusType(GameStatusType.OFF);
    }

    private void throwIfNotIngame(Player player) {
        if (!playerGameData.inGame(player)) {
            throw new ParameterException(Message.NOT_INGAME);
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