package com.jla388.sfu.greenfoodchallenge;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jla388.sfu.greenfoodchallenge.Activity.ViewAllMealsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//ENABLE FILTERING LATER
public class MealAdapter extends BaseExpandableListAdapter implements Filterable {

    private Context c;
    private List<Meal> meals;
    private List<Meal> mealOriginal;
    private LayoutInflater inflater;


    public MealAdapter(Context context, List<Meal> meals){
        this.c=c;
        this.meals = meals;
        //inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //this line causes crash
        inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int groupPos, int childPos) {
        // TODO Auto-generated method stub
        return meals.get(groupPos).getPictureAndDescription().get(childPos);
    }

    @Override
    public Object getGroup(int groupPos) {
        return meals.get(groupPos);
    }


    @Override
    public int getGroupCount() {
        return meals.size();
    }

    //Get number of children. Always 2 in our code however
    @Override
    public int getChildrenCount(int groupPosw) {
        // TODO Auto-generated method stub
        return meals.get(groupPosw).getPictureAndDescription().size();
    }


    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //ONLY INFLATE XML TEAM ROW MODEL IF ITS NOT PRESENT,OTHERWISE REUSE IT
        if(convertView == null){
            convertView=inflater.inflate(R.layout.meal_adapter, null);
        }

        TextView mealName = (TextView) convertView.findViewById(R.id.meal_name);
        TextView restaurantName = (TextView) convertView.findViewById(R.id.restaurant_name);
        TextView restaurantLocation = (TextView) convertView.findViewById(R.id.restaurant_location);
        TextView protein_amount = (TextView) convertView.findViewById(R.id.meal_protein);

        Meal meal = (Meal) getGroup(groupPosition);

        mealName.setText(meal.getMealName());
        restaurantName.setText(meal.getNameOfRestaurant());
        restaurantLocation.setText(" at " + meal.getRestaurantLocation());
        protein_amount.setText(" has " + meal.getMealProtein() + " protein");

        return convertView;
    }


    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView,
                             ViewGroup parent) {

        if(convertView==null) {
            convertView=inflater.inflate(R.layout.meal_adapter_expand, null);
        }

        Meal aMeal = (Meal) getGroup(groupPos);


        System.out.println( "SIZE OF ARRAY LIST IS " + aMeal.getPictureAndDescription().size());


        System.out.println(aMeal.getMealName());
        //TextView nameTv=(TextView) convertView.findViewById(R.id.textView1);
        final ImageView img =(ImageView) convertView.findViewById(R.id.imageView1);


        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        String a = storageReference.child(aMeal.getMealName()) + "";

        storageReference.child(aMeal.getMealName().trim()+"").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Log.d("testing2222", "download bs url - " + uri.toString());
                Glide.with(img.getContext()).load(uri).into(img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        //nameTv.setText(description);

        return convertView;
    }


    //unused
    @Override
    public long getGroupId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    //Not used
    @Override
    public long getChildId(int arg0, int arg1) {
        return 0;
    }

    //Not used
    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }





    static class MealSortComparator implements Comparator<Meal> {

        @Override
        public int compare(Meal customer1, Meal customer2) {

           String proteinA =  customer1.getMealProtein();
           String proteinB =  customer2.getMealProtein();

           if(proteinA.compareTo(proteinB) > 0){
               return 1;
           } else if(proteinA.compareTo(proteinB) < 0 ){
               return -1;
           } else{
               return 0;
           }
        }
    }

    private class CFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            //FILTERING DONE HERE
            FilterResults results = new FilterResults();
            ArrayList filteredMeal = new ArrayList<>();


            if(mealOriginal == null) {
                mealOriginal = new ArrayList<>();
                mealOriginal.addAll(meals); //deep copy
            }

            if(constraint.toString().equals("Protein")){
                Collections.sort(mealOriginal, new MealSortComparator());
                results.count = mealOriginal.size();
                results.values = mealOriginal;
                return results;
            }

            if(constraint == null || constraint.length() == 0){
                results.count = mealOriginal.size();
                results.values = mealOriginal;
                filteredMeal.addAll(mealOriginal);
            }  else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Meal meal : mealOriginal) {
                    if (meal.getRestaurantLocation().toLowerCase().contains(filterPattern)) {
                        filteredMeal.add(meal);
                    }
                }
                results.values = filteredMeal;
                results.count = filteredMeal.size();
            }
            return results;



        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Meal> filteredMeal = (List<Meal>) results.values;
            meals.clear();
            for(Meal meal : filteredMeal){
                meals.add(meal);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public Filter getFilter(){
        return new MealAdapter.CFilter();
    }




}
