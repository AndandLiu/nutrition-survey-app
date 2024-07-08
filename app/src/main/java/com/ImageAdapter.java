package com.jla388.sfu.greenfoodchallenge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Meal> mMeals;

    public ImageAdapter(Context context,List<Meal>meals){
        mContext = context;
        mMeals = meals;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        Meal current_meal = mMeals.get(i);
        imageViewHolder.textViewName.setText(current_meal.getMealName());
        Picasso.with(mContext)
                .load(current_meal.getPictureOfMeal())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageViewHolder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mMeals.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public ImageView mImageView;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_meal_name);
            mImageView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}
