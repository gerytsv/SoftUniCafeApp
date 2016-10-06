package com.example.android.softunicafeapp.activities;

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
import com.example.android.softunicafeapp.data.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextUserName, editTextUserSurName, editTextUserPhone, editTextUserEmail, editTextUserPassword;
    DBAdapter DBAdapter;
    Button btnRegister;
    TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // getting Instance of the database adapter
        DBAdapter = new DBAdapter(this);
        DBAdapter = DBAdapter.open();

        editTextUserName = (EditText) findViewById(R.id.editText_name);
        editTextUserSurName = (EditText) findViewById(R.id.editText_surName);
        editTextUserPhone = (EditText) findViewById(R.id.editText_phone);
        editTextUserEmail = (EditText) findViewById(R.id.editText_email);
        editTextUserPassword = (EditText) findViewById(R.id.editText_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
        textViewLogin = (TextView) findViewById(R.id.login_textView);

        textViewLogin.setOnClickListener(this);

    }

    public void onSignUpClick(View v) {

            String name = editTextUserName.getText().toString();
            String surName = editTextUserSurName.getText().toString();
            String email = editTextUserEmail.getText().toString();
            String phone = editTextUserPhone.getText().toString();
            String password = editTextUserPassword.getText().toString();

            if (email.equals("") || password.equals("") || surName.equals("")) {
                Toast.makeText(this, "Field Vaccant", Toast.LENGTH_LONG).show();
            } else {
                //inserting the data in the database
                User user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setSurName(surName);
                user.setPhone(phone);
                user.setPassword(password);

                DBAdapter.insertEntry(user);
            }
        }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_textView){
            /// Create Intent for SignUpActivity and Start The Activity
            Intent intentSignUP = new Intent(this, LoginActivity.class);
            startActivity(intentSignUP);
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        DBAdapter.close();
    }
}




