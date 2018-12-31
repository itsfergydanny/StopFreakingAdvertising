package com.fergydanny.utils;

import org.bukkit.ChatColor;

public class Colorize {
    public static String c(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
