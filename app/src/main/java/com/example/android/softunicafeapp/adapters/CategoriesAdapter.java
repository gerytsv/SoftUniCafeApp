package com.example.android.softunicafeapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.softunicafeapp.R;
import com.example.android.softunicafeapp.data.ListItem;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder> {

    private List<ListItem> listData;
    private LayoutInflater inflater;

    public CategoriesAdapter(List<ListItem> listData, Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public CategoriesAdapter.CategoriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.categories_item, parent, false);
        return new CategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoriesAdapter.CategoriesHolder holder, int position) {
        ListItem item = listData.get(position);
        holder.title.setText(item.getTitle());
        holder.image.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class CategoriesHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView image;
        private View container;

        public CategoriesHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.item_text);
            image = (ImageView) itemView.findViewById(R.id.item_image);
            container = itemView.findViewById(R.id.item_container);
        }
    }
}

