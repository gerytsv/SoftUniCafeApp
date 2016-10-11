package com.example.android.softunicafeapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.softunicafeapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.android.softunicafeapp.R.id.order_btn;

public class UserDataActivity extends AppCompatActivity {

    private EditText mName, mPhone, mNote;
    private Button mOrderBtn;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders");

        mName = (EditText) findViewById(R.id.editText_name);
        mPhone = (EditText) findViewById(R.id.editText_phone);
        mNote = (EditText) findViewById(R.id.editText_note);
        mOrderBtn = (Button) findViewById(order_btn);

        mProgress = new ProgressDialog(this);

        mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPosting();

            }
        });

    }

    private void startPosting() {


        String userName = mName.getText().toString().trim();
        String userPhone = mPhone.getText().toString().trim();
        String userNote = mNote.getText().toString().trim();

        mName.setError("");
        mPhone.setError("");

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPhone)) {
            StorageReference filePath = mStorage.child("Orders");   //RECYCLER VIEW should connect somewhere here
            DatabaseReference allOrders = mDatabase.push();

            mProgress.setMessage("Loading...");
            mProgress.show();

            allOrders.child("Name").setValue(userName);
            allOrders.child("Phone").setValue(userPhone);
            if (!userNote.equals("")) {
                allOrders.child("Note").setValue(userNote);
            }
            // here we add the recycler view info a.k.a. the names of the items the user selected to purchase
            mProgress.dismiss();
            Toast.makeText(this, "Order sent.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UserDataActivity.this, CategoriesActivity.class));
        } else {
            if (TextUtils.isEmpty(userName)) mName.setError("Please, complete this field!");
            if (TextUtils.isEmpty(userPhone)) mPhone.setError("Please, complete this field!");
        }
    }
}


