package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jla388.sfu.greenfoodchallenge.Pledge;
import com.jla388.sfu.greenfoodchallenge.R;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import com.jla388.sfu.greenfoodchallenge.Screenshot;


import java.util.ArrayList;
import java.util.List;

public class UserPledgeActivity extends AppCompatActivity {


    private TextView userPledgeTitle;
    private TextView userPledgeContent;
    private ImageView userLogoView;
    private TextView userSubPledgeContent;
    private ArrayList<String> logoList;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference editDatabaseReference;
    private FirebaseUser user;

    private ImageView imageView;

    Button ShareButton;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    private Spinner spinner;

    Boolean firstTime = true;


    private void initSpinner(){

        spinner = (Spinner) findViewById(R.id.user_pledge_spinner);
        List<String> list = new ArrayList<>();


        list.add("Choose an option");
        list.add("Edit");
        list.add("Delete");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

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

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        ShareButton = (Button)findViewById(R.id.ShareButton);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);


        imageView = (ImageView) findViewById(R.id.imageView);
        ShareButton = (Button) findViewById(R.id.ShareButton);
        ShareButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Bitmap b = Screenshot.TakeScreenShotOfRootView(imageView);
                //imageView.setImageBitmap(b);
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(UserPledgeActivity.this,"share successful!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(UserPledgeActivity.this,"share cancel!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(UserPledgeActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
                //assign b to share
                SharePhoto pic = new SharePhoto.Builder()
                        .setBitmap(b)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(pic)
                        .build();
                shareDialog.show(content);

            }

        });

        initSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //First slot, Edot

                System.out.println("Position is : " + position);
                if(!firstTime) {

                    if (position == 0) {
                        System.out.println("Position is : " + position);
                        Toast.makeText(UserPledgeActivity.this,"Fired",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserPledgeActivity.this, NewPledgeActivity.class);
                        startActivity(intent);
                    }else if (position == 1) {
                        System.out.println("Position is : " + position);
                        Toast.makeText(UserPledgeActivity.this, "Fired", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserPledgeActivity.this, NewPledgeActivity.class);
                        startActivity(intent);
                    } else if (position == 2) {
                        System.out.println("Position is : " + position);
                        //2nd Slot Delete
                        databaseReference.child(user.getUid()).removeValue();
                        Toast.makeText(UserPledgeActivity.this,"Deleted pledge",Toast.LENGTH_SHORT).show();
                        finish();
                    }


                } else{
                    firstTime = false;
                }

            }



            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // do nothing
            }
        });


    }

    private void setUpUserContent(Pledge userPledgeData) {

        userPledgeTitle.setText(userPledgeData.getName()+ "'s Pledge to Save \n Planet Earth");

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
