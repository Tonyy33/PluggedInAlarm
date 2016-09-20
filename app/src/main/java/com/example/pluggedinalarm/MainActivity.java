package com.example.pluggedinalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    PendingIntent pending_intent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;

        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        update_text = (TextView) findViewById(R.id.update_text);

        final Calendar calendar = Calendar.getInstance();

        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);

        Button start_alarm = (Button) findViewById(R.id.start_alarm);

        start_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                set_alarm_text("Alarm on!");

                my_intent.putExtra("extra","alarm on" );

                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
            }
        });

        Button stop_alarm = (Button) findViewById(R.id.end_alarm);


        stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                set_alarm_text("Alarm off!");

                alarm_manager.cancel(pending_intent);

                my_intent.putExtra("extra", "alarm off");

                sendBroadcast(my_intent);

            }
        });





    }

    private void set_alarm_text(String output) {

        update_text.setText(output);

    }
}
