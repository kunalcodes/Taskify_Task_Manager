package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateTaskActivity extends AppCompatActivity {

    private Button mBtnCreateTaskAdd;
    private EditText mEtCreateTaskTitle;
    private EditText mEtCreateTaskDescription;
    private EditText mEtCreateTaskDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        initViews();
        mBtnCreateTaskAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setNewTask = new Intent(CreateTaskActivity.this, HomeActivity.class);
                setNewTask.putExtra("Title", mEtCreateTaskTitle.getText().toString());
                setNewTask.putExtra("Description", mEtCreateTaskDescription.getText().toString());
                setNewTask.putExtra("Date", mEtCreateTaskDate.getText().toString());
                setNewTask.putExtra("Time", "Time");
                setNewTask.putExtra("isComplete", false);
                startActivity(setNewTask);
            }
        });
    }

    private void initViews() {
        mBtnCreateTaskAdd = findViewById(R.id.btnCreateTaskAdd);
        mEtCreateTaskDate = findViewById(R.id.etCreateTaskDate);
        mEtCreateTaskTitle = findViewById(R.id.etCreateTaskTitle);
        mEtCreateTaskDescription = findViewById(R.id.etCreateTaskDescription);
    }
}