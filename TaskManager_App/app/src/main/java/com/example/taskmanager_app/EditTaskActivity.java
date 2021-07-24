package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {

    private Button mBtnEditTaskApply;
    private RadioButton mBtnRadioEditTaskPending;
    private RadioButton mBtnRadioEditTaskCompleted;
    private EditText mEtEditTaskTitle;
    private EditText mEtEditTaskDescription;
    private EditText mEtEditTaskDate;
    private TaskModel taskModel;
    private TextView mTvEditTaskDelete;
    private String username;
    private FirebaseDatabase firebaseDatabase;
    private String CurrentUser;
    private String Task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        username = PreferenceHelper.getStringFromPreference(EditTaskActivity.this, "Username");
        firebaseDatabase = FirebaseDatabase.getInstance();
        CurrentUser = username.replace(".", "");
        DatabaseReference node = firebaseDatabase.getReference("Users/"+CurrentUser+"/Tasks");

        Intent intent = getIntent();
        Task = intent.getStringExtra("Task");
        String Title = intent.getStringExtra("Title");
        String Description = intent.getStringExtra("Description");
        String Date = intent.getStringExtra("Date");

        boolean Status = intent.getBooleanExtra("Status", true);
        initViews();

        mEtEditTaskTitle.setText(Title);
        mEtEditTaskDescription.setText(Description);
        mEtEditTaskDate.setText(Date);
        mBtnRadioEditTaskPending.setChecked(false);
        mBtnRadioEditTaskCompleted.setChecked(false);

        mEtEditTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoeDateTimeDialog(mEtEditTaskDate);
            }
        });

        mBtnRadioEditTaskPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtnRadioEditTaskPending.isSelected()) {
                    mBtnRadioEditTaskPending.setSelected(false);
                    mBtnRadioEditTaskPending.setChecked(false);
                } else {
                    mBtnRadioEditTaskPending.setSelected(true);
                    mBtnRadioEditTaskPending.setChecked(true);
                    mBtnRadioEditTaskCompleted.setSelected(false);
                    mBtnRadioEditTaskCompleted.setChecked(false);
                }
            }
        });

        mBtnRadioEditTaskCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtnRadioEditTaskCompleted.isSelected()) {
                    mBtnRadioEditTaskCompleted.setSelected(false);
                    mBtnRadioEditTaskCompleted.setChecked(false);
                } else {
                    mBtnRadioEditTaskCompleted.setSelected(true);
                    mBtnRadioEditTaskCompleted.setChecked(true);
                    mBtnRadioEditTaskPending.setSelected(false);
                    mBtnRadioEditTaskPending.setChecked(false);
                }
            }
        });

        mTvEditTaskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                node.child(Task).removeValue();
                Intent editTask = new Intent(EditTaskActivity.this, HomeActivity.class);
                startActivity(editTask);
            }
        });

        mBtnEditTaskApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTitleValid() && isDateValid() && isStatusValid()) {
                    node.child(Task).removeValue();
                    String Title = mEtEditTaskTitle.getText().toString();
                    String Date = mEtEditTaskDate.getText().toString();
                    String Description = mEtEditTaskDescription.getText().toString();
                    String newTask = mEtEditTaskTitle.getText().toString().trim();
                    boolean isComplete = mBtnRadioEditTaskCompleted.isChecked();
                    taskModel = new TaskModel(Title, Description, Date, isComplete);
                    node.child(newTask).setValue(taskModel);
                    Intent editTask = new Intent(EditTaskActivity.this, HomeActivity.class);
                    startActivity(editTask);
                }
            }
        });
    }

    private boolean isStatusValid() {
        if (mBtnRadioEditTaskCompleted.isChecked() || mBtnRadioEditTaskPending.isChecked()){
            return true;
        } else {
            Toast.makeText(EditTaskActivity.this, "Please select the status", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isDateValid() {
        if (mEtEditTaskDate.getText().toString().length() != 0){
            return true;
        } else {
            mEtEditTaskDate.setError("Please select date and time");
            return false;
        }
    }

    private boolean isTitleValid() {
        if (mEtEditTaskTitle.getText().toString().length() != 0){
            return true;
        } else {
            mEtEditTaskTitle.setError("Title can not be empty");
            return false;
        }
    }

    private void shoeDateTimeDialog(EditText mEtCreateTaskDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd :: HH:mm");
                        mEtCreateTaskDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(EditTaskActivity.this, R.style.TimePickerTheme, onTimeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(EditTaskActivity.this, R.style.TimePickerTheme, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void initViews() {

        mBtnEditTaskApply = findViewById(R.id.btnEditTaskApply);
        mEtEditTaskDate = findViewById(R.id.etEditTaskDate);
        mEtEditTaskTitle = findViewById(R.id.etEditTaskTitle);
        mEtEditTaskDescription = findViewById(R.id.etEditTaskDescription);
        mBtnRadioEditTaskPending = findViewById(R.id.btnRadioEditTaskPending);
        mBtnRadioEditTaskCompleted = findViewById(R.id.btnRadioEditTaskCompleted);
        mTvEditTaskDelete = findViewById(R.id.tvEditTaskDelete);
    }
}