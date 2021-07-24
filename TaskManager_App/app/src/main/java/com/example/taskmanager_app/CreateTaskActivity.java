package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    private Button mBtnCreateTaskAdd;
    private RadioButton mBtnRadioCreateTaskPending;
    private RadioButton mBtnRadioCreateTaskCompleted;
    private EditText mEtCreateTaskTitle;
    private EditText mEtCreateTaskDescription;
    private EditText mEtCreateTaskDate;
    private TaskModel taskModel;
    private String username;
    private FirebaseDatabase firebaseDatabase;
    private String CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        username = PreferenceHelper.getStringFromPreference(CreateTaskActivity.this, "Username");
        firebaseDatabase = FirebaseDatabase.getInstance();
        CurrentUser = username.replace(".", "");
        DatabaseReference node = firebaseDatabase.getReference("Users/" + CurrentUser + "/Tasks");

        initViews();
        mEtCreateTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoeDateTimeDialog(mEtCreateTaskDate);
            }
        });

        mBtnRadioCreateTaskPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtnRadioCreateTaskPending.isSelected()) {
                    mBtnRadioCreateTaskPending.setSelected(false);
                    mBtnRadioCreateTaskPending.setChecked(false);
                } else {
                    mBtnRadioCreateTaskPending.setSelected(true);
                    mBtnRadioCreateTaskPending.setChecked(true);
                    mBtnRadioCreateTaskCompleted.setSelected(false);
                    mBtnRadioCreateTaskCompleted.setChecked(false);
                }
            }
        });

        mBtnRadioCreateTaskCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtnRadioCreateTaskCompleted.isSelected()) {
                    mBtnRadioCreateTaskCompleted.setSelected(false);
                    mBtnRadioCreateTaskCompleted.setChecked(false);
                } else {
                    mBtnRadioCreateTaskCompleted.setSelected(true);
                    mBtnRadioCreateTaskCompleted.setChecked(true);
                    mBtnRadioCreateTaskPending.setSelected(false);
                    mBtnRadioCreateTaskPending.setChecked(false);
                }
            }
        });


        mBtnCreateTaskAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTitleValid() && isDateValid() && isStatusValid()) {
                    String Title = mEtCreateTaskTitle.getText().toString();
                    String Date = mEtCreateTaskDate.getText().toString();
                    String Description = mEtCreateTaskDescription.getText().toString();
                    String Task = mEtCreateTaskTitle.getText().toString().trim();
                    boolean isComplete = mBtnRadioCreateTaskCompleted.isChecked();
                    taskModel = new TaskModel(Title, Description, Date, isComplete);
                    node.child(Task).setValue(taskModel);
                    Intent setNewTask = new Intent(CreateTaskActivity.this, HomeActivity.class);
                    startActivity(setNewTask);
                }
            }
        });
    }

    private boolean isStatusValid() {
        if (mBtnRadioCreateTaskCompleted.isChecked() || mBtnRadioCreateTaskPending.isChecked()) {
            return true;
        } else {
            Toast.makeText(CreateTaskActivity.this, "Please select the status", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isDateValid() {
        if (mEtCreateTaskDate.getText().toString().length() != 0) {
            return true;
        } else {
            mEtCreateTaskDate.setError("Please select date and time");
            return false;
        }
    }

    private boolean isTitleValid() {
        if (mEtCreateTaskTitle.getText().toString().length() != 0) {
            return true;
        } else {
            mEtCreateTaskTitle.setError("Title can not be empty");
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
                new TimePickerDialog(CreateTaskActivity.this, R.style.TimePickerTheme, onTimeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(CreateTaskActivity.this, R.style.TimePickerTheme, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void initViews() {
        mBtnCreateTaskAdd = findViewById(R.id.btnCreateTaskAdd);
        mEtCreateTaskDate = findViewById(R.id.etCreateTaskDate);
        mEtCreateTaskTitle = findViewById(R.id.etCreateTaskTitle);
        mEtCreateTaskDescription = findViewById(R.id.etCreateTaskDescription);
        mBtnRadioCreateTaskPending = findViewById(R.id.btnRadioCreateTaskPending);
        mBtnRadioCreateTaskCompleted = findViewById(R.id.btnRadioCreateTaskCompleted);

    }

}