package kunal.project.taskify.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtLogInEmail;
    private EditText mEtLogInPassword;
    private Button mBtnLogIn;
    private ImageButton mViewLogInBack;
    private TextView mTvLogInLoadingText;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference node;
    private String REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseDatabase = FirebaseDatabase.getInstance();
        initViewsAndClickListeners();
    }

    private void initViewsAndClickListeners() {
        mEtLogInEmail = findViewById(R.id.etLogInEmail);
        mEtLogInPassword = findViewById(R.id.etLogInPassword);
        mBtnLogIn = findViewById(R.id.btnLogIn);
        mBtnLogIn.setOnClickListener(this);
        mTvLogInLoadingText = findViewById(R.id.tvLogInLoadingText);
        mViewLogInBack = findViewById(R.id.viewLogInBack);
        mViewLogInBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLogIn:
                if (isEmailValid() && isPasswordValid()) {
                    loginUser();
                }
                break;
            case R.id.viewLogInBack:
                onBackPressed();
                break;
        }
    }

    private void loginUser() {
        mTvLogInLoadingText.setVisibility(View.VISIBLE);
        String email = mEtLogInEmail.getText().toString();
        String password = mEtLogInPassword.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            mTvLogInLoadingText.setVisibility(View.INVISIBLE);
                            Intent goToHome = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(goToHome);
                            finish();
                        } else {
                            mTvLogInLoadingText.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                mEtLogInEmail.getText().toString().matches(REGEX)) {
            return true;
        } else {
            mEtLogInEmail.setError("Enter a valid email");
            return false;
        }
    }
}