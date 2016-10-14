package com.example.android.softunicafeapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.softunicafeapp.R;
import com.example.android.softunicafeapp.adapters.CategoriesAdapter;
import com.example.android.softunicafeapp.data.CategoriesData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements CategoriesAdapter.ClickListener {

    public static int pos;
    private RecyclerView recView;
    private CategoriesAdapter adapter;
    private CategoriesAdapter.ClickListener clickListener;
    private ArrayList listData;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(Color.TRANSPARENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");


        // because the index of the categories starts at zero
        // so category one will be zero and the pos shouldn't be considered to point at category one for default
        pos = -1;

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        collapsingToolbarLayout.setTitle("");
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#00000000")); // transparent color = #00000000
        collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#00000000"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0)); //Color of your title

        listData = (ArrayList) CategoriesData.getListData();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(CategoriesActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Auth state changed.", Toast.LENGTH_SHORT).show();
                }
            }
        };


        //mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        //mDatabase.keepSynced(true);
        mDatabaseUsers.keepSynced(true);

        recView = (RecyclerView) findViewById(R.id.recycler_list);
        recView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CategoriesAdapter(listData, this);
        recView.setAdapter(adapter);
        adapter.setClickListener(this);

     /*   FloatingActionButton ordersFab = (FloatingActionButton) findViewById(R.id.orders_fab);
        ordersFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriesActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });
    */
        checkIfUserExists();

    }

    private void checkIfUserExists() {
        if (mAuth.getCurrentUser() != null) {
            final String user_id = mAuth.getCurrentUser().getUid();
            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.hasChild(user_id)) {

                        Intent intent = new Intent(CategoriesActivity.this, RegisterActivity.class);
                        startActivity(intent);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        pos = -1;
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent setupIntent = new Intent(getApplicationContext(), CategoriesActivity.class);
        startActivity(setupIntent);
        pos = -1;
    }


    @Override
    public void itemClicked(View view, int position) {


        if (position == 0) {
            startActivity(new Intent(this, ProductsActivity.class));
            pos = 0;
        }
        if (position == 1) {
            startActivity(new Intent(this, ProductsActivity.class));
            pos = 1;
        }
        if (position == 2) {
            startActivity(new Intent(this, ProductsActivity.class));
            pos = 2;
        }

        //else Toast.makeText(this, "Still doesn't have activity", Toast.LENGTH_SHORT).show();

    }
}
