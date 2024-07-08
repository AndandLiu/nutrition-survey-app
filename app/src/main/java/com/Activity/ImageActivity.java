package com.jla388.sfu.greenfoodchallenge.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jla388.sfu.greenfoodchallenge.ImageAdapter;
import com.jla388.sfu.greenfoodchallenge.Meal;
import com.jla388.sfu.greenfoodchallenge.R;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressBar;

    private DatabaseReference mDatabaseReference;
    private List<Meal> mMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progress_circle);

        mMeals = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Meal meal = postSnapshot.getValue(Meal.class);
                    mMeals.add(meal);
                }

                mAdapter = new ImageAdapter(ImageActivity.this,mMeals);
                mRecyclerView.setAdapter(mAdapter);
                mProgressBar.setVisibility(mRecyclerView.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ImageActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(mRecyclerView.INVISIBLE);

            }
        });
    }
}
