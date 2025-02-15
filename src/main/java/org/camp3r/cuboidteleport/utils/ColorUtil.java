package org.camp3r.cuboidteleport.utils;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([a-fA-F0-9]{6})");

    public static String color(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        while (matcher.find()) {
            String hexCode = matcher.group(1);
            StringBuilder hexReplacement = new StringBuilder("§x");
            for (char c : hexCode.toCharArray()) {
                hexReplacement.append('§').append(c);
            }
            message = message.replace("&#" + hexCode, hexReplacement);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
