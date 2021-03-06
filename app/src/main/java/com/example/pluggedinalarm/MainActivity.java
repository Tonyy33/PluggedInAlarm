package com.example.pluggedinalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public AlarmManager alarmManager;
    public TimePicker alarmTimepicker;
    public PendingIntent pendingIntent;

    public TextView tvUpdateText;
    public Button btnStartAlarm;
    public Button btnStopAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmTimepicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        tvUpdateText = (TextView) findViewById(R.id.updateText);
        final Calendar calendar = Calendar.getInstance();
        final Intent myIntent = new Intent(this, DailyAlarm.class);

        btnStartAlarm = (Button) findViewById(R.id.startAlarm);
        btnStartAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test-log", " Service on click alarm on");
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimepicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimepicker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                setAlarmText("Alarm on!");

                myIntent.putExtra("extra","alarm on" );
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 1000 * 60, pendingIntent);
            }
        });

        btnStopAlarm = (Button) findViewById(R.id.endAlarm);
        btnStopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test-log", " Service on click alarm off");
                setAlarmText("Alarm off!");

                alarmManager.cancel(pendingIntent);
                myIntent.putExtra("extra", "alarm off");
                sendBroadcast(myIntent);
            }
        });
    }

    private void setAlarmText(String output) {
        tvUpdateText.setText(output);
    }
}
