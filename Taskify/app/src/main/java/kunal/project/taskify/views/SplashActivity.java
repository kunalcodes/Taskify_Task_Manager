package kunal.project.taskify.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import kunal.project.taskify.R;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkLoginStatus();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void checkLoginStatus() {
        if (mAuth.getCurrentUser()==null){
            intent = new Intent(SplashActivity.this, OnBoardingActivity.class);
            goToNextActivity();
        } else {
            Toast.makeText(SplashActivity.this, "USER LOGGED IN", Toast.LENGTH_SHORT).show();
            intent = new Intent(SplashActivity.this, HomeActivity.class);
            goToNextActivity();
        }
    }

    private void goToNextActivity() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}