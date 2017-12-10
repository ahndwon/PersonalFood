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

    public FoodModel(ArrayList<HashMap<String, String>> mArrayList, String tagFoodName, String tagPositiveIngredient, String tagNegativeIngredient1) {
        for(HashMap hashMap : mArrayList){
            foodList.add(new Food(hashMap.get(tagFoodName).toString(),hashMap.get(tagPositiveIngredient).toString(),
                    hashMap.get(tagNegativeIngredient1).toString()));
        }
    }

    public void setOnFoodLoadListener(OnFoodLoadListener onFoodLoadListener){
        this.onFoodLoadListener = onFoodLoadListener;
    }

    public FoodModel(){
        init();
    }

    public FoodModel(List<Food> foodList) {
        this.foodList = foodList;
    };


    private void init(){
        foodList.add(new Food("김치말이국수", "김치", "김치"));
        foodList.add(new Food("김치말이국수", "김치", "김치"));
        foodList.add(new Food("김치말이국수", "김치", "김치"));
        foodList.add(new Food("김치말이국수", "김치", "김치"));
        foodList.add(new Food("짬뽕", "밀가루", "밀가루"));
        foodList.add(new Food("떡볶이", "밀가루", "밀가루"));
        foodList.add(new Food("아이스크림", "우유", "초콜릿"));
        foodList.add(new Food("김치", "김치", "김치"));


    }

    public void fetchFood(){
        if(onFoodLoadListener != null){
            onFoodLoadListener.onFetchFood(foodList);
        }
    }
}
