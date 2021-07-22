package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<TaskModel> taskModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView mTvHomeExHeyUser;
    private String username;
    private String Title;
    private String Description;
    private String Date;
    private String Time;
    private boolean isComplete;
    private Button mBtnHomeExAdd;
    private TaskAdapter taskAdapter;
    private ImageView mIvHomeExUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null){
            username = intent.getStringExtra("Username");
            Title = intent.getStringExtra("Title");
            Description = intent.getStringExtra("Description");
            Date = intent.getStringExtra("Title");
            Time = intent.getStringExtra("Time");
            isComplete = intent.getBooleanExtra("isComplete", false);
        }
        initViews();
        buildRecyclerViewData();
        setRecyclerViewAdapter();
        mIvHomeExUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile=new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(goToProfile);
            }
        });
        mBtnHomeExAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createNewTask = new Intent(HomeActivity.this, CreateTaskActivity.class);
                startActivity(createNewTask);
                taskModelList.add(new TaskModel(Title, Description, Date, Time, isComplete));
                taskAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setRecyclerViewAdapter() {
        taskAdapter = new TaskAdapter(taskModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void buildRecyclerViewData() {
        taskModelList.add(new TaskModel(Title, Description, Date, Time, isComplete));
    }

    private void initViews() {
        mIvHomeExUser=findViewById(R.id.ivHomeExUser);
        recyclerView = findViewById(R.id.recyclerView);
        mBtnHomeExAdd = findViewById(R.id.btnHomeExAdd);
        mTvHomeExHeyUser = findViewById(R.id.tvHomeExHeyUser);
        mTvHomeExHeyUser.setText("Hey "+username+",");
    }
}