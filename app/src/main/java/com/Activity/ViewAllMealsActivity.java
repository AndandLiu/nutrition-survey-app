package com.jla388.sfu.greenfoodchallenge.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jla388.sfu.greenfoodchallenge.CustomUserAdapter;
import com.jla388.sfu.greenfoodchallenge.Meal;
import com.jla388.sfu.greenfoodchallenge.MealAdapter;
import com.jla388.sfu.greenfoodchallenge.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAllMealsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    private ArrayList<Meal> mealArrayList;
    private ExpandableListView mealListView;
    private MealAdapter adapter;
    private Spinner spinner;
    private boolean firstTime = true;


    private void initializeSpinner(){
        spinner = findViewById(R.id.spinner_meal);

        List<String> list = new ArrayList<>();
        list.add("All");
        list.add("Richmond");
        list.add("Burnaby");
        list.add("Vancouver");
        list.add("Protein");
        ArrayAdapter<String> adapterTwo = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterTwo);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_meals);

        mealArrayList = new ArrayList<Meal>();
        mealListView =  findViewById(R.id.view_all_meal_list);

        adapter = new MealAdapter(this,mealArrayList);
        mealListView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("testing2222", "new child added");
                //Firebase code here then add meal
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    mealArrayList.add(snapshot.getValue(Meal.class));

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //You click on this it expands
        mealListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
        initializeSpinner();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(position == 0 ){
                    if(firstTime)
                        firstTime = false;
                    else {
                        adapter.getFilter().filter("");
                    }
                } else if(position == 1){
                    adapter.getFilter().filter("RICHMOND");
                } else if(position == 2){
                    adapter.getFilter().filter("BURNABY");
                } else if(position == 3){
                    adapter.getFilter().filter("VANCOUVER");
                } else if(position == 4){
                    //filter by protein
                    adapter.getFilter().filter("Protein");


                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // do nothing
            }
        });




    }


    public static Intent makeIntent(MealActivity mealActivity) {
        Intent intent = new Intent(mealActivity, ViewAllMealsActivity.class);
        return intent;
    }
}
