package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.text.Html;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jla388.sfu.greenfoodchallenge.R;
import com.jla388.sfu.greenfoodchallenge.UserInfo;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference userRef;
    private FirebaseUser user;

    public static UserInfo loggedInUser;
    private Button logoutBtn;
    private TextView welcomeText;
    private ListView listView;
    private TextView userWelcomeTitle;
    private Button traccBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        userWelcomeTitle = (TextView) findViewById(R.id.welcomeText);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Log.d("testing2222", databaseReference.toString());


        databaseReference.addChildEventListener(new ChildEventListener() {

            //new user added
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("testing2222", "new entry happening");
                String currentUserKey = firebaseAuth.getCurrentUser().getUid();
                Log.d("testing2222", "user key " + currentUserKey);
                Log.d("testing2222", "searching key " + dataSnapshot.getKey());

                if(TextUtils.equals(dataSnapshot.getKey(), currentUserKey)){
                    Log.d("testing2222", "user found on the db");
                    Log.d("testing2222" , dataSnapshot.child("userName").getValue().toString());

                    String nameOfTheUser = dataSnapshot.child("userName").getValue().toString();
                    double previousDayData =  dataSnapshot.child("previousDayFoodData").getValue(Double.class);
                    String userMunicipality = dataSnapshot.child("municipalityOfUser").getValue().toString();
                    int pledgedAmount =  dataSnapshot.child("pledgedAmountOfSavings").getValue(int.class);
                    int totalDays =  dataSnapshot.child("totalDaysRecorded").getValue(int.class);
                    double totalCo2Saved =  dataSnapshot.child("totalSavings").getValue(Double.class);
                    String gender = dataSnapshot.child("userGender").getValue().toString();

                    loggedInUser = new UserInfo(nameOfTheUser, previousDayData, userMunicipality, pledgedAmount, totalDays, totalCo2Saved, gender);
                    loggedInUser.setUserKey(currentUserKey);
                    Log.d("testing2222", "all info retrieved");

                    userWelcomeTitle.setText(loggedInUser.getUserName());


                }
                else{
                    Log.d("testing2222", "user not found on the db");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("testing2222" , "update happening");

                String currentUserKey = firebaseAuth.getCurrentUser().getUid();

                if(TextUtils.equals(dataSnapshot.getKey(), currentUserKey)){

                    Log.d("testing2222", "user found - " + dataSnapshot.child("userName").getValue().toString());

                    String nameOfTheUser = dataSnapshot.child("userName").getValue().toString();
                    double previousDayData =  dataSnapshot.child("previousDayFoodData").getValue(Double.class);
                    String userMunicipality = dataSnapshot.child("municipalityOfUser").getValue().toString();
                    int pledgedAmount =  dataSnapshot.child("pledgedAmountOfSavings").getValue(int.class);
                    int totalDays =  dataSnapshot.child("totalDaysRecorded").getValue(int.class);
                    double totalCo2Saved =  dataSnapshot.child("totalSavings").getValue(Double.class);
                    String gender = dataSnapshot.child("userGender").getValue().toString();

                    loggedInUser.setUserName(nameOfTheUser);
                    loggedInUser.setPreviousDayFoodData(previousDayData);
                    loggedInUser.setMunicipalityOfUser(userMunicipality);
                    loggedInUser.setPledgedAmountOfSavings(pledgedAmount);
                    loggedInUser.setTotalDaysRecorded(totalDays);
                    loggedInUser.setTotalSavings(totalCo2Saved);
                    loggedInUser.setUserGender(gender);

                    Log.d("testing2222", "logged in user data modified locally ");
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }




    public static Intent makeIntent(SignInActivity mainActivity) {
        Intent intent = new Intent(mainActivity, WelcomeActivity.class);
        return intent;
    }


    public void logoutProfile(View view) {
        //ViewDBActivity.userList.clear();
        firebaseAuth.signOut();
        finish();
    }

    public void trackCo2ForToday(View view) {
        Intent intent = TrackTypeActivity.makeIntent(WelcomeActivity.this);
        startActivity(intent);
    }

    public void goToPledgePage(View view) {
        Intent intent = PledgeActivity.makeIntent(WelcomeActivity.this);
        startActivity(intent);
    }

}


