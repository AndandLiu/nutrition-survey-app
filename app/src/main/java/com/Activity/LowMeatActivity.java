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
    private double suggestedAmountForBeans = 0;
    private double suggestedAmountForVegetables = 0;
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

        for (int i = 0; i < 5; i++) {
            if (result[i].getAmount_input() > 0) {

                //Beef
                if (i == 0) {
                    double suggestedAmount = result[i].getAmount_input()/ 2;
                    totalsuggestC02 += (suggestedAmount/1000) * 27;//for Beef
                    advise += "Your consumption of Beef : " + result[i].getAmount_input() + "g\nRecommended consumption : " + suggestedAmount + "g\n";

                    //Replacing taken amount with veggies, half of it gives to Beans, other half gives to Vegetables
                    suggestedAmountForBeans += suggestedAmount/2;
                    suggestedAmountForVegetables += suggestedAmount/2;
                }
                //Pork
                if (i == 1) {
                    double suggestedAmount = result[i].getAmount_input() / 2;
                    totalsuggestC02 += (suggestedAmount/1000) * 12.1;//for Pork
                    advise += "Your consumption of Pork: " + result[i].getAmount_input() + "g\nRecommended consumption : " + suggestedAmount + "g\n";

                    //update the consumption of BEANS and VEGETABLES
                    suggestedAmountForBeans += suggestedAmount/2;
                    suggestedAmountForVegetables += suggestedAmount/2;

                }
                //Chicken
                if (i == 2)
                {
                    double suggestedAmount = result[i].getAmount_input() / 2;
                    totalsuggestC02 += (suggestedAmount/1000) * 6.9;//for Chicken
                    advise += "Your consumption of Chicken : " + result[i].getAmount_input() + "g\nRecommended consumption : " + suggestedAmount + "g\n";

                    //update the consumption of BEANS and VEGETABLES
                    suggestedAmountForBeans += suggestedAmount/2;
                    suggestedAmountForVegetables += suggestedAmount/2;
                }
                //Fish
                if (i == 3)
                {
                    double suggestedAmount = result[i].getAmount_input() / 2;
                    totalsuggestC02 += (suggestedAmount/1000) * 6.1;
                    advise += "Your consumption of Fish : " + result[i].getAmount_input() + "g\nRecommended consumption : " + suggestedAmount + "g\n";

                    //update the consumption of BEANS and VEGETABLES
                    suggestedAmountForBeans += suggestedAmount/2;
                    suggestedAmountForVegetables += suggestedAmount/2;
                }
                if (i == 4)
                {
                    double suggestedAmount = result[i].getAmount_input() / 2;
                    totalsuggestC02 += (suggestedAmount/1000) * 4.8;
                    advise += "Your consumption of Eggs: " + result[i].getAmount_input() + "g\nRecommended consumption : " + suggestedAmount + "g\n";

                    //update the consumption of BEANS and VEGETABLES
                    suggestedAmountForBeans += suggestedAmount/2;
                    suggestedAmountForVegetables += suggestedAmount/2;
                }
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
