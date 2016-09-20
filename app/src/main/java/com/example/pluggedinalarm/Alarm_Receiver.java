package com.example.pluggedinalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by azimo123 on 20/09/16.
 */
public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String my_string = intent.getExtras().getString("extra");

        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        service_intent.putExtra("extra", my_string);

        context.startService(service_intent);

    }
}
