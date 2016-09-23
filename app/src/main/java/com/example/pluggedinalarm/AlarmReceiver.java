package com.example.pluggedinalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by azimo123 on 20/09/16.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String myString = intent.getExtras().getString("extra");
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);
        serviceIntent.putExtra("extra", myString);
        context.startService(serviceIntent);
    }
}
