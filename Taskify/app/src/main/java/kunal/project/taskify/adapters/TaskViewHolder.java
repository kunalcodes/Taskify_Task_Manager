package kunal.project.taskify.adapters;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Formatter;

import kunal.project.taskify.R;
import kunal.project.taskify.TaskModel;
import kunal.project.taskify.utils.TaskItemClickListener;


public class TaskViewHolder extends RecyclerView.ViewHolder {

    private TextView mTvTaskDate;
    private TextView mTvTaskTitle;
    private TextView mTvTaskTime;
    private RadioButton mBtnRadioTask;
    private View mViewTaskExpandedView;
    private View mViewTaskView;
    private TextView mTvTaskUpdate;
    private TextView mTvTaskDelete;
    private TextView mTvCompleted;
    private TaskItemClickListener itemClickListener;


    public TaskViewHolder(@NonNull View itemView, TaskItemClickListener itemClickListener) {
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
        mTvCompleted = itemView.findViewById(R.id.tvCompleted);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setData(TaskModel taskModel) {
        String[] dateAndTimeArray = taskModel.getDate().split(" @");
        String time = dateAndTimeArray[1];
        mTvTaskDate.setText(getDatetext(dateAndTimeArray[0]));
        mTvTaskTitle.setText(taskModel.getTitle());
        mTvTaskTime.setText(time);


        if (taskModel.getComplete()) {
            mTvCompleted.setVisibility(View.VISIBLE);
        } else {
            mTvCompleted.setVisibility(View.INVISIBLE);
        }

        mBtnRadioTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtnRadioTask.isSelected()) {
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
                    mViewTaskView.setBackgroundResource(R.drawable.bg_et_white_half_round);
                    mViewTaskExpandedView.setVisibility(View.VISIBLE);
                    mTvTaskUpdate.setVisibility(View.VISIBLE);
                    mTvTaskDelete.setVisibility(View.VISIBLE);
                } else {
                    mViewTaskView.setBackgroundResource(R.drawable.bg_et_white);
                    mViewTaskExpandedView.setVisibility(View.GONE);
                    mTvTaskUpdate.setVisibility(View.GONE);
                    mTvTaskDelete.setVisibility(View.GONE);
                }
            }
        });

        mTvTaskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnRadioTask.setChecked(false);
                itemClickListener.onDeleteClicked(getAdapterPosition());
            }
        });

        mTvTaskUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onUpdateClicked(getAdapterPosition());
                mBtnRadioTask.setSelected(false);
                mBtnRadioTask.setChecked(false);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getDatetext(String taskDate) {
        Calendar mCalendar = Calendar.getInstance();
        String localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String[] taskDateArray = taskDate.split("/");
        String[] localDateArray = localDate.split("/");
        if (Integer.parseInt(taskDateArray[0])==Integer.parseInt(localDateArray[0]) &&
                Integer.parseInt(taskDateArray[1])==Integer.parseInt(localDateArray[1])) {
            if (Integer.parseInt(taskDateArray[2])==Integer.parseInt(localDateArray[2])){
                return "Today";
            } else if (Integer.parseInt(taskDateArray[2])-Integer.parseInt(localDateArray[2])==1){
                return "Tomorrow";
            } else if (Integer.parseInt(taskDateArray[2])-Integer.parseInt(localDateArray[2])==-1){
                return "Yesterday";
            } else {
                return getDateWithMonthName(taskDateArray);
            }
        } else {
            return getDateWithMonthName(taskDateArray);
        }
    }

    private String getDateWithMonthName(String[] taskDateArray) {
        String monthName = "";
        switch (taskDateArray[1]){
            case "01":
                monthName = "Jan";
                break;
            case "02":
                monthName = "Feb";
                break;
            case "03":
                monthName = "Mar";
                break;
            case "04":
                monthName = "Apr";
                break;
            case "05":
                monthName = "May";
                break;
            case "06":
                monthName = "Jun";
                break;
            case "07":
                monthName = "Jul";
                break;
            case "08":
                monthName = "Aug";
                break;
            case "09":
                monthName = "Sep";
                break;
            case "10":
                monthName = "Oct";
                break;
            case "11":
                monthName = "Nov";
                break;
            case "12":
                monthName = "Dec";
                break;
        }
        return taskDateArray[2]+" "+monthName+" "+taskDateArray[0];
    }
}
