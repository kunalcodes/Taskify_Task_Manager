package com.example.taskmanager_app;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private TextView mTvTaskDate;
    private TextView mTvTaskTitle;
    private TextView mTvTaskTime;
    private RadioButton mBtnRadioTask;
    private View mViewTaskExpandedView;
    private TextView mTvTaskUpdate;
    private TextView mTvTaskDelete;


    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        initViews(itemView);
    }

    private void initViews(View itemView) {
        mTvTaskDate = itemView.findViewById(R.id.tvTaskDate);
        mTvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
        mTvTaskTime = itemView.findViewById(R.id.tvTaskTime);
        mBtnRadioTask = itemView.findViewById(R.id.btnRadioTask);
        mViewTaskExpandedView = itemView.findViewById(R.id.viewTaskExpandedView);
        mTvTaskUpdate = itemView.findViewById(R.id.tvTaskUpdate);
        mTvTaskDelete = itemView.findViewById(R.id.tvTaskDelete);
    }

    public void setData(TaskModel taskModel) {
        mTvTaskDate.setText(taskModel.getDate() + "");
        mTvTaskTitle.setText(taskModel.getTitle());
        mTvTaskTime.setText(taskModel.getTime() + "");

        mBtnRadioTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mViewTaskExpandedView.setVisibility(View.VISIBLE);
                    mTvTaskUpdate.setVisibility(View.VISIBLE);
                    mTvTaskDelete.setVisibility(View.VISIBLE);
                } else {
                    mViewTaskExpandedView.setVisibility(View.GONE);
                    mTvTaskUpdate.setVisibility(View.GONE);
                    mTvTaskDelete.setVisibility(View.GONE);
                }
            }
        });
    }
}
