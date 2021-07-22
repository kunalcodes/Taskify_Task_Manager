package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtSignUpEmail;
    private EditText mEtSignUpPassword;
    private EditText mEtSignUpRePassword;
    private Button mBtnSignUpCreateAccount;
    private String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
        mBtnSignUpCreateAccount.setOnClickListener(this);

    }

    private void initViews() {
        mEtSignUpEmail = findViewById(R.id.etSignUpEmail);
        mEtSignUpPassword = findViewById(R.id.etSignUpPassword);
        mEtSignUpRePassword = findViewById(R.id.etSignUpRePassword);
        mBtnSignUpCreateAccount = findViewById(R.id.btnSignUpCreateAccount);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSignUpCreateAccount:
                if (isEmailValid() && isPasswordValid()) {
                    Intent goToHome = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(goToHome);
                }
                break;
        }
    }

    private boolean isPasswordValid() {
        if (mEtSignUpPassword.getText().toString().length() >= 6) {
            if (mEtSignUpPassword.getText().toString().matches(mEtSignUpRePassword.getText().toString())){
                return true;
            } else {
                mEtSignUpRePassword.setError("Passwords must be same");
                return false;
            }
        } else {
            mEtSignUpPassword.setError("Password should contain at least 6 characters");
            return false;
        }
    }

    private boolean isEmailValid() {
        if (mEtSignUpEmail.getText().toString().length() >= 1 &&
                mEtSignUpEmail.getText().toString().matches(emailRegex)){
            return true;
        } else {
            mEtSignUpEmail.setError("Enter a valid email");
            return false;
        }
    }
}