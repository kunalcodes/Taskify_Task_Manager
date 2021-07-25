package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.InputType;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    private Button mBtnCreateTaskAdd;
    private EditText mEtCreateTaskTitle;
    private EditText mEtCreateTaskDescription;
    private EditText mEtCreateTaskDate;
    private TaskModel taskModel;
    private FirebaseDatabase firebaseDatabase;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        createNotificationChanel();

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference node = firebaseDatabase.getReference("Users/CurrentUser/Tasks");

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
                String Title = mEtCreateTaskTitle.getText().toString();
                String Date = mEtCreateTaskDate.getText().toString().substring(0,8);
                String Description = mEtCreateTaskDescription.getText().toString();
                String Time = mEtCreateTaskDate.getText().toString().substring(12);
                String Task = mEtCreateTaskTitle.getText().toString().trim();
                boolean isComplete = false;
                taskModel = new TaskModel(Title,Description,Date,Time, isComplete);
                node.child(Task).setValue(taskModel);
                Intent setNewTask = new Intent(CreateTaskActivity.this, HomeActivity.class);
                startActivity(setNewTask);
                setAlarm();
            }

            private void setAlarm() {
                Calendar calendar=Calendar.getInstance();
                alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(CreateTaskActivity.this,AlarmReceiver.class);
                pendingIntent =PendingIntent.getBroadcast(CreateTaskActivity.this,0,intent,0);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                Toast.makeText(CreateTaskActivity.this,"Task created successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNotificationChanel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="foxandroidReminderChannel";
            String description="Channel For Alarm Manager";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel("forandroid",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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