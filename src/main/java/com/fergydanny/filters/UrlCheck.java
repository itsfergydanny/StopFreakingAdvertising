package com.fergydanny.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlCheck {
    public static String test(String msg) {
        // Remove whats after the slash
        String[] messages = msg.split("/");
        msg = messages[0];

        // Replace () with .
        msg = msg.replaceAll("\\(.*\\)", ".");

        // Match urls with subdomains
        Pattern p = Pattern.compile("(http:\\/\\/|https:\\/\\/|http:\\/\\/www|https:\\/\\/www|www|mc|play|hub|us|eu)(\\s*\\.\\s*)(\\S*)(\\s*\\.\\s*)(\\S{2,3})");
        Matcher m = p.matcher(msg);
        if (m.find()) {
            return m.group();
        }

        // Match urls without subdomains
        p = Pattern.compile("([a-zA-Z0-9]*\\s*\\.\\s*)(co|com|org|edu|gov|uk|net|ca|de|jp|fr|au|us|ru|ch|it|nl|se|no|es|mil|me|io|pw|xyz)([^a-zA-Z]|$)");
        m = p.matcher(msg);
        if (m.find()) {
            return m.group();
        }

        return "";
    }
}
