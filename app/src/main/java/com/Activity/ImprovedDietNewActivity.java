package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.jla388.sfu.greenfoodchallenge.Foods;
import com.jla388.sfu.greenfoodchallenge.R;

public class ImprovedDietNewActivity extends AppCompatActivity {

    private CardView meat_eater;
    private CardView low_meat;
    private CardView plant_based;
    private Foods[] result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improved__diet__new);
        extractIntentData();
        meat_eater =(CardView) findViewById(R.id.meat_eater_card);
        meat_eater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMeatEaterNext();
            }
        });

        low_meat =(CardView) findViewById(R.id.low_meat_card);
        low_meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLowMeatNext();
            }
        });

        plant_based = (CardView) findViewById(R.id.plant_based_card);
        plant_based.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPlantBasedNext();
            }
        });


    }
    private void extractIntentData() {
        Intent intent = getIntent();
        result = (Foods[]) intent.getSerializableExtra("user_input");
        //TextView textviewww = (TextView) findViewById(R.id.textView6);
        //double item = result[3].getAmount_input();
        //String s = String.valueOf(item);
        //textviewww.setText(s);
    }
    public void onClickMeatEaterNext() {
        Intent intent = new Intent(this, MeatEaterActivity.class);
        intent.putExtra("user_input",result);
        startActivity(intent);
    }

    public void onClickLowMeatNext() {
        Intent intent = new Intent(this, LowMeatActivity.class);
        intent.putExtra("user_input",result);
        startActivity(intent);
    }
    public void onClickPlantBasedNext(){
        Intent intent = new Intent(this, PlantBasedActivity.class);
        intent.putExtra("user_input",result);
        startActivity(intent);
    }
}
