package kunal.project.taskify.views;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import kunal.project.taskify.R;
import kunal.project.taskify.TaskModel;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<TaskModel> taskModelList = new ArrayList<>();
    private String userName;
    private Button mBtnProfileSignOut;
    private TextView mTvProfileTotalTaskData;
    private TextView mTvProfileCompletedTaskData;
    private TextView mTvProfileCompletionRate;
    private TextView mTvProfilePendingTaskData;
    private TextView mTvProfileMissedTaskData;
    private TextView mTvProfileEmailId;
    private PieChart mPieChart;
    private FirebaseAuth mAuth;
    private DatabaseReference mNode;
    private String mUid;
    private ImageButton mBtnProfileBack;
    private int totalTaskNumber;
    private int completedTaskNumber;
    private int pendingTaskNumber;
    private int missedTaskNumber;
    private int completionPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        userName = mAuth.getCurrentUser().getEmail();
        mUid = mAuth.getCurrentUser().getUid();
        mNode = FirebaseDatabase.getInstance().getReference("Users");
        calculateData();
        initViews();
    }

    private void calculateData() {
        mNode.child(mUid).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalTaskNumber = 0;
                missedTaskNumber = 0;
                completedTaskNumber = 0;
                pendingTaskNumber = 0;
                String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd @hh:mm a"));
                for (DataSnapshot taskDataSnapshot : snapshot.getChildren()) {
                    if (taskDataSnapshot.getValue() != null) {
                        TaskModel taskModel = taskDataSnapshot.getValue(TaskModel.class);
                        totalTaskNumber += 1;
                        if (taskModel.getComplete()) {
                            completedTaskNumber++;
                        } else {
                            if (taskModel.getDate().compareTo(localDateTime) >= 0) {
                                pendingTaskNumber++;
                            } else {
                                missedTaskNumber++;
                            }
                        }
                    }
                }
                if (totalTaskNumber == 0) {
                    completionPercentage = 0;
                } else {
                    completionPercentage = (int) ((completedTaskNumber * 100) / totalTaskNumber);
                }
                initViews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initViews() {
        mTvProfileEmailId = findViewById(R.id.tvProfileEmailId);
        mTvProfileEmailId.setText(userName);
        mBtnProfileBack = findViewById(R.id.btnProfileBack);
        mBtnProfileBack.setOnClickListener(this);
        mBtnProfileSignOut = findViewById(R.id.btnProfileSignOut);
        mBtnProfileSignOut.setOnClickListener(this);
        mTvProfileTotalTaskData = findViewById(R.id.tvProfileTotalTaskData);
        mTvProfileCompletedTaskData = findViewById(R.id.tvProfileCompletedTaskData);
        mTvProfilePendingTaskData = findViewById(R.id.tvProfilePendingTaskData);
        mTvProfileMissedTaskData = findViewById(R.id.tvProfileMissedTaskData);
        mTvProfileCompletionRate = findViewById(R.id.tvProfileCompletionRate);
        mPieChart = findViewById(R.id.doNutChart);

        mTvProfileTotalTaskData.setText(totalTaskNumber + "");
        mTvProfileCompletedTaskData.setText(completedTaskNumber + "");
        mTvProfilePendingTaskData.setText(pendingTaskNumber + "");
        mTvProfileMissedTaskData.setText(missedTaskNumber + "");
        mTvProfileCompletionRate.setText(completionPercentage + "%");

        mPieChart.addPieSlice(new PieModel("Pending Tasks", pendingTaskNumber, Color.parseColor("#FB9F33")));
        mPieChart.addPieSlice(new PieModel("Missed Tasks", missedTaskNumber, Color.parseColor("#9C9C9C")));
        mPieChart.addPieSlice(new PieModel("Completed Tasks", completedTaskNumber, Color.parseColor("#4263EC")));
        mPieChart.startAnimation();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnProfileBack:
                onBackPressed();
                break;
            case R.id.btnProfileSignOut:
                mAuth.signOut();
                Intent sigOut = new Intent(ProfileActivity.this, OnBoardingActivity.class);
                startActivity(sigOut);
                finish();
                break;
        }
    }

}