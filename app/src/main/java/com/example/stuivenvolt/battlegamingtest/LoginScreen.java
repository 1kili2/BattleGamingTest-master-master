package com.example.stuivenvolt.battlegamingtest;

import android.content.Intent;
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

public class LoginScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String email, password;
    private EditText emailET, passwordET;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
        passwordET = (EditText) findViewById(R.id.login_password);
        emailET = (EditText) findViewById(R.id.login_email);
        super.onCreate(savedInstanceState);
    }

    public void Login_User(View view) {
        if(emailET.getText().toString().equals("") || passwordET.getText().toString().equals("")) {
            Toast.makeText(LoginScreen.this,"Email: "+emailET.getText().toString()+"    Password: " +passwordET.getText().toString(), Toast.LENGTH_SHORT).show();
        }else{
            email = emailET.getText().toString();
            password = passwordET.getText().toString();
            Toast.makeText(LoginScreen.this,"authenticating", Toast.LENGTH_SHORT).show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Login Succes:", "signInWithEmail:success");
                                user = mAuth.getCurrentUser();
                                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Login Fail:", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginScreen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }




    }
}
