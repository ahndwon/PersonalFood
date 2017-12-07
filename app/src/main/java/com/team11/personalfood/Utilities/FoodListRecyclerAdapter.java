package com.team11.personalfood.Utilities;

import android.view.ViewGroup;

import com.team11.personalfood.Models.Food;


public class FoodListRecyclerAdapter extends AbstractRecyclerAdapter<Food> {

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FoodListViewHolder(parent);
    }
}
