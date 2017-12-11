package com.team11.personalfood.Models;

import com.team11.personalfood.Utilities.OnFoodLoadListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodModel {

    private List<Food> foodList = new ArrayList<>();
    private OnFoodLoadListener onFoodLoadListener;

    public FoodModel(ArrayList<HashMap<String,String>> mArrayList) {

    }

    public FoodModel(ArrayList<HashMap<String, String>> mArrayList, String tagFoodName, String tagFoodUrl, String tagNegativeIngredient, String tagPositiveIngredient) {
        for(HashMap hashMap : mArrayList) {
            foodList.add(new Food(hashMap.get(tagFoodName).toString(),
                    hashMap.get(tagFoodUrl).toString(),
                    hashMap.get(tagNegativeIngredient).toString(),
                    hashMap.get(tagPositiveIngredient).toString()));
        }
    }

    public void setOnFoodLoadListener(OnFoodLoadListener onFoodLoadListener){
        this.onFoodLoadListener = onFoodLoadListener;
    }


    public void fetchFood(){
        if(onFoodLoadListener != null){
            onFoodLoadListener.onFetchFood(foodList);
        }
    }
}
