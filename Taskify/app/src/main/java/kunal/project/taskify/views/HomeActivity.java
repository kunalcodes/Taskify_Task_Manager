package kunal.project.taskify.views;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kunal.project.taskify.adapters.TaskAdapter;
import kunal.project.taskify.R;
import kunal.project.taskify.TaskModel;
import kunal.project.taskify.utils.TaskItemClickListener;

public class HomeActivity extends AppCompatActivity implements TaskItemClickListener, View.OnClickListener {

    private ArrayList<TaskModel> taskList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView mTvHomeExHeyUser;
    private String userName;
    private String mUid;
    private FloatingActionButton mBtnHomeExAdd;
    private TaskAdapter taskAdapter;
    private TextView mTvHomeFetchingData;
    private ImageView mIvHomeExUser;
    private DatabaseReference mNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userName = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mNode = FirebaseDatabase.getInstance().getReference();
        mNode.child(mUid).setValue("Hey");
        initViewsAndClickListeners();

        setRecyclerViewAdapter();
        mTvHomeFetchingData.setVisibility(View.VISIBLE);
        buildRecyclerViewData();
    }


    private void setRecyclerViewAdapter() {
        taskAdapter = new TaskAdapter(taskList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void buildRecyclerViewData() {
        mNode.child(mUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskList.clear();
                for (DataSnapshot taskDataSnapshot : snapshot.getChildren()) {
//                    TaskModel taskModel = taskDataSnapshot.getValue(TaskModel.class);
                }
                mTvHomeFetchingData.setVisibility(View.INVISIBLE);
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mTvHomeFetchingData.setVisibility(View.INVISIBLE);
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViewsAndClickListeners() {
        mTvHomeFetchingData = findViewById(R.id.tvHomeFetchingData);
        mIvHomeExUser = findViewById(R.id.ivHomeExUser);
        recyclerView = findViewById(R.id.recyclerView);
        mTvHomeExHeyUser = findViewById(R.id.tvHomeExHeyUser);
        mTvHomeExHeyUser.setText("Hey " + userName + ",");
        mBtnHomeExAdd = findViewById(R.id.btnHomeExAdd);
        mBtnHomeExAdd.setOnClickListener(this);
        mIvHomeExUser.setOnClickListener(this);
    }

//    @Override
    public void onDeleteClicked(int position) {
//        String Task = taskList.get(position).getTitle().trim();
//        mNode.child(Task).removeValue();
    }
//
//
//    @Override
    public void onUpdateClicked(int position) {
//        String Task = taskModelList.get(position).getTitle().trim();
//        Intent editTask = new Intent(HomeActivity.this, EditTaskActivity.class);
//        editTask.putExtra("Task", Task);
//        editTask.putExtra("Title", taskModelList.get(position).getTitle());
//        editTask.putExtra("Description", taskModelList.get(position).getDescription());
//        editTask.putExtra("Date", taskModelList.get(position).getDate());
//        editTask.putExtra("Status", taskModelList.get(position).getComplete());
//        startActivity(editTask);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnHomeExAdd:
//                Intent createNewTask = new Intent(HomeActivity.this, CreateTaskActivity.class);
//                startActivity(createNewTask);
                break;
            case R.id.ivHomeExUser:
//                Intent goToProfile = new Intent(getApplicationContext(), ProfileActivity.class);
//                startActivity(goToProfile);
                break;
        }
    }
}