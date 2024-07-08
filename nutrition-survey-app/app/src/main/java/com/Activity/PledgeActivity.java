package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jla388.sfu.greenfoodchallenge.R;

public class PledgeActivity extends AppCompatActivity {



    private Button userPledge;
    private Button view_all_pledge;
    private Button create_new_pledge;
    private TextView pledgeStats;
    private int totalPledgedAmount;
    private int totalPeoplePledged;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    public static Boolean userHasPledge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pledge);


        userPledge = findViewById(R.id.user_pledge);
        view_all_pledge = (Button) findViewById(R.id.user_pledge);
        create_new_pledge = (Button) findViewById(R.id.create_new_pledge);
        pledgeStats = (TextView) findViewById(R.id.totalPledgeStats);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("pledge");

        totalPeoplePledged = 0;
        totalPledgedAmount = 0;

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot pledgeData : dataSnapshot.getChildren()){
                    String totalSavings = pledgeData.child("pledgedAmount").getValue().toString();
                    totalPledgedAmount += Integer.parseInt(totalSavings);
                    totalPeoplePledged += 1;
                    pledgeStats.setText("");
                    pledgeStats.setText("Total people pleged already = " + totalPeoplePledged);
                    pledgeStats.append("\n Total pledged savings = " + totalPledgedAmount);
                    Log.d("testing2222", "keys - pledge waali then user waali" );
                    Log.d("testing2222", String.valueOf(pledgeData.child("key").getValue().toString()));
                    Log.d("testing2222", String.valueOf(user.getUid()));
                    if(TextUtils.equals(String.valueOf(pledgeData.child("key").getValue().toString()), String.valueOf(user.getUid()))){
                        userHasPledge = true;
                        create_new_pledge.setVisibility(View.GONE);
                        userPledge.setVisibility(View.VISIBLE);
                        Log.d("testing2222", "success");
                        Log.d("testing2222", String.valueOf(pledgeData.child("key").getValue().toString()));
                        Log.d("testing2222", String.valueOf(user.getUid()));
                        break;
                    }
                    else {
                        userHasPledge = false;
                        userPledge.setVisibility(View.GONE);
                        create_new_pledge.setVisibility(View.VISIBLE);
                        Log.d("testing2222", "fail");
                        Log.d("testing2222", String.valueOf(pledgeData.child("key").getValue().toString()));
                        Log.d("testing2222", String.valueOf(user.getUid()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public static Intent makeIntent(WelcomeActivity welcomeActivity) {
        Intent intent = new Intent(welcomeActivity, PledgeActivity.class);
        return intent;
    }


    public void viewAllPledges(View view) {
        Intent intent = ViewAllPledgeActivity.makeIntent(PledgeActivity.this);
        startActivity(intent);
    }

    public void createNewUserPledge(View view) {
        Intent intent = new Intent(PledgeActivity.this, NewPledgeActivity.class);
        startActivity(intent);
    }

    public void userPledgePage(View view) {
        Intent intent = new Intent(PledgeActivity.this, UserPledgeActivity.class);
        startActivity(intent);
    }


}
