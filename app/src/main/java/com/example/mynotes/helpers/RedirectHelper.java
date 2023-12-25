package com.example.mynotes.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.mynotes.LoginActivity;
import com.example.mynotes.NoteDetailActivity;
import com.example.mynotes.RegisterActivity;
import com.example.mynotes.SuccessActivity;

public class RedirectHelper {

    public static void goToSuccessActivity(Context context){
        Intent goToSuccess = new Intent(context, SuccessActivity.class);
        context.startActivity(goToSuccess);
    }
    // END OF GO TO SUCCESS ACTIVITY METHOD.

    public static void goToLogin(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
    // END OF GO TO LOGIN ACTIVITY METHOD.
}
// END OF REDIRECT HELPER CLASS.
