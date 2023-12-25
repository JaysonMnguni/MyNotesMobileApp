package com.example.mynotes.helpers;

public class StringResourceHelper {

    private static final String USER_DETAIL_PREF = "USER_INFO";


    // Set Regular Expression Pattern for Email:
    public static boolean regexEmailValidationPattern(String email) {
        // Set Pattern:
        String regex = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})";

        if(email.matches(regex)) {
            return true;
        }
        return false;
    }
    // End Of Set Regular Expression Pattern for Email.

    public static String getUserDetailPrefName(){
        return USER_DETAIL_PREF;
    }
    // END OF GET USER DETAIL PREF NAME METHOD.
}
// END OF STRING RESOURCE HELPER CLASS.
