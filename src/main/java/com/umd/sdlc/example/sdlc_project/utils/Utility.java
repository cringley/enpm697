package com.umd.sdlc.example.sdlc_project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    
    /**
     * Checks if an email contains any illegal characters. We only accept @ . _ as special characters
     * @param email - email to check
     * @return true or false if email contains illegal characters
     */
    public static boolean checkForValidEmail(String email) {
        Pattern pattern = Pattern.compile("[^A-Za-z0-9@._]");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
