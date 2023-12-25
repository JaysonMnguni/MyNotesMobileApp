package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mynotes.helpers.StringResourceHelper;

public class SuccessActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private boolean authenticated;
    private Button returnHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        getSupportActionBar().hide();

        // GET PREFERENCES:
        preferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), MODE_PRIVATE);

        // GET AUTHENTICATED USER:
        authenticated = preferences.getBoolean("authenticated", false);

        returnHomeBtn = findViewById(R.id.success_return_home_btn);

        returnHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(authenticated){
                    returnToHome();
                }else {
                    goToLoginIfNotAuthenticated();
                }
                // END OF CHECK IF USER IS AUTHENTICATED IF BLOCK.
            }
            // END OF ON ON CLICK METHOD.
        });
        // END OF RETURN HOME ON CLICK LISTENER OBJECT.
    }
    // END OF ON CREATE METHOD.

    public void returnToHome(){
        Intent returnHomeIntent = new Intent(SuccessActivity.this, MainActivity.class);
        startActivity(returnHomeIntent);
        finish();
    }
    // END OF RETURN HOME METHOD.

    public void goToLoginIfNotAuthenticated(){
        Intent intent = new Intent(SuccessActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    // END OF GO TO LOGIN ACTIVITY IF NOT AUTHENTICATED METHOD.
}
// END OF SUCCESS ACTIVITY CLASS.