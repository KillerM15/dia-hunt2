package killerm.minecraft.utilities;

import killerm.minecraft.controller.ConfigController;

public class ConfigDeducer {
    private String locationIndicator;

    public ConfigDeducer(String locationIndicator) {
        this.locationIndicator = locationIndicator;
    }

    public boolean isLocation(String value) {
        if (value == locationIndicator) {
            return true;
        }

        return false;
    }

    public boolean isNumber(String value) {
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
