package net.coruptical.hermitcore.models;

import net.coruptical.hermitcore.HermitCore;

import java.util.HashMap;
import java.util.Map;

public enum ConfigPermission {
    STAFF_MENU(HermitCore.getInstance().getStaffMenuPermission()),
    STAFF_CHAT(HermitCore.getInstance().getStaffChatPermission()),
    RESTORE_CHEST(HermitCore.getInstance().getRestoreChestPermission());

    private static final Map<String, ConfigPermission> BY_LABEL = new HashMap<>();

    static {
        for (ConfigPermission cp : values()){
            BY_LABEL.put(cp.label, cp);
        }
    }

    private final String label;

    private ConfigPermission(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

    public static ConfigPermission valueOfLabel(String label){
        return BY_LABEL.get(label);
    }


}
