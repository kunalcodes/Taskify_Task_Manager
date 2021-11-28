package kunal.project.taskify.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kunal.project.taskify.R;
import kunal.project.taskify.TaskModel;

public class EditTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnEditTaskApply;
    private RadioButton mBtnRadioEditTaskPending;
    private RadioButton mBtnRadioEditTaskCompleted;
    private EditText mEtEditTaskTitle;
    private EditText mEtEditTaskDescription;
    private EditText mEtEditTaskDate;
    private EditText mEtEditTaskTime;
    private TextView mTvEditTaskStatus;
    private TextView mTvEditTaskDelete;
    private TaskModel mTaskModel;
    private ImageButton mBtnEditTaskBack;
    private String mUid;
    private DatabaseReference mNode;
    private String mTaskId;
    Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mNode = FirebaseDatabase.getInstance().getReference("Users");
        initViewsAndClickListeners();
        getTaskFromBundle();
    }

    private void getTaskFromBundle() {
        Intent intent = getIntent();
        mTaskModel = (TaskModel) intent.getExtras().getSerializable("taskmodel");
        String[] dateAndTimeArray = mTaskModel.getDate().split(" @");
        mEtEditTaskTitle.setText(mTaskModel.getTitle());
        mEtEditTaskDescription.setText(mTaskModel.getDescription());
        mEtEditTaskDate.setText(dateAndTimeArray[0]);
        mEtEditTaskTime.setText(dateAndTimeArray[1]);
        if (mTaskModel.getComplete()){
            mBtnRadioEditTaskCompleted.setChecked(true);
        } else {
            mBtnRadioEditTaskPending.setChecked(true);
        }
    }

    private void initViewsAndClickListeners() {
        mBtnEditTaskBack = findViewById(R.id.btnEditTaskBack);
        mBtnEditTaskBack.setOnClickListener(this);
        mBtnEditTaskApply = findViewById(R.id.btnEditTaskApply);
        mBtnEditTaskApply.setOnClickListener(this);
        mEtEditTaskDate = findViewById(R.id.etEditTaskDate);
        mEtEditTaskDate.setOnClickListener(this);
        mEtEditTaskTime = findViewById(R.id.etEditTaskTime);
        mEtEditTaskTime.setOnClickListener(this);
        mTvEditTaskStatus = findViewById(R.id.tvEditTaskStatus);
        mEtEditTaskTitle = findViewById(R.id.etEditTaskTitle);
        mEtEditTaskDescription = findViewById(R.id.etEditTaskDescription);
        mBtnRadioEditTaskPending = findViewById(R.id.btnRadioEditTaskPending);
        mBtnRadioEditTaskPending.setOnClickListener(this);
        mBtnRadioEditTaskCompleted = findViewById(R.id.btnRadioEditTaskCompleted);
        mBtnRadioEditTaskCompleted.setOnClickListener(this);
        mTvEditTaskDelete = findViewById(R.id.tvEditTaskDelete);
        mTvEditTaskDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnEditTaskBack:
                onBackPressed();
                break;
            case R.id.tvEditTaskDelete:
                mTaskId = mTaskModel.getTaskId();
                mNode.child(mUid).child(mTaskId).removeValue();
                finish();
                break;
            case R.id.btnEditTaskApply:
                if (isTitleValid() && isDateValid() && isTimeValid() && isStatusValid()) {
                    String Title = mEtEditTaskTitle.getText().toString();
                    String Date = mEtEditTaskDate.getText().toString() + " @" + mEtEditTaskTime.getText().toString();
                    String Description = mEtEditTaskDescription.getText().toString();
                    boolean isComplete = mBtnRadioEditTaskCompleted.isChecked();
                    mTaskId = mTaskModel.getTaskId();
                    mTaskModel = new TaskModel(mTaskId, Title, Description, Date, isComplete);
                    mNode.child(mUid).child(mTaskId).setValue(mTaskModel);
                    finish();
                }
                break;
            case R.id.btnRadioEditTaskCompleted:
                if (mBtnRadioEditTaskCompleted.isSelected()) {
                    mBtnRadioEditTaskCompleted.setSelected(false);
                    mBtnRadioEditTaskCompleted.setChecked(false);
                } else {
                    mBtnRadioEditTaskCompleted.setSelected(true);
                    mBtnRadioEditTaskCompleted.setChecked(true);
                    mBtnRadioEditTaskPending.setSelected(false);
                    mBtnRadioEditTaskPending.setChecked(false);
                }
                break;
            case R.id.btnRadioEditTaskPending:
                if (mBtnRadioEditTaskPending.isSelected()) {
                    mBtnRadioEditTaskPending.setSelected(false);
                    mBtnRadioEditTaskPending.setChecked(false);
                } else {
                    mBtnRadioEditTaskPending.setSelected(true);
                    mBtnRadioEditTaskPending.setChecked(true);
                    mBtnRadioEditTaskCompleted.setSelected(false);
                    mBtnRadioEditTaskCompleted.setChecked(false);
                }
                break;
            case R.id.etEditTaskDate:
                showDateDialog();
                break;
            case R.id.etEditTaskTime:
                showTimeDialog();
                break;
        }
    }

    private void showDateDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                mEtEditTaskDate.setText(simpleDateFormat.format(mCalendar.getTime()));
            }
        };
        new DatePickerDialog(EditTaskActivity.this, R.style.CalendarTheme, dateSetListener,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimeDialog() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
                mEtEditTaskTime.setText(simpleDateFormat.format(mCalendar.getTime()));
            }
        };
        new TimePickerDialog(EditTaskActivity.this, R.style.CalendarTheme, onTimeSetListener,
                mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
    }


    private boolean isTitleValid() {
        if (mEtEditTaskTitle.getText().toString().length() != 0) {
            return true;
        } else {
            mEtEditTaskTitle.setError("Title can not be empty");
            return false;
        }
    }

    private boolean isStatusValid() {
        if (mBtnRadioEditTaskCompleted.isChecked() || mBtnRadioEditTaskPending.isChecked()) {
            return true;
        } else {
            mTvEditTaskStatus.setError("Please select the status");
            return false;
        }
    }

    private boolean isDateValid() {
        if (mEtEditTaskDate.getText().toString().length() != 0) {
            return true;
        } else {
            mEtEditTaskDate.setError("Please select a date");
            return false;
        }
    }

    private boolean isTimeValid() {
        if (mEtEditTaskTime.getText().toString().length() != 0) {
            return true;
        } else {
            mEtEditTaskTime.setError("Please select a time");
            return false;
        }
    }
}