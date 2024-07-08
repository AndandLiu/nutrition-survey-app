package com.jla388.sfu.greenfoodchallenge;

/**
 * Class that stores userData
 */
public class UserInfo {
    private String userKey;
    private String userName;
    //private symbol
    private double previousDayFoodData;
    private double firstDayFoodData;
    private String municipalityOfUser;
    private int pledgedAmountOfSavings;
    private int totalDaysRecorded;
    private double totalSavings;
    private String userGender;


    public UserInfo (String name, double previousDayFood, String municipality, int pledgedAmount, int totalDays, double totalCo2Saved, String gender){
        userName = name;
        previousDayFoodData = previousDayFood;
        municipalityOfUser = municipality;
        pledgedAmountOfSavings = pledgedAmount;
        totalDaysRecorded = totalDays;
        totalSavings = totalCo2Saved;
        userGender = gender;
    }

    public UserInfo(){
        userKey = "none";
        userName = "none";
        previousDayFoodData = 0;
        municipalityOfUser = "none";
        pledgedAmountOfSavings = 0;
        totalDaysRecorded = 0;
        totalSavings = 0;
        userGender = "none";
    }


    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String Key) {
        userKey = Key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        userName = name;
    }

    public double getPreviousDayFoodData() {
        return previousDayFoodData;
    }

    public void setPreviousDayFoodData(double previousDayFoodData) {
        this.previousDayFoodData = previousDayFoodData;
    }

    public double getFirstDayFoodData() {
        return firstDayFoodData;
    }

    public void setFirstDayFoodData(double firstDayFoodData) {
        this.firstDayFoodData = firstDayFoodData;
    }

    public String getMunicipalityOfUser() {
        return municipalityOfUser;
    }

    public void setMunicipalityOfUser(String municipalityOfUser) {
        this.municipalityOfUser = municipalityOfUser;
    }

    public int getPledgedAmountOfSavings() {
        return pledgedAmountOfSavings;
    }

    public void setPledgedAmountOfSavings(int pledgedAmountOfSavings) {
        this.pledgedAmountOfSavings = pledgedAmountOfSavings;
    }

    public int getTotalDaysRecorded() {
        return totalDaysRecorded;
    }

    public void setTotalDaysRecorded(int totalDaysRecorded) {
        this.totalDaysRecorded = totalDaysRecorded;
    }

    public double getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(double savings) {
        totalSavings = savings;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String gender) {
        userGender = gender;
    }


}
