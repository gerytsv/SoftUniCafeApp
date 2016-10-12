package com.example.android.softunicafeapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.softunicafeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.android.softunicafeapp.R.string.error_field_required;
import static com.example.android.softunicafeapp.R.string.error_invalid_email;
import static com.example.android.softunicafeapp.R.string.error_invalid_password;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextUserName, editTextUserSurName, editTextUserPhone, editTextUserEmail, editTextUserPassword;
    Button btnRegister;
    TextView loginTextView;
    Context context = this;
    //DBAdapter db;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //db = new DBAdapter(RegisterActivity.this);

        mAuth = FirebaseAuth.getInstance();

        mProgress = new ProgressDialog(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        editTextUserName = (EditText) findViewById(R.id.editText_name);
        editTextUserSurName = (EditText) findViewById(R.id.editText_surName);
        editTextUserPhone = (EditText) findViewById(R.id.editText_phone);
        editTextUserEmail = (EditText) findViewById(R.id.editText_email);
        editTextUserPassword = (EditText) findViewById(R.id.editText_password);
        btnRegister = (Button) findViewById(R.id.btn_register);

        loginTextView = (TextView) findViewById(R.id.login_textView);
        loginTextView.setOnClickListener(this);

    }

    public void onSignUpClick(View v) {

        final String name = editTextUserName.getText().toString();
        final String surName = editTextUserSurName.getText().toString();
        final String phone = editTextUserPhone.getText().toString();
        String email = editTextUserEmail.getText().toString();
        String password = editTextUserPassword.getText().toString();

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        /* Reset errors.
        editTextUserEmail.setError(null);
        editTextUserPassword.setError(null);
        editTextUserSurName.setError(null);
        editTextUserName.setError(null);
        editTextUserPhone.setError(null); */

        //setting errors
        if (TextUtils.isEmpty(email)) editTextUserEmail.setError(getString(error_field_required));
        else editTextUserEmail.setError(null);
        if (TextUtils.isEmpty(password))
            editTextUserPassword.setError(getString(error_field_required));
        else editTextUserPassword.setError(null);
        if (TextUtils.isEmpty(surName))
            editTextUserSurName.setError(getString(error_field_required));
        else editTextUserSurName.setError(null);
        if (TextUtils.isEmpty(name)) editTextUserName.setError(getString(error_field_required));
        else editTextUserName.setError(null);
        if (TextUtils.isEmpty(phone)) editTextUserPhone.setError(getString(error_field_required));
        else editTextUserPhone.setError(null);
        if (!TextUtils.isEmpty(password) && password.length() < 4)
            editTextUserPassword.setError(getString(error_invalid_password));
        else editTextUserPassword.setError(null);
        //if (!isValidEmail(email) && !TextUtils.isEmpty(email)) editTextUserEmail.setError(getString(error_invalid_email));

        if (!email.matches(emailPattern) && !TextUtils.isEmpty(email))
            editTextUserEmail.setError(getString(error_invalid_email));
        else editTextUserEmail.setError(null);


        if (!TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(password) &&
                !TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(surName) &&
                !TextUtils.isEmpty(phone) &&
                password.length() > 4 &&
                email.matches(emailPattern)) { //isValidEmail(email)
            mProgress.setMessage("Signing Up ...");
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDatabase.child(user_id);
                        current_user_db.child("name").setValue(name);
                        current_user_db.child("surname").setValue(surName);
                        current_user_db.child("phone").setValue(phone);

                        mProgress.dismiss();
                        Toast.makeText(RegisterActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, CategoriesActivity.class);
                        startActivity(intent);
                    }
                }
            });

        }
    }

    /* validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
*/

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_textView) {
            Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intentLogin);
        }
    }
}




