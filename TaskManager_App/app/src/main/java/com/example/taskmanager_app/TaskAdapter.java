package com.example.taskmanager_app;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private ArrayList<TaskModel> taskModelList;
    private ItemClickListener itemClickListener;

    public TaskAdapter(ArrayList<TaskModel> taskModelArrayList, ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
        taskModelList = taskModelArrayList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout, parent, false);
        return new TaskViewHolder(view, itemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel taskModel = taskModelList.get(position);
        holder.setData(taskModel);
    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }
}
