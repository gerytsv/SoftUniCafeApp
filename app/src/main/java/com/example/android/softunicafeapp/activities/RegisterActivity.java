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

import com.example.android.softunicafeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // Reset errors.
        editTextUserEmail.setError(null);
        editTextUserPassword.setError(null);
        editTextUserSurName.setError(null);
        editTextUserName.setError(null);
        editTextUserPhone.setError(null);

        //setting errors
        //TO DO: when there are errors, make them appear without having to click on the edit text box
        if (TextUtils.isEmpty(email)) editTextUserEmail.setError(getString(error_field_required));
        if (TextUtils.isEmpty(password))
            editTextUserPassword.setError(getString(error_field_required));
        if (TextUtils.isEmpty(surName))
            editTextUserSurName.setError(getString(error_field_required));
        if (TextUtils.isEmpty(name)) editTextUserName.setError(getString(error_field_required));
        if (TextUtils.isEmpty(phone)) editTextUserPhone.setError(getString(error_field_required));
        if (!TextUtils.isEmpty(password) && password.length() < 4)
            editTextUserPassword.setError(getString(error_invalid_password));
        if (!isValidEmail(email)) editTextUserEmail.setError(getString(error_invalid_email));

        if (!TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(password) &&
                !TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(surName) &&
                !TextUtils.isEmpty(phone) &&
                password.length() > 4 &&
                isValidEmail(email)) {
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

                        Intent intent = new Intent(RegisterActivity.this, CategoriesActivity.class);
                        startActivity(intent);
                    }
                }
            });
            /*inserting the data in the database
            db.open();
            db.insert(name, surName, email, phone, password);
            db.close();
            Toast.makeText(this, "Account created", Toast.LENGTH_LONG).show(); */
        }
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

/*    public void view(View v) {

        String txt = db.get();
        Dialog d = new Dialog(RegisterActivity.this);
        d.setTitle("My Data");
        TextView tv = new TextView(RegisterActivity.this);
        tv.setText(txt);
        d.setContentView(tv);
        d.show();

    } */

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_textView) {
            Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intentLogin);
        }
    }
}




