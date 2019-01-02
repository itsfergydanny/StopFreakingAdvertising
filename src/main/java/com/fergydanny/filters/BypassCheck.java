package com.fergydanny.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO Make this better, it doesn't fully work now

public class BypassCheck {
    public static boolean test(String msg) {
        // Match the word dot
        Pattern p = Pattern.compile("(.*|\\s)[dD](\\s|)[oO0](\\s|)[tT]([^a-zA-Z]|\\s|$)");
        Matcher m = p.matcher(msg);
        return m.find();
    }
}
