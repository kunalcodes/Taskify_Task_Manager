package com.example.taskmanager_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements ItemClickListener {

    private ArrayList<TaskModel> taskModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView mTvHomeExHeyUser;
    private String username;
    private String CurrentUser;
    private Button mBtnHomeExAdd;
    private TaskAdapter taskAdapter;
    private ImageView mIvHomeExUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference node;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        username = PreferenceHelper.getStringFromPreference(HomeActivity.this, "Username");
        CurrentUser = username.replace(".", "");
        firebaseDatabase = FirebaseDatabase.getInstance("https://taskmanagerapp-1407d-default-rtdb.firebaseio.com/");
        node = firebaseDatabase.getReference("Users/"+CurrentUser+"/Tasks");
        initViews();
        setRecyclerViewAdapter();
        buildRecyclerViewData();

        mIvHomeExUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(goToProfile);
            }
        });
        mBtnHomeExAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createNewTask = new Intent(HomeActivity.this, CreateTaskActivity.class);
                startActivity(createNewTask);
            }
        });
    }

    private void setRecyclerViewAdapter() {
        taskAdapter = new TaskAdapter(taskModelList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void buildRecyclerViewData() {
        node.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskModelList.clear();

                for (DataSnapshot taskDataSnapshot : snapshot.getChildren()) {

                    TaskModel taskModel = taskDataSnapshot.getValue(TaskModel.class);
                    taskModelList.add(taskModel);
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initViews() {
        mIvHomeExUser = findViewById(R.id.ivHomeExUser);
        recyclerView = findViewById(R.id.recyclerView);
        mBtnHomeExAdd = findViewById(R.id.btnHomeExAdd);
        mTvHomeExHeyUser = findViewById(R.id.tvHomeExHeyUser);
        mTvHomeExHeyUser.setText("Hey " + username + ",");
    }

    @Override
    public void onDeleteClicked(int position) {
        String Task = taskModelList.get(position).getTitle().trim();
        node.child(Task).removeValue();
    }


    @Override
    public void onUpdateClicked(int position) {
        String Task = taskModelList.get(position).getTitle().trim();
        Intent editTask = new Intent(HomeActivity.this, EditTaskActivity.class);
        editTask.putExtra("Task", Task);
        editTask.putExtra("Title", taskModelList.get(position).getTitle());
        editTask.putExtra("Description", taskModelList.get(position).getDescription());
        editTask.putExtra("Date", taskModelList.get(position).getDate());
        editTask.putExtra("Status", taskModelList.get(position).isComplete());
        startActivity(editTask);
    }
}