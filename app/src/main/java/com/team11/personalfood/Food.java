package com.team11.personalfood;

public class Food {
    private String foodName;
    private String negativeIngredient;
    private String positiveIngredient;

    public Food(String foodName, String negativeIngredient, String positiveIngerdient) {
        this.foodName = foodName;
        this.negativeIngredient = negativeIngredient;
        this.positiveIngredient = positiveIngerdient;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getNegativeIngredient() {
        return negativeIngredient;
    }

    public String getPositiveIngredient() {
        return positiveIngredient;
    }
    //    public String getIngredient() {
//        return ingredient;
//    }


    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setNegativeIngredient(String negativeIngredient) {
        this.negativeIngredient = negativeIngredient;
    }

    public void setPositiveIngredient(String positiveIngredient) {
        this.positiveIngredient = positiveIngredient;
    }

    //    public void setIngredient(String ingredient) {
//        this.ingredient = ingredient;
//    }
}
