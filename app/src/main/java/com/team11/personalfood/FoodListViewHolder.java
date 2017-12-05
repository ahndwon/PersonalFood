package com.team11.personalfood;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


class FoodListViewHolder extends AbstractViewHolder<Food> {

    private TextView foodName;
    private TextView negativeIngredient;
    private TextView positiveIngredient;
    private ImageView foodImage;

    public FoodListViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false));

        foodName = itemView.findViewById(R.id.foodName_textView);
        negativeIngredient = itemView.findViewById(R.id.negativeIngredient_textView);
        positiveIngredient = itemView.findViewById(R.id.positiveIngredient_textView);
        foodImage = itemView.findViewById(R.id.food_imageView);
    }

    @Override
    public void onBindView(@NonNull Food item, int position) {
        foodName.setText(item.getFoodName());
        negativeIngredient.setText(item.getNegativeIngredient());
        positiveIngredient.setText(item.getPositiveIngredient());

//        StringBuilder buses = new StringBuilder();
//        for(Bus bus : item.getBusList()) {
//            buses.append(bus.getNumber()).append(" ");
//        }
//        busList.setText(buses.toString());
    }
}
