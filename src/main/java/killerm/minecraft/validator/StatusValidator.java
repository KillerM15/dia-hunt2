package killerm.minecraft.validator;

import killerm.minecraft.communication.Message;
import killerm.minecraft.error.ParameterException;

public class StatusValidator {
    public void validateStatus(String[] params) {
        if (params.length > 0) {
            throw new ParameterException(Message.TOO_MANY_INPUTS);
        }
    }
}
