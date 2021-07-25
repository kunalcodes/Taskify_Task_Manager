package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtLogInEmail;
    private EditText mEtLogInPassword;
    private Button mBtnLogIn;
    private final String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initViews();
        mBtnLogIn.setOnClickListener(this);
    }

    private void initViews() {
        mEtLogInEmail = findViewById(R.id.etLogInEmail);
        mEtLogInPassword = findViewById(R.id.etLogInPassword);
        mBtnLogIn = findViewById(R.id.btnLogIn);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLogIn:
                if (isEmailValid() && isPasswordValid()) {
                    Intent goToHome = new Intent(LogInActivity.this, HomeActivity.class);
                    goToHome.putExtra("Username", mEtLogInEmail.getText().toString());
                    startActivity(goToHome);
                }
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
                mEtLogInEmail.getText().toString().matches(emailRegex)){
            return true;
        } else {
            mEtLogInEmail.setError("Enter a valid email");
            return false;
        }
    }
}