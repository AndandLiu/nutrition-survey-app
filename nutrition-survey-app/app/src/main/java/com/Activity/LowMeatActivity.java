package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.jla388.sfu.greenfoodchallenge.Foods;
import com.jla388.sfu.greenfoodchallenge.R;

/**
 * Shows what you need to do to improve consumption of food
 */
public class LowMeatActivity extends AppCompatActivity {

    final double avgCanadianFoodFootPrintPerYear = 1360.78;//kg//1.5tons
    final double co2eSavedByEachTreeAnnually = 22;//kg
    final double totalPopulationOfVancouver = 2463000;

    private Foods[] result;
    private double totalsuggestC02 = 0;
    private TextView result_better_diet;
    private String advise = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_meat);
        extractIntentData();
    }
    private void extractIntentData() {
        Intent intent = getIntent();
        result = (Foods[]) intent.getSerializableExtra("user_input");
        for (int i = 0; i < 6; i++) {
            if (result[i].getAmount_input() >= 20) {
                result_better_diet = (TextView) findViewById(R.id.result_better_diet);
                if (i == 0) {
                    double suggestedAmount = result[i].getAmount_input()/ 2;
                    totalsuggestC02 += (suggestedAmount/1000) * 27;
                    advise += "Beef\nYour consumption : " + result[i].getAmount_input() +
                            "g\nRecommended consumption : " + suggestedAmount +
                            "\nIncrease double consumption of Beans and Vegetables\n";
                    if (result[i+2].getAmount_input() != 0)
                    {
                        totalsuggestC02 += ((result[i+2].getAmount_input()*2)/1000) * 6.9;
                    }
                } else if (i == 1) {
                    double suggestedAmount = result[i].getAmount_input() / 2;
                    totalsuggestC02 += (suggestedAmount/1000) * 12.1;
                    advise += "Pork\nYour consumption : " + result[i].getAmount_input() +
                            "g\nRecommended consumption : " + suggestedAmount +
                            "g\nIncrease 50% consumption of Beans and Vegetables";
                    if (result[i+2].getAmount_input() != 0)
                    {
                        totalsuggestC02 += ((result[i+2].getAmount_input()*1.5) /1000) * 6.1;
                    }
                } else if (i == 2) {
                    double suggestedAmount = result[i].getAmount_input() / 2;
                    totalsuggestC02 += (result[i].getAmount_input()/1000) * 6.9;
                    advise += "Chicken\nYour consumption : " + result[i].getAmount_input() +
                            "g\nRecommended consumption : " + suggestedAmount +
                            "g\nIncrease 25% consumption of Beans and Vegetables";
                } else if (i == 3)
                    {
                        double suggestedAmount = result[i].getAmount_input() * 0.75;
                        totalsuggestC02 += (result[i].getAmount_input()/1000) * 6.1;
                        advise += "Fish\nYour consumption : " + result[i].getAmount_input() +
                            "g\nRecommended consumption : " + suggestedAmount +
                            "g\nIncrease 25% consumption of Beans and Vegetables";
                }
                else
                {
                    double suggestedAmount = result[i].getAmount_input() * 0.75;
                    totalsuggestC02 += (result[i].getAmount_input()/1000) * 6.1;
                    advise += "Eggs\nYour consumption : " + result[i].getAmount_input() +
                            "g\nRecommended consumption : " + suggestedAmount +
                            "g\nIncrease 25% consumption of Beans and Vegetables";
                }
            }
            else {
                double suggestedAmount = result[i].getAmount_input() * 3;
                totalsuggestC02 += (result[i].getAmount_input()/1000) * 3;
            }
        }
        advise += "\nEating more Veggies produces less C02e than Meat products :)\nto decrease C02e production up to 20% ";

        advise += "\n Using this diet would result in saving " + totalsuggestC02 + " kg everyday. " +
                "This compared to average Canadian diet that produces " + avgCanadianFoodFootPrintPerYear/365 + " kg of Co2e per day (i.e"+ avgCanadianFoodFootPrintPerYear + "kg/year) " +
                "is equivalent to saving " + (avgCanadianFoodFootPrintPerYear - (totalsuggestC02*365)) + " kg of Co2 every year " +
                "and is same as planting " + (avgCanadianFoodFootPrintPerYear - (totalsuggestC02*365))/co2eSavedByEachTreeAnnually + " trees every year" +
                "\n If the whole city of Vancouver with a population fo roughly 2.463 million were to adopt this diet." +
                "It will be same as planting "+ ((avgCanadianFoodFootPrintPerYear - (totalsuggestC02*365))/co2eSavedByEachTreeAnnually)*totalPopulationOfVancouver + " tress every year in total";



        result_better_diet.setText(advise);
        //TextView textviewww = (TextView) findViewById(R.id.textView6);
        //double item = result[3].getAmount_input();
        //String s = String.valueOf(item);
        //textviewww.setText(s);
    }
}
