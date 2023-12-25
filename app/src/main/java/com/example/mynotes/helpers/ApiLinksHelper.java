package com.example.mynotes.helpers;

public class ApiLinksHelper {
    // MAIN BASE API URI:
    private static final String BASE_URL = "http://192.168.0.200:8081/api/v1/";


    public static String authUserApiUri(){
        return BASE_URL + "auth/authenticate";
    }
    // END OF AUTHENTICATE USER API URI METHOD.

    public static  String registerUserApiUri(){
        return BASE_URL + "auth/register";
    }
    // END OF REGISTER USER API URI METHOD.

    public static String getMyNotesApiUri(){
        return BASE_URL + "note/my_notes";
    }
    // END OF GET MY NOTES API URI METHOD.

    public static String createNoteApiUri(){
        return BASE_URL + "note/create";
    }
    // END OF CREATE NOTES API URI METHOD.

    public static String deleteNoteApiUri(){
        return BASE_URL + "note/delete";
    }
    // END OF DELETE NOTE API URI METHOD.
}
// END OF API LINKS HELPER CLASS.