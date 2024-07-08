package com.jla388.sfu.greenfoodchallenge.Activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jla388.sfu.greenfoodchallenge.Foods;
import com.jla388.sfu.greenfoodchallenge.R;

/**
 * Activity that contains sliders that determine the proportion of the food you have eaten
 */
public class FoodAmountActivity extends AppCompatActivity {

    public static final String EXTRA_FOOD_LIST = "selectedFoodItemsFromSelection";
    Foods[]  foodAmountList = new Foods[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_amount);
        extractIntentData();
        makeSlidersForCheckedItems();
    }

    private  void makeSlidersForCheckedItems() {
        int [] seekBarResId = {R.id.simpleSeekBar, R.id.simpleSeekBar2, R.id.simpleSeekBar3, R.id.simpleSeekBar4, R.id.simpleSeekBar5, R.id.simpleSeekBar6, R.id.simpleSeekBar7};
        int [] textBoxResId = {R.id.foodNameProgress , R.id.foodNameProgress2 , R.id.foodNameProgress3 , R.id.foodNameProgress4 , R.id.foodNameProgress5, R.id.foodNameProgress6 , R.id.foodNameProgress7};
        for(int foodItem  = 0 ; foodItem < foodAmountList.length ; foodItem++){
            if(foodAmountList[foodItem].getIs_selected()){

               final String nameOfFoodItem = foodAmountList[foodItem].getName();
               final TextView selectedFoodItemName = (TextView) findViewById(textBoxResId[foodItem]);
               selectedFoodItemName.setText(nameOfFoodItem + " | 0 ");


                SeekBar selectedSeekBar = (SeekBar) findViewById(seekBarResId[foodItem]);
                final int finalFoodItem = foodItem;

                selectedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int amountSelected;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        amountSelected = progress;
                        selectedFoodItemName.setText(nameOfFoodItem + " | " + progress);
                        Log.d("Test", nameOfFoodItem + String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Log.d("Test", "started tracking");
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        Log.d("Test", "stopping tracking");
                        foodAmountList[finalFoodItem].setAmount_input(amountSelected);
                        Log.d("Test", nameOfFoodItem + " amount stored = " + foodAmountList[finalFoodItem].getAmount_input());
                    }
                });

            }
            else{
                SeekBar nonSelectedSeekbar = (SeekBar) findViewById(seekBarResId[foodItem]);
                nonSelectedSeekbar.setVisibility(View.GONE);

                TextView nonSelectedTextView = (TextView) findViewById(textBoxResId[foodItem]);
                nonSelectedTextView.setVisibility(View.GONE);
            }

        }
    }

    private void extractIntentData() {
        Intent intent = getIntent();
        foodAmountList =  (Foods[]) intent.getSerializableExtra(EXTRA_FOOD_LIST);
        Log.d("Test", String.valueOf(foodAmountList.length));
    }


    public static Intent makeIntent(FoodSelectionActivity foodSelectionActivity, Foods[] food_list) {
        Intent intent = new Intent (foodSelectionActivity, FoodAmountActivity.class);
        intent.putExtra(EXTRA_FOOD_LIST,food_list);
        return intent;
    }


    public void submitButtonClicked(View view) {
        Log.d("Test" , "submit button clicked");
        Intent intent = ResultActivityNew.makeIntent(FoodAmountActivity.this, foodAmountList);
        Log.d("Test" , "intent");
        startActivity(intent);
    }

}
