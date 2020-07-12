package killerm.minecraft.utilities;

public class ConfigValueTypeDeducer {
    private String locationIndicator;

    public ConfigValueTypeDeducer(String locationIndicator) {
        this.locationIndicator = locationIndicator;
    }

    public boolean isLocation(String value) {
        if (value.equals(locationIndicator)) {
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
