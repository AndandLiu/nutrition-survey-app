package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jla388.sfu.greenfoodchallenge.Foods;
import com.jla388.sfu.greenfoodchallenge.R;


/**
 * Incomplete class for a pure vegan diet, implement in a future sprint
 */
public class PlantBasedActivity extends AppCompatActivity {

    final double avgCanadianFoodFootPrintPerYear = 1360.78;//kg//1.5tons
    final double co2eSavedByEachTreeAnnually = 22;//kg
    final double totalPopulationOfVancouver = 2463000;

    private Foods[] result;
    private double totalsuggestC02 = 0;
    private double suggestedAmount = 0;
    private double suggestedAmountForBeans = 0;
    private double suggestedAmountForVegetables = 0;
    private String name;
    private TextView result_better_diet;
    private String advise = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_based);
        extractIntentData();
    }
    private void extractIntentData() {
        Intent intent = getIntent();
        result = (Foods[]) intent.getSerializableExtra("user_input");
        for (int i = 0; i < 5; i++) {
            if (result[i].getAmount_input() > 0) {
                suggestedAmount += result[i].getAmount_input();
                if (i == 0){name = "Beef";}
                if (i == 1){name = "Pork";}
                if (i == 2){name = "Chicken";}
                if (i == 3){name = "Fish";}
                if (i == 4){name = "Eggs";}
                advise += "Your consumption of " + name + ": " + result[i].getAmount_input() + "g\nRecommended consumption : 0g\n";
                suggestedAmountForBeans += suggestedAmount/2;
                suggestedAmountForVegetables += suggestedAmount/2;
            }
        }
        totalsuggestC02 += ((result[5].getAmount_input()+ suggestedAmountForBeans)/1000)*2 + ((result[6].getAmount_input()+suggestedAmountForVegetables)/1000)*2;
        advise += "Suggested amount of Beans : " + (result[5].getAmount_input() + suggestedAmountForBeans) + "g\n" + "Suggested Amount of Vegetables: " + (result[6].getAmount_input() + suggestedAmountForVegetables) + "g\n";
        advise += "\nTotal c02e decreased in percentage : " + Math.round((((ResultActivity.totalCo2eFromUserInput) - totalsuggestC02)/(ResultActivity.totalCo2eFromUserInput)*100)*100.0)/100.0 + "%\n";
        advise += "\nBy replacing half of the meat products' consumption with vegetables, you can save : " + Math.round(((ResultActivity.totalCo2eFromUserInput) - totalsuggestC02)*100.0)/100.0 + " kg of C02e everyday.\n" +
                "This compared to average Canadian diet that produces " + Math.round((avgCanadianFoodFootPrintPerYear/365)*100.0)/100.0 + " kg of Co2e per day \n (i.e"+ Math.round(avgCanadianFoodFootPrintPerYear*100.0)/100.0 + "kg/year) " +
                "is equivalent to saving " + Math.round((avgCanadianFoodFootPrintPerYear - (totalsuggestC02*365))*100.0)/100.0 + " kg of Co2 every year\n " +
                "and is same as planting " + Math.round(((avgCanadianFoodFootPrintPerYear - (totalsuggestC02*365))/co2eSavedByEachTreeAnnually)*100.0)/100.0 + " trees every year" +
                "\n If the whole city of Vancouver with a population fo roughly 2.463 million were to adopt this diet." +
                "It will be same as planting "+ String.format("%.2f",((avgCanadianFoodFootPrintPerYear - (totalsuggestC02*365))/co2eSavedByEachTreeAnnually)*totalPopulationOfVancouver) + " tress every year in total";

        result_better_diet = findViewById(R.id.result_better_diet);
        result_better_diet.setText(advise);
        //TextView textviewww = (TextView) findViewById(R.id.textView6);
        //double item = result[3].getAmount_input();
        //String s = String.valueOf(item);
        //textviewww.setText(s);
    }
}
