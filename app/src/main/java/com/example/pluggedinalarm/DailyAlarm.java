package com.example.pluggedinalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

//alarm codzienny który bedzie uruchamiał alarm konkretny

public class DailyAlarm extends BroadcastReceiver {
    private static final String TAG = "Daily Alarm";
    public AlarmManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent = new Intent(context, ActionAlarm.class);
        myIntent.putExtra("extra", "alarm on");
        Log.i(TAG, "onReceive: " + myIntent.getStringExtra("extra"));
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        final Calendar calendar = Calendar.getInstance();
        Log.d(TAG, " DailyAlarm is working");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);

    }

}
