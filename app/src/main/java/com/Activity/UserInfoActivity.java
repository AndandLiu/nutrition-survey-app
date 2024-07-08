package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jla388.sfu.greenfoodchallenge.R;
import com.jla388.sfu.greenfoodchallenge.UserInfo;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Initial Activity that pops up and ask the user for their weight age, and gender
 */
public class UserInfoActivity extends AppCompatActivity {

    private UserInfo mUser;
    private EditText mNameField;
    private EditText mAgeField;
    private EditText mWeightField;

    private String name;
    private int age;
    private int weight;
    Button DoneButton;
    RadioGroup gGender;
    RadioButton rb;

    private Button AboutButton;

    /**
     *Checks if one of the fields is empty, return true if it is. else false
     * @return true if one of the editText is empty
     */
    private boolean checkIfEmpty(){
        if(TextUtils.isEmpty(mNameField.getText().toString())){
            return true;
        }
        if(TextUtils.isEmpty(mAgeField.getText().toString())){
            return true;
        }
        if(TextUtils.isEmpty(mWeightField.getText().toString())){
            return true;
        }
        return false;
    }

    /**
     * Initiates the about button
     */
    private void initiateAboutButton(){
        AboutButton = (Button) findViewById(R.id.about);
        AboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Saves the data
     */
    private void saveData(){

        //we need to write name age and weight.
       String filename = "save.csv";
       String fileContents = name + "," + age + "," + weight;

        FileOutputStream outputStream;

        try{
            outputStream = openFileOutput(filename,Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    private boolean fileExists(){
        String fileName = "save.csv";
        String path = getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        if(fileExists()) {
            Intent i = new Intent(UserInfoActivity.this,FoodSelectionActivity.class);
            finish();
            startActivity(i);
        } else {
            gGender = (RadioGroup) findViewById(R.id.gender_group);
            mNameField = (EditText) findViewById(R.id.text_name);
            mAgeField = (EditText) findViewById(R.id.text_age);
            mWeightField = (EditText) findViewById(R.id.text_weight);

            DoneButton = (Button) findViewById(R.id.done);
            DoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkIfEmpty()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Error! " +
                                " you didn't fill out all the fields!", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        name = mNameField.getText().toString();
                        age = Integer.valueOf(mAgeField.getText().toString());
                        weight = Integer.valueOf(mWeightField.getText().toString());
                        String g = rb.getText().toString();

                        mUser = new UserInfo();
                        mUser.setUserName(name);
                        mUser.setUserGender(g);

                        saveData();
                        Intent intent = new Intent(UserInfoActivity.this, FoodSelectionActivity.class);
                        startActivity(intent);
                    }
                }
            });

            initiateAboutButton();
        }
    }

    public void RBClick(View v){
        int radioButtonId = gGender.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radioButtonId);
    }
}

