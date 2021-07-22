package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {
    private Button mBtnHomeExAdd;
   private ImageView mIvHomeExUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mBtnHomeExAdd=findViewById(R.id.btnHomeExAdd);
        mBtnHomeExAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateNewTask=new Intent(getApplicationContext(),CreateTaskActivity.class);
                startActivity(goToCreateNewTask);
            }
        });
        mIvHomeExUser=findViewById(R.id.ivHomeExUser);
        mIvHomeExUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile=new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(goToProfile);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}