package kunal.project.taskify.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kunal.project.taskify.R;
import kunal.project.taskify.TaskModel;
import kunal.project.taskify.utils.TaskItemClickListener;

public class ArchivedTaskAdapter extends RecyclerView.Adapter<ArchivedTaskViewHolder> {

    private ArrayList<TaskModel> taskList;
    private TaskItemClickListener itemClickListener;

    public ArchivedTaskAdapter(ArrayList<TaskModel> taskModelArrayList, TaskItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
        taskList = taskModelArrayList;
    }

    @NonNull
    @Override
    public ArchivedTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.archived_task_item_layout, parent, false);
        return new ArchivedTaskViewHolder(view, itemClickListener);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ArchivedTaskViewHolder holder, int position) {
        TaskModel taskModel = taskList.get(position);
        holder.setData(taskModel);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
