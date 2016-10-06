package com.example.android.softunicafeapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    DBAdapter DBAdapter;
    EditText editTextEmail;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get the References of views
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        // create a instance of SQLite Database
        DBAdapter = new DBAdapter(this);

        // Get The Reference Of the Buttons
        btnSignIn = (Button) findViewById(R.id.sign_in_btn);
        textViewRegistration = (TextView) findViewById(R.id.register_textView);

        textViewRegistration.setOnClickListener(this);


    }

    public void onSignInClick(View v) {

            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String storedPassword = DBAdapter.getPass(email);

            if (password.equals(storedPassword)) {
                //this intent loads the main(Categories') screen
                Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                intent.putExtra("Email", email);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Email or Password does not match", Toast.LENGTH_LONG).show();
            }

        }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.register_textView) {

            /// Create Intent for SignUpActivity and Start The Activity
            Intent intentSignUP = new Intent(this, RegisterActivity.class);
            startActivity(intentSignUP);
        }
    }
}


    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        DBAdapter.close();
    }*/







/*
    *//**
     * Id to identity READ_CONTACTS permission request.
 *//*
    private static final int REQUEST_READ_CONTACTS = 0;
    private User myUser;


 * private static final String[] DUMMY_CREDENTIALS = new String[]{
 * "foo@example.com:hello", "bar@example.com:world"
 * };
 * <p>
     * Keep track of the login task to ensure we can cancel it if requested.
 *//*
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mRegisterLink;
    Button mLoginBtn;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ctx = this;
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mRegisterLink = (TextView) findViewById(R.id.register_textView);

        mRegisterLink.setOnClickListener(new View.OnClickListener() {

        @Override
         public void onClick(View view) {
           Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
           LoginActivity.this.startActivity(registerIntent);
         }
        }
        );
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, null); //third atg was 'this'
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    *//**
     * Callback received when a permissions request has been completed.
 *//*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    *//**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
 *//*
    private void attemptLogin() {

        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        mAuthTask = new UserLoginTask(email, password, this);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password, this);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    *//**
     * Shows the progress UI and hides the login form.
 *//*
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

  //  @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

   // @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

   // @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    *//**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
 *//*
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final Context mContext;

        UserLoginTask(String email, String password, Context context) {
            mEmail = email;
            mPassword = password;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            DBTools dbTools = null;
            try{

               dbTools = new DBTools(mContext);
               myUser = dbTools.getUser(mEmail);

                if (myUser.userId > 0) {
                    // Account exists, check password.
                    if (myUser.password.equals(mPassword))
                        return true;
                    else
                        return false;
                } else {
                    myUser.password = mPassword;
                    return true;
                }
            } finally{
                if (dbTools != null)
                    dbTools.close();
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                if (myUser.userId > 0){
                    finish();
                    Intent myIntent = new Intent(LoginActivity.this,CategoriesActivity.class);
                    LoginActivity.this.startActivity(myIntent);
                } else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    DBTools dbTools = null;
                                    try{
                                        finish();
                                        dbTools = new DBTools(mContext);
                                        myUser = dbTools.insertUser(myUser);
                                        Toast myToast = Toast.makeText(mContext, "updating Report", Toast.LENGTH_SHORT);
                                        myToast.show();
                                        Intent myIntent = new Intent(LoginActivity.this, CategoriesActivity.class);
                                        LoginActivity.this.startActivity(myIntent);
                                    } finally{
                                        if (dbTools != null)
                                            dbTools.close();
                                    }
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    mPasswordView.setError(getString(R.string.error_try_again));
                                    mPasswordView.requestFocus();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
                    builder.setMessage(R.string.confirm_registry).setPositiveButton(R.string.yes, dialogClickListener)
                            .setNegativeButton(R.string.no, dialogClickListener).show();
                }
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}*/

