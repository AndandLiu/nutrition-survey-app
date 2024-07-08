package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jla388.sfu.greenfoodchallenge.Meal;
import com.jla388.sfu.greenfoodchallenge.R;

public class MealActivity extends AppCompatActivity {

    private Button viewAll;
    private Button addNew;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        viewAll = (Button) findViewById(R.id.viewAllMealsBtn);
        addNew = (Button) findViewById(R.id.addANewMealBtn);

    }


    public void viewAllMealBtnClicked(View view) {
        Intent intent = ViewAllMealsActivity.makeIntent(MealActivity.this);
        startActivity(intent);

    }

    public void newMealBtnClicked(View view) {
        Intent intent = NewMealActivity.makeIntent(MealActivity.this);
        startActivity(intent);
    }

    public static Intent makeIntent(TrackTypeActivity trackTypeActivity) {
        Intent intent = new Intent(trackTypeActivity, MealActivity.class);
        return intent;
    }
}
