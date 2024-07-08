package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jla388.sfu.greenfoodchallenge.Foods;
import com.jla388.sfu.greenfoodchallenge.R;

/**
 * Activity that shows how to reduce C02e without reducing the amount you eat
 */
public class MeatEaterActivity extends AppCompatActivity {
    final double avgCanadianFoodFootPrintPerYear = 1360.78;//kg//1.5tons
    final double co2eSavedByEachTreeAnnually = 22;//kg
    final double totalPopulationOfVancouver = 2463000; //2.463 million

    private Foods[] result;
    private double totalsuggestC02 = 0;
    private TextView result_better_diet;
    private String advise = "";
    private double suggestedAmountForBeef = 0;
    private double suggestedAmountForPork = 0;
    private double suggestedAmountForChicken = 0;
    private double suggestedAmountForFish = 0;
    private double suggestedAmountForEggs = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat_eater);
        extractIntentData();
    }

    private void extractIntentData() {
        Intent intent = getIntent();
        result = (Foods[]) intent.getSerializableExtra("user_input");
        double suggestedAmountForBeans = result[5].getAmount_input();
        double suggestedAmountForVegetables = result[6].getAmount_input();
        for (int i = 0; i < 5; i++)
        {
            if (result[i].getAmount_input() > 0) {
                if (i == 0) {
                    double suggestedAmount = result[i].getAmount_input() / 2;
                    suggestedAmountForBeef += suggestedAmount;
                    advise += "Your consumption of Beef: " + result[i].getAmount_input() + "g\n";
                    suggestedAmountForPork += suggestedAmount;
                }
                if (i == 1) {
                    double suggestedAmount = result[i].getAmount_input() / 2;
                    suggestedAmountForPork += suggestedAmount;
                    advise += "Your consumption of Pork : " + result[i].getAmount_input() + "g\n";
                    suggestedAmountForChicken += suggestedAmount;
                }
                if (i == 2) {
                    double suggestedAmount = result[i].getAmount_input() / 2;
                    suggestedAmountForChicken += suggestedAmount;
                    advise += "Your consumption of Chicken : " + result[i].getAmount_input() + "g\n";
                    suggestedAmountForFish += suggestedAmount;
                }
                if (i == 3) {
                    double suggestedAmount = result[i].getAmount_input() / 2;
                    suggestedAmountForFish += suggestedAmount;
                    advise += "Your consumption of Fish: " + result[i].getAmount_input() + "g\n";
                    suggestedAmountForEggs += suggestedAmount;
                }
                if (i == 4) {
                    double suggestedAmount = result[i].getAmount_input();
                    suggestedAmountForEggs += suggestedAmount;
                    advise += "Eggs\nYour consumption : " + result[i].getAmount_input() +
                            "g\nEggs is one of the lowest C02e consumption among the meat products :).Keep same proportion if you want.\n";
                }
            }
        }
        totalsuggestC02 += (suggestedAmountForBeef/1000)*27 + (suggestedAmountForPork/1000)*12.1 + (suggestedAmountForChicken/1000)*6.9 +
                (suggestedAmountForFish/1000)*6.1 + (suggestedAmountForEggs/1000)*4.8;

        totalsuggestC02 += ((result[5].getAmount_input()+ suggestedAmountForBeans)/1000)*2 + ((result[6].getAmount_input()+suggestedAmountForVegetables)/1000)*2;
        advise += "Recommended consumption of Beef :" + suggestedAmountForBeef +"g\n";
        advise += "Recommended consumption of Pork :" + suggestedAmountForPork +"g\n";
        advise += "Recommended consumption of Chicken :" + suggestedAmountForChicken +"g\n";
        advise += "Recommended consumption of Fish :" + suggestedAmountForFish +"g\n";
        advise += "Recommended consumption of Eggs :" + suggestedAmountForEggs +"g\n";
        advise += "Recommended consumption of Beans :" + suggestedAmountForBeans +"g\n";
        advise += "Recommended consumption of Vegetables :" + suggestedAmountForVegetables +"g\n";

        advise += "\nTotal c02e decreased in percentage : " + Math.round((((ResultActivity.totalCo2eFromUserInput) - totalsuggestC02)/(ResultActivity.totalCo2eFromUserInput)*100)*100.0)/100.0 + "%\n";
        advise += "\nBy replacing half of the meat products' consumption with vegetables, you can save : " + Math.round(((ResultActivity.totalCo2eFromUserInput) - totalsuggestC02)*100.0)/100.0 + " kg of C02e everyday.\n" +
                "This compared to average Canadian diet that produces " + Math.round((avgCanadianFoodFootPrintPerYear/365)*100.0)/100.0 + " kg of Co2e per day \n (i.e"+ Math.round(avgCanadianFoodFootPrintPerYear*100.0)/100.0 + "kg/year) " +
                "is equivalent to saving " + Math.round((avgCanadianFoodFootPrintPerYear - (totalsuggestC02*365))*100.0)/100.0 + " kg of Co2 every year\n " +
                "and is same as planting " + Math.round(((avgCanadianFoodFootPrintPerYear - (totalsuggestC02*365))/co2eSavedByEachTreeAnnually)*100.0)/100.0 + " trees every year" +
                "\n If the whole city of Vancouver with a population fo roughly 2.463 million were to adopt this diet." +
                "It will be same as planting "+ String.format("%.2f",(((avgCanadianFoodFootPrintPerYear - (totalsuggestC02*365))/co2eSavedByEachTreeAnnually)*totalPopulationOfVancouver)) + " tress every year in total";

        result_better_diet = findViewById(R.id.result_better_diet);
        result_better_diet.setText(advise);
        //TextView textviewww = (TextView) findViewById(R.id.textView6);
        //double item = result[3].getAmount_input();
        //String s = String.valueOf(item);
        //textviewww.setText(s);
        //ResultActivity.totalCo2eFromUserInput

    }
}
