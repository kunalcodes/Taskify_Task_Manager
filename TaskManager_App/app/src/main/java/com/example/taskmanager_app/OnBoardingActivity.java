package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OnBoardingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnOnBoardingCreateAccount;
    private TextView mTvOnBoardingLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        int LoginStatus =PreferenceHelper.getIntFromPreference(OnBoardingActivity.this,"LoginStatus");
        if (LoginStatus == 1){
            Intent goToHome = new Intent(OnBoardingActivity.this, HomeActivity.class);
            startActivity(goToHome);
        }

        initViews();
        mTvOnBoardingLogin.setOnClickListener(this);
        mBtnOnBoardingCreateAccount.setOnClickListener(this);
    }

    private void initViews() {
        mBtnOnBoardingCreateAccount = findViewById(R.id.btnOnBoardingCreateAccount);
        mTvOnBoardingLogin = findViewById(R.id.tvOnBoardingLogin);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnOnBoardingCreateAccount:
                Intent goToSignUp = new Intent(OnBoardingActivity.this, SignUpActivity.class);
                startActivity(goToSignUp);
                break;
            case R.id.tvOnBoardingLogin:
                Intent goToLogIn = new Intent(OnBoardingActivity.this, LogInActivity.class);
                startActivity(goToLogIn);
                break;
        }
    }
}