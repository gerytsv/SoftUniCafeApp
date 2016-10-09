package com.example.android.softunicafeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.softunicafeapp.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductsActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        // setting the reference(url) to the d
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cafe-22cef.firebaseio.com/");

        final FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(
                this,
                String.class,
                android.R.layout.simple_list_item_1,
                databaseReference) {
            @Override
            protected void populateView(View v, String model, int position) {

                TextView textView = (TextView) findViewById(android.R.id.text1);
                textView.setText(model);
            }
        };

        // mListView.setAdapter(firebaseListAdapter); gonna be doing this later
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_products, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_orders) {
            startActivity(new Intent(ProductsActivity.this, OrdersActivity.class));
        }
        if (item.getItemId() == R.id.action_settings) {
            // Do something here
        }

        return super.onOptionsItemSelected(item);
    }
}
