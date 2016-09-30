package com.example.pluggedinalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//odpala alarm na konkretną godzinę

public class ActionAlarm extends BroadcastReceiver {
    private static final String TAG = "Action Alarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        String myString = intent.getExtras().getString("extra");
        Log.i(TAG, "onReceive: " + myString);
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);
        serviceIntent.putExtra("extra", myString);
        context.startService(serviceIntent);
        Log.d(TAG, " Action Alarm is working");
    }
}
