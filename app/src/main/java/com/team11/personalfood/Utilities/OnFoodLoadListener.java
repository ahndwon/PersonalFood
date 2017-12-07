package com.team11.personalfood.Utilities;


import com.team11.personalfood.Models.Food;

import java.util.List;

public interface OnFoodLoadListener {
    void onFetchFood(List<Food> foodList);
}
