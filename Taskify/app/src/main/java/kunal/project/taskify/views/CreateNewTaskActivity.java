package kunal.project.taskify.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class CreateNewTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnCreateTaskAdd;
    private RadioButton mBtnRadioCreateTaskPending;
    private RadioButton mBtnRadioCreateTaskCompleted;
    private EditText mEtCreateTaskTitle;
    private EditText mEtCreateTaskDescription;
    private EditText mEtCreateTaskDate;
    private EditText mEtCreateTaskTime;
    private TextView mTvCreateTaskStatus;
    private TaskModel taskModel;
    private ImageButton mBtnCreateTaskBack;
    private String mUid;
    private DatabaseReference mNode;
    private String mTaskId;
    Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mNode = FirebaseDatabase.getInstance().getReference("Users");
        initViewsAndClickListeners();
    }

    private void initViewsAndClickListeners() {
        mBtnCreateTaskBack = findViewById(R.id.btnCreateTaskBack);
        mBtnCreateTaskBack.setOnClickListener(this);
        mBtnCreateTaskAdd = findViewById(R.id.btnCreateTaskAdd);
        mBtnCreateTaskAdd.setOnClickListener(this);
        mEtCreateTaskDate = findViewById(R.id.etCreateTaskDate);
        mEtCreateTaskDate.setOnClickListener(this);
        mEtCreateTaskTime = findViewById(R.id.etCreateTaskTime);
        mEtCreateTaskTime.setOnClickListener(this);
        mTvCreateTaskStatus = findViewById(R.id.tvCreateTaskStatus);
        mEtCreateTaskTitle = findViewById(R.id.etCreateTaskTitle);
        mEtCreateTaskDescription = findViewById(R.id.etCreateTaskDescription);
        mBtnRadioCreateTaskPending = findViewById(R.id.btnRadioCreateTaskPending);
        mBtnRadioCreateTaskPending.setOnClickListener(this);
        mBtnRadioCreateTaskCompleted = findViewById(R.id.btnRadioCreateTaskCompleted);
        mBtnRadioCreateTaskCompleted.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnCreateTaskBack:
                onBackPressed();
                break;
            case R.id.btnCreateTaskAdd:
                if (isTitleValid() && isDateValid() && isTimeValid() && isStatusValid()) {
                    String Title = mEtCreateTaskTitle.getText().toString();
                    String Date = mEtCreateTaskDate.getText().toString() + " @" + mEtCreateTaskTime.getText().toString();
                    String Description = mEtCreateTaskDescription.getText().toString();
                    boolean isComplete = mBtnRadioCreateTaskCompleted.isChecked();
                    mTaskId = mNode.child(mUid).push().getKey();
                    taskModel = new TaskModel(mTaskId, Title, Description, Date, isComplete);
                    mNode.child(mUid).child(mTaskId).setValue(taskModel);
                    finish();
                }
                break;
            case R.id.btnRadioCreateTaskCompleted:
                if (mBtnRadioCreateTaskCompleted.isSelected()) {
                    mBtnRadioCreateTaskCompleted.setSelected(false);
                    mBtnRadioCreateTaskCompleted.setChecked(false);
                } else {
                    mBtnRadioCreateTaskCompleted.setSelected(true);
                    mBtnRadioCreateTaskCompleted.setChecked(true);
                    mBtnRadioCreateTaskPending.setSelected(false);
                    mBtnRadioCreateTaskPending.setChecked(false);
                }
                break;
            case R.id.btnRadioCreateTaskPending:
                if (mBtnRadioCreateTaskPending.isSelected()) {
                    mBtnRadioCreateTaskPending.setSelected(false);
                    mBtnRadioCreateTaskPending.setChecked(false);
                } else {
                    mBtnRadioCreateTaskPending.setSelected(true);
                    mBtnRadioCreateTaskPending.setChecked(true);
                    mBtnRadioCreateTaskCompleted.setSelected(false);
                    mBtnRadioCreateTaskCompleted.setChecked(false);
                }
                break;
            case R.id.etCreateTaskDate:
                showDateDialog();
                break;
            case R.id.etCreateTaskTime:
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
                mEtCreateTaskDate.setText(simpleDateFormat.format(mCalendar.getTime()));
            }
        };
        new DatePickerDialog(CreateNewTaskActivity.this, R.style.CalendarTheme, dateSetListener,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimeDialog() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
                mEtCreateTaskTime.setText(simpleDateFormat.format(mCalendar.getTime()));
            }
        };
        new TimePickerDialog(CreateNewTaskActivity.this, R.style.CalendarTheme, onTimeSetListener,
                mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
    }


    private boolean isTitleValid() {
        if (mEtCreateTaskTitle.getText().toString().length() != 0) {
            return true;
        } else {
            mEtCreateTaskTitle.setError("Title can not be empty");
            return false;
        }
    }

    private boolean isStatusValid() {
        if (mBtnRadioCreateTaskCompleted.isChecked() || mBtnRadioCreateTaskPending.isChecked()) {
            return true;
        } else {
            mTvCreateTaskStatus.setError("Please select the status");
            return false;
        }
    }

    private boolean isDateValid() {
        if (mEtCreateTaskDate.getText().toString().length() != 0) {
            return true;
        } else {
            mEtCreateTaskDate.setError("Please select a date");
            return false;
        }
    }

    private boolean isTimeValid() {
        if (mEtCreateTaskTime.getText().toString().length() != 0) {
            return true;
        } else {
            mEtCreateTaskTime.setError("Please select a time");
            return false;
        }
    }
}