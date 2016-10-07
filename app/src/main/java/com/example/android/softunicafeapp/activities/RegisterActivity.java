package com.example.android.softunicafeapp.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.softunicafeapp.R;
import com.example.android.softunicafeapp.adapters.DBAdapter;

import static com.example.android.softunicafeapp.R.string.error_field_required;
import static com.example.android.softunicafeapp.R.string.error_invalid_email;
import static com.example.android.softunicafeapp.R.string.error_invalid_password;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextUserName, editTextUserSurName, editTextUserPhone, editTextUserEmail, editTextUserPassword;
    Button btnRegister;
    TextView loginTextView;
    Context context = this;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DBAdapter(RegisterActivity.this);

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

        String name = editTextUserName.getText().toString();
        String surName = editTextUserSurName.getText().toString();
        String email = editTextUserEmail.getText().toString();
        String phone = editTextUserPhone.getText().toString();
        String password = editTextUserPassword.getText().toString();

        // Reset errors.
        editTextUserEmail.setError(null);
        editTextUserPassword.setError(null);
        editTextUserSurName.setError(null);
        editTextUserName.setError(null);
        //setting errors
        if (!email.contains("@")) editTextUserEmail.setError(getString(error_invalid_email));
        //if to check if the phone number is more than or less that 10 symbols
        //if(!phone.length(10)) editTextUserPhone.setError(getString(error_invalid_phone)); --> Doesnt happen!
        if (email.equals("")) editTextUserEmail.setError(getString(error_field_required));
        if (password.equals("")) editTextUserPassword.setError(getString(error_field_required));
        if (surName.equals("")) editTextUserSurName.setError(getString(error_field_required));
        if (name.equals("")) editTextUserName.setError(getString(error_field_required));

        if (password.length() < 4) {
            editTextUserPassword.setError(getString(error_invalid_password));
        } else {
            //inserting the data in the database
            db.open();
            db.insert(name, surName, email, phone, password);
            db.close();
            Toast.makeText(this, "Account created", Toast.LENGTH_LONG).show();
        }
    }

    public void view(View v) {

        String txt = db.get();
        Dialog d = new Dialog(RegisterActivity.this);
        d.setTitle("My Data");
        TextView tv = new TextView(RegisterActivity.this);
        tv.setText(txt);
        d.setContentView(tv);
        d.show();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_textView) {
            Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intentLogin);
        }
    }
}




