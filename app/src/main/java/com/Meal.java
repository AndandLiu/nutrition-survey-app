package com.jla388.sfu.greenfoodchallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Meal {
    private String mealName;
    private String mealProtein;
    private Double mealProteinAmount;
    private String nameOfRestaurant;
    private String restaurantLocation;
    private String pictureOfMeal;
    private String mealDescription;
    private double curr_longitude;


    private double curr_laltitude;

    private List<String> pictureAndDescription;
    public static final int URL  = 0;
//    ////LET 0 contain the url of the image
//    ////LET 1 be the description of the picture


    public Meal() {
        this.mealName = "none";
        this.mealProtein = "none";
        this.mealProteinAmount = 0.0;
        this.nameOfRestaurant = "none";
        this.restaurantLocation = "none";
        this.pictureOfMeal = "none";
        this.mealDescription = "none";
        this.curr_laltitude=-100;
        this.curr_longitude=-190;
      //  this.pictureOfMeal = "none";
      //  this.mealDescription = "none";
        pictureAndDescription = new ArrayList<>(1);
        pictureAndDescription.add(mealName);

    }

    public Meal(String nameOfMeal, String nameOfProtein, Double proteinAmount, String restaurant, String location, String picture, String description){
        this.mealName = nameOfMeal;
        this.mealProtein = nameOfProtein;
        this.mealProteinAmount = proteinAmount;
        this.nameOfRestaurant = restaurant;
        this.restaurantLocation = location;
        this.pictureOfMeal = picture;
        this.mealDescription = description;

        pictureAndDescription = new ArrayList<>(1);
        pictureAndDescription.add(mealName);

        this.curr_laltitude=-100;
        this.curr_longitude=-190;
    }

    public double getCurr_longitude() {
        return curr_longitude;
    }

    public void setCurr_longitude(double curr_longitude) {
        this.curr_longitude = curr_longitude;
    }

    public double getCurr_laltitude() {
        return curr_laltitude;
    }

    public void setCurr_laltitude(double curr_laltitude) {
        this.curr_laltitude = curr_laltitude;
    }


    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealProtein() {
        return mealProtein;
    }

    public void setMealProtein(String mealProtein) {
        this.mealProtein = mealProtein;
    }

    public String getNameOfRestaurant() {
        return nameOfRestaurant;
    }

    public void setNameOfRestaurant(String nameOfRestaurant) {
        this.nameOfRestaurant = nameOfRestaurant;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public Double getMealProteinAmount() {
        return mealProteinAmount;
    }

    public void setMealProteinAmount(Double mealProteinAmount) {
        this.mealProteinAmount = mealProteinAmount;
    }

    public String getPictureOfMeal() {
        return pictureOfMeal;
    }

    public void setPictureOfMeal(String pictureOfMeal) {
        this.pictureAndDescription.set(0,pictureOfMeal);
        this.pictureOfMeal = pictureOfMeal;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }

   public List<String> getPictureAndDescription(){
        return pictureAndDescription;
    }

}
