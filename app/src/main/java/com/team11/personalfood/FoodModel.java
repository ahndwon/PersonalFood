package com.team11.personalfood;

import java.util.ArrayList;
import java.util.List;

public class FoodModel {

    private List<Food> foodList = new ArrayList<>();
    private OnFoodLoadListener onFoodLoadListener;

    public void setOnFoodLoadListener(OnFoodLoadListener onFoodLoadListener){
        this.onFoodLoadListener = onFoodLoadListener;
    }

    public FoodModel(){
        init();
    }

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
