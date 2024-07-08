package com.jla388.sfu.greenfoodchallenge.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.jla388.sfu.greenfoodchallenge.Meal;
import com.jla388.sfu.greenfoodchallenge.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class NewMealActivity extends AppCompatActivity {

    private Button button_upload;
    private Uri filePath;
    private ImageView mealImage;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    private TextView mtext_view_show_uploads;
    private EditText medit_text_meal_name;
    private EditText mMeal_protein;
    private EditText mRestaurant_name;
    private EditText mDescription;
    private double finalFoodAmount;
    private SeekBar foodAmount;

    //firebase
    FirebaseStorage storage;
    private StorageReference storageRef;
    private DatabaseReference DatabaseRef;
    private StorageTask mUploadTask;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private ArrayList<Meal> testArrayList = new ArrayList<>();

    //location
    private FusedLocationProviderClient client;
    Geocoder mGeocoder;
    double curr_longitude;
    double curr_laltitude;
    List<Address> curr_address;
    private String address="none";
    private String area;
    private String city;
    private String country;
    private String postalCode;
    private String fullAddress="n/a";

    private TextView foodAmountText;
    private boolean isLocationGot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal);

        mGeocoder = new Geocoder(this, Locale.getDefault());

        requestLocationPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(NewMealActivity.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(NewMealActivity.this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            client.getLastLocation().addOnSuccessListener(NewMealActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        curr_longitude = location.getLongitude();
                        curr_laltitude = location.getLatitude();
                        try{
                            curr_address = mGeocoder.getFromLocation(curr_laltitude,curr_longitude,1);
                            address = curr_address.get(0).getAddressLine(0);
                            area = curr_address.get(0).getLocality();
                            city = curr_address.get(0).getAdminArea();
                            country = curr_address.get(0).getCountryName();
                            postalCode = curr_address.get(0).getPostalCode();
                            //fullAddress = area+", "+city+", "+country+", "+postalCode;
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                }
            });

        }


        Button GetLocation = findViewById(R.id.locate_button);
        GetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView locationInfo = findViewById(R.id.text_view_location);
                locationInfo.setText(fullAddress);


                fullAddress=address;
                isLocationGot = true;
                locationInfo.setText(address);


            }
        });

        /*
        Button SeeMap = findViewById(R.id.test_map_bt);
        SeeMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(NewMealActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });*/

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        //firebase init
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference();
        DatabaseRef = FirebaseDatabase.getInstance().getReference("uploads").child(mFirebaseUser.getUid());
        //meal info
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        foodAmount = (SeekBar) findViewById(R.id.mealAmount);
        foodAmountText = (TextView) findViewById(R.id.foodAmountText);
        foodAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int amount = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                amount = progress;
                finalFoodAmount = progress;
                String editText = "Enter the proportions in grams";
                foodAmountText.setText( editText + " " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //food amount started tracking
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //food amount stopped tracking
            }
        });

        mealImage = (ImageView) findViewById(R.id.meal_image);
        button_upload = (Button) findViewById(R.id.upload_button);
        mtext_view_show_uploads = findViewById(R.id.text_view_show_uploads);
        medit_text_meal_name = findViewById(R.id.edit_text_meal_name);
        mMeal_protein = findViewById(R.id.edit_text_protein);
        mRestaurant_name = findViewById(R.id.edit_text_restaurant);
        mDescription = findViewById(R.id.edit_text_description);
        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(NewMealActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImage();
                }
            }
        });

        mtext_view_show_uploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                SelectImage();
            }
        });
    }

    private void mealInfoUpload(Meal newMeal) {
        Meal newAddedMeal = new Meal();
        //should be inverse?
        newAddedMeal = newMeal;
        DatabaseRef.child(newAddedMeal.getMealName()).setValue(newAddedMeal);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadImage() {

        if (filePath == null) {
            Toast.makeText(NewMealActivity.this, "no image selected", Toast.LENGTH_SHORT).show();
        }

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            final Meal meal = new Meal();
            if (isEditTextEmpty(medit_text_meal_name) == false) {
                meal.setMealName(medit_text_meal_name.getText().toString().trim());

            }
            if (isEditTextEmpty(mMeal_protein) == false) {
                meal.setMealProtein(mMeal_protein.getText().toString().trim());

            }
            if (isEditTextEmpty(mRestaurant_name) == false) {
                meal.setNameOfRestaurant(mRestaurant_name.getText().toString().trim());

            }
            if (isEditTextEmpty(mDescription) == false) {
                meal.setMealDescription(mDescription.getText().toString().trim());

            }
            if(isLocationGot == true)
            {
                meal.setCurr_laltitude(curr_laltitude);
                meal.setCurr_longitude(curr_longitude);
            }

            meal.setRestaurantLocation(fullAddress);

            final StorageReference ref = storageRef.child(meal.getMealName());

            mUploadTask = ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            Toast.makeText(NewMealActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();


                            //meal.setPictureOfMeal(ref.getDownloadUrl().toString());
                            meal.setMealProteinAmount(finalFoodAmount);
                            meal.setPictureOfMeal(meal.getMealName());
                            mealInfoUpload(meal);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(NewMealActivity.this, "Upload Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });

            //final ImageView test = findViewById(R.id.imageView4);
            //Glide.with(test.getContext()).load(storageRef.child(meal.getMealName()).getDownloadUrl()).into(test);
            //Log.d("testing2222", "download url - " + url);





        }

    }

    private void SelectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(NewMealActivity.this);
        //builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == REQUEST_CAMERA) {


                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                mealImage.setImageBitmap(bmp);
                filePath = getImageUri(getApplicationContext(), bmp);

            } else if (requestCode == SELECT_FILE) {
                filePath = data.getData();
                Uri selectedImageUri = data.getData();
                Picasso.with(this).load(selectedImageUri).into(mealImage);


            }
        }
    }

    private Uri getImageUri(Context Context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(Context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void openImagesActivity() {
        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

    public static Intent makeIntent(MealActivity mealActivity) {
        Intent intent = new Intent(mealActivity, NewMealActivity.class);
        return intent;
    }

    private boolean isEditTextEmpty(EditText et) {
        if (et.getText().toString().trim().length() > 0) {
            return false;
        }
        return true;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

}
