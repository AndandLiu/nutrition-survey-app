package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.jla388.sfu.greenfoodchallenge.Foods;
import com.jla388.sfu.greenfoodchallenge.R;

import static android.widget.CompoundButton.*;

/**Activity with a bunch of boxes where you can check the food you have eaten
 */
public class FoodSelectionActivity extends AppCompatActivity {

    final Foods[]food_list = new Foods[7];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selction);

        food_list[0]=new Foods("Beef",0,27);
        food_list[1]=new Foods("Pork",0,12.1);
        food_list[2]=new Foods("Chicken",0,6.9);
        food_list[3]=new Foods("Fish",0,6.1);
        food_list[4]=new Foods("Eggs",0,4.8);
        food_list[5]=new Foods("Beans",0,2);
        food_list[6]=new Foods("Vegetables",0,2);

        CheckBox cBeef;
        CheckBox cPork;
        CheckBox cChicken;
        CheckBox cFish;
        CheckBox cEggs;
        CheckBox cBeans;
        CheckBox cVegetables;

        cBeef = (CheckBox) findViewById(R.id.food_beef);
        cPork = (CheckBox) findViewById(R.id.food_pork);
        cChicken = (CheckBox) findViewById(R.id.food_chicken);
        cFish = (CheckBox) findViewById(R.id.food_fish);
        cEggs = (CheckBox) findViewById(R.id.food_eggs);
        cBeans = (CheckBox) findViewById(R.id.food_beans);
        cVegetables = (CheckBox) findViewById(R.id.food_vegetables);

        cBeef.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                food_list[0].setIs_selected(isChecked);
            }
        });
        cPork.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                food_list[1].setIs_selected(isChecked);
            }
        });
        cChicken.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                food_list[2].setIs_selected(isChecked);
            }
        });
        cFish.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                food_list[3].setIs_selected(isChecked);
            }
        });
        cEggs.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                food_list[4].setIs_selected(isChecked);
            }
        });
        cBeans.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                food_list[5].setIs_selected(isChecked);
            }
        });
        cVegetables.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                food_list[6].setIs_selected(isChecked);
            }
        });

    }

    public static Intent makeIntent(TrackTypeActivity trackTypeActivity) {
        Intent intent = new Intent(trackTypeActivity, FoodSelectionActivity.class);
        return intent;
    }

    public void onFoodSelectionNext(View view) {
        Intent intent = FoodAmountActivity.makeIntent(FoodSelectionActivity.this , food_list);
        startActivity(intent);
    }



}
