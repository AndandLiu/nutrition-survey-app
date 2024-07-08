package com.jla388.sfu.greenfoodchallenge.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jla388.sfu.greenfoodchallenge.R;

public class SignInActivity extends AppCompatActivity {

    private Button userSignInSignUp;
    private Button signInBtn;
    private EditText signInEmail;
    private EditText signInPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        signInBtn = (Button) findViewById(R.id.userSignInButton);
        userSignInSignUp = (Button) findViewById(R.id.userSignInButtonSignUp);


        signInEmail = (EditText) findViewById(R.id.userSignInEmail);
        signInPassword = (EditText) findViewById(R.id.userSignInPassword);

        Log.d("testing2222", "signin page");
    }

    public void signInUser(View view) {
        String email = signInEmail.getText().toString();
        String password = signInPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this,"One of the credentials box is empty. Try Again", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Toast.makeText(this, "Logging in User", Toast.LENGTH_SHORT).show();

        }
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignInActivity.this, "Userlogging in " , Toast.LENGTH_SHORT).show();


                            //intent new activity  here -- welcome page
                            //loginUser();
                            startActivity(WelcomeActivity.makeIntent(SignInActivity.this));
                        }
                        else{
                            if(task.getException().getLocalizedMessage() == "The password is invalid or the user does not have a password."){
                                Toast.makeText(SignInActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                signInPassword.setText("");
                                signInPassword.setHint("Wrong Password - try again");
                            }
                            else {
                                Toast.makeText(SignInActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void loginUser() {
        Intent intent = WelcomeActivity.makeIntent(SignInActivity.this);
        startActivity(intent);
    }


    public static Intent makeIntent(SignUpActivity secondaryActivity) {
        Intent intent = new Intent(secondaryActivity, SignInActivity.class);
        return intent;

    }


    public void openRegistrationPage(View view) {
        Intent intent = SignUpActivity.makeIntent(SignInActivity.this);
        startActivity(intent);
    }

}
