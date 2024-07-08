package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jla388.sfu.greenfoodchallenge.Foods;
import com.jla388.sfu.greenfoodchallenge.R;

/**
 * Result activity that shows the final results of your improved diet
 * and what would happen if entire city adopted it
 */
public class ResultActivity extends AppCompatActivity {


    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    public static final String EXTRA_FOOD_AMOUNT_LIST = "foodItemsListWithAmount";
    Foods[] foodResultList = new Foods[7];
    public static double totalCo2eFromUserInput = 0;
    final double avgCanadianFoodFootPrintPerYear = 1360.78;//kg//1.5tons
    private double lastDayFoodData;
    final double co2eSavedByEachTreeAnnually = 22;//kg
    private Button improve_diet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        lastDayFoodData = WelcomeActivity.loggedInUser.getPreviousDayFoodData();
        extractIntentData();
        getTotalCo2eSavedByUserAnnually();
        presentCitySavings();

    }

    private void getTotalCo2eSavedByUserAnnually() {

        for (int foodItem = 0; foodItem < foodResultList.length; foodItem++) {
            double gramToKilo = (foodResultList[foodItem].getAmount_input())/1000;
            double co2ePerKiloForTheFoodItem = foodResultList[foodItem].getCO2ePerKilo();
            totalCo2eFromUserInput += (gramToKilo * co2ePerKiloForTheFoodItem);//in kg

        }
        //Storing updated info in the db
        double updatedTotalCo2e = totalCo2eFromUserInput + WelcomeActivity.loggedInUser.getTotalSavings();
        Log.d("Test", String.valueOf(updatedTotalCo2e));
        int updatedTotalDaysRecorded = WelcomeActivity.loggedInUser.getTotalDaysRecorded() + 1;
        Log.d("Test", String.valueOf(updatedTotalDaysRecorded));

        databaseReference.child(user.getUid()).child("previousDayFoodData").setValue(totalCo2eFromUserInput);
        databaseReference.child(user.getUid()).child("totalSavings").setValue(updatedTotalCo2e);
        databaseReference.child(user.getUid()).child("totalDaysRecorded").setValue(updatedTotalDaysRecorded);

    }

    private void presentCitySavings() {
        float totalPopulationOfVancouver = 2463000; //2.463 million
        TextView citySavings = (TextView) findViewById(R.id.citySavings);

        citySavings.setText("You diet total consumption in terms of Co2 is approximately " + totalCo2eFromUserInput  + " /day. This when compared to an avg canadian of " + avgCanadianFoodFootPrintPerYear/365 + "/day.");
        if(lastDayFoodData > totalCo2eFromUserInput){
            citySavings.append("Your diet showed an improvement from yesterday. Saving approximately  " + (lastDayFoodData - totalCo2eFromUserInput) + "kg of co2." +
                    " If you continue to save the same amount, by the end of year you'll save " + ((lastDayFoodData - totalCo2eFromUserInput)*365)/co2eSavedByEachTreeAnnually + " trees." );
        }
        citySavings.append("\n We can help you improve your diet gradually. " +
                "\n Click below Improve Diet to see your suggestions.");
    }

    private void extractIntentData() {
        Intent intent = getIntent();
        foodResultList = (Foods[]) intent.getSerializableExtra(EXTRA_FOOD_AMOUNT_LIST);
    }

    public static Intent makeIntent(FoodAmountActivity foodAmountActivity, Foods[] foodAmountList) {
        Intent intent = new Intent(foodAmountActivity, ResultActivity.class);
        intent.putExtra(EXTRA_FOOD_AMOUNT_LIST, foodAmountList);
        return intent;
    }

    public void onImproveSelectionNext(View view) {
        Intent intent = new Intent(this, ImprovedDietNewActivity.class);
        intent.putExtra("user_input",foodResultList);
        startActivity(intent);
    }
}
