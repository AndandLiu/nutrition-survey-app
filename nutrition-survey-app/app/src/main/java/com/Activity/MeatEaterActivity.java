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
    private Foods[] result;
    private double totalCo2e = 0;
    private double totalsuggestC02 = 0;
    final double avgCanadianFoodFootPrintPerYear = 1360.78;//kg//1.5tons
    final double co2eSavedByEachTreeAnnually = 22;//kg
    final double totalPopulationOfVancouver = 2463000; //2.463 million
    private TextView better_diet;
    private String advise = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat_eater);
        extractIntentData();
    }

    private void extractIntentData() {
        Intent intent = getIntent();
        totalCo2e = ResultActivity.totalCo2eFromUserInput;
        result = (Foods[]) intent.getSerializableExtra("user_input");
        for (int i = 0; i < 6; i++)
        {
            if (result[i].getAmount_input() >= 20)
            {
                better_diet = (TextView) findViewById(R.id.result_better_diet);
                if (i == 0)
                {
                    double suggestedAmount = result[i].getAmount_input()/2;
                    totalsuggestC02 += (suggestedAmount/1000) * 27;
                    advise += "Beef\nYour consumption : " + result[i].getAmount_input() +
                            "g\nRecommended consumption : " + suggestedAmount +
                    "g\nIncrease double consumption of Chicken \n";
                    if (result[i+2].getAmount_input() != 0)
                    {
                        totalsuggestC02 += ((result[i+2].getAmount_input()*2)/1000) * 6.9;
                    }
                }
                else if (i == 1)
                {
                    double suggestedAmount = result[i].getAmount_input()/2;
                    totalsuggestC02 += (suggestedAmount/1000) * 12.1;
                    advise += "Pork\nYour consumption : " + result[i].getAmount_input() +
                            "g\nRecommended consumption : " + suggestedAmount +
                            "g\nIncrease 50% consumption of Fish\n";
                    if (result[i+2].getAmount_input() != 0)
                    {
                        totalsuggestC02 += ((result[i+2].getAmount_input()*1.5) /1000) * 6.1;
                    }
                }
                else if (i == 2)
                {
                    totalsuggestC02 += (result[i].getAmount_input()/1000) * 6.9;
                    advise += "Chicken\nYour consumption : " + result[i].getAmount_input() +
                            "g\nChicken is one of the lowest C02e consumption among the meat products :).Keep same proportion if you want.\n";
                }
                else if (i == 3)
                {
                    totalsuggestC02 += (result[i].getAmount_input()/1000) * 6.1;
                    advise += "Fish\nYour consumption : " + result[i].getAmount_input() +
                            "g\nFish is one of the lowest C02e consumption among the meat products :).Keep same proportion if you want.\n";
                }
                else
                {
                    totalsuggestC02 += (result[i].getAmount_input()/1000) * 6.1;
                    advise += "Eggs\nYour consumption : " + result[i].getAmount_input() +
                            "g\nEggs is one of the lowest C02e consumption among the meat products :).Keep same proportion if you want.\n";
                }

            }
            else{
                totalsuggestC02 += (result[i].getAmount_input()/1000) * 3;
            }
        }
        advise += "\nTotal C02e from recommended amount produces : " + totalsuggestC02  + "\nEating more Chicken, Fish or Eggs produces less C02e :)";
        advise += "\nTotal c02e decreased in percentage : " + ((totalsuggestC02/totalCo2e)*100)/2 + "\n";
        advise += "\n Using this diet would result in saving " + totalsuggestC02 + " kg everyday. " +
                "This compared to average Canadian diet that produces " + avgCanadianFoodFootPrintPerYear/365 + " kg of Co2e per day (i.e"+ avgCanadianFoodFootPrintPerYear + "kg/year) " +
                "is equivalent to saving " + (avgCanadianFoodFootPrintPerYear - (totalsuggestC02*365)) + " kg of Co2 every year " +
                "and is same as planting " + (avgCanadianFoodFootPrintPerYear - (totalsuggestC02*365))/co2eSavedByEachTreeAnnually + " trees every year" +
                "\n If the whole city of Vancouver with a population of roughly 2.463 million were to adopt this diet." +
                "It will be same as planting "+ ((avgCanadianFoodFootPrintPerYear - (totalsuggestC02*365))/co2eSavedByEachTreeAnnually)*totalPopulationOfVancouver + " trees every year in total";


        better_diet.setText(advise);
    }
}
