package com.example.mynotes.helpers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrationFormValidatorHelper implements TextWatcher {

    private TextInputEditText txtRegFirstName, txtRegLastName, txtRegEmail, txtRegPassword, txtRegConfirm;
    private TextInputLayout txtRegFirstNameLayout, txtRegLastNameLayout,txtRegEmailLayout, txtRegPassLayout, txtRegConfirmLayout;

    private Button registerBtn;


    // END OF NO ARGS CONSTRUCTOR.

    public RegistrationFormValidatorHelper(TextInputEditText txtRegFirstName,
                                           TextInputEditText txtRegLastName,
                                           TextInputEditText txtRegEmail,
                                           TextInputEditText txtRegPassword,
                                           TextInputEditText txtRegConfirm,
                                           TextInputLayout txtRegFirstNameLayout,
                                           TextInputLayout txtRegLastNameLayout,
                                           TextInputLayout txtRegEmailLayout,
                                           TextInputLayout txtRegPassLayout,
                                           TextInputLayout txtRegConfirmLayout,
                                           Button registerBtn) {
        this.txtRegFirstName = txtRegFirstName;
        this.txtRegLastName = txtRegLastName;
        this.txtRegEmail = txtRegEmail;
        this.txtRegPassword = txtRegPassword;
        this.txtRegConfirm = txtRegConfirm;
        this.txtRegFirstNameLayout = txtRegFirstNameLayout;
        this.txtRegLastNameLayout = txtRegLastNameLayout;
        this.txtRegEmailLayout = txtRegEmailLayout;
        this.txtRegPassLayout = txtRegPassLayout;
        this.txtRegConfirmLayout = txtRegConfirmLayout;
        this.registerBtn = registerBtn;
    }
    // END OF ALL ARGS CONSTRUCTOR.

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    // END OF BEFORE TEXT CHANGE.

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        txtRegPassLayout.setError(null);
        txtRegConfirmLayout.setError(null);
        String passwordField = txtRegPassword.getText().toString().trim();
        String confirmField     = txtRegConfirm.getText().toString().trim();

        if(passwordField.length() < 8){
            txtRegPassLayout.setError("Password must be 8 characters or more");
        }


        if(confirmField.length() < 8){
            txtRegPassLayout.setError("Password must be 8 characters or more");
        }
    }
    // END OF ON TEXT CHANGE METHOD.

    @Override
    public void afterTextChanged(Editable s) {
        String firstName    = txtRegFirstName.getText().toString().trim();
        String lastName     = txtRegLastName.getText().toString().trim();
        String email        = txtRegEmail.getText().toString().trim();
        String password     = txtRegPassword.getText().toString().trim();
        String confirm      = txtRegConfirm.getText().toString().trim();

        txtRegFirstNameLayout.setError(null);
        txtRegLastNameLayout.setError(null);
        txtRegEmailLayout.setError(null);
        txtRegPassLayout.setError(null);
        txtRegConfirmLayout.setError(null);

        if(firstName.isBlank() || firstName.isBlank()){
            txtRegFirstNameLayout.setError("First name cannot be empty!");
        }
        // END OF CHECK FIRST NAME FIELD IS NOT EMPTY.

        if(lastName.isBlank() || lastName.isBlank()){
            txtRegLastNameLayout.setError("Last name cannot be empty!");
        }
        // END OF CHECK LAST NAME FIELD IS NOT EMPTY.

        if(email.isBlank() || email.isBlank()){
            txtRegEmailLayout.setError("Email cannot be empty!");
        }
        // END OF CHECK EMAIL FIELD IS NOT EMPTY.

        if(!StringResourceHelper.regexEmailValidationPattern(email)){
            txtRegEmailLayout.setError("Enter a valid email address");
        }
        // END OF VALIDATE EMAIL ADDRESS EXPRESSION IF BLOCK

        if(password.isBlank() || password.isBlank()){
            txtRegPassLayout.setError("Password cannot be empty!");
        }
        // END OF CHECK PASSWORD FIELD IS NOT EMPTY.

        if(confirm.isBlank() || confirm.isBlank()){
            txtRegConfirmLayout.setError("Confirm cannot be empty!");
        }
        // END OF CHECK CONFIRM FIELD IS NOT EMPTY.

        if(!password.equals(confirm)){
            txtRegPassLayout.setError("Passwords do not match!");
            txtRegConfirmLayout.setError("Passwords do not match!");
            //registerBtn.setEnabled(false);
        }
        // END OF CHECK FIRST NAME FIELD IS NOT EMPTY.

        // SET VALID EMAIL ADDRESS BOOLEAN VALUE:
        boolean isEmailValid = StringResourceHelper.regexEmailValidationPattern(email);
        // RE-ENABLE BUTTON IF ALL FIELDS ARE FILLED AND PASSWORDS MATCH
        registerBtn.setEnabled(!firstName.isBlank() && !lastName.isBlank()
                && !email.isBlank() && isEmailValid && password.equals(confirm));

    }
    // END OF AFTER TEXT CHANGED METHOD.

}
// END OF REGISTRATION FORM VALIDATOR CLASS.
