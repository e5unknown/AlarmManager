package com.dev420.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private EditText title;
    private EditText message;
    private TextView textViewTime;

    private NotificationHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.title);
        message = findViewById(R.id.message);
        helper = new NotificationHelper(this);
        textViewTime = findViewById(R.id.text_alarm);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button_cancel).setOnClickListener(this);
        findViewById(R.id.button_time_picker).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_time_picker:
                DialogFragment dialog = new TimePickerFragment();
                dialog.show(getSupportFragmentManager(), "timeDialog");
                break;
            case R.id.button_cancel:
                cancelAlarm();
            case R.id.button1:
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NotificationCompat.Builder nb1 = helper.getChannelNotification();
                        helper.getManager().notify(1, nb1.build());
                    }
                }, 5000);
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, i);
        c.set(Calendar.MINUTE, i1);
        c.set(Calendar.SECOND, 0);
        updateTime(c);
        startAlarm(c);
    }

    private void updateTime(Calendar c) {
        textViewTime.setText("Alarm set: "+ DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()));
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.setAction(AlertReceiver.ACTION_ALARM);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.setAction(AlertReceiver.ACTION_ALARM);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        textViewTime.setText("Alarm cancelled");
    }
}
