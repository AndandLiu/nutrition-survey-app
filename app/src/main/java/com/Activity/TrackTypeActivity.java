package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.jla388.sfu.greenfoodchallenge.Activity.NewMealActivity;
import com.jla388.sfu.greenfoodchallenge.R;

public class TrackTypeActivity extends AppCompatActivity {

    private CardView homeCookTrack;
    private CardView eatOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_type);

        homeCookTrack = (CardView) findViewById(R.id.homeCookBtn);
        eatOut = (CardView) findViewById(R.id.eatOutBtn);


    }

    public static Intent makeIntent(WelcomeActivity welcomeActivity) {
        Intent intent = new Intent(welcomeActivity, TrackTypeActivity.class);
        return intent;
    }

    public void homeCookBtnClicked(View view) {
        Intent intent = FoodSelectionActivity.makeIntent(TrackTypeActivity.this);
        startActivity(intent);
    }

    public void eatOutBtnClicked(View view) {
        Intent intent =  MealActivity.makeIntent(TrackTypeActivity.this);
        startActivity(intent);
    }

}
