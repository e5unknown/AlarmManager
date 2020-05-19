package com.dev420.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

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
        textViewTime = findViewById(R.id.textview);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.buttonTime).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonTime:
                DialogFragment dialog = new TimePickerFragment();
                dialog.show(getSupportFragmentManager(), "timeDialog");
                break;
            case R.id.button1:
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NotificationCompat.Builder nb1 = helper.getChannel1Notification(title.getText().toString(), message.getText().toString());
                        helper.getManager().notify(1, nb1.build());
                    }
                }, 5000);
                break;
            case R.id.button2:
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NotificationCompat.Builder nb2 = helper.getChannel2Notification(title.getText().toString(), message.getText().toString());
                        helper.getManager().notify(2, nb2.build());
                    }
                }, 6000);
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        textViewTime.setText("Hour: " + i + ", minute: " + i1);
    }
}
