package killerm.minecraft.validator;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.DiaHuntParameterException;

public class ConfigValidator {
    public void validateExecute(String[] params) {
        // More logic checks were not necessary
        // if oneParam -> validateGet -> if >1 param makes no sense
        if (params.length > 2) {
            throw new DiaHuntParameterException(Message.TOO_MANY_ARGS);
        }
    }
}
