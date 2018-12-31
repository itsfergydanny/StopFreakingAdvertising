package com.fergydanny.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlCheck {
    public static String test(String msg) {
        // Match urls starting with www, http or https
        Pattern p = Pattern.compile("^(http://www\\.|https://www\\.|http://|https://)?[a-z0-9]+([\\-.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$");
        Matcher m = p.matcher(msg);
        if (m.find()) {
            System.out.println("caught " + m.group().replaceAll("(http://www\\.|https://www\\.|http://|https://|www\\.|(/[^/]+$))|/$", ""));
            return m.group().replaceAll("(http://www\\.|https://www\\.|http://|https://|www\\.|(/[^/]+$))|/$", "");
        }
        return "";
    }
}
