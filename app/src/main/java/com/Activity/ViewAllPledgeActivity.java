package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jla388.sfu.greenfoodchallenge.CustomUserAdapter;
import com.jla388.sfu.greenfoodchallenge.Pledge;
import com.jla388.sfu.greenfoodchallenge.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAllPledgeActivity extends AppCompatActivity {

    private static final int All = 0;
    private static final int RICHMOND = 1;
    private static final int BURNABY = 2;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private ArrayList<Pledge> pledgeArrayList;
    private ListView pledgeListView;
    private CustomUserAdapter adapter;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_pledge);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("pledge");

        pledgeArrayList = new ArrayList<Pledge>();
        pledgeListView = (ListView) findViewById(R.id.pledgeList);
        adapter = new CustomUserAdapter(this,pledgeArrayList);
        pledgeListView.setAdapter(adapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("testing2222", "pledge entry happening");

                String key = dataSnapshot.child("key").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String municipality = dataSnapshot.child("municipality").getValue().toString();
                String avgSavings = dataSnapshot.child("avgSavings").getValue().toString();
                String pledgedAmount = dataSnapshot.child("pledgedAmount").getValue().toString();
                String userLogoLocation = dataSnapshot.child("userLogoLocation").getValue().toString();

                Pledge newPledge = new Pledge(key, name, municipality, avgSavings, pledgedAmount, userLogoLocation);

                pledgeArrayList.add(newPledge);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("testing2222", "pledge entry edit");

                String key = dataSnapshot.child("key").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String municipality = dataSnapshot.child("municipality").getValue().toString();
                String avgSavings = dataSnapshot.child("avgSavings").getValue().toString();
                String pledgedAmount = dataSnapshot.child("pledgedAmount").getValue().toString();
                String userLogoLocation = dataSnapshot.child("userLogoLocation").getValue().toString();

                Pledge newPledge = new Pledge(key, name, municipality, avgSavings, pledgedAmount, userLogoLocation);

               for(Pledge userPledge : pledgeArrayList){
                   if(TextUtils.equals(userPledge.getKey(), key)){
                       pledgeArrayList.remove(userPledge);//removing the old one
                       pledgeArrayList.add(newPledge);//adding in the updated one
                   }
               }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("testing2222", "pledge entry deleted");

                String key = dataSnapshot.child("key").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String municipality = dataSnapshot.child("municipality").getValue().toString();
                String avgSavings = dataSnapshot.child("avgSavings").getValue().toString();
                String pledgedAmount = dataSnapshot.child("pledgedAmount").getValue().toString();
                String userLogoLocation = dataSnapshot.child("userLogoLocation").getValue().toString();

                Pledge newPledge = new Pledge(key, name, municipality, avgSavings, pledgedAmount, userLogoLocation);

                for(Pledge userPledge : pledgeArrayList){
                    if(TextUtils.equals(userPledge.getKey(), key)){
                        pledgeArrayList.remove(userPledge);//removing the old one
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //n/a to our application
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        initializeSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                adapter.getFilter().filter("");
                if(position == All){
                } else if(position == RICHMOND){
                    adapter.getFilter().filter("RICHMOND");
                } else if(position == BURNABY){
                    adapter.getFilter().filter("BURNABY");
                } else if(position == 3){
                    adapter.getFilter().filter("VANCOUVER");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // do nothing
            }
        });

    }

    private void initializeSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);

        List<String> list = new ArrayList<>();

        list.add("All");
        list.add("Richmond");
        list.add("Burnaby");
        list.add("Vancouver");

        ArrayAdapter<String> adapterTwo = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterTwo);
    }

    public static Intent makeIntent(PledgeActivity pledgeActivity) {
        Intent intent = new Intent(pledgeActivity, ViewAllPledgeActivity.class);
        return intent;
    }
}
