package killerm.minecraft.communication;

import killerm.minecraft.commands.CommandType;
import killerm.minecraft.data.DiaConfig;
import killerm.minecraft.helper.CollectionDelimiter;
import killerm.minecraft.utilities.Team;

// Intentional code duplication and ugly inner class to increase readability
public class Message {
    public static final String GOLD = "§6";
    public static final String GREY = "§7";
    public static final String DARK_RED = "§4";
    public static final String RED = "§c";
    public static final String DARK_BLUE = "§1";
    public static final String AQUA = "§b";
    public static final String BLUE = "§9";
    public static final String GREEN = "§a";
    public static final String DARK_AQUA = "§3";
    public static final String BOLD = "§l";
    public static final String STRIKE = "§m";
    public static final String RESET = "§r";
    public static final String TEAM_AQUA = BLUE;
    public static final String TEAM_LAVA = RED;

    public static final String PREFIX_BROADCAST = AQUA + BOLD + "◆Dia Hunt◆ " + DARK_AQUA;
    public static final String PREFIX_BROADCAST_ERROR = PREFIX_BROADCAST + DARK_RED + "ERROR: " + DARK_AQUA;
    public static final String PREFIX_TELL = AQUA + BOLD + "◆ " + GREY;
    public static final String PREFIX_TELL_ERROR = PREFIX_TELL + RESET + DARK_RED + "ERROR: " + GREY;

    public static final String COLLECTION_DELIMITER = ", ";
    public static final String VALID_COMMANDTYPES = "Valid commands: " + CollectionDelimiter.delimit(CommandType.values(), CommandType::toString);
    public static final String COMMANDTYPE_NOT_VALID = "This command isn't valid! " + VALID_COMMANDTYPES;
    public static final String COMMAND_NOT_IMPLEMENTED = "This command hasn't been implemented yet!";
    public static final String VALID_DIACONFIGS = "Valid configurations: " + CollectionDelimiter.delimit(DiaConfig.values(), DiaConfig::toString);
    public static final String DIACONFIG_NOT_VALID = "This configuration isn't valid! " + VALID_DIACONFIGS;
    public static final String DIACONFIG_NOT_IMPLEMENTED = "This command hasn't been implemented yet!";
    public static final String DESCRIPTION_EMPTY = "";
    public static final String DESCRIPTION_CONFIG = "Change config values. Usage: /diahunt config KEY (VALUE)";

    public static final String VALID_TEAMS = "Valid teams: " + CollectionDelimiter.delimit(Team.values(), Team::toString);
    public static final String TEAM_NOT_VALID = "This team isn't valid! " + VALID_TEAMS;
    public static final String IS_SET_TO = " is set to ";
    public static final String CONFIG_LOCATION_INDICATOR = "location";
    public static final String TOO_MANY_ARGS = "You entered too many arguments!";
    public static final String AND = " and ";
    public static final String NOT_SAME_TYPE = " are not the same type!";
    public static final String SYMBOL_FIRE = "ᐃ";
    public static final String SYMBOL_WATER = "ᐁ";
    public static final String SPACE = " ";
    public static final String RIGHT_ARROW = "→";
    public static final String LEFT_ARROW = "←";
    public static final String JOINED_TEAM = " has joined team ";
    public static final String LEFT = " has left Dia Hunt!";
    public static final String DIA_CHEST = "Dia Chest";
    public static final String DIAMOND = "Diamond";
    public static final String COULD_NOT_RANDOM_PLAYER = "Couldn't generate random player because team ";
    public static final String IS_EMPTY = " is empty!";
    public static final String DIA_LINE = "◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆";
    public static final String GAME_STARTED_BY = "§lGame started by ";
    public static final String EXMARK = "!";
    public static final String YOU_ARE_INVITED = "You have been invited in a Dia Hunt game!";
    public static final String ACCEPT = "§a§l→JOIN←";
    public static final String HAVE_BEEN_INVITED = " have been invited";
    public static final String HAS_BEEN_INVITED = " has been invited";
    public static final String GAME_START_IN = "Game will start in ";
    public static final String SECONDS = " seconds!";
    public static final String START_DIA = "◆Start!◆";
    public static final String GET_DIAS = "Get all the diamonds!";
    public static final String GAME_STARTED = "DiaHunt has been started!";
}