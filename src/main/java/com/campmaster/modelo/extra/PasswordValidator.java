package com.campmaster.modelo.extra;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        if (password.isEmpty()) {
            return false;
        }

        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
