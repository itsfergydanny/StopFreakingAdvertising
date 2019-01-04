package com.fergydanny.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlCheck {
    public static String test(String msg) {
        // Remove whats after the slash
        String[] messages = msg.split("/");
        msg = messages[0];
        msg.replaceAll("([^a-zA-Z\\s\\d])", "");
        // Match urls, starting with www/http/https or even nothing
        Pattern p = Pattern.compile("^(?!.*)|(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-.]{1}[a-z0-9]+)*(\\s|.*)\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?|(?!.*)$");
        Matcher m = p.matcher(msg);
        if (m.find()) {
            return m.group();
        }
        return "";
    }
}
