/**
package com.example.pluggedinalarm;

import android.content.Intent;
import android.content.IntentFilter;


public class BatteryStatus {

    private boolean isCharging () {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);

        int status = batteryStatus.getIntExtra(android.os.BatteryManager.EXTRA_STATUS, -1);
        return status == android.os.BatteryManager.BATTERY_STATUS_CHARGING ||
                status == android.os.BatteryManager.BATTERY_STATUS_FULL;
    }


}
*/