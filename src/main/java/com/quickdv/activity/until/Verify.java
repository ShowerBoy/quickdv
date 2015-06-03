package com.quickdv.activity.until;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lilongfei on 15/6/2.
 */
public class Verify {

    public static boolean isPhoneVail(String email) {
        //TODO: Replace this with your own logic
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
