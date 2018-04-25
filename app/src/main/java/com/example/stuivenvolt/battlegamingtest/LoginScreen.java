package com.example.stuivenvolt.battlegamingtest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
    private ImageButton ibLogin;
    FirebaseUser user;
    private boolean isBig = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
        passwordET = (EditText) findViewById(R.id.login_password);
        emailET = (EditText) findViewById(R.id.login_email);
        ibLogin = (ImageButton) findViewById(R.id.imageButton3);
        super.onCreate(savedInstanceState);
    }

    public void Login_User(View view) {
        if(emailET.getText().toString().equals("") || passwordET.getText().toString().equals("")) {
            Toast.makeText(LoginScreen.this, R.string.empty_login_field, Toast.LENGTH_SHORT).show();
        }else{
            changebutton();
            email = emailET.getText().toString();
            password = passwordET.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Login Succes:", "signInWithEmail:success");
                                user = mAuth.getCurrentUser();
                                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                                HideLoadingAnimation();
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Login Fail:", "signInWithEmail:failure", task.getException());
                                HideLoadingAnimation();
                                Toast.makeText(LoginScreen.this, R.string.failed_login_message, Toast.LENGTH_SHORT).show();
                                changebutton();
                            }
                        }
                    });
        }
    }
    //show
    public void ShowLoadingAnimation()
    {
        RelativeLayout pageLoading = (RelativeLayout) findViewById(R.id.main_layoutPageLoading);
        pageLoading.setVisibility(View.VISIBLE);
    }

    //hide
    public void HideLoadingAnimation()
    {
        RelativeLayout pageLoading = (RelativeLayout) findViewById(R.id.main_layoutPageLoading);
        pageLoading.setVisibility(View.GONE);
    }

    private void changebutton(){
        if(isBig == false) {
            ibLogin.setImageResource(R.drawable.young_viking_200);
            isBig = true;
            ShowLoadingAnimation();
        }else{
            ibLogin.setImageResource(R.drawable.veteran_viking_200);
            isBig = false;
        }
    }
}
