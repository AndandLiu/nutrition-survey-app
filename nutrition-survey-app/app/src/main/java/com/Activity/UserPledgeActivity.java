package com.jla388.sfu.greenfoodchallenge.Activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jla388.sfu.greenfoodchallenge.Pledge;
import com.jla388.sfu.greenfoodchallenge.R;

import java.util.ArrayList;

public class UserPledgeActivity extends AppCompatActivity {


    private TextView userPledgeTitle;
    private TextView userPledgeContent;
    private ImageView userLogoView;
    private TextView userSubPledgeContent;
    private ArrayList<String> logoList;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pledge);

        userPledgeContent = (TextView) findViewById(R.id.userPledgeConent);
        userPledgeTitle = (TextView) findViewById(R.id.userPledgeTitle);
        userSubPledgeContent = (TextView) findViewById(R.id.subUserContent);
        userLogoView = (ImageView) findViewById(R.id.userLogo);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("pledge");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot pledgeData : dataSnapshot.getChildren()){
                    String keyFromPledge = String.valueOf(pledgeData.child("key").getValue().toString());
                    String loggedInUserKey = String.valueOf(user.getUid());
                    if(TextUtils.equals(keyFromPledge, loggedInUserKey)){
                        String userAvgSavings = pledgeData.child("avgSavings").getValue().toString();
                        String userKey = pledgeData.child("key").getValue().toString();
                        String userName = pledgeData.child("name").getValue().toString();
                        String userMunicipality = pledgeData.child("municipality").getValue().toString();
                        String userPledgedAmount = pledgeData.child("pledgedAmount").getValue().toString();
                        String userLogo = pledgeData.child("userLogoLocation").getValue().toString();

                        Pledge pledgeFromDB = new Pledge(userKey, userName, userMunicipality, userAvgSavings, userPledgedAmount, userLogo);

                        setUpUserLogo(pledgeFromDB);
                        setUpUserContent(pledgeFromDB);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void setUpUserContent(Pledge userPledgeData) {

        userPledgeTitle.setText(userPledgeData.getName() + "'s Pledge to Save Planet Earth");

        Integer treesSaved = Integer.parseInt(userPledgeData.getPledgedAmount());


        userPledgeContent.setText("I " + userPledgeData.getName() + " of Planet earth realizes it's importance.");
        userPledgeContent.append("\nThe level of Co2 emissions are dangerously damaging my planet");
        userPledgeContent.append("\nThe dangers of increase in Co2 is hugely contributed by the way we live and eat and for that");
        userPledgeContent.append("\nI would like to take a pledge to save by planet earth by reducing my Carbon footprint in terms of the food footprint");
        userPledgeContent.append("\nI thereby Pledge to save " + userPledgeData.getPledgedAmount() + " kg of Co2e/year for the planet.");
        userPledgeContent.append(("\nThis will be equivalent to planting ") + treesSaved + " trees per year.");


        userSubPledgeContent.setText(" My average savings per day currently is  " + userPledgeData.getAvgSavings() + " kg per day");
        userSubPledgeContent.append("\n");
    }

    private void setUpUserLogo(Pledge userPledgeData) {
        String logo = userPledgeData.getUserLogoLocation().toString();

        if(TextUtils.equals(logo, "userLogoOption1")){
            userLogoView.setImageResource(R.drawable.user_logo_option1);
        }
        else if(TextUtils.equals(logo, "userLogoOption2")){
            userLogoView.setImageResource(R.drawable.user_logo_option2);
        }
         else if (TextUtils.equals(logo, "userLogoOption3")){
            userLogoView.setImageResource(R.drawable.user_logo_option3);
        }
        else if(TextUtils.equals(logo, "userLogoOption4")){
            userLogoView.setImageResource(R.drawable.user_logo_option4);
        }
         else if(TextUtils.equals(logo, "userLogoOption5")){
            userLogoView.setImageResource(R.drawable.user_logo_option5);
        }
         else if(TextUtils.equals(logo, "userLogoOption6")){
            userLogoView.setImageResource(R.drawable.user_logo_option6);
        }
        else {
            userLogoView.setImageResource(R.drawable.image_button_border);
        }
    }
}
