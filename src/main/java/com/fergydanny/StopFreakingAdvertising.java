package com.fergydanny;

import com.fergydanny.events.playerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class StopFreakingAdvertising extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new playerChatEvent(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
