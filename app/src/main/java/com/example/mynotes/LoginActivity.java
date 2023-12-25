package com.example.mynotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mynotes.helpers.ApiLinksHelper;
import com.example.mynotes.helpers.LoginFormValidationHelper;
import com.example.mynotes.helpers.StringResourceHelper;
import com.example.mynotes.utils.MyVolleySingletonUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    //private static final String USER_DETAIL_PREF = "USER_INFO";
    private SharedPreferences preferences;
    private RequestQueue mRequestQueue;

    private LoginFormValidationHelper loginValidator;
    private TextView  txtGoToSignIn;

    private TextInputEditText txt_email, txt_password;
    private TextInputLayout txtEmailLayout, txtPasswordLayout;

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // REMOVE ACTION BAR:
        getSupportActionBar().hide();

        // INITIALIZE REQUEST QUEUE:
        mRequestQueue = MyVolleySingletonUtil.getInstance(LoginActivity.this).getRequestQueue();


        // INITIATE / HOOK VIEW COMPONENTS:
        txt_email        = findViewById(R.id.txt_email);
        txt_password     = findViewById(R.id.txt_password);
        txtGoToSignIn   = findViewById(R.id.txt_go_to_sign_up);
        loginBtn        = findViewById(R.id.login_btn);
        // GET LAYOUTS:
        txtEmailLayout      = findViewById(R.id.txt_email_layout);
        txtPasswordLayout   = findViewById(R.id.txt_password_layout);

        // GET FORM VALIDATOR OBJECT:
        loginValidator = new LoginFormValidationHelper(txt_email, txt_password, txtEmailLayout, txtPasswordLayout, loginBtn);

        // ADD TEXT FIELD LISTENERS:
        txt_email.addTextChangedListener(loginValidator);
        txt_password.addTextChangedListener(loginValidator);

        // PROCESS LOGIN:
        processLogin();

        // REDIRECT TO REGISTER ACTIVITY:
        redirectToRegister();

    }
    // END OF ON CREATE LOGIN ACTIVITY METHOD.


    public void processLogin(){
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser(txt_email.getText().toString(), txt_password.getText().toString());
               // Toast.makeText(LoginActivity.this, "Login Button Clicked!", Toast.LENGTH_SHORT).show();
            }
            // END OF ONCLICK METHOD.
        });
        // END OF ON LOGIN CLICK LISTENER METHOD.
    }
    // END OF PROCESS LOGIN ACTION BUTTON.

    public void redirectToRegister(){
        txtGoToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
            // END OF ON CLICK METHOD.
        });
        // END OF GO TO REGISTER ON CLICK LISTENER METHOD.
    }
    // END OF REDIRECT TO REGISTER PAGE ACTION METHOD.

    public void authenticateUser(String email, String password){

        // SET USER DATA MAP OBJECT:
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        JsonObjectRequest request
                    = new JsonObjectRequest(Request.Method.POST, ApiLinksHelper.authUserApiUri(), new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    // INITIATE PREFERENCES:
                    preferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    try {

                        editor.putInt("user_id", response.getInt("userId"));
                        editor.putString("first_name", response.getString("firstName"));
                        editor.putString("last_name", response.getString("lastName"));
                        editor.putString("username", response.getString("username"));
                        editor.putString("token", response.getString("token"));
                        editor.putBoolean("authenticated", true);
                        editor.apply();
                        // REDIRECT TO MAIN IF AUTHENTICATED:
                        goToMainIfAuthenticated();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Error msg 1 try block: " + e.getMessage());
                        System.out.println("Error msg 2: " + "In try block");
                        Toast.makeText(LoginActivity.this, "Try Block error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        throw new RuntimeException(e);
                    }
                    // END OF JSON RESPONSE OBJECT TRY BLOCK.
                }
                // END OF ON RESPONSE METHOD.
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    System.out.println("Error msg 1: " + error.getMessage());
                    System.out.println("Auth Error: " + error.networkResponse);
                    System.out.println("Auth Error Two: " + error.getLocalizedMessage());

                    Toast.makeText(LoginActivity.this, "Failed To Log-In", Toast.LENGTH_LONG).show();
                }
            });
            // END OF JSON OBJECT REQUEST OBJECT.

        // ADD TO REQUEST QUE:
        mRequestQueue.add(request);
    }
    // END OF AUTHENTICATED USER METHOD.

    public void goToRegister(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    // END OF GO TO REGISTER ACTIVITY.

    public void goToMainIfAuthenticated(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        // DISPLAY SUCCESS MESSAGE IF AUTHENTICATED:
        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
        finish();
    }
    // END OF GO TO LOGIN INTENT METHOD.

}
// END OF LOGIN ACTIVITY CLASS.