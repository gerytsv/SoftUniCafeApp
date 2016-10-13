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
    private ClickListener clickListener;
    //private ItemClickCallback itemClickCallback;
    private Context context;

    public CategoriesAdapter(List<ListItem> listData, Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
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
        holder.subTitle.setText(item.getSubTitle());
        holder.image.setImageResource(item.getImageResId());

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);

    }

    class CategoriesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView subTitle;
        private ImageView image;
        private View container;

        public CategoriesHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.item_text_title);
            subTitle = (TextView) itemView.findViewById(R.id.item_text_subTitle);
            image = (ImageView) itemView.findViewById(R.id.item_image);
            container = itemView.findViewById(R.id.item_container);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getAdapterPosition()); // NOT SURE. it should be getPosition but method is deprecated
            }
        }
    }

}

