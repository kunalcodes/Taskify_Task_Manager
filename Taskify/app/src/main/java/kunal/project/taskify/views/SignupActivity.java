package kunal.project.taskify.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kunal.project.taskify.R;
import kunal.project.taskify.utils.PreferenceHelper;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtSignUpEmail;
    private EditText mEtSignUpPassword;
    private EditText mEtSignUpRePassword;
    private Button mBtnSignUpCreateAccount;
    private TextView mTvSignUpLoadingText;
    private FirebaseAuth mAuth;
    private ImageButton mViewSignUpBack;
    private String REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViewsAndClickListeners();
    }

    private void initViewsAndClickListeners() {
        mEtSignUpEmail = findViewById(R.id.etSignUpEmail);
        mEtSignUpPassword = findViewById(R.id.etSignUpPassword);
        mEtSignUpRePassword = findViewById(R.id.etSignUpRePassword);
        mBtnSignUpCreateAccount = findViewById(R.id.btnSignUpCreateAccount);
        mBtnSignUpCreateAccount.setOnClickListener(this);
        mTvSignUpLoadingText = findViewById(R.id.tvSignUpLoadingText);
        mViewSignUpBack = findViewById(R.id.viewSignUpBack);
        mViewSignUpBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSignUpCreateAccount:
                if (isEmailValid() && isPasswordValid()) {
                    createNewUser();
                }
                break;
            case R.id.viewSignUpBack:
                onBackPressed();
                break;
        }
    }


    private void createNewUser() {
        mTvSignUpLoadingText.setVisibility(View.VISIBLE);
        String email = mEtSignUpEmail.getText().toString();
        String password = mEtSignUpPassword.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            mTvSignUpLoadingText.setVisibility(View.INVISIBLE);
                            Intent goToHome = new Intent(SignupActivity.this, HomeActivity.class);
                            startActivity(goToHome);
                            finish();
                        } else {
                            mTvSignUpLoadingText.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignupActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean isPasswordValid() {
        if (mEtSignUpPassword.getText().toString().length() >= 6) {
            if (mEtSignUpPassword.getText().toString().matches(mEtSignUpRePassword.getText().toString())) {
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
                mEtSignUpEmail.getText().toString().matches(REGEX)) {
            return true;
        } else {
            mEtSignUpEmail.setError("Enter a valid email");
            return false;
        }
    }
}