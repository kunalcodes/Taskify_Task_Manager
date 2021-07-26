package com.example.taskmanager_app;

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
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

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
    private int totalTaskNumber = 0;
    private int completedTaskNumber = 0;
    private int pendingTaskNumber = 0;

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
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       mTvProfileTotalTaskData.setText(totalTaskNumber + "");
                       mTvProfileCompletedTaskData.setText(completedTaskNumber + "");
                       mTvProfilePendingTaskData.setText(pendingTaskNumber + "");
                       if(totalTaskNumber == 0){
                           mTvProfileCompletionRate.setText("0" + "%");
                       } else {
                           int percentage = (int) ((completedTaskNumber * 100) / totalTaskNumber);
                           mTvProfileCompletionRate.setText(percentage + "%");
                       }
//                       int percentage = (int) ((completedTaskNumber * 100) / totalTaskNumber);
//                       mTvProfileCompletionRate.setText(percentage + "%");
                   }
               });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





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

        PieChart pieChart = findViewById(R.id.doNutChart);
        pieChart.addPieSlice(new PieModel("Pending Tasks",pendingTaskNumber,Color.parseColor("#4263EC")));
        pieChart.addPieSlice(new PieModel("Completed Tasks",completedTaskNumber,Color.parseColor("#FB9F33")));
        pieChart.startAnimation();

//        ArrayList<PieEntry> tasks = new ArrayList<>();
//        tasks.add(new PieEntry(pendingTaskNumber, ""));
//        tasks.add(new PieEntry(completedTaskNumber, ""));
//
//        PieDataSet pieDataSet = new PieDataSet(tasks, "");
//        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//        pieDataSet.setValueTextColor(Color.BLACK);
//        pieDataSet.setValueTextSize(16f);
//
//        PieData pieData = new PieData(pieDataSet);
//
//        pieChart.setData(pieData);
//        pieChart.getDescription().setEnabled(false);
//        pieChart.animate();
//        pieChart.setHoleRadius(80);
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