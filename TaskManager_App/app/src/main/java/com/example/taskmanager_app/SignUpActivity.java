package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {
private View mViewSignUpBack;
private Button mBtnSignUpCreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        clickListener();


    }

    private void clickListener() {
        mBtnSignUpCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);

            }
        });
        mViewSignUpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),OnBoardingActivity.class);
                startActivity(intent);

            }
        });
    }

    private void initView() {
        mViewSignUpBack =findViewById(R.id.viewSignUpBack);
        mBtnSignUpCreateAccount=findViewById(R.id.btnSignUpCreateAccount);
    }
}