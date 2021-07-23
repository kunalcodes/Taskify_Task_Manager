package com.example.taskmanager_app;

import android.content.Intent;
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
    private View mViewTaskView;
    private TextView mTvTaskUpdate;
    private TextView mTvTaskDelete;
    private ItemClickListener itemClickListener;


    public TaskViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super(itemView);
        this.itemClickListener = itemClickListener;
        initViews(itemView);
    }

    private void initViews(View itemView) {
        mTvTaskDate = itemView.findViewById(R.id.tvTaskDate);
        mTvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
        mTvTaskTime = itemView.findViewById(R.id.tvTaskTime);
        mBtnRadioTask = itemView.findViewById(R.id.btnRadioTask);
        mViewTaskExpandedView = itemView.findViewById(R.id.viewTaskExpandedView);
        mViewTaskView = itemView.findViewById(R.id.viewTaskView);
        mTvTaskUpdate = itemView.findViewById(R.id.tvTaskUpdate);
        mTvTaskDelete = itemView.findViewById(R.id.tvTaskDelete);
    }

    public void setData(TaskModel taskModel) {
        mTvTaskDate.setText(taskModel.getDate() + "");
        mTvTaskTitle.setText(taskModel.getTitle());
        mTvTaskTime.setText(taskModel.getTime() + "");

        mBtnRadioTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnRadioTask.isSelected()){
                    mBtnRadioTask.setSelected(false);
                    mBtnRadioTask.setChecked(false);
                } else {
                    mBtnRadioTask.setSelected(true);
                    mBtnRadioTask.setChecked(true);
                }
            }
        });

        mBtnRadioTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mViewTaskView.setBackgroundResource(R.drawable.et_whitehalfround_bg);
                    mViewTaskExpandedView.setVisibility(View.VISIBLE);
                    mTvTaskUpdate.setVisibility(View.VISIBLE);
                    mTvTaskDelete.setVisibility(View.VISIBLE);
                } else {
                    mViewTaskView.setBackgroundResource(R.drawable.et_white_bg);
                    mViewTaskExpandedView.setVisibility(View.GONE);
                    mTvTaskUpdate.setVisibility(View.GONE);
                    mTvTaskDelete.setVisibility(View.GONE);
                }
            }
        });

        mTvTaskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onDeleteClicked(getAdapterPosition());
            }
        });

        mTvTaskUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onUpdateClicked(getAdapterPosition());
            }
        });
    }
}
