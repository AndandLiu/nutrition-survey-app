package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jla388.sfu.greenfoodchallenge.R;

/**
 * Activity that shows the information on why this app was created and what purpose is it for
 */
public class AboutActivity extends AppCompatActivity {

    private TextView about;
    private Button backButton;

    /**
     * Initiates the parameters required for this activity
     */
    public void initiate(){
        backButton = (Button) findViewById(R.id.back_button);
        backButton.setText("Back");

        //do we need this?? the user can just hit the arrow button on their phone to go back
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(AboutActivity.this,UserInfoActivity.class);
                startActivity(myIntent);
            }
        });

        about = (TextView) findViewById(R.id.text_view);
        about.setText(R.string.about_text);

    }


    /**
     * Initiates the about activity explaining what the green food activity challenge is about
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initiate();

    }
}
