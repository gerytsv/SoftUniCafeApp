package com.example.android.softunicafeapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.softunicafeapp.R;
import com.example.android.softunicafeapp.data.ProductsData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductsActivity extends AppCompatActivity {

    private ListView mListView;
    private RecyclerView mProductsList;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Products");

        mProductsList = (RecyclerView) findViewById(R.id.recyclerView_products);
        mProductsList.setHasFixedSize(true);
        mProductsList.setLayoutManager(new LinearLayoutManager(this));

        // mListView.setAdapter(firebaseListAdapter); gonna be doing this later
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ProductsData, ProductsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ProductsData, ProductsViewHolder>(
                ProductsData.class,
                R.layout.item_products,
                ProductsViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(ProductsViewHolder viewHolder, ProductsData model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(), model.getImage());
            }
        };

        mProductsList.setAdapter(firebaseRecyclerAdapter);
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


    public static class ProductsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ProductsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setTitle(String title) {
            TextView productTitle = (TextView) mView.findViewById(R.id.product_title);
            productTitle.setText(title);
        }

        public void setDesc(String desc) {
            TextView productDesc = (TextView) mView.findViewById(R.id.product_description);
            productDesc.setText(desc);
        }

        public void setImage(Context ctx, String image) {
            ImageView productImage = (ImageView) mView.findViewById(R.id.product_image);
            Picasso.with(ctx).load(image).into(productImage);
        }


    }


}

