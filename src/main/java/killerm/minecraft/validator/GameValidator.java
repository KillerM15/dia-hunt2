package killerm.minecraft.validator;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntParameterException;
import killerm.minecraft.game.Team;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameValidator {
    public void validateStart(String[] params) {
        for (String playerName : params) {
            if (Bukkit.getPlayer(playerName) == null) {
                throw new DiaHuntParameterException(playerName + Message.IS_NO_PLAYER);
            }
        }
    }

    public void validateStop(String[] params) {
        if (params.length > 0) {
            throw new DiaHuntParameterException(Message.TOO_MANY_INPUTS);
        }
    }

    public void validateJoin(String[] params) {
        if (params.length > 1) {
            throw new DiaHuntParameterException(Message.TOO_MANY_INPUTS);
        }

        if (params.length == 1) {
            String teamParam = params[0];
            if (!isAValidTeam(teamParam)) {
                throw new DiaHuntParameterException(Message.TEAM_NOT_VALID);
            }
        }
    }

    private boolean isAValidTeam(String teamString) {
        List<String> teamStrings = Arrays.stream(Team.values())
                .map(Object::toString).collect(Collectors.toList());

        return teamStrings.contains(teamString);
    }

    public void validateLeave(String[] params) {
        if (params.length > 0) {
            throw new DiaHuntParameterException(Message.TOO_MANY_INPUTS);
        }
    }
}
