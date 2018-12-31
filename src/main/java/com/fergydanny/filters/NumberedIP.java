package com.fergydanny.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberedIP {
    public static boolean test(String msg) {
        String commasToPeriods = msg.replaceAll(",", ".");
        Pattern p = Pattern.compile(".*(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).*");
        Matcher m = p.matcher(commasToPeriods);
        return m.find();
    }
}
