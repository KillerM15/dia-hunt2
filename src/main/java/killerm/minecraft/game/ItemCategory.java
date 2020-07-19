package killerm.minecraft.game;

import killerm.minecraft.communication.Message;
import org.apache.commons.lang.StringUtils;

public enum ItemCategory {
    BUILDING(Message.DESCRIPTION_BUILDING),
    MELEE(Message.DESCRIPTION_MELEE),
    RANGED(Message.DESCRIPTION_RANGED),
    EFFECTS(Message.DESCRIPTION_EFFECTS),
    PROTECTION(Message.DESCRIPTION_PROTECTION),
    TRICKY(Message.DESCRIPTION_TRICKY);

    private String description;

    private ItemCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(super.toString().toLowerCase());
    }
}
