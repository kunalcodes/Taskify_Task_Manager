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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtSignUpEmail;
    private EditText mEtSignUpPassword;
    private EditText mEtSignUpRePassword;
    private Button mBtnSignUpCreateAccount;
    private TextView mTvSignUpLoadingText;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference node;
    private String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseDatabase = FirebaseDatabase.getInstance("https://taskmanagerapp-1407d-default-rtdb.firebaseio.com/");
        initViews();
        mBtnSignUpCreateAccount.setOnClickListener(this);

    }

    private void initViews() {
        mEtSignUpEmail = findViewById(R.id.etSignUpEmail);
        mEtSignUpPassword = findViewById(R.id.etSignUpPassword);
        mEtSignUpRePassword = findViewById(R.id.etSignUpRePassword);
        mBtnSignUpCreateAccount = findViewById(R.id.btnSignUpCreateAccount);
        mTvSignUpLoadingText = findViewById(R.id.tvSignUpLoadingText);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSignUpCreateAccount:
                if (isEmailValid() && isPasswordValid()) {
                    mTvSignUpLoadingText.setVisibility(View.VISIBLE);

                    String emailInput = mEtSignUpEmail.getText().toString();
                    String password = mEtSignUpPassword.getText().toString();

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(emailInput, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        mTvSignUpLoadingText.setVisibility(View.INVISIBLE);
                                        PreferenceHelper.writeIntToPreference(SignUpActivity.this, "LoginStatus", 1);
                                        PreferenceHelper.writeStringToPreference(SignUpActivity.this, "Username", mEtSignUpEmail.getText().toString());
                                        node = firebaseDatabase.getReference("Users");
                                        String Username = mEtSignUpEmail.getText().toString().replace(".", "");
                                        node.child(Username).child("username").setValue(mEtSignUpEmail.getText().toString().trim());
                                        node.child(Username).child("password").setValue(mEtSignUpPassword.getText().toString());
                                        Intent goToHome = new Intent(SignUpActivity.this, HomeActivity.class);
                                        startActivity(goToHome);
                                    } else {
                                        mTvSignUpLoadingText.setVisibility(View.INVISIBLE);
                                        Toast.makeText(SignUpActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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