package com.example.mynotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mynotes.helpers.ApiLinksHelper;
import com.example.mynotes.helpers.RedirectHelper;
import com.example.mynotes.helpers.RegistrationFormValidatorHelper;
import com.example.mynotes.utils.MyVolleySingletonUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private TextView txtGoToSignUp;
    private Button registerBtn;
    private TextInputEditText txtRegFirstName, txtRegLastName, txtRegEmail, txtRegPassword, txtRegConfirm;
    private TextInputLayout txtRegFirstNameLayout, txtRegLastNameLayout,txtRegEmailLayout, txtRegPassLayout, txtRegConfirmLayout;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // REMOVE ACTION BAR:
        getSupportActionBar().hide();

        // INSTANTIATE VOLLEY REQUEST QUE INSTANCE:
        requestQueue = MyVolleySingletonUtil.getInstance(RegisterActivity.this).getRequestQueue();

        // INITIATE / HOOK VIEW COMPONENTS:
        txtGoToSignUp   = findViewById(R.id.txt_go_to_sign_in);
        registerBtn     = findViewById(R.id.register_btn);

        // TEXT INPUT EDIT TEXT FIELDS:
        txtRegFirstName     = findViewById(R.id.txt_first_name);
        txtRegLastName      = findViewById(R.id.txt_last_name);
        txtRegEmail         = findViewById(R.id.txt_email);
        txtRegPassword      = findViewById(R.id.txt_password);
        txtRegConfirm       = findViewById(R.id.txt_confirm);
        // TEXT INPUT LAYOUTS:
        txtRegFirstNameLayout   = findViewById(R.id.txt_first_name_layout);
        txtRegLastNameLayout    = findViewById(R.id.txt_last_name_layout);
        txtRegEmailLayout       = findViewById(R.id.txt_email_layout);
        txtRegPassLayout        = findViewById(R.id.txt_password_layout);
        txtRegConfirmLayout     = findViewById(R.id.txt_confirm_layout);

        // VALIDATE FORM DATA / FIELDS:
        RegistrationFormValidatorHelper regFormValidator
                = new RegistrationFormValidatorHelper(txtRegFirstName,
                txtRegLastName, txtRegEmail, txtRegPassword, txtRegConfirm,
                txtRegFirstNameLayout, txtRegLastNameLayout, txtRegEmailLayout,
                txtRegPassLayout, txtRegConfirmLayout, registerBtn);

        txtRegFirstName.addTextChangedListener(regFormValidator);
        txtRegLastName.addTextChangedListener(regFormValidator);
        txtRegEmail.addTextChangedListener(regFormValidator);
        txtRegPassword.addTextChangedListener(regFormValidator);
        txtRegConfirm.addTextChangedListener(regFormValidator);


        // PROCESS USER REGISTRATION:
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
            // END OF ON CLICK METHOD.
        });
        // END OF REGISTER USER ON CLICK LISTENER OBJECT.

        txtGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedirectHelper.goToLogin(RegisterActivity.this);
                finish();
            }
            // END OF ON ONCLICK METHOD.
        });
        // END OF GO TO LOGIN ON CLICK LISTENER METHOD.
    }
    // END OF ON CREATE METHOD.



    public void registerUser(){
        // GET DATA:
        String first_name   = txtRegFirstName.getText().toString();
        String last_name    = txtRegLastName.getText().toString();
        String email        = txtRegEmail.getText().toString();
        String password     = txtRegPassword.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiLinksHelper.registerUserApiUri(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("RegisterActivity", response.toString());
                Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                RedirectHelper.goToSuccessActivity(RegisterActivity.this);
                finish();
            }
            // END OF ON RESPONSE METHOD.
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("RegisterActivity", "Failed to register user");
                Toast.makeText(RegisterActivity.this, "Failed to register user", Toast.LENGTH_LONG).show();
            }
            // END OF ON ERROR RESPONSE METHOD.
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
            // END OF GET PARAMS METHOD.
        };
        // END OF STRING REQUEST OBJECT.

        // ADD / SEND REQUEST:
        requestQueue.add(stringRequest);
    }
    // END OF REGISTER USER METHOD.

}
// END OF REGISTER ACTIVITY CLASS.