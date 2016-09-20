/**
package com.example.pluggedinalarm;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;


public class BatterySatus {

    private boolean isCharging() {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
    }

}
*/