package com.jla388.sfu.greenfoodchallenge.Activity;

import android.graphics.Color;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jla388.sfu.greenfoodchallenge.Pledge;
import com.jla388.sfu.greenfoodchallenge.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewPledgeActivity extends AppCompatActivity {

    private String name;
    private String municipality;
    private String pledgeGoal;
    private String userLogo;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private Button submit;
    private ArrayList<ImageButton> userOptions = new ArrayList<ImageButton>();
    private ImageButton userLogoOption1;
    private ImageButton userLogoOption2;
    private ImageButton userLogoOption3;
    private ImageButton userLogoOption4;
    private ImageButton userLogoOption5;
    private ImageButton userLogoOption6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pledge);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("pledge");

        userLogoPopulate();
        submit = (Button) findViewById(R.id.submit);


        for(final ImageButton userLogoButton : userOptions){
            userLogoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer resource =  (Integer) userLogoButton.getTag();
                    if(TextUtils.equals(String.valueOf(resource), String.valueOf(R.drawable.image_button_border))){
                        userLogoButton.setBackgroundResource(R.drawable.image_button_border2);
                        userLogoButton.setTag(R.drawable.image_button_border2);

                        //buttons other than the selected ones
                        for (ImageButton nonSelectedButton : userOptions){
                            if(nonSelectedButton != userLogoButton){
                                nonSelectedButton.setBackgroundResource(R.drawable.image_button_border);//set to black
                                nonSelectedButton.setTag(R.drawable.image_button_border);
                            }
                        }
                    }
                    else {
                        userLogoButton.setBackgroundResource(R.drawable.image_button_border);
                        userLogoButton.setTag(R.drawable.image_button_border);
                    }
                }
            });
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read();
                Toast.makeText(NewPledgeActivity.this, "Pledge Saved" , Toast.LENGTH_SHORT).show();
                PledgeActivity.userHasPledge = true;
                finish();
            }
        });
    }

    private void userLogoPopulate() {
        userLogoOption1 = (ImageButton) findViewById(R.id.userLogoOption1);
        userLogoOption2 = (ImageButton) findViewById(R.id.userLogoOption2);
        userLogoOption3 = (ImageButton) findViewById(R.id.userLogoOption3);
        userLogoOption4 = (ImageButton) findViewById(R.id.userLogoOption4);
        userLogoOption5 = (ImageButton) findViewById(R.id.userLogoOption5);
        userLogoOption6 = (ImageButton) findViewById(R.id.userLogoOption6);

        userOptions.add(userLogoOption1);
        userOptions.add(userLogoOption2);
        userOptions.add(userLogoOption3);
        userOptions.add(userLogoOption4);
        userOptions.add(userLogoOption5);
        userOptions.add(userLogoOption6);

        for (ImageButton userButton : userOptions){
            userButton.setBackground(null);
            userButton.setTag(R.drawable.image_button_border);
            userButton.setBackgroundResource(R.drawable.image_button_border);
        }
    }

    private void read(){

        TextView myName = (TextView) findViewById(R.id.name);
        TextView myMuni = (TextView) findViewById(R.id.municipality);
        TextView myGoal = (TextView) findViewById(R.id.pledgeKg);

        //modified tag here
        //plz see to it
        ImageButton selectedImageLogo = null;
        String userLogoLocation = null;
        for(ImageButton userButton : userOptions){
            Integer tag = (Integer) userButton.getTag();
            if(TextUtils.equals(String.valueOf(tag), String.valueOf(R.drawable.image_button_border2))){
                selectedImageLogo = userButton;
                selectedImageLogo.setTag(userButton.getDrawable().toString());
            }
        }

        for(ImageButton selectedButton : userOptions){
            Log.d("testing2222", "All buttons id -> " +  String.valueOf(selectedButton.getId()));
            if(selectedButton.getDrawable().getConstantState().equals(selectedImageLogo.getDrawable().getConstantState())){
                Log.d("testing2222" , "BUTTON KEY DEKHOOOO" + String.valueOf(selectedButton.getId()) + "     " + selectedButton.getContentDescription());
                userLogoLocation = String.valueOf(selectedButton.getContentDescription());

            }
        }




        name = myName.getText().toString();
        municipality = myMuni.getText().toString();
        pledgeGoal = myGoal.getText().toString();
        userLogo = String.valueOf(userLogoLocation);

        // it is empty
        if(myGoal.toString().equals("") || myGoal == null){
            Toast.makeText(this,"Please enter the pledge goal", Toast.LENGTH_SHORT)
                    .show();
        }
        if(TextUtils.isEmpty(userLogo)){
            Toast.makeText(this,"Please Choose a logo!!", Toast.LENGTH_SHORT)
                    .show();
        }
        else{
            Pledge newPledge = new Pledge();
            databaseReference.child(firebaseUser.getUid()).setValue(newPledge);
            databaseReference.child(firebaseUser.getUid()).child("name").setValue(name);
            databaseReference.child(firebaseUser.getUid()).child("key").setValue(firebaseUser.getUid());
            databaseReference.child(firebaseUser.getUid()).child("municipality").setValue(municipality);
            databaseReference.child(firebaseUser.getUid()).child("pledgedAmount").setValue(pledgeGoal);
            databaseReference.child(firebaseUser.getUid()).child("userLogoLocation").setValue(userLogo);


            Double avgSaved = WelcomeActivity.loggedInUser.getTotalSavings()/WelcomeActivity.loggedInUser.getTotalDaysRecorded();

            databaseReference.child(firebaseUser.getUid()).child("avgSavings").setValue(avgSaved);

        }

    }

}
