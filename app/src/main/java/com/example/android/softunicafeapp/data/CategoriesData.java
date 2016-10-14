package com.example.android.softunicafeapp.data;


import com.example.android.softunicafeapp.R;

import java.util.ArrayList;
import java.util.List;

public class CategoriesData {

    private static final int[] images = {
            R.drawable.coffee,
            R.drawable.sandwich,
            R.drawable.baguette
    };
    private static final String[] titles = {
            "Hot drinks",
            "Sandwiches",
            "Baguettes"
    };
    private static final String[] subTitles = {
            "Yes, coffee!",
            "Warm me up for Code!",
            "Hot n cheesy"
    };

    public static List<ListItem> getListData() {
        List<ListItem> data = new ArrayList<>();
        //images[0].setImageResource(R.drawable.categorycoffee);

        //Repeat process 2 times, so that we have enough data to demonstrate a scrollable RecyclerView
        //for (int x = 0; x < 2; x++) {
            //create ListItem with dummy data, then add them to our List
            for (int i = 0; i < titles.length; i++) {
                ListItem item = new ListItem();
                item.setTitle(titles[i]);
                item.setSubTitle(subTitles[i]);
                item.setImageResId(images[i]);
                data.add(item);
            }

        return data;
    }
}
