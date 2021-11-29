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

public class UpComingTaskAdapter extends RecyclerView.Adapter<UpComingTaskViewHolder> {

    private ArrayList<TaskModel> taskList;
    private TaskItemClickListener itemClickListener;

    public UpComingTaskAdapter(ArrayList<TaskModel> taskModelArrayList, TaskItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
        taskList = taskModelArrayList;
    }

    @NonNull
    @Override
    public UpComingTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_task_item_layout, parent, false);
        return new UpComingTaskViewHolder(view, itemClickListener);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull UpComingTaskViewHolder holder, int position) {
        TaskModel taskModel = taskList.get(position);
        holder.setData(taskModel);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
