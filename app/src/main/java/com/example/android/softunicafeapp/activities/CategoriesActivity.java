package com.example.android.softunicafeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.example.android.softunicafeapp.R;
import com.example.android.softunicafeapp.adapters.CategoriesAdapter;
import com.example.android.softunicafeapp.data.CategoriesData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CategoriesActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recView;
    private CategoriesAdapter adapter;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(CategoriesActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders");
        mDatabase.keepSynced(true);

        recView = (RecyclerView) findViewById(R.id.recycler_list);
        recView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CategoriesAdapter(CategoriesData.getListData(), this);
        recView.setAdapter(adapter);

        FloatingActionButton ordersFab = (FloatingActionButton) findViewById(R.id.orders_fab);
        ordersFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriesActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
