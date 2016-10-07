package com.example.android.softunicafeapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.softunicafeapp.R;
import com.example.android.softunicafeapp.adapters.DBAdapter;

/**
 * A login screen that offers login via email and password.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    Button btnSignIn;
    TextView textViewRegistration;
    EditText editTextPassword;
    AutoCompleteTextView userEmail;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get the References of views
        userEmail = (AutoCompleteTextView) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        db = new DBAdapter(LoginActivity.this);

        // Get The Reference Of the Buttons
        btnSignIn = (Button) findViewById(R.id.sign_in_btn);
        textViewRegistration = (TextView) findViewById(R.id.register_textView);

        textViewRegistration.setOnClickListener(this);


    }

    public void onSignInClick(View v) {


        String email = userEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String storedPassword = db.getLoginPass(password);
        String storedEmail = db.getLoginEmail(email);
        if (!password.equals("") && !email.equals("")) {
            if (password.equals(storedPassword) && email.equals(storedEmail)) {
                //this intent loads the main(Categories') screen
                Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                intent.putExtra("Email", email); // idk for what?
                startActivity(intent);
                finish();
            } else
                Toast.makeText(LoginActivity.this, "Email or Password does not match", Toast.LENGTH_LONG).show();
        }
        if (email.equals("")) userEmail.setError("Insert email");
        if (password.equals("")) editTextPassword.setError("Insert password");

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.register_textView) {

            /// Create Intent for SignUpActivity and Start The Activity
            Intent intentSignUP = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intentSignUP);
            finish();
        }
    }

}
