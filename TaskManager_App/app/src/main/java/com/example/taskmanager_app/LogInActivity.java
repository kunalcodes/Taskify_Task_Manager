package com.example.taskmanager_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtLogInEmail;
    private EditText mEtLogInPassword;
    private Button mBtnLogIn;
    private View mViewLogInBack;
    private TextView mTvLogInLoadingText;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference node;
    private String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        firebaseDatabase = FirebaseDatabase.getInstance("https://taskmanagerapp-1407d-default-rtdb.firebaseio.com/");
        initViews();
        mBtnLogIn.setOnClickListener(this);
        mViewLogInBack.setOnClickListener(this);
    }

    private void initViews() {
        mEtLogInEmail = findViewById(R.id.etLogInEmail);
        mEtLogInPassword = findViewById(R.id.etLogInPassword);
        mBtnLogIn = findViewById(R.id.btnLogIn);
        mTvLogInLoadingText = findViewById(R.id.tvLogInLoadingText);
        mViewLogInBack = findViewById(R.id.viewLogInBack);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLogIn:
                if (isEmailValid() && isPasswordValid()) {

                    mTvLogInLoadingText.setVisibility(View.VISIBLE);

                    String emailInput = mEtLogInEmail.getText().toString();
                    String password = mEtLogInPassword.getText().toString();

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(emailInput, password)
                            .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        mTvLogInLoadingText.setVisibility(View.INVISIBLE);
                                        PreferenceHelper.writeIntToPreference(LogInActivity.this, "LoginStatus", 1);
                                        PreferenceHelper.writeStringToPreference(LogInActivity.this, "Username", mEtLogInEmail.getText().toString());
                                        node = firebaseDatabase.getReference("Users");
                                        String Username = mEtLogInEmail.getText().toString().replace(".", "");
//                                        node.child(Username).child("username").setValue(mEtLogInEmail.getText().toString().trim());
//                                        node.child(Username).child("password").setValue(mEtLogInPassword.getText().toString());
                                        Intent goToHome = new Intent(LogInActivity.this, HomeActivity.class);
                                        startActivity(goToHome);
                                    } else {
                                        mTvLogInLoadingText.setVisibility(View.INVISIBLE);
                                        Toast.makeText(LogInActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    break;
                }
                break;
            case R.id.viewLogInBack:
                Intent goToOnBoarding = new Intent(LogInActivity.this, OnBoardingActivity.class);
                startActivity(goToOnBoarding);
                break;
        }
    }

    private boolean isPasswordValid() {
        if (mEtLogInPassword.getText().toString().length() >= 6) {
            return true;
        } else {
            mEtLogInPassword.setError("Password should contain at least 6 characters");
            return false;
        }
    }

    private boolean isEmailValid() {
        if (mEtLogInEmail.getText().toString().length() >= 1 &&
                mEtLogInEmail.getText().toString().matches(emailRegex)) {
            return true;
        } else {
            mEtLogInEmail.setError("Enter a valid email");
            return false;
        }
    }
}