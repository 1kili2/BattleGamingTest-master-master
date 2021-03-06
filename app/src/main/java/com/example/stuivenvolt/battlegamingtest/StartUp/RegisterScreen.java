package com.example.stuivenvolt.battlegamingtest.StartUp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.stuivenvolt.battlegamingtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    private String email, password;
    private EditText nameET, surnameET, /*apodoET,*/ emailET, passwordET, checkPasswordET;
    boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            if(user != null){
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            setContentView(R.layout.activity_register_screen);
            emailET = findViewById(R.id.user_email);
            passwordET = findViewById(R.id.user_password);
            nameET = findViewById(R.id.user_name);
            surnameET = findViewById(R.id.user_surname);
            checkPasswordET = findViewById(R.id.user_password_check);
        }else{
            Toast.makeText(RegisterScreen.this, "No internet connection, please check connection and reload the app!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_main_no_connection);
        }

    }

    private void Register(){
        ShowLoadingAnimation();
        email = emailET.getText().toString().toLowerCase();
        password = passwordET.getText().toString();
        if(!email.equals("") && !password.equals("")) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        private static final String TAG = "";

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");

                        Login(email, password);

                        SetInfo();
                        addToDatabase();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        HideLoadingAnimation();
                        Toast.makeText(RegisterScreen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                }
            });
        }else{
            Toast.makeText(RegisterScreen.this, R.string.empty_register_email_field, Toast.LENGTH_SHORT).show();
        }
    }

    private void Login(String mail, String pass){
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    private static final String TAG = "";
                    FirebaseUser user = mAuth.getCurrentUser();
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            user.sendEmailVerification();
                            HideLoadingAnimation();
                            Intent intent = new Intent(RegisterScreen.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            HideLoadingAnimation();
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
        if(emailET.getText().toString().contains("@") && emailET.getText().toString().contains(".")) {
            if (!passwordET.getText().toString().equals("")) {
                if (passwordET.getText().toString().length() >= 6) {
                    if (passwordET.getText().toString().equals(checkPasswordET.getText().toString())) {
                        Register();
                    } else {
                        Toast.makeText(RegisterScreen.this, R.string.password_mismatch, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegisterScreen.this, R.string.pass_length, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterScreen.this, R.string.empty_register_password_field, Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(RegisterScreen.this, R.string.valid_email, Toast.LENGTH_LONG).show();
        }
    }

    public void Login_User(View view) {
        /*email = emailET.getText().toString();
        password = passwordET.getText().toString();
        Login(email, password);*/
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }

    //show
    private void ShowLoadingAnimation()
    {
        RelativeLayout pageLoading = findViewById(R.id.main_layoutPageLoading);
        pageLoading.setVisibility(View.VISIBLE);
    }


    //hide
    private void HideLoadingAnimation()
    {
        RelativeLayout pageLoading = findViewById(R.id.main_layoutPageLoading);
        pageLoading.setVisibility(View.GONE);
    }

    private void addToDatabase(){
        String mail = emailET.getText().toString().replace("."," ");

        String name = nameET.getText().toString() + " " + surnameET.getText().toString();
        //Toast.makeText(RegisterScreen.this, mail, Toast.LENGTH_LONG).show();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("usuarios");

        //Toast.makeText(RegisterScreen.this, "in ondatachange", Toast.LENGTH_LONG).show();
        Log.e("database: ", "start database fill");

        myRef.child(mail).child("Public").child("Guild").setValue(null);
        myRef.child(mail).child("Public").child("IsSmith").setValue(false);
        myRef.child(mail).child("Public").child("Name").setValue(name);
        myRef.child(mail).child("Public").child("NickName").setValue("");
        myRef.child(mail).child("Public").child("Rank").setValue("Member");
        myRef.child(mail).child("Public").child("Motto").setValue(getString(R.string.profile_Motto));

        myRef.child(mail).child("Private").child("Phone Number").setValue("");
        myRef.child(mail).child("Private").child("Adress").setValue("");
        Log.e("database: ", "finish database fill");

            /*try {
                sleep(1300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
    }
}
