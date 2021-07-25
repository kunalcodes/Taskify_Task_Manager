package com.example.taskmanager_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProfileActivity extends AppCompatActivity {

    private ArrayList<TaskModel> taskModelList = new ArrayList<>();
    private Button mBtnProfileSignOut;
    private TextView mTvProfileTotalTaskData;
    private TextView mTvProfileCompletedTaskData;
    private TextView mTvProfileCompletionRate;
    private String username;
    private String CurrentUser;
    private TextView mTvProfilePendingTaskData;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference node;
    private View mViewProfileBack;
    private TextView mTvProfileEmailId;
    private int totalTaskNumber = 10;
    private int completedTaskNumber = 7;
    private int pendingTaskNumber = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = PreferenceHelper.getStringFromPreference(ProfileActivity.this, "Username");
        CurrentUser = username.replace(".", "");
        firebaseDatabase = FirebaseDatabase.getInstance("https://taskmanagerapp-1407d-default-rtdb.firebaseio.com/");
        node = firebaseDatabase.getReference("Users/" + CurrentUser + "/Tasks");
        initViews();

        node.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //taskModelList.clear();
                for (DataSnapshot taskDataSnapshot : snapshot.getChildren()) {

                    TaskModel taskModel = taskDataSnapshot.getValue(TaskModel.class);
                    //taskModelList.add(taskModel);
                    totalTaskNumber += 1;
                    if (taskModel.getComplete()) {
                        completedTaskNumber += 1;
                    } else {
                        pendingTaskNumber += 1;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mTvProfileTotalTaskData.setText(totalTaskNumber + "");
        mTvProfileCompletedTaskData.setText(completedTaskNumber + "");
        mTvProfilePendingTaskData.setText(pendingTaskNumber + "");

        int percentage = (int)((completedTaskNumber*100)/totalTaskNumber);
        mTvProfileCompletionRate.setText(percentage+"%");

        mViewProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHome = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(goToHome);
            }
        });

        mBtnProfileSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceHelper.writeIntToPreference(ProfileActivity.this, "LoginStatus", 0);
                FirebaseAuth.getInstance().signOut();
                Intent sigOut = new Intent(ProfileActivity.this, OnBoardingActivity.class);
                startActivity(sigOut);
            }
        });

        PieChart pieChart=findViewById(R.id.doNutChart);

        ArrayList<PieEntry> tasks=new ArrayList<>();
        tasks.add(new PieEntry(pendingTaskNumber,""));
        tasks.add(new PieEntry(completedTaskNumber,""));

        PieDataSet pieDataSet=new PieDataSet(tasks,"");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData=new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.animate();
        pieChart.setHoleRadius(80);
    }

    private void initViews() {
        mTvProfileEmailId = findViewById(R.id.tvProfileEmailId);
        mViewProfileBack = findViewById(R.id.viewProfileBack);
        mBtnProfileSignOut = findViewById(R.id.btnProfileSignOut);
        mTvProfileTotalTaskData = findViewById(R.id.tvProfileTotalTaskData);
        mTvProfileCompletedTaskData = findViewById(R.id.tvProfileCompletedTaskData);
        mTvProfilePendingTaskData = findViewById(R.id.tvProfilePendingTaskData);
        mTvProfileCompletionRate = findViewById(R.id.tvProfileCompletionRate);
        mTvProfileEmailId.setText(username);
    }
}