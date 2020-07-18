package killerm.minecraft.game;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntParameterException;

public enum Team {
    AQUA, LAVA;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static Team getTeam(String teamString) {
        for (Team t : Team.values()) {
            if (teamString.equals(t.toString())) {
                return t;
            }
        }

        throw new DiaHuntParameterException(Message.TEAM_NOT_VALID);
    }

    public Team getEnemy() {
        if (this == Team.AQUA) {
            return Team.LAVA;
        }

        return Team.AQUA;
    }
}