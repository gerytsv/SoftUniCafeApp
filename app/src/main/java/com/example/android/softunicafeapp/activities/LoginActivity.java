package com.example.android.softunicafeapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.softunicafeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.android.softunicafeapp.R.id.email;

//import com.example.android.softunicafeapp.adapters.DBAdapter;

/**
 * A login screen that offers login via email and password.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    Button btnSignIn;
    TextView textViewRegistration;
    EditText mLoginPassword;
    AutoCompleteTextView mLoginEmail;
    //DBAdapter db;
    ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();


        // get the References of views
        mLoginEmail = (AutoCompleteTextView) findViewById(email);
        mLoginPassword = (EditText) findViewById(R.id.password);

        //db = new DBAdapter(LoginActivity.this);

        // Get The Reference Of the Buttons
        btnSignIn = (Button) findViewById(R.id.sign_in_btn);
        textViewRegistration = (TextView) findViewById(R.id.register_textView);

        textViewRegistration.setOnClickListener(this);
        //setting custom font
        //Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/hotpizza.ttf");
        //btnSignIn.setTypeface(myCustomFont);

    }

    public void onSignInClick(View v) {
        String email = mLoginEmail.getText().toString();
        String password = mLoginPassword.getText().toString();

        //mProgressDialog.setMessage("Signing in...");
        //mProgressDialog.show();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Field vacant!", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkIfUserExists();
                        //mProgressDialog.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void checkIfUserExists() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(user_id)) {

                    Intent intent = new Intent(LoginActivity.this, CategoriesActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Please, setup yoyr account.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_logout) {
            mAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     @Override public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.menu_main, menu);
     return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {

    if(item.getItemId() == R.id.action_logout){
    mAuth.signOut();
    }
    return super.onOptionsItemSelected(item);
    } */

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }


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

