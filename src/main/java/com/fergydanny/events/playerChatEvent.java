package com.fergydanny.events;

import com.fergydanny.StopFreakingAdvertising;
import com.fergydanny.filters.BypassCheck;
import com.fergydanny.filters.NumberedIP;
import com.fergydanny.filters.UrlCheck;
import com.fergydanny.utils.Colorize;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class playerChatEvent implements Listener {

    private String warningMessage;
    private List<String> whitelistedDomains;

    public playerChatEvent(StopFreakingAdvertising plugin) {
        warningMessage = Colorize.c(plugin.getConfig().getString("warning-message"));
        whitelistedDomains = plugin.getConfig().getStringList("whitelisted-domains");
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncPlayerChatEvent e) {

        Player player = e.getPlayer();

        // Bypass check if user has the permission: sfa.bypass
        if (player.hasPermission("sfa.bypass")) {
            return;
        }

        String message = e.getMessage().toLowerCase();

        System.out.println("[DEBUG] Player sent message " + message);

        // Check for basic url like test.com, mc.test.com and such
        String checkUrl = UrlCheck.test(message);
        if (!checkUrl.isEmpty() && !whitelistedDomains.contains(checkUrl)) {
            stop(e, player);
            return;
        }

        // Check if people are trying to bypass the filter by using something like (dot)
        if (BypassCheck.test(message)) {
            stop(e, player);
            return;
        }

        //  Check for valid Ipv4 Address
        if (NumberedIP.test(message)) {
            stop(e, player);
            return;
        }
    }

    private void stop(AsyncPlayerChatEvent e, Player player) {
        e.setCancelled(true);
        player.sendMessage(warningMessage);
    }
}
