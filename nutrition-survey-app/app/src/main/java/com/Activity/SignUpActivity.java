package com.jla388.sfu.greenfoodchallenge.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jla388.sfu.greenfoodchallenge.R;
import com.jla388.sfu.greenfoodchallenge.UserInfo;

public class SignUpActivity extends AppCompatActivity {

    private Button signUpBtn;
    private Button UserSignInSignUp;
    private EditText signUpEmail;
    private EditText signUpPassword;
    private EditText signUpFirstName;
    private EditText signUpLastName;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressBar = new ProgressBar(this);

        firebaseAuth = FirebaseAuth.getInstance();

        signUpBtn = (Button) findViewById(R.id.userSignUpButtonSignIn);
        UserSignInSignUp = (Button) findViewById(R.id.userSignUpButtonSignIn);

        signUpEmail = (EditText) findViewById(R.id.userSignUpEmail);
        signUpPassword = (EditText) findViewById(R.id.userSignUpPassword);
        signUpFirstName = (EditText) findViewById(R.id.userFirstName);
        signUpLastName = (EditText) findViewById(R.id.userLastName);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    public void registerUser(final View view) {
        String email = signUpEmail.getText().toString();
        String password = signUpPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this,"One of the credentials box is empty. Try Again", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Toast.makeText(this, "Registering User", Toast.LENGTH_SHORT).show();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "Registration Successful" , Toast.LENGTH_SHORT).show();
                                createNewUserInTheList();
                            }
                            else {
                                if(task.getException().getLocalizedMessage() == "The email is already in use by another account."){
                                    Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage() + "Try Logging-in", Toast.LENGTH_SHORT).show();
                                    UserSignInSignUp.performClick();
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

        }


    }

    private void createNewUserInTheList() {
        key = firebaseAuth.getCurrentUser().getUid();
        UserInfo newRegisteredUser = new UserInfo();
        newRegisteredUser.setUserName(signUpFirstName.getText() + " " + signUpLastName.getText());
        newRegisteredUser.setUserKey(key);

        databaseReference.child(key).setValue(newRegisteredUser);

    }

    public void openSignInPage(View view) {
        Intent intent = SignInActivity.makeIntent(SignUpActivity.this);
        startActivity(intent);
    }

    public static Intent makeIntent(SignInActivity mainActivity) {
        Intent intent = new Intent(mainActivity, SignUpActivity.class);
        return intent;
    }
}
