package com.team11.personalfood;

import android.view.ViewGroup;


public class FoodListRecyclerAdapter extends AbstractRecyclerAdapter<Food> {

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FoodListViewHolder(parent);
    }
}
