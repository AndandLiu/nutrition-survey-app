package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jla388.sfu.greenfoodchallenge.Foods;
import com.jla388.sfu.greenfoodchallenge.R;

/**
 * Activity that lets you select how you want to change your diet
 * high-meat means you want to maintain your consumption of meat but replace it with better alternatives
 * low-meat means you want to reduce your consumption of meat and replace it with vegetables
 */
public class ImprovedDietActivity extends AppCompatActivity {
    private Button meat_eater;
    private Button low_meat;
    private Foods[] result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improved_diet);
        extractIntentData();
        meat_eater =(Button) findViewById(R.id.meat_eater);
        meat_eater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMeatEaterNext();
            }
        });

        low_meat =(Button) findViewById(R.id.low_meat);
        low_meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLowMeatNext();
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
}
