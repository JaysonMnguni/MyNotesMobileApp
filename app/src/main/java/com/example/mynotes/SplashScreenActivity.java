package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.mynotes.helpers.StringResourceHelper;

public class SplashScreenActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    //private static final String USER_DETAIL_PREF = "USER_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // GET PREFERENCES:
        preferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), MODE_PRIVATE);


        // HIDE ACTION BAR:
        getSupportActionBar().hide();

        // HANDLER METHOD TO REDIRECT TO LOGIN OR MAIN:
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // CHECK IF USER IS AUTHENTICATED IF BLOCK:
                if(preferences.getBoolean("authenticated", false)) {
                    goToMainIfAuthenticated();
                }else {
                    goToLoginIfNotAuthenticated();
                }
                // END OF CHECK IF USER IS AUTHENTICATED IF BLOCK.
            }
            // END OF HANDLER POST DELAY RUN METHOD.
        }, 5000);
        // END OF HANDLER METHOD TO REDIRECT TO LOGIN OR MAIN.
    }
    // END OF SPLASH SCREEN ON CREATE METHOD.

    public void goToMainIfAuthenticated(){
        Intent goToMainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(goToMainIntent);
        finish();
    }
    // END OF GO TO MAIN ACTIVITY IF AUTHENTICATED METHOD.

    public void goToLoginIfNotAuthenticated(){
        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    // END OF GO TO LOGIN ACTIVITY IF NOT AUTHENTICATED METHOD.
}
// END OF SPLASH SCREEN ACTIVITY CLASS.