package com.example.taskmanager_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenPage extends AppCompatActivity {
    TextView mtvSplash;
    ImageView mIvSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_page);

        mtvSplash=findViewById(R.id.TextViewSplashScreen);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
        mtvSplash.setAnimation(animation);
        mtvSplash.startAnimation(animation);

        mIvSplash=findViewById(R.id.imageViewSplashScreen);
        Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
        mIvSplash.setAnimation(animation1);
        mIvSplash.startAnimation(animation1);

        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(000);
                    Intent intent=new Intent(getApplicationContext(),OnBoardingActivity.class);
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