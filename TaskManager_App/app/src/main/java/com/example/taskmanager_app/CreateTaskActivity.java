package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.InputType;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    private Button mBtnCreateTaskAdd;
    private EditText mEtCreateTaskTitle;
    private EditText mEtCreateTaskDescription;
    private EditText mEtCreateTaskDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        initViews();
        mEtCreateTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoeDateTimeDialog(mEtCreateTaskDate);
            }
        });
        mBtnCreateTaskAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setNewTask = new Intent(CreateTaskActivity.this, HomeActivity.class);
                setNewTask.putExtra("Title", mEtCreateTaskTitle.getText().toString());
                setNewTask.putExtra("Description", mEtCreateTaskDescription.getText().toString());
                setNewTask.putExtra("Date", mEtCreateTaskDate.getText().toString());
                setNewTask.putExtra("Time", "Time");
                setNewTask.putExtra("isComplete", false);
                startActivity(setNewTask);
            }
        });
    }

    private void shoeDateTimeDialog(EditText mEtCreateTaskDate) {
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yy :: HH:mm");
                        mEtCreateTaskDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(CreateTaskActivity.this,R.style.TimePickerTheme,onTimeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(CreateTaskActivity.this,R.style.TimePickerTheme,dateSetListener,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void initViews() {
        mBtnCreateTaskAdd = findViewById(R.id.btnCreateTaskAdd);
        mEtCreateTaskDate = findViewById(R.id.etCreateTaskDate);
        mEtCreateTaskTitle = findViewById(R.id.etCreateTaskTitle);
        mEtCreateTaskDescription = findViewById(R.id.etCreateTaskDescription);
    }
}