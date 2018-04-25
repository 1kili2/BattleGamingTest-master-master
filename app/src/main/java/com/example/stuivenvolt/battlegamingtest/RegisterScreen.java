package com.example.stuivenvolt.battlegamingtest;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String email, password;
    private EditText nameET, surnameET,apodoET, emailET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_register_screen);
        emailET = (EditText) findViewById(R.id.user_email);
        passwordET = (EditText) findViewById(R.id.user_password);
        nameET = (EditText) findViewById(R.id.user_name);
        surnameET = (EditText) findViewById(R.id.user_surname);

    }

    private void Register(){
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        if(email != null && password != null) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                public static final String TAG = "";

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        try{
                            this.wait(10000);
                        }catch (Exception e){
                            Log.w(TAG, e);
                        }
                        Login(email, password);
                        try{
                            this.wait(10000);
                        }catch (Exception e){
                            Log.w(TAG, e);
                        }
                        SetInfo();
                        } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterScreen.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        }

                        // ...
                }
            });
        }
    }

    private void Login(String mail, String pass){
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public static final String TAG = "";

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(RegisterScreen.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(RegisterScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void SetInfo(){
        String username = nameET.getText().toString() + " " + surnameET.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build();
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("profile: ", "User profile updated.");
                            }
                        }
                    });

        } else {
            // No user is signed in
            Toast.makeText(RegisterScreen.this, "No user Logged in.", Toast.LENGTH_LONG).show();
        }
    }

    public void Register_User(View view) {
        Register();
    }

    public void Login_User(View view) {
        /*email = emailET.getText().toString();
        password = passwordET.getText().toString();
        Login(email, password);*/
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }
}
