package com.example.android.softunicafeapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.softunicafeapp.R;
import com.example.android.softunicafeapp.adapters.DBAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.android.softunicafeapp.R.id.email;

/**
 * A login screen that offers login via email and password.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    Button btnSignIn;
    TextView textViewRegistration;
    EditText editTextPassword;
    AutoCompleteTextView userEmail;
    DBAdapter db;
    ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //check whether the user is logged in
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                    startActivity(intent);
                }
            }
        };

        // get the References of views
        userEmail = (AutoCompleteTextView) findViewById(email);
        editTextPassword = (EditText) findViewById(R.id.password);

        //db = new DBAdapter(LoginActivity.this);

        // Get The Reference Of the Buttons
        btnSignIn = (Button) findViewById(R.id.sign_in_btn);
        textViewRegistration = (TextView) findViewById(R.id.register_textView);

        textViewRegistration.setOnClickListener(this);
        //setting custom font
        //Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/hotpizza.ttf");
        //btnSignIn.setTypeface(myCustomFont);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onSignInClick(View v) {
        String email = userEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        mProgressDialog.setMessage("Signing in...");
        mProgressDialog.show();

        //new
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Field vacant!", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in problem", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    //new code ends here

    /**

        String errorMsgEmail = "Please, insert your email first.";
        String errorMsgPass = "Please, insert your password first.";
        // Reset errors.
        userEmail.setError(null);
        editTextPassword.setError(null);

        if (!db.getLoginEmail(email).equals(errorMsgEmail) && !db.getLoginPass(password).equals(errorMsgPass)) {
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
        }
        if (db.getLoginEmail(email).equals(errorMsgEmail)) userEmail.setError(errorMsgEmail);
        if (db.getLoginPass(password).equals(errorMsgPass)) editTextPassword.setError(errorMsgPass);
        if (email.equals("")) userEmail.setError("Insert email");
        if (password.equals("")) editTextPassword.setError("Insert password");

    }

     **/
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

