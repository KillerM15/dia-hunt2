package killerm.minecraft.controller;

import killerm.minecraft.communication.Message;
import killerm.minecraft.communication.Printer;
import killerm.minecraft.data.DiaConfig;
import killerm.minecraft.utilities.ConfigValueTypeDeducer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ConfigController {
    private Printer printer;
    private ConfigValueTypeDeducer configValueTypeDeducer;

    public ConfigController() {
        this.printer = new Printer();
        this.configValueTypeDeducer = new ConfigValueTypeDeducer(Message.CONFIG_LOCATION_INDICATOR);
    }

    public ConfigController(Printer printer, ConfigValueTypeDeducer configValueTypeDeducer) {
        this.printer = printer;
        this.configValueTypeDeducer = configValueTypeDeducer;
    }

    public void execute(Player player, String[] params) {
        if (oneParameter(params)) {
            executeGet(player, params);
        } else {
            executeSet(player, params);
        }
    }

    private boolean oneParameter(String[] params) {
        return params.length == 1;
    }

    private void executeGet(Player player, String[] params) {
        DiaConfig diaConfig = DiaConfig.getInstance(params[0]);
        printer.tell(player, diaConfig.toString() + Message.IS_SET_TO + diaConfig.get().toString());
    }

    public void executeSet(Player player, String[] params) {
        DiaConfig diaConfig = DiaConfig.getInstance(params[0]);

        if (configValueTypeDeducer.isNumber(params[1])) {
            double value = Double.parseDouble(params[1]);
            diaConfig.set(value);
        } else if (configValueTypeDeducer.isLocation(params[1])) {
            Location loc = player.getLocation();
            diaConfig.set(loc);
        } else { //String
            diaConfig.set(params[1]);
        }

        printer.tell(player, Message.NEW_CONFIG_VALUE);

        executeGet(player, params);
    }
}
