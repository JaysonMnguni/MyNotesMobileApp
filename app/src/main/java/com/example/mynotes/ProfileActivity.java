package com.example.mynotes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mynotes.helpers.StringResourceHelper;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private TextInputEditText txtProfFirstName, txtProfLastName, txtProfEmail;
    private Button profileLogoutBtn;
    ActionBar actionBar;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // ACTION BAR SETUP:
        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        // HOOK / INITIATE VIEW ELEMENTS:
        txtProfFirstName        = findViewById(R.id.txt_prof_first_name);
        txtProfLastName         = findViewById(R.id.txt_prof_last_name);
        txtProfEmail            = findViewById(R.id.txt_prof_email);
        profileLogoutBtn        = findViewById(R.id.profile_logout_btn);

        profileLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logUserOut();
            }
        });
        // END OF PROFILE LOGOUT ON CLICK LISTENER OBJECT.

        setUserDetailsInEditTextFields();

    }
    // END OF ON CREATE METHOD.

    public void setUserDetailsInEditTextFields(){
        // GET STORED PREFERENCES:
        prefs = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), MODE_PRIVATE);
        // SET TEXT INPUT FIELDS VALUES:
        txtProfFirstName.setText(prefs.getString("first_name", ""));
        txtProfLastName.setText(prefs.getString("last_name", ""));
        txtProfEmail.setText(prefs.getString("username", ""));
    }
    // END OF SET USER DETAILS IN EDIT TEXT FIELDS.

    public void logUserOut(){
        clearPreferences();
        goToLogin();
        Toast.makeText(ProfileActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
    }
    // END OF LOG USER OUT METHOD.

    public void clearPreferences(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
    // END OF LOGOUT OR CLEAR PREFERENCES.

    public void goToLogin(){
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    // END OF GO TO LOGIN ACTIVITY.
}
// END OF PROFILE ACTIVITY CLASS.