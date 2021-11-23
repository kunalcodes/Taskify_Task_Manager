package kunal.project.taskify.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kunal.project.taskify.R;
import kunal.project.taskify.TaskModel;
import kunal.project.taskify.utils.TaskItemClickListener;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private ArrayList<TaskModel> taskList;
    private TaskItemClickListener itemClickListener;

    public TaskAdapter(ArrayList<TaskModel> taskModelArrayList, TaskItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
        taskList = taskModelArrayList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout, parent, false);
        return new TaskViewHolder(view, itemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel taskModel = taskList.get(position);
        holder.setData(taskModel);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
