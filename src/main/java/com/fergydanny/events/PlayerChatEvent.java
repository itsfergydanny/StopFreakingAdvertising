package com.fergydanny.events;

import com.fergydanny.StopFreakingAdvertising;
import com.fergydanny.filters.BypassCheck;
import com.fergydanny.filters.NumberedIP;
import com.fergydanny.filters.UrlCheck;
import com.fergydanny.utils.Colorize;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;
import java.util.Set;

public class PlayerChatEvent implements Listener {

    private StopFreakingAdvertising plugin;
    private String warningMessage;
    private List<String> whitelistedDomains;
    private List<String> customRules;
    private List<String> clearMessage;
    private boolean clearChat;
    private int maxWarnings;
    private String punishmentCommand;
    private boolean customFilter;
    private boolean urlFilter;
    private boolean bypassFilter;
    private boolean numberedIP;

    public PlayerChatEvent(StopFreakingAdvertising plugin) {
        this.plugin = plugin;
        warningMessage = Colorize.c(plugin.getConfig().getString("warning-message"));
        whitelistedDomains = plugin.getConfig().getStringList("whitelisted-domains");
        customRules = plugin.getConfig().getStringList("custom-filters");
        clearMessage = plugin.getConfig().getStringList("clear-chat-message");
        clearChat = plugin.getConfig().getBoolean("clear-chat");
        maxWarnings = plugin.getConfig().getInt("punishment-threshold");
        punishmentCommand = plugin.getConfig().getString("punishment-command");
        customFilter = plugin.getConfig().getBoolean("custom-filter");
        urlFilter = plugin.getConfig().getBoolean("url-filter");
        bypassFilter = plugin.getConfig().getBoolean("bypass-filter");
        numberedIP = plugin.getConfig().getBoolean("numberedip-filter");
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncPlayerChatEvent e) {

        Player player = e.getPlayer();

        // Bypass check if user has the permission: sfa.bypass
        if (player.hasPermission("sfa.bypass")) {
            return;
        }

        String message = e.getMessage().toLowerCase();
        Set<Player> recipients = e.getRecipients();

        System.out.println("[DEBUG] Player sent message " + message);

        // Check if matches any custom filters
        if (customFilter) {
            for (String str : customRules) {
                if (message.contains(str)) {
                    stop(player, recipients, message);
                    return;
                }
            }
        }

        // Check for basic url like test.com, mc.test.com and such
        if (urlFilter) {
            String checkUrl = UrlCheck.test(message);
            System.out.println("checked url is " + checkUrl);
            if (!checkUrl.isEmpty() && !whitelistedDomains.contains(checkUrl)) {
                stop(player, recipients, message);
                return;
            }
        }

        // Check if people are trying to bypass the filter by using something like (dot)
        if (bypassFilter) {
            if (BypassCheck.test(message)) {
                stop(player, recipients, message);
                return;
            }
        }

        //  Check for valid Ipv4 Address
        if (numberedIP) {
            if (NumberedIP.test(message)) {
                stop(player, recipients, message);
                return;
            }
        }
    }

    private void stop(Player player, Set<Player> recipients, String message) {
        if (clearChat) {
            clearChat(recipients);
        }
        player.sendMessage(warningMessage);
        int currentWarnings = plugin.getWarnings().getOrDefault(player.getUniqueId(), 1);
        plugin.getWarnings().put(player.getUniqueId(), (currentWarnings + 1));
        // If user reaches max warnings, run punishment command
        if (currentWarnings == maxWarnings) {
            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            scheduler.runTask(plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.getWarnings().put(player.getUniqueId(), 1);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), punishmentCommand.replace("%player%", player.getName()).replace("/", ""));
                }
            });
        }
        System.out.println("[StopFreakingAdvertising] " + player.getName() + " has been caught advertising with the following message: \"" + message + "\".");
    }

    private void clearChat(Set<Player> recipients) {
        for (Player player : recipients) {
            // Users with the sfa.staff permission do not have their chat cleared.
            if (!player.hasPermission("sfa.staff")) {
                for (int i = 0; i<100; i++) {
                    player.sendMessage(" ");
                }
            }
            for (String line : clearMessage) {
                player.sendMessage(Colorize.c(line));
            }
        }
    }
}
