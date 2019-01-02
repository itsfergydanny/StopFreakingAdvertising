package com.fergydanny;

import com.fergydanny.events.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class StopFreakingAdvertising extends JavaPlugin {

    private HashMap<UUID, Integer> warnings = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerChatEvent(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public HashMap<UUID, Integer> getWarnings() {
        return warnings;
    }
}
