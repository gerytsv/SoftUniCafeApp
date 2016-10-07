package com.example.android.softunicafeapp.data;


import java.util.ArrayList;
import java.util.List;

public class CategoriesData {

    private static final int[] images = {android.R.drawable.ic_popup_reminder, android.R.drawable.ic_menu_add,
            android.R.drawable.ic_menu_delete}; //TO DO: change these pics with the real ones for the categories
    private static String[] categoriesTitles = {
            "category 1",
            "category 2",
            "category 3",
            "category 4",
    };

    public static List<ListItem> getListData() {
        List<ListItem> data = new ArrayList<>();

        //Repeat process 4 times, so that we have enough data
        for (int x = 0; x < 4; x++) {
            //create ListItem with data, then add it to our List
            for (int i = 0; i < categoriesTitles.length && i < images.length; i++) {
                ListItem item = new ListItem();
                item.setImageResId(images[i]);
                item.setTitle(categoriesTitles[i]);
                data.add(item);
            }
        }
        return data;
    }

}
