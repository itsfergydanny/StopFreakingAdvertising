package com.fergydanny.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BypassCheck {
    public static boolean test(String msg) {
        // Match the word dot
        Pattern p = Pattern.compile("(\\s*)[dD](\\s*)[oO0](\\s*)[tT](\\s*)([^a-zA-Z])");
        Matcher m = p.matcher(msg);
        return m.find();
    }
}
