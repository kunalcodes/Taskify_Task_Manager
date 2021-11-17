package kunal.project.taskify.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kunal.project.taskify.R;

public class OnBoardingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnOnBoardingCreateAccount;
    private TextView mTvOnBoardingLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        initViewsAndClickListeners();
    }

    private void initViewsAndClickListeners() {
        mBtnOnBoardingCreateAccount = findViewById(R.id.btnOnBoardingCreateAccount);
        mBtnOnBoardingCreateAccount.setOnClickListener(this);
        mTvOnBoardingLogin = findViewById(R.id.tvOnBoardingLogin);
        mTvOnBoardingLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnOnBoardingCreateAccount:
                Intent goToSignUp = new Intent(OnBoardingActivity.this, SignupActivity.class);
                startActivity(goToSignUp);
                break;
            case R.id.tvOnBoardingLogin:
                Intent goToLogIn = new Intent(OnBoardingActivity.this, LoginActivity.class);
                startActivity(goToLogIn);
                break;
        }
    }
}