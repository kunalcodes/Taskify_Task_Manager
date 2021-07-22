package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OnBoardingActivity extends AppCompatActivity {
    private Button mBtnOnBoardingCreateAccount;
    private TextView mTvOnBoardingLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        initView();
        mBtnOnBoardingCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });
        mTvOnBoardingLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),LogInActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void initView() {
        mBtnOnBoardingCreateAccount=findViewById(R.id.btnOnBoardingCreateAccount);
        mTvOnBoardingLogin=findViewById(R.id.tvOnBoardingLogin);
    }
}